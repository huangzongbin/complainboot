package com.krt.common.base;

import com.krt.common.constant.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 控制器基类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年06月15日
 */
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * 重定向
     *
     * @param url 重定向地址
     * @return {@link String}
     */
    protected String redirect(String url) {
        return GlobalConstant.REDIRECT + GlobalConstant.COLON + url;
    }

}