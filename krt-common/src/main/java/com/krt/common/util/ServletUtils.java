package com.krt.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * servlet 访问工具类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年4月29日
 */
@Slf4j
public class ServletUtils {

    /**
     * 获取当前请求对象
     */
    public static HttpServletRequest getRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断是不是ajax访问
     */
    public static boolean isAjax(HttpServletRequest request) {
        //判断是不是ajax访问
        boolean isAjax = false;
        Enumeration<String> values = request.getHeaders("X-Requested-With");
        while (values.hasMoreElements()) {
            String value = values.nextElement();
            if ("XMLHttpRequest".equalsIgnoreCase(value)) {
                isAjax = true;
                break;
            }
        }
        return isAjax;
    }

    /**
     * response 响应
     *
     * @param response
     * @param object
     */
    public static void printJson(HttpServletResponse response, Object object){
        PrintWriter writer = null;
        try {
            response.setContentType("application/json;charset=utf-8");
            writer = response.getWriter();
            writer.print(object);
            writer.flush();
        }catch (Exception e){
            log.error("response 响应JSON失败",e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * response 响应
     *
     * @param response
     * @param object
     */
    public static void printText(HttpServletResponse response, Object object){
        PrintWriter writer = null;
        try {
            response.setContentType("text/html;charset=utf-8");
            writer = response.getWriter();
            writer.print(object);
            writer.flush();
        }catch (Exception e){
            log.error("response 响应Text失败",e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
