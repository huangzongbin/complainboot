package com.krt.sys.controller;

import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.ReturnBean;
import com.krt.common.constant.GlobalConstant;
import com.krt.common.session.SessionUser;
import com.krt.common.util.ShiroUtils;
import com.krt.common.util.ServletUtils;
import com.krt.common.validator.ValidatorUtils;
import com.krt.sys.dto.LoginFormDTO;
import com.krt.sys.entity.User;
import com.krt.sys.service.IRoleScopeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;

/**
 * 系统登录控制层
 *
 * @author 殷帅
 * @version 1.0
 */
@Slf4j
@Controller
public class LoginController extends BaseController {

    @Autowired
    private IRoleScopeService roleScopeService;

    /**
     * 是否开启验证码
     */
    @Value("${web.verify-code}")
    private boolean ifVerifyCode;

    /**
     * 登陆页
     *
     * @return {@link String}
     */
    @GetMapping("login")
    public String login() {
        // 判断session是否登录成功
        Subject subject = SecurityUtils.getSubject();
        Object o = ShiroUtils.getSessionAttribute(GlobalConstant.SESSION_USER);
        if (subject.isAuthenticated()) {
            //已登录直接跳转到首页
            return redirect("index");
        } else {
            if (ServletUtils.isAjax(request)) {
                //通知ajax session失效
                response.setHeader("Session-Status", "timeout");
            }
            return "login";
        }
    }

    /**
     * 用户登录
     *
     * @param loginFormDTO 用户登录
     * @return {@link ReturnBean}
     */
    @KrtLog(value = "后台登录", type = GlobalConstant.LogType.LOGIN, para = false)
    @PostMapping("login")
    @ResponseBody
    public ReturnBean login(LoginFormDTO loginFormDTO) {
        ValidatorUtils.validateEntity(loginFormDTO);
        try {
            //开启验证码
            if (ifVerifyCode) {
                String ticket = (String) ShiroUtils.getSessionAttribute(GlobalConstant.KAPTCHA_SESSION_TICKET);
                log.debug("验证码:{}", ticket);
                if (!loginFormDTO.getTicket().equalsIgnoreCase(ticket)) {
                    return ReturnBean.error("验证码错误");
                }else{
                    Objects.requireNonNull(ShiroUtils.getSession()).removeAttribute(GlobalConstant.KAPTCHA_SESSION_TICKET);
                }
            }
            // shiro的登录
            UsernamePasswordToken token = new UsernamePasswordToken(loginFormDTO.getUsername(), loginFormDTO.getPassword());
            Subject subject = SecurityUtils.getSubject();
            // 失败会抛出异常
            subject.login(token);
            //登录成功保存session
            User user = (User) subject.getPrincipal();
            SessionUser sessionUser = new SessionUser();
            BeanUtils.copyProperties(user, sessionUser);
            ShiroUtils.setSessionAttribute(GlobalConstant.SESSION_USER, sessionUser);
            Object o = ShiroUtils.getSessionAttribute(GlobalConstant.SESSION_USER);
            //数据权限
            if (!sessionUser.isAdmin()) {
                List<String> dataId = roleScopeService.selectUserDataScope(user);
                ShiroUtils.setSessionAttribute(GlobalConstant.DATA_SCOPE, dataId);
            }
            return ReturnBean.ok();
        } catch (UnknownAccountException e) {
            return ReturnBean.error("账号不正确,请检查后重新输入");
        } catch (IncorrectCredentialsException e) {
            return ReturnBean.error("密码不正确,请检查后重新输入");
        } catch (LockedAccountException e) {
            return ReturnBean.error("账号已被锁定,请联系管理员");
        }  catch (ExcessiveAttemptsException e) {
            return ReturnBean.error("登录失败多次，账户锁定10分钟");
        }catch (Exception e) {
            log.error("登录异常",e);
            return ReturnBean.error("程序异常,请联系管理员");
        }
    }

    /**
     * 退出
     *
     * @return {@link String}
     */
    @GetMapping("logout")
    public String logout() {
        Subject subject = ShiroUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "login";
    }

}
