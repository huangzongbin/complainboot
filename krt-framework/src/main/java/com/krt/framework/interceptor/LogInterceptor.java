package com.krt.framework.interceptor;

import com.krt.common.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * 日志拦截器
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年03月29日
 */
@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<Long> START_TIME_THREAD_LOCAL = new NamedThreadLocal<Long>("ThreadLocal Log StartTime") {
        @Override
        protected Long initialValue() {
            log.debug("ThreadLocal Log StartTime initialValue");
            return System.currentTimeMillis();

        }
    };

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
        if (log.isDebugEnabled()) {
            //1、开始时间
            long beginTime = System.currentTimeMillis();
            //线程绑定变量（该数据只有当前请求的线程可见）
            START_TIME_THREAD_LOCAL.set(beginTime);
            log.debug("开始计时: {}  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS").format(beginTime), httpServletRequest.getServletPath());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
        if (modelAndView != null) {
            log.debug("返回视图: {}", modelAndView.getViewName());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        try {
            // 打印JVM信息。
            if (log.isDebugEnabled()) {
                //得到线程绑定的局部变量（开始时间）
                long beginTime = START_TIME_THREAD_LOCAL.get();
                //2、结束时间
                long endTime = System.currentTimeMillis();
                log.debug("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
                        new SimpleDateFormat("hh:mm:ss.SSS").format(endTime), DateUtils.timeStampToDate(endTime - beginTime),
                        httpServletRequest.getServletPath(), Runtime.getRuntime().maxMemory() / 1024 / 1024, Runtime.getRuntime().totalMemory() / 1024 / 1024, Runtime.getRuntime().freeMemory() / 1024 / 1024,
                        (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);
            }
        } finally {
            //删除线程变量中的数据，防止内存泄漏
            START_TIME_THREAD_LOCAL.remove();
        }
    }
}
