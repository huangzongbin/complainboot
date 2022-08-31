package com.krt.oauth.authz.base;


import com.krt.oauth.authz.OauthTokenxRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import javax.servlet.http.HttpServletResponse;

/**
 * token请求控制器
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
public interface OauthTokenHandler {

    /**
     * 判断模式是否支持
     *
     * @param tokenRequest
     * @return true 支持 false不支持
     * @throws OAuthProblemException
     */
    boolean support(OauthTokenxRequest tokenRequest) throws OAuthProblemException;

    /**
     * 处理请求
     *
     * @param tokenRequest
     * @param response
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    void handle(OauthTokenxRequest tokenRequest, HttpServletResponse response) throws OAuthProblemException, OAuthSystemException;


}