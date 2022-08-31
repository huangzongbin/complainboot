package com.krt.oauth.service;

import com.krt.oauth.entity.ClientDetails;
import com.krt.oauth.entity.OauthCode;
import com.krt.oauth.entity.OauthToken;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.util.Set;

/**
 * 认证接口
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
public interface IOauthService {

    /**
     * 添加认证code并返回code
     *
     * @param clientDetails 客户端详情
     * @return 认证code
     * @throws OAuthSystemException
     */
    String insertAuthCode(ClientDetails clientDetails) throws OAuthSystemException;

    /**
     * 获取token 没有就创建
     *
     * @param clientDetails
     * @param scopes
     * @param includeRefreshToken
     * @return OauthToken
     * @throws OAuthSystemException
     */
    OauthToken doAccessToken(ClientDetails clientDetails, Set<String> scopes, boolean includeRefreshToken) throws OAuthSystemException;

    /**
     * 获取token 总是创建新的
     *
     * @param clientDetails
     * @param scopes
     * @return OauthToken
     * @throws OAuthSystemException
     */
    OauthToken doNewAccessToken(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException;

    /**
     * 查询客户端详情
     *
     * @param clientId
     * @return ClientDetails
     */
    ClientDetails selectClientById(String clientId);

    /**
     * 删除AuthCode
     *
     * @param code
     * @param clientDetails
     */
    void deleteAuthCode(String code, ClientDetails clientDetails);

    /**
     * 根据AuthCode获取accessToken
     *
     * @param clientDetails
     * @param code
     * @return OauthToken
     * @throws OAuthSystemException
     */
    OauthToken doAccessTokenByAuthCode(ClientDetails clientDetails, String code) throws OAuthSystemException;

    /**
     * 创建token grant_type=client_credentials
     *
     * @param clientDetails
     * @param scopes
     * @return OauthToken
     * @throws OAuthSystemException
     */
    OauthToken doAccessTokenByClient(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException;

    /**
     * 创建token grant_type=password
     *
     * @param clientDetails
     * @param scopes
     * @param username
     * @return OauthToken
     * @throws OAuthSystemException
     */
    OauthToken doAccessTokenByPassword(ClientDetails clientDetails, Set<String> scopes, String username) throws OAuthSystemException;

    /**
     * 根据refreshToken 刷新accessToken
     *
     * @param refreshToken
     * @param clientId
     * @return OauthToken
     * @throws OAuthSystemException
     */
    OauthToken doAccessTokenByRefreshToken(String refreshToken, String clientId) throws OAuthSystemException;

    /**
     * 查询AuthCode
     *
     * @param code
     * @param clientDetails
     * @return OauthToken
     */
    OauthCode selectAuthCode(String code, ClientDetails clientDetails);

    /**
     * 根据refreshToken 和 客户端id 查询token
     *
     * @param refreshToken
     * @param clientId
     * @return OauthToken
     */
    OauthToken selectAccessTokenByRefreshToken(String refreshToken, String clientId);

    /**
     * 删除 oauthToken
     *
     * @param oauthToken
     */
    void deleteAccessToken(OauthToken oauthToken);
}
