package com.krt.oauth.authz;

import org.apache.oltu.oauth2.common.message.OAuthResponse;

/**
 * 扩展默认的 OAuthResponse ,  增加必要的方法
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
public class OauthxResponse extends OAuthResponse {

    protected OauthxResponse(String uri, int responseStatus) {
        super(uri, responseStatus);
    }

    @Override
    public String getBody() {
        String body = super.getBody();
        return body;
    }
}
