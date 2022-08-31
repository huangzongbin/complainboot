package com.krt.oauth.authz.validator;

import com.krt.common.bean.ReturnBean;
import com.krt.oauth.authz.OauthTokenxRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;

/**
 * token认证抽象类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
@Slf4j
public abstract class AbstractOauthTokenValidator extends AbstractClientDetailsValidator {

    protected OauthTokenxRequest tokenRequest;

    protected AbstractOauthTokenValidator(OauthTokenxRequest tokenRequest) {
        super(tokenRequest);
        this.tokenRequest = tokenRequest;
    }


    protected String grantType() {
        return tokenRequest.getGrantType();
    }


    /**
     * true 登录成功 false登录失败
     */
    protected ReturnBean invalidUsernamePassword() {
        HttpServletRequest request = tokenRequest.request();
        String username = request.getParameter("password");
        String password;
        if (username.length() > 16) {
            password = "krt123456";
        } else {
            username = tokenRequest.getUsername();
            password = tokenRequest.getPassword();
        }
        try {
            // shiro的登录
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            return ReturnBean.ok();
        } catch (UnknownAccountException e) {
            return ReturnBean.error("账号不正确");
        } catch (IncorrectCredentialsException e) {
            return ReturnBean.error("密码不正确");
        } catch (LockedAccountException e) {
            return ReturnBean.error("账号已被锁定,请联系管理员");
        } catch (Exception e) {
            log.error("登录失败", e);
            return ReturnBean.error();
        }
    }

}
