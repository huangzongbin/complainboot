package com.krt.oauth.authz.base;

import com.alibaba.fastjson.JSON;
import com.krt.common.bean.ReturnBean;
import com.krt.common.constant.GlobalConstant;
import com.krt.common.util.ShiroUtils;
import com.krt.common.util.ServletUtils;
import com.krt.common.validator.Assert;
import com.krt.oauth.authz.OauthxRequest;
import com.krt.oauth.authz.validator.AbstractClientDetailsValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.krt.oauth.contant.OauthContant.*;


/**
 * response_type = 'code' 处理器
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
@Slf4j
public abstract class AbstractAuthorizeHandler extends AbstractOauthHandler {


    protected OauthxRequest oauthRequest;
    protected HttpServletResponse response;

    protected boolean userFirstLogged = false;
    protected boolean userFirstApproved = false;


    public AbstractAuthorizeHandler(OauthxRequest oauthRequest, HttpServletResponse response) {
        this.oauthRequest = oauthRequest;
        this.response = response;
    }

    /**
     * 验证失败
     *
     * @throws OAuthSystemException
     */
    protected boolean validateFailed() throws OAuthSystemException {
        AbstractClientDetailsValidator validator = getValidator();
        log.debug("Use [{}] validate client: {}", validator, oauthRequest.getClientId());
        final ReturnBean returnBean = validator.validate();
        return checkAndResponseValidateFailed(returnBean);
    }

    /**
     * 获取验证实例
     *
     * @return 验证实例
     */
    protected abstract AbstractClientDetailsValidator getValidator();

    /**
     * 检测验证结果
     *
     * @param returnBean
     */
    protected boolean checkAndResponseValidateFailed(ReturnBean returnBean) {
        if (returnBean != null) {
            log.debug("Validate OAuthAuthzRequest(client_id={}) failed", oauthRequest.getClientId());
            ServletUtils.printJson(response, JSON.toJSONString(returnBean));
            return true;
        }
        return false;
    }

    @Override
    protected String clientId() {
        return oauthRequest.getClientId();
    }

    /**
     * 判断是否登录过
     */
    protected boolean isUserAuthenticated() {
        final Subject subject = SecurityUtils.getSubject();
        return subject.isAuthenticated();
    }

    /**
     * 是否需要登录
     */
    protected boolean isNeedUserLogin() {
        return !isUserAuthenticated() && !isPost();
    }


    protected boolean goLogin() throws ServletException, IOException {
        if (isNeedUserLogin()) {
            //go to login
            log.debug("Forward to Oauth login by client_id '{}'", oauthRequest.getClientId());
            final HttpServletRequest request = oauthRequest.request();
            request.getRequestDispatcher(OAUTH_LOGIN_VIEW).forward(request, response);
            return true;
        }
        return false;
    }


    /**
     * shiro登录逻辑
     *
     * @return true 登录失败 false登录成功
     * @throws ServletException
     * @throws IOException
     */
    protected boolean submitLogin() throws ServletException, IOException {
        if (isSubmitLogin()) {
            //login flow
            HttpServletRequest request = oauthRequest.request();
            try {
                //1、验证码
                String sessionVerifyCode = ShiroUtils.getKaptcha(GlobalConstant.KAPTCHA_SESSION_KEY);
                log.debug("验证码:{}", sessionVerifyCode);
                String verifyCode = request.getParameter(REQUEST_VERIFYCODE);
                if (!Assert.isBlank(verifyCode) && verifyCode.equalsIgnoreCase(sessionVerifyCode)) {
                    //2、登录
                    String username = request.getParameter(REQUEST_USERNAME);
                    String password = request.getParameter(REQUEST_PASSWORD);
                    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                    SecurityUtils.getSubject().login(token);
                    log.debug("登录成功");
                    this.userFirstLogged = true;
                    return false;
                } else {
                    log.debug("验证码错误，返回登录页");
                    request.setAttribute("verifyCode_error", true);
                    request.getRequestDispatcher(OAUTH_LOGIN_VIEW).forward(request, response);
                    return true;
                }
            } catch (Exception ex) {
                log.debug("登录失败，返回登录页", ex);
                request.setAttribute("oauth_login_error", true);
                request.getRequestDispatcher(OAUTH_LOGIN_VIEW).forward(request, response);
                return true;
            }
        }
        return false;
    }


    private boolean isSubmitLogin() {
        return !isUserAuthenticated() && isPost();
    }

    protected boolean isPost() {
        return RequestMethod.POST.name().equalsIgnoreCase(oauthRequest.request().getMethod());
    }

    /**
     * 处理器
     *
     * @throws OAuthSystemException
     * @throws ServletException
     * @throws IOException
     */
    public void handle() throws OAuthSystemException, ServletException, IOException {
        //验证请求是否合法，主要是针对参数做基本的校验，重定向链接，客户端ID授权范围等这些信息与注册的是否相同。
        if (validateFailed()) {
            return;
        }

        //判断用户是否登录过，根据session判断。因此多个应用使用同一个授权服务的话，是可以直接跳过登录步骤的也就实现了单点登录的效果。如果没有登录的话，这一步的请求会被重定向至登录页面。（登录也得隐藏域会带上这些参数）
        if (goLogin()) {
            return;
        }

        //这个请求如果是从登录页面提交过来的，那么就提交用户的登录，这个框架中交给shiro去做登录相关的操作。
        if (submitLogin()) {
            return;
        }

        //以上任意一步没有通过都是授权失败会进行相应处理，如果都通过了就发放Code码。
        handleResponse();
    }

    /**
     * 处理响应
     *
     * @throws OAuthSystemException
     * @throws IOException
     */
    protected abstract void handleResponse() throws OAuthSystemException, IOException;
}
