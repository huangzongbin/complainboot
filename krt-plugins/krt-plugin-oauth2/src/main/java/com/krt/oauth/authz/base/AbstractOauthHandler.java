package com.krt.oauth.authz.base;

import com.krt.common.bean.ReturnBean;
import com.krt.common.util.SpringUtils;
import com.krt.oauth.entity.ClientDetails;
import com.krt.oauth.entity.OauthToken;
import com.krt.oauth.service.IOauthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * 对OAUTH各种流程的操作进行抽象
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
@Slf4j
public abstract class AbstractOauthHandler {


    /**
     * 认证服务层
     */
    protected transient IOauthService oauthService = SpringUtils.getBean(IOauthService.class);

    /**
     * 客户端
     */
    private ClientDetails clientDetails;

    /**
     * 获取客户端id
     *
     * @return 客户端id
     */
    protected abstract String clientId();

    /**
     * 获取客户端详情
     */
    protected ClientDetails clientDetails() {
        if (clientDetails == null) {
            final String clientId = clientId();
            clientDetails = oauthService.selectClientById(clientId);
            log.debug("客户端详情: {} by clientId: {}", clientDetails, clientId);
        }
        return clientDetails;
    }


    /**
     * 创建新的AccessToken
     *
     * @param oauthToken
     * @param queryOrJson 是通过url返回 还是json true url:重定向 false返回json
     * @throws OAuthSystemException
     */
    protected ReturnBean createTokenResponse(OauthToken oauthToken, boolean queryOrJson) throws OAuthSystemException {
        final ClientDetails tempClientDetails = clientDetails();
        final OAuthASResponse.OAuthTokenResponseBuilder builder = OAuthASResponse
                .tokenResponse(HttpServletResponse.SC_OK)
                .location(tempClientDetails.getRedirectUri())
                .setAccessToken(oauthToken.getAccessToken())
                .setExpiresIn(String.valueOf(oauthToken.getAccessTokenExpiredSeconds()))
                .setTokenType(oauthToken.getTokenType());
        final String refreshToken = oauthToken.getRefreshToken();
        if (StringUtils.isNotEmpty(refreshToken)) {
            builder.setRefreshToken(refreshToken);
        }
        if (queryOrJson) {
            log.debug("===============:{}", builder.buildQueryMessage().getHeaders());
            return ReturnBean.ok(builder.buildQueryMessage().getLocationUri());
        } else {
            Map map = new HashMap(3);
            map.put("access_token", oauthToken.getAccessToken());
            map.put("expires_in", String.valueOf(oauthToken.getAccessTokenExpiredSeconds()));
            if (StringUtils.isNotEmpty(refreshToken)) {
                map.put("refresh_token", oauthToken.getRefreshToken());
            }
            return ReturnBean.ok(map);
        }
    }

}
