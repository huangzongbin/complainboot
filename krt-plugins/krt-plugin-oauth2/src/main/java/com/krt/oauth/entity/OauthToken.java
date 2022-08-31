package com.krt.oauth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.krt.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * 认证token实体类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月12日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("oauth_token")
public class OauthToken extends BaseEntity {


    public static final String BEARER_TYPE = "Bearer";
    /**
     * 默认的 refresh_token 的有效时长: 30天
     */
    public final static int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 30;

    /**
     * 默认的 access_token 的有效时长: 12小时
     */
    public final static int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 12;

    /**
     * 将毫秒转成秒
     */
    protected final static long THOUSAND = 1000L;

    /**
     * Bearer||Mac
     */
    private String tokenType;

    /**
     * 用户名
     */
    private String username;

    /**
     *
     */
    private String authenticationId;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * access_token
     */
    private String accessToken;

    /**
     * access_token有效期
     */
    private Integer accessTokenExpiredSeconds = ACCESS_TOKEN_VALIDITY_SECONDS;

    /**
     * refresh_token
     */
    private String refreshToken;

    /**
     * refresh_token有效期
     */
    private Integer refreshTokenExpiredSeconds = REFRESH_TOKEN_VALIDITY_SECONDS;


    /**
     * 判断accessToken 过期
     */
    public boolean accessTokenExpired(ClientDetails clientDetails) {
        final long time = getInsertTime().getTime() + (clientDetails.getAccessTokenValidity() * THOUSAND);
        return time < System.currentTimeMillis();
    }

    /**
     * 判断refreshToken 过期
     */
    public boolean refreshTokenExpired(ClientDetails clientDetails) {
        final long time = getInsertTime().getTime() + (clientDetails.getRefreshTokenValidity() * THOUSAND);
        return time < System.currentTimeMillis();
    }

    /**
     * 克隆token
     */
    public OauthToken cloneMe() {
        OauthToken oauthToken = new OauthToken();
        oauthToken.setUsername(username);
        oauthToken.setClientId(clientId);
        oauthToken.setTokenType(tokenType);
        return oauthToken;
    }

    /**
     * 根据details更新OauthToken
     *
     * @param details
     */
    public OauthToken updateByClientDetails(ClientDetails details) {
        this.clientId = details.getClientId();
        final Integer accessTokenValidity = details.getAccessTokenValidity();
        if (accessTokenValidity != null && accessTokenValidity > 0) {
            this.accessTokenExpiredSeconds = accessTokenValidity;
        }
        final Integer refreshTokenValidity = details.getRefreshTokenValidity();
        if (refreshTokenValidity != null && refreshTokenValidity > 0) {
            this.refreshTokenExpiredSeconds = refreshTokenValidity;
        }
        return this;
    }

}