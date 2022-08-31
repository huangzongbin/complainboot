package com.krt.oauth.authz.handler;

import com.alibaba.fastjson.JSON;
import com.krt.common.bean.ReturnBean;
import com.krt.common.util.ServletUtils;
import com.krt.oauth.authz.OauthTokenxRequest;
import com.krt.oauth.authz.base.AbstractOauthTokenHandler;
import com.krt.oauth.authz.validator.AbstractOauthTokenValidator;
import com.krt.oauth.authz.validator.AuthorizationCodeClientDetailsValidator;
import com.krt.oauth.entity.OauthToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.stereotype.Component;

/**
 * 授权码模式（authorization code）处理器
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
@Slf4j
@Component
public class AuthorizationCodeTokenHandler extends AbstractOauthTokenHandler {

    /**
     * 判断是否支持模式
     *
     * @param tokenRequest
     */
    @Override
    public boolean support(OauthTokenxRequest tokenRequest) {
        final String grantType = tokenRequest.getGrantType();
        return GrantType.AUTHORIZATION_CODE.toString().equalsIgnoreCase(grantType);
    }

    /**
     * 规则验证通过后处理
     * 请求url:/oauth/token?client_id=unity-client&client_secret=unity&grant_type=authorization_code&code=zLl170&redirect_uri=redirect_uri
     *
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    @Override
    public synchronized void  handleAfterValidation() throws OAuthSystemException {
        //1、返回新的accessToken
        responseToken();
        //2、清除authorization_code 保证authorization_code只能用一次
        removeCode();
    }

    /**
     * 移除authorization_code
     */
    private void removeCode() {
        final String code = tokenRequest.getCode();
        oauthService.deleteAuthCode(code, clientDetails());
        log.debug("Remove code: {}", code);
    }

    /**
     * 返回accessToken
     *
     * @throws OAuthSystemException
     */
    private void responseToken() throws OAuthSystemException {
        OauthToken oauthToken = oauthService.doAccessTokenByAuthCode(clientDetails(), tokenRequest.getCode());
        final ReturnBean returnBean = createTokenResponse(oauthToken, false);
        Object rbJson = JSON.toJSONString(returnBean);
        log.debug("授权码模式响应：{}", rbJson);
        ServletUtils.printJson(response, rbJson);
    }

    /**
     * 模式具体规则判断
     */
    @Override
    protected AbstractOauthTokenValidator getValidator() {
        return new AuthorizationCodeClientDetailsValidator(tokenRequest);
    }

}
