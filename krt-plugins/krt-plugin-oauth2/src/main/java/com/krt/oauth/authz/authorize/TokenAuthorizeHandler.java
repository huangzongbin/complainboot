package com.krt.oauth.authz.authorize;

import com.alibaba.fastjson.JSON;
import com.krt.common.bean.ReturnBean;
import com.krt.common.util.ServletUtils;
import com.krt.oauth.authz.OauthxRequest;
import com.krt.oauth.authz.base.AbstractAuthorizeHandler;
import com.krt.oauth.authz.validator.AbstractClientDetailsValidator;
import com.krt.oauth.authz.validator.TokenClientDetailsValidator;
import com.krt.oauth.entity.ClientDetails;
import com.krt.oauth.entity.OauthToken;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * response_type = 'token'请求控制器
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
public class TokenAuthorizeHandler extends AbstractAuthorizeHandler {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthorizeHandler.class);


    public TokenAuthorizeHandler(OauthxRequest oauthRequest, HttpServletResponse response) {
        super(oauthRequest, response);
    }

    @Override
    protected AbstractClientDetailsValidator getValidator() {
        return new TokenClientDetailsValidator(oauthRequest, false);
    }

    /**
     * 处理响应
     *
     * @throws OAuthSystemException
     * @throws IOException
     */
    @Override
    protected void handleResponse() throws OAuthSystemException, IOException {
        if (forceNewAccessToken()) {
            forceTokenResponse();
        } else {
            final ClientDetails clientDetails = clientDetails();
            OauthToken oauthToken = oauthService.doAccessToken(clientDetails, oauthRequest.getScopes(), false);
            if (oauthToken.accessTokenExpired(clientDetails)) {
                expiredTokenResponse(oauthToken);
            } else {
                normalTokenResponse(oauthToken);
            }
        }
    }

    private void forceTokenResponse() throws OAuthSystemException, IOException {
        OauthToken accessToken = oauthService.doNewAccessToken(clientDetails(), oauthRequest.getScopes());
        normalTokenResponse(accessToken);
    }

    /**
     * url返回token
     *
     * @param accessToken
     * @throws OAuthSystemException
     * @throws IOException
     */
    private void normalTokenResponse(OauthToken accessToken) throws OAuthSystemException, IOException {
        final ReturnBean returnBean = createTokenResponse(accessToken, true);
        logger.debug("'token' response: {}", returnBean);
        response.sendRedirect(returnBean.getData().toString());
    }

    /**
     * AccessToken 过期返回
     * @param oauthToken
     * @throws OAuthSystemException
     */
    private void expiredTokenResponse(OauthToken oauthToken) throws OAuthSystemException {
        logger.debug("AccessToken {} is expired", oauthToken);
        ServletUtils.printJson(response, JSON.toJSON(ReturnBean.error("access_token '" + oauthToken.getAccessToken() + "' 已过期")));
    }

    private boolean forceNewAccessToken() {
        final ClientDetails clientDetails = clientDetails();
        if (clientDetails.getTrusted()) {
            return userFirstLogged;
        } else {
            return userFirstApproved;
        }
    }
}
