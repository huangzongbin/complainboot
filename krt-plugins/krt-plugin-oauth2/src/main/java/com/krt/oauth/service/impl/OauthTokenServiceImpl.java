package com.krt.oauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.krt.common.base.BaseServiceImpl;
import com.krt.oauth.entity.OauthToken;
import com.krt.oauth.mapper.OauthTokenMapper;
import com.krt.oauth.service.IOauthTokenService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 认证token接口实现层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月27日
 */
@Service
public class OauthTokenServiceImpl extends BaseServiceImpl<OauthTokenMapper, OauthToken> implements IOauthTokenService {

    /**
     * 查询AccessToken
     *
     * @param clientId         客户端
     * @param username         用户名
     * @param authenticationId 认证id
     */
    @Override
    @Cacheable(value = "accessTokenCache", key = "#clientId+':'+#username+':'+#authenticationId", unless = "#result == null")
    public OauthToken selectAccessToken(String clientId, String username, String authenticationId) {
        LambdaQueryWrapper<OauthToken> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OauthToken::getClientId, clientId)
                .eq(OauthToken::getUsername, username)
                .eq(OauthToken::getAuthenticationId, authenticationId);
        return selectOne(queryWrapper);
    }

    /**
     * 查询AccessToken
     */
    @Override
    @Cacheable(value = "accessTokenCache", key = "#accessToken", unless = "#result == null")
    public OauthToken selectAccessToken(String accessToken) {
        LambdaQueryWrapper<OauthToken> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OauthToken::getAccessToken, accessToken);
        return selectOne(queryWrapper);
    }

    /**
     * 查询AccessToken
     *
     * @param refreshToken 刷新token
     * @param clientId     客户端
     * @return OauthToken
     */
    @Override
    @Cacheable(value = "refreshTokenCache", key = "#refreshToken+':'+#clientId", unless = "#result == null")
    public OauthToken selectAccessTokenByRefreshToken(String refreshToken, String clientId) {
        LambdaQueryWrapper<OauthToken> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OauthToken::getClientId, clientId);
        queryWrapper.eq(OauthToken::getRefreshToken, refreshToken);
        return selectOne(queryWrapper);
    }
}
