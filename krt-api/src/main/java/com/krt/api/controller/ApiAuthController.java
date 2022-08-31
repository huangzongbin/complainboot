package com.krt.api.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.krt.api.dto.UserDTO;
import com.krt.common.annotation.KrtIgnoreAuth;
import com.krt.common.base.BaseController;
import com.krt.common.bean.ReturnBean;
import com.krt.common.bean.ReturnCode;
import com.krt.common.util.ServletUtils;
import com.krt.common.validator.Assert;
import com.krt.oauth.authz.OauthTokenxRequest;
import com.krt.oauth.authz.OauthxRequest;
import com.krt.oauth.authz.authorize.CodeAuthorizeHandler;
import com.krt.oauth.authz.authorize.TokenAuthorizeHandler;
import com.krt.oauth.authz.dispatcher.OauthTokenHandleDispatcher;
import com.krt.oauth.entity.ClientDetails;
import com.krt.oauth.entity.OauthToken;
import com.krt.oauth.service.IClientDetailsService;
import com.krt.oauth.service.IOauthTokenService;
import com.krt.rent.entity.RentHouse;
import com.krt.rent.enums.HouseTypeEnum;
import com.krt.rent.service.IRentHouseService;
import com.krt.sys.entity.User;
import com.krt.sys.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 认证控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月30日
 */
@Slf4j
@Controller
@RequestMapping("api/oauth")
@Api(tags = "1、认证API")
public class ApiAuthController extends BaseController {

    @Autowired
    private IClientDetailsService clientDetailsService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOauthTokenService oauthTokenService;

    @Autowired
    private IRentHouseService rentHouseService;

