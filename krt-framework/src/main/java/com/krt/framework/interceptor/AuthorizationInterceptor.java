package com.krt.framework.interceptor;

import com.alibaba.fastjson.JSON;
import com.krt.common.annotation.KrtIgnoreAuth;
import com.krt.common.bean.ReturnBean;
import com.krt.common.bean.ReturnCode;
import com.krt.common.util.ServletUtils;
import com.krt.oauth.entity.ClientDetails;
import com.krt.oauth.entity.OauthToken;
import com.krt.oauth.service.IClientDetailsService;
import com.krt.oauth.service.IOauthTokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * API权限(Token)验证
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年04月28日
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private IOauthTokenService oauthTokenService;

    @Autowired
    private IClientDetailsService clientDetailsService;

    public static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        KrtIgnoreAuth krtIgnoreAuth;
        if (handler instanceof HandlerMethod) {
            krtIgnoreAuth = ((HandlerMethod) handler).getMethodAnnotation(KrtIgnoreAuth.class);
        } else {
            return true;
        }
        //从header中获取token
        String accessToken = request.getHeader("accessToken");
        //header获取不到 从参数获取
        if (accessToken == null) {
            accessToken = request.getParameter("accessToken");
        }
        //如果有@IgnoreAuth注解且忽略token参数，则不验证token
        if (krtIgnoreAuth != null && krtIgnoreAuth.ignoreToken()) {
            return true;
        } else if (krtIgnoreAuth != null && !krtIgnoreAuth.ignoreToken()) {
            //如果有@IgnoreAuth注解且不忽略token参数，有就验证 没有就不验证
            if (StringUtils.isBlank(accessToken)) {
                return true;
            }
        }
        //token为空
        if (StringUtils.isBlank(accessToken)) {
            ReturnCode rc = ReturnCode.ACCESS_TOKEN_NULL;
            ServletUtils.printJson(response, JSON.toJSONString(ReturnBean.error(rc)));
            return false;
        }
        //查询token信息
        OauthToken oauthToken = oauthTokenService.selectAccessToken(accessToken);
        if (oauthToken == null) {
            //oauthToken不存在
            ReturnCode rc = ReturnCode.ACCESS_TOKEN_ERROR;
            ServletUtils.printJson(response, JSON.toJSONString(ReturnBean.error(rc)));
            return false;
        } else {
            ClientDetails clientDetails = clientDetailsService.selectByClientId(oauthToken.getClientId());
            //token过期
            if (clientDetails != null && oauthToken.accessTokenExpired(clientDetails)) {
                ReturnCode rc = ReturnCode.ACCESS_TOKEN_EXPIRE;
                ServletUtils.printJson(response, JSON.toJSONString(ReturnBean.error(rc)));
                return false;
            }
            //客户端被禁用
            if (clientDetails == null) {
                ServletUtils.printJson(response, JSON.toJSONString(ReturnBean.error("客户端被禁用")));
                return false;
            }
        }
        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(LOGIN_USER_KEY, oauthToken.getUsername());
        return true;
    }
}
