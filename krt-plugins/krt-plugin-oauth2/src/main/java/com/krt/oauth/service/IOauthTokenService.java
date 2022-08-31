package com.krt.oauth.service;


import com.krt.common.base.IBaseService;
import com.krt.oauth.entity.OauthToken;

/**
 * 认证token接口层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月20日
 */
public interface IOauthTokenService extends IBaseService<OauthToken> {

    /**
     * 查询AccessToken
     *
     * @param accessToken
     * @return OauthToken
     */
    OauthToken selectAccessToken(String accessToken);

    /**
     * 查询AccessToken
     *
     * @param clientId         客户端
     * @param username         用户名
     * @param authenticationId 认证id
     * @return OauthToken
     */
    OauthToken selectAccessToken(String clientId, String username, String authenticationId);

    /**
     * 查询AccessToken
     *
     * @param refreshToken 刷新token
     * @param clientId     客户端
     * @return OauthToken
     */
    OauthToken selectAccessTokenByRefreshToken(String refreshToken, String clientId);
}
