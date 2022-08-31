package com.krt.common.annotation;

import com.krt.common.constant.GlobalConstant;

import java.lang.annotation.*;


/**
 * 自定义日志注解
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年06月15日
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KrtLog {

    String value() default "";

    /**
     * 0：登录日志 1：操作日志 :2 异常日志 3:定时任务
     */
    int type() default GlobalConstant.LogType.OPERATE;

    /**
     * 是否记录参数 true 记录 false 不记录
     */
    boolean para() default true;

}
