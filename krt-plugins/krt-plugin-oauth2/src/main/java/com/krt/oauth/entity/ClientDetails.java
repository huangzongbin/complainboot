package com.krt.oauth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.krt.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.oltu.oauth2.common.message.types.GrantType;


/**
 * 客户端详情实体类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月12日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("oauth_client_details")
public class ClientDetails extends BaseEntity {

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端秘钥
     */
    private String clientSecret;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 客户端url
     */
    private String clientUri;

    /**
     * 客户端图表连接
     */
    private String clientIconUri;

    /**
     * 客户端所拥有的资源ID,多个ID时使用逗号(,)分隔
     */
    private String resourceIds;

    /**
     * 客户端所支持的授权模式(grant_type),至少一个, 多个值时使用逗号(,)分隔, 如: password,refresh_token
     */
    private String grantTypes;

    /**
     * 客户端重定向url
     */
    private String redirectUri;

    /**
     * shiro 角色
     */
    private String roles;

    /**
     * access_token 的有效时长, 单位: 秒.
     */
    private Integer accessTokenValidity;

    /**
     * refresh_token的 有效时长, 单位: 秒
     */
    private Integer refreshTokenValidity;

    /**
     * 客户端描述
     */
    private String description;

    /**
     * 状态0：正常 1禁用
     */
    private Integer state;

    /**
     * 该 客户端是否为授信任的,若为信任的,, 则在 grant_type = authorization_code 时将跳过用户同意/授权 步骤
     */
    private Boolean trusted;

    /**
     * 是否支持RefreshToken
     */
    public boolean supportRefreshToken() {
        return this.grantTypes != null && this.grantTypes.contains(GrantType.REFRESH_TOKEN.toString());
    }

    /**
     * 是否支持ClientCredentials
     */
    public boolean supportClientCredentials() {
        return this.grantTypes != null && this.grantTypes.contains(GrantType.CLIENT_CREDENTIALS.toString());
    }

}