package com.krt.oauth.authz.validator;

import com.krt.common.bean.ReturnBean;
import com.krt.oauth.authz.enums.StateEnums;
import com.krt.oauth.entity.ClientDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

/**
 * response_type = 'code'规则验证
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
@Slf4j
public class CodeClientDetailsValidator extends AbstractClientDetailsValidator {

    public CodeClientDetailsValidator(OAuthAuthzRequest oauthRequest) {
        super(oauthRequest);
    }

    /**
     * 规则验证
     * grant_type="authorization_code"
     * /oauth/authorize?response_type=code&scope=read,write&client_id=[client_id]&redirect_uri=[redirect_uri]&state=[state]
     *
     * @param clientDetails
     * @throws OAuthSystemException
     */
    @Override
    public ReturnBean validateSelf(ClientDetails clientDetails) throws OAuthSystemException {

        //验证redirect_uri
        final String redirectURI = oauthRequest.getRedirectURI();
        if (redirectURI == null || !redirectURI.equals(clientDetails.getRedirectUri())) {
            log.debug("Invalid redirect_uri '{}' by response_type = 'code', client_id = '{}'", redirectURI, clientDetails.getClientId());
            return ReturnBean.error("redirect_uri错误");
        }

        if (StateEnums.FORBIDDEN.getValue().equals(clientDetails.getState().toString())) {
            log.debug("client_id={} 已被禁用", oauthRequest.getClientId());
            return ReturnBean.error("客户端已被禁用");
        }

        return null;
    }

}
