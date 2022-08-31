package com.krt.oauth.authz.handler;

import com.alibaba.fastjson.JSON;
import com.krt.common.bean.ReturnBean;
import com.krt.common.util.ServletUtils;
import com.krt.oauth.authz.OauthTokenxRequest;
import com.krt.oauth.authz.base.AbstractOauthTokenHandler;
import com.krt.oauth.authz.validator.AbstractOauthTokenValidator;
import com.krt.oauth.authz.validator.RefreshTokenClientDetailsValidator;
import com.krt.oauth.entity.OauthToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.stereotype.Component;

/**
 * 刷新access_token处理器
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
@Slf4j
@Component
public class RefreshTokenHandler extends AbstractOauthTokenHandler {

    /**
     * 判断是否支持模式
     *
     * @param tokenRequest
     */
    @Override
    public boolean support(OauthTokenxRequest tokenRequest) {
        final String grantType = tokenRequest.getGrantType();
        return GrantType.REFRESH_TOKEN.toString().equalsIgnoreCase(grantType);
    }

    /**
     * 规则验证通过后处理
     * 请求url:/oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=refresh_token&refresh_token=b36f4978-a172-4aa8-af89-60f58abe3ba1
     *
     * @throws OAuthSystemException
     */
    @Override
    public synchronized void handleAfterValidation() throws OAuthSystemException {
        final String refreshToken = tokenRequest.getRefreshToken();
        OauthToken oauthToken = oauthService.doAccessTokenByRefreshToken(refreshToken, tokenRequest.getClientId());
        final ReturnBean returnBean = createTokenResponse(oauthToken, false);
        Object rbJson = JSON.toJSONString(returnBean);
        log.debug("简化模式响应：{}", rbJson);
        ServletUtils.printJson(response, rbJson);

    }

    @Override
    protected AbstractOauthTokenValidator getValidator() {
        return new RefreshTokenClientDetailsValidator(tokenRequest);
    }

}
