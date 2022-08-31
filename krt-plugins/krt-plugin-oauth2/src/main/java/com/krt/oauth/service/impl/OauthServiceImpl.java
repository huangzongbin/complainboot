package com.krt.oauth.service.impl;

import com.krt.common.util.ShiroUtils;
import com.krt.oauth.entity.ClientDetails;
import com.krt.oauth.entity.OauthCode;
import com.krt.oauth.entity.OauthToken;
import com.krt.oauth.service.IClientDetailsService;
import com.krt.oauth.service.IOauthCodeService;
import com.krt.oauth.service.IOauthService;
import com.krt.oauth.service.IOauthTokenService;
import com.krt.oauth.util.DefaultAuthenticationIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

/**
 * 认证接口
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
@Slf4j
@Service
public class OauthServiceImpl implements IOauthService {

    @Autowired
    private IOauthCodeService oauthCodeService;
    @Autowired
    private IOauthTokenService oauthTokenService;
    @Autowired
    private IClientDetailsService clientDetailsService;
    @Autowired
    private OAuthIssuer oAuthIssuer;
    @Autowired
    private DefaultAuthenticationIdGenerator defaultAuthenticationIdGenerator;

    /**
     * 查询客户端详情
     *
     * @param clientId 客户端id
     */
    @Override
    public ClientDetails selectClientById(String clientId) {
        return clientDetailsService.selectByClientId(clientId);
    }

    /**
     * 创建AuthCode
     *
     * @param clientDetails 客户端详情
     */
    @Override
    public  String insertAuthCode(ClientDetails clientDetails) throws OAuthSystemException {
        final String authCode = oAuthIssuer.authorizationCode();
        final String clientId = clientDetails.getClientId();
        final String username = ShiroUtils.getSessionUser().getUsername();
        OauthCode oauthCode = oauthCodeService.selectOauthCodeByUsernameClientId(username, clientId);
        if (oauthCode != null) {
            log.debug("OauthCode ({}) 存在，需要先删除再创建一个新的", oauthCode);
            oauthCodeService.deleteById(oauthCode.getId());
        }
        //创建新的认证code
        oauthCode = new OauthCode();
        oauthCode.setCode(authCode);
        oauthCode.setUsername(username);
        oauthCode.setClientId(clientDetails.getClientId());
        oauthCodeService.insert(oauthCode);
        return authCode;
    }

    /**
     * 删除AuthCode
     *
     * @param code          认证code
     * @param clientDetails 客户端详情
     */
    @Override
    public void deleteAuthCode(String code, ClientDetails clientDetails) {
        OauthCode oauthCode = oauthCodeService.selectOauthCodeByCodeAndClientId(code, clientDetails.getClientId());
        oauthCodeService.deleteById(oauthCode.getId());
    }

    /**
     * 查询AuthCode
     *
     * @param code
     * @param clientDetails
     */
    @Override
    public OauthCode selectAuthCode(String code, ClientDetails clientDetails) {
        OauthCode oauthCode = oauthCodeService.selectOauthCodeByCodeAndClientId(code, clientDetails.getClientId());
        return oauthCode;
    }

    /**
     * 获取token 没有就创建
     *
     * @param clientDetails       客户端详情
     * @param scopes              权限
     * @param includeRefreshToken 是否创建 RefreshToken
     */
    @Override
    public OauthToken doAccessToken(ClientDetails clientDetails, Set<String> scopes, boolean includeRefreshToken) throws OAuthSystemException {
        String scope = OAuthUtils.encodeScopes(scopes);
        final String username = ShiroUtils.getSessionUser().getUsername();
        final String clientId = clientDetails.getClientId();
        final String authenticationId = defaultAuthenticationIdGenerator.generate(clientId, username, scope);
        OauthToken oauthToken = oauthTokenService.selectAccessToken(clientId, username, authenticationId);
        if (oauthToken != null) {
            ((IOauthService) AopContext.currentProxy()).deleteAccessToken(oauthToken);
        }
        oauthToken = insertOauthToken(clientDetails, includeRefreshToken, username, authenticationId);
        log.debug("创建一个新的 AccessToken: {}", oauthToken);
        return oauthToken;
    }

    /**
     * 获取token 总是创建新的
     *
     * @param clientDetails 客户端详情
     * @param scopes        权限
     */
    @Override
    public OauthToken doNewAccessToken(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException {
        String scope = OAuthUtils.encodeScopes(scopes);
        final String username = ShiroUtils.getSessionUser().getUsername();
        final String clientId = clientDetails.getClientId();
        final String authenticationId = defaultAuthenticationIdGenerator.generate(clientId, username, scope);
        OauthToken oauthToken = oauthTokenService.selectAccessToken(clientId, username, authenticationId);
        if (oauthToken != null) {
            ((IOauthService) AopContext.currentProxy()).deleteAccessToken(oauthToken);
        }
        oauthToken = insertOauthToken(clientDetails, false, username, authenticationId);
        log.debug("创建一个新的 AccessToken: {}", oauthToken);
        return oauthToken;
    }

    /**
     * 根据AuthCode获取accessToken
     *
     * @param clientDetails 客户端详情
     * @param code          认证code
     */
    @Override
    public OauthToken doAccessTokenByAuthCode(ClientDetails clientDetails, String code) throws OAuthSystemException {
        final OauthCode oauthCode = oauthCodeService.selectOauthCodeByCodeAndClientId(code, clientDetails.getClientId());
        final String username = oauthCode.getUsername();
        final String clientId = clientDetails.getClientId();
        final String authenticationId = defaultAuthenticationIdGenerator.generate(clientId, username, null);
        OauthToken oauthToken = oauthTokenService.selectAccessToken(clientId, username, authenticationId);
        if (oauthToken != null) {
            ((IOauthService) AopContext.currentProxy()).deleteAccessToken(oauthToken);
        }
        oauthToken = insertOauthToken(clientDetails, clientDetails.supportRefreshToken(), username, authenticationId);
        log.debug("创建一个新的 AccessToken: {}", oauthToken);
        return oauthToken;
    }

    /**
     * 创建token grant_type=client_credentials
     *
     * @param clientDetails
     * @param scopes
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OauthToken doAccessTokenByClient(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException {
        String scope = OAuthUtils.encodeScopes(scopes);
        final String clientId = clientDetails.getClientId();
        final String authenticationId = defaultAuthenticationIdGenerator.generate(clientId, clientId, scope);
        OauthToken oauthToken = oauthTokenService.selectAccessToken(clientId, clientId, authenticationId);
        if (oauthToken == null) {
            log.debug("accessToken 不存在需要创建一个新的, client_id: {}", clientId);
        } else {
            log.debug("删除旧的accessToken: {} 再创建一个新的, client_id: {}", oauthToken.getAccessToken(), clientId);
            ((IOauthService) AopContext.currentProxy()).deleteAccessToken(oauthToken);
        }
        oauthToken = insertOauthToken(clientDetails, clientDetails.supportRefreshToken(), clientId, authenticationId);
        log.debug("创建一个新的 AccessToken: {}", oauthToken);
        return oauthToken;
    }

    /**
     * 创建token grant_type=password
     *
     * @param clientDetails
     * @param scopes
     * @param username
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OauthToken doAccessTokenByPassword(ClientDetails clientDetails, Set<String> scopes, String username) throws OAuthSystemException {
        String scope = OAuthUtils.encodeScopes(scopes);
        final String clientId = clientDetails.getClientId();
        final String authenticationId = defaultAuthenticationIdGenerator.generate(clientId, username, scope);
        OauthToken oauthToken = oauthTokenService.selectAccessToken(clientId, username, authenticationId);
        if (oauthToken == null) {
            log.debug("accessToken 不存在需要创建一个新的 , client_id: {}", clientId);
        } else {
            log.debug("删除旧的accessToken: {} 再创建一个新的, client_id: {}", oauthToken.getAccessToken(), clientId);
            ((IOauthService) AopContext.currentProxy()).deleteAccessToken(oauthToken);
        }
        oauthToken = insertOauthToken(clientDetails, clientDetails.supportRefreshToken(), username, authenticationId);
        log.debug("创建一个新的 AccessToken: {}", oauthToken);
        return oauthToken;
    }

    /**
     * 根据refreshToken 刷新accessToken
     *
     * @param refreshToken
     * @param clientId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OauthToken doAccessTokenByRefreshToken(String refreshToken, String clientId) throws OAuthSystemException {
        final OauthToken oldToken = oauthTokenService.selectAccessTokenByRefreshToken(refreshToken, clientId);
        OauthToken newToken = oldToken.cloneMe();
        log.debug("创建一个新的AccessToken: {} 克隆至旧的 AccessToken: {}", newToken.getAccessToken(), oldToken.getAccessToken());
        ClientDetails details = clientDetailsService.selectByClientId(clientId);
        newToken.updateByClientDetails(details);
        final String authId = defaultAuthenticationIdGenerator.generate(clientId, oldToken.getUsername(), null);
        newToken.setAuthenticationId(authId);
        newToken.setAccessToken(oAuthIssuer.accessToken());
        newToken.setRefreshToken(oAuthIssuer.refreshToken());
        ((IOauthService) AopContext.currentProxy()).deleteAccessToken(oldToken);
        log.debug("删除 old AccessToken: {}", oldToken);
        oauthTokenService.insert(newToken);
        log.debug("创建 new AccessToken: {}", newToken);
        return newToken;
    }


    /**
     * 根据refreshToken 和 客户端id 查询token
     *
     * @param refreshToken
     * @param clientId
     */
    @Override
    @Cacheable(value = "refreshTokenCache", key = "#refreshToken+':'+#clientId")
    public OauthToken selectAccessTokenByRefreshToken(String refreshToken, String clientId) {
        return oauthTokenService.selectAccessTokenByRefreshToken(refreshToken, clientId);
    }

    /**
     * 创建token
     *
     * @param clientDetails
     * @param includeRefreshToken
     * @param username
     * @param authenticationId
     * @throws OAuthSystemException
     */
    private OauthToken insertOauthToken(ClientDetails clientDetails, boolean includeRefreshToken, String username, String authenticationId) throws OAuthSystemException {
        OauthToken oauthToken = new OauthToken();
        oauthToken.setUsername(username);
        oauthToken.setTokenType(OauthToken.BEARER_TYPE);
        oauthToken.setClientId(clientDetails.getClientId());
        oauthToken.setAccessToken(oAuthIssuer.accessToken());
        oauthToken.setAuthenticationId(authenticationId);
        oauthToken.setClientId(clientDetails.getClientId());
        oauthToken.setInsertTime(new Date());
        oauthToken.setUpdateTime(new Date());
        oauthToken.setAccessTokenExpiredSeconds(clientDetails.getAccessTokenValidity());
        oauthToken.setRefreshTokenExpiredSeconds(clientDetails.getRefreshTokenValidity());
        if (includeRefreshToken) {
            oauthToken.setRefreshToken(oAuthIssuer.refreshToken());
        }
        oauthTokenService.insert(oauthToken);
        log.debug("保存 AccessToken: {}", oauthToken);
        return oauthToken;
    }


    /**
     * 删除accessToken
     *
     * @param oauthToken
     */
    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "accessTokenCache", key = "#oauthToken.accessToken"),
                    @CacheEvict(value = "accessTokenCache", key = "#oauthToken.clientId+':'+#oauthToken.username+':'+#oauthToken.authenticationId"),
                    @CacheEvict(value = "refreshTokenCache", key = "#oauthToken.refreshToken+':'+#oauthToken.clientId"),
            }
    )
    public void deleteAccessToken(OauthToken oauthToken) {
        oauthTokenService.deleteById(oauthToken.getId());
    }


}
