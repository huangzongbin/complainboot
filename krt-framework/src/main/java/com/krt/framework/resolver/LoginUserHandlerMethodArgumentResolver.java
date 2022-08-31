package com.krt.framework.resolver;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.krt.common.annotation.KrtLoginUser;
import com.krt.framework.interceptor.AuthorizationInterceptor;
import com.krt.sys.entity.User;
import com.krt.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 有@KrtLoginUser注解的方法参数，注入当前登录用户
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年04月28日
 */
@Slf4j
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private IUserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(User.class) && parameter.hasParameterAnnotation(KrtLoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request, WebDataBinderFactory factory) {
        //获取用户名
        Object object = request.getAttribute(AuthorizationInterceptor.LOGIN_USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if (object == null) {
            return null;
        }
        //获取用户信息
        return userService.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, object));
    }
}
