package com.krt.oauth.authz.validator;

import com.krt.common.bean.ReturnBean;
import com.krt.oauth.authz.OauthTokenxRequest;
import com.krt.oauth.entity.ClientDetails;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 密码模式规则验证
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
public class PasswordClientDetailsValidator extends AbstractOauthTokenValidator {

    private static final Logger logger = LoggerFactory.getLogger(PasswordClientDetailsValidator.class);

    public PasswordClientDetailsValidator(OauthTokenxRequest oauthRequest) {
        super(oauthRequest);
    }

    /**
     * 规则验证
     * /oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=password&scope=read,write&username=mobile&password=mobile
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

        //验证账号密码
        return invalidUsernamePassword();
    }

}
