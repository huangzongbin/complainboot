package com.krt.oauth.config;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * oauth2.0 认证
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月28日
 */
@Configuration
public class OauthConfig {

    /**
     * md5加密
     */
    @Bean
    public MD5Generator md5Generator() {
        return new MD5Generator();
    }

    /**
     * 使用MD5 OAuthIssuer, 生成随机值,如 access_token, refresh_token
     * 可根据需要扩展使用其他的实现
     *
     * @param md5Generator
     */
    @Bean("oAuthIssuer")
    public OAuthIssuerImpl oAuthIssuer(MD5Generator md5Generator) {
        return new OAuthIssuerImpl(md5Generator);
    }
}
