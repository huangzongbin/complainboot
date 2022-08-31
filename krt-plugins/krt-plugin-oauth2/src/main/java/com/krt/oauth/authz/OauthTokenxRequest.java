package com.krt.oauth.authz;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import javax.servlet.http.HttpServletRequest;

/**
 * TokenRequest分装类，方便拓展
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
public class OauthTokenxRequest extends OAuthTokenRequest {


    public OauthTokenxRequest(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException {
        super(request);
    }

    public HttpServletRequest request() {
        return this.request;
    }
}
