package com.krt.oauth.authz.dispatcher;

import com.krt.common.util.SpringUtils;
import com.krt.oauth.authz.OauthTokenxRequest;
import com.krt.oauth.authz.base.OauthTokenHandler;
import com.krt.oauth.authz.handler.AuthorizationCodeTokenHandler;
import com.krt.oauth.authz.handler.ClientCredentialsTokenHandler;
import com.krt.oauth.authz.handler.PasswordTokenHandler;
import com.krt.oauth.authz.handler.RefreshTokenHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Token 请求调度器
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
@Slf4j
public class OauthTokenHandleDispatcher {

    /**
     * 调度器链
     */
    private List<OauthTokenHandler> handlers = new ArrayList<>();

    /**
     * token请求
     */
    private final OauthTokenxRequest tokenRequest;

    /**
     * 响应
     */
    private final HttpServletResponse response;


    public OauthTokenHandleDispatcher(OauthTokenxRequest tokenRequest, HttpServletResponse response) {
        this.tokenRequest = tokenRequest;
        this.response = response;
        initialHandlers();
    }

    /**
     * 初始化调度器链
     */
    private void initialHandlers() {
        //添加授权码模式支持
        handlers.add(SpringUtils.getBean(AuthorizationCodeTokenHandler.class));
        //添加密码模式支持
        handlers.add(SpringUtils.getBean(PasswordTokenHandler.class));
        //添加刷新access_token模式支持
        handlers.add(SpringUtils.getBean(RefreshTokenHandler.class));
        //添加客户端模式支持
        handlers.add(SpringUtils.getBean(ClientCredentialsTokenHandler.class));
        log.debug("Initialed '{}' OAuthTokenHandler(s): {}", handlers.size(), handlers);
    }

    /**
     * 执行器
     *
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    public void dispatch() throws OAuthProblemException, OAuthSystemException {
        for (OauthTokenHandler handler : handlers) {
            //根据请求类型grantType执行
            if (handler.support(tokenRequest)) {
                log.debug("Found '{}' handle OAuthTokenxRequest: {}", handler, tokenRequest);
                handler.handle(tokenRequest, response);
                return;
            }
        }
        throw new IllegalStateException("没有相应的处理器处理该请求: " + tokenRequest);
    }
}
