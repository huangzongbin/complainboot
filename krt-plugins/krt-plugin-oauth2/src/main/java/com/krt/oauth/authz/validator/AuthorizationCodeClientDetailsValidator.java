package com.krt.oauth.authz.validator;

import com.krt.common.bean.ReturnBean;
import com.krt.oauth.authz.OauthTokenxRequest;
import com.krt.oauth.entity.ClientDetails;
import com.krt.oauth.entity.OauthCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 * AuthorizationCode模式规则验证
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
@Slf4j
public class AuthorizationCodeClientDetailsValidator extends AbstractOauthTokenValidator {

    public AuthorizationCodeClientDetailsValidator(OauthTokenxRequest tokenRequest) {
        super(tokenRequest);
    }

    /**
     * 规则验证
     *
     * @param clientDetails
     * @throws OAuthSystemException
     */
    @Override
    protected ReturnBean validateSelf(ClientDetails clientDetails) throws OAuthSystemException {

        //验证grant_type
        final String grantType = grantType();
        if (!clientDetails.getGrantTypes().contains(grantType)) {
            log.debug("验证grant_type '{}', client_id = '{}'", grantType, clientDetails.getClientId());
            return ReturnBean.error("grant_type错误");
        }

        //验证client_secret
        final String clientSecret = oauthRequest.getClientSecret();
        if (clientSecret == null || !clientSecret.equals(clientDetails.getClientSecret())) {
            log.debug("验证client_secret '{}', client_id = '{}'", clientSecret, clientDetails.getClientId());
            return ReturnBean.error("client_secret错误");
        }


        //验证redirect_uri
        final String redirectURI = oauthRequest.getRedirectURI();
        if (redirectURI == null || !redirectURI.equals(clientDetails.getRedirectUri())) {
            log.debug("验证redirect_uri'{}', client_id = '{}'", clientSecret, clientDetails.getClientId());
            return ReturnBean.error("redirect_uri错误");
        }

        //验证code
        String code = ((OauthTokenxRequest) oauthRequest).getCode();
        OauthCode oauthCode = oauthService.selectAuthCode(code, clientDetails());
        if (oauthCode == null) {
            log.debug("验证code '{}', client_id = '{}'", code, clientDetails.getClientId());
            return ReturnBean.error("code错误");
        }
        return null;
    }
}
