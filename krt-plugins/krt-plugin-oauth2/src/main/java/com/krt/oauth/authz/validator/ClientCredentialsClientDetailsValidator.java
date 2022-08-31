package com.krt.oauth.authz.validator;

import com.krt.common.bean.ReturnBean;
import com.krt.oauth.authz.OauthTokenxRequest;
import com.krt.oauth.entity.ClientDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 * 客户端模式规则验证
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
@Slf4j
public class ClientCredentialsClientDetailsValidator extends AbstractOauthTokenValidator {

    public ClientCredentialsClientDetailsValidator(OauthTokenxRequest oauthRequest) {
        super(oauthRequest);
    }


    /**
     * 规则验证
     * /oauth/token?client_id=credentials-client&client_secret=credentials-secret&grant_type=client_credentials&scope=read write
     *
     * @param clientDetails
     * @throws OAuthSystemException
     */
    @Override
    protected ReturnBean validateSelf(ClientDetails clientDetails) throws OAuthSystemException {
        //验证grant_type
        final String grantType = ((OauthTokenxRequest) oauthRequest).getGrantType();
        log.debug("clientDetails:{}", clientDetails);
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

        return null;
    }


}