    /**
     * 用于页面跳转 请求格式如下:
     * grant_type="authorization_code" -> response_type="code"
     * http://localhost:8080/krt/oauth/authorize?response_type=code&scope=read&client_id=mobile-client&redirect_uri=http://www.baidu.com&state=1
     * <p/>
     * grant_type="implicit"   -> response_type="token"
     * oauth/authorize?response_type=token&scope=read,write&client_id=[client_id]&client_secret=[client_secret]&redirect_uri=[redirect_uri]
     * http://localhost:8080/krt/oauth/authorize?response_type=token&scope=read&client_id=mobile-client&client_secret=mobile&redirect_uri=http://www.baidu.com&state=1
     * <p/>
     */
    @KrtIgnoreAuth
    @PostMapping("authorize")
    @ApiOperation(
            value = "授权",
            notes = "用于页面跳转 请求格式如下:</br>"
                    + "response_type=code</br>"
                    + "response_type=code&client_id=[client_id]&redirect_uri=[redirect_uri]</br>"
                    + "response_type=token</br>"
                    + "response_type=token&client_id=[client_id]&client_secret=[client_secret]&redirect_uri=[redirect_uri]</br>"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "response_type", value = "影响类型:code || token", required = true),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "client_id", value = "客户端id", required = true),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "client_secret", value = "客户端secret", required = true),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "redirect_uri", value = "重定向url", required = true)
    })
    public void authorize() throws OAuthSystemException, ServletException, IOException {
        try {
            OauthxRequest oauthRequest = new OauthxRequest(request);
            if (oauthRequest.isCode()) {
                CodeAuthorizeHandler codeAuthorizeHandler = new CodeAuthorizeHandler(oauthRequest, response);
                log.debug("处理response_type = 'code' handler: {}", codeAuthorizeHandler);
                codeAuthorizeHandler.handle();
            } else if (oauthRequest.isToken()) {
                TokenAuthorizeHandler tokenAuthorizeHandler = new TokenAuthorizeHandler(oauthRequest, response);
                log.debug("处理response_type = 'token' handler: {}", tokenAuthorizeHandler);
                tokenAuthorizeHandler.handle();
            }
        } catch (OAuthProblemException e) {
            log.debug("认证异常", e);
            ReturnBean returnBean = ReturnBean.error(e.getDescription());
            ServletUtils.printJson(response, JSON.toJSONString(returnBean));
        }

    }


    /**
     * 获取token(一般用于接口模式) 支持4种协议
     * <p>
     * grant_type=authorization_code
     * http://localhost:8080/krt/oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=authorization_code&code=zLl170&redirect_uri=http://www.baidu.com
     * <p/>
     * grant_type=password
     * http://localhost:8080/krt/oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=password&scope=read&username=admin&password=123456
     * <p/>
     * grant_type=refresh_token
     * http://localhost:8080/krt/oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=refresh_token&refresh_token=3870d0dca232c821ae87084789ef16fe
     * <p/>
     * grant_type=client_credentials
     * http://localhost:8080/krt/oauth/token?mobile-client&client_secret=mobile&grant_type=client_credentials&scope=read write
     *
     * @throws OAuthSystemException 异常
     */
    @KrtIgnoreAuth
    @PostMapping("token")
    @ApiOperation(
            value = "获取token",
            notes = "grant_type=authorization_code 参数:</br>"
                    + "grant_type=authorization_code&client_id=[client_id]&client_secret=[client_secret]&code=[code]&redirect_uri=[redirect_uri]</br>"
                    + "grant_type=password 参数:</br>"
                    + "grant_type=password&client_id=[client_id]&client_secret=[client_secret]&username=[username]&password=[password]</br>"
                    + "grant_type=refresh_token 参数:</br>"
                    + "grant_type=refresh_token&client_id=[client_id]&client_secret=[client_secret]&refresh_token=[refresh_token]</br>"
                    + "grant_type=client_credentials 参数:</br>"
                    + "grant_type=client_credentials&client_id=[client_id]&client_secret=[client_secret]"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "grant_type", value = "类型:authorization_code || password || refresh_token || client_credentials", required = true),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "client_id", value = "客户端id", required = true),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "client_secret", value = "客户端secret", required = true),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "code", value = "授权编码"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "redirect_uri", value = "重定向url"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "username", value = "用户账号"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "password", value = "用户密码"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "refresh_token", value = "refresh_token"),
    })
    public void token() throws OAuthSystemException {
        try {
            //封装请求
            OauthTokenxRequest tokenRequest = new OauthTokenxRequest(request);
            //添加调度器链
            OauthTokenHandleDispatcher tokenHandleDispatcher = new OauthTokenHandleDispatcher(tokenRequest, response);
            //执行
            tokenHandleDispatcher.dispatch();
            log.info("dddddd");
        } catch (OAuthProblemException e) {
            log.debug("获取token异常", e);
            ReturnBean returnBean = ReturnBean.error(e.getDescription());
            ServletUtils.printJson(response, JSON.toJSONString(returnBean));
        }
    }


    /**
     * 检测token
     */
    @PostMapping("validateToken")
    @ApiOperation(
            value = "验证接口token",
            notes = "后台检测token"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "accessToken", name = "accessToken", value = "鉴权token", required = true),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "clientId", value = "客户端id", required = true)
    })
    @ResponseBody
    public ReturnBean validateToken(@RequestHeader String accessToken, String clientId) {
        ClientDetails clientDetails = clientDetailsService.selectByClientId(clientId);
        if (clientDetails != null) {
            OauthToken oauthToken = oauthTokenService.selectAccessToken(accessToken);
            //token为空
            if(oauthToken==null){
                return ReturnBean.error(ReturnCode.ACCESS_TOKEN_ERROR);
            }else{
                //token过期
                if(oauthToken.accessTokenExpired(clientDetails)){
                    return ReturnBean.error(ReturnCode.ACCESS_TOKEN_EXPIRE);
                }else{
                    // 账号密码登录 返回用户实体
                    if (!oauthToken.getClientId().equals(oauthToken.getUsername())) {
                        User user = userService.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, oauthToken.getUsername()));
                        //根据需求封装VO
                        UserDTO userVO = new UserDTO();
                        BeanUtils.copyProperties(user, userVO);
                        return ReturnBean.ok(userVO);
                    } else {
                        //客户端登录
                        return ReturnBean.ok();
                    }
                }
            }
        } else {
            return ReturnBean.error(ReturnCode.INVALID_REQUEST.getCode(), "client_id错误");
        }

    }
}
