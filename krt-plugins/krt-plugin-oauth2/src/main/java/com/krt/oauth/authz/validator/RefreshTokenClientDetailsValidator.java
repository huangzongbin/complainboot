package com.krt.oauth.authz.validator;

import com.krt.common.bean.ReturnBean;
import com.krt.oauth.authz.OauthTokenxRequest;
import com.krt.oauth.entity.ClientDetails;
import com.krt.oauth.entity.OauthToken;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 * 简化模式规则验证
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
public class RefreshTokenClientDetailsValidator extends AbstractOauthTokenValidator {

    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenClientDetailsValidator.class);


    public RefreshTokenClientDetailsValidator(OauthTokenxRequest oauthRequest) {
        super(oauthRequest);
    }


    /**
     * 规则验证
     * /oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=refresh_token&refresh_token=b36f4978-a172-4aa8-af89-60f58abe3ba1
     *
     * @param clientDetails
     * @throws OAuthSystemException
     */
    @Override
    protected ReturnBean validateSelf(ClientDetails clientDetails) throws OAuthSystemException {

        //验证grant_type
        final String grantType = ((OauthTokenxRequest) oauthRequest).getGrantType();
        if (!clientDetails.getGrantTypes().contains(grantType)) {
            logger.debug("验证grant_type '{}', client_id = '{}'", grantType, clientDetails.getClientId());
            return ReturnBean.error("grant_type错误");
        }

        //验证client_secret
        final String clientSecret = oauthRequest.getClientSecret();
        if (clientSecret == null || !clientSecret.equals(clientDetails.getClientSecret())) {
            logger.debug("验证client_secret '{}', client_id = '{}'", clientSecret, clientDetails.getClientId());
            return ReturnBean.error("client_secret错误");
        }

        //验证refresh_token
        final String refreshToken = tokenRequest.getRefreshToken();
        OauthToken oauthToken = oauthService.selectAccessTokenByRefreshToken(refreshToken, oauthRequest.getClientId());
        if (oauthToken == null || oauthToken.refreshTokenExpired(clientDetails)) {
            logger.debug("验证refresh_token: '{}'", refreshToken);
            return ReturnBean.error("refreshToken错误");
        }

        return null;
    }

}
