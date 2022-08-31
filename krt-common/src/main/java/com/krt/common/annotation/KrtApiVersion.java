package com.krt.common.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;


/**
 * API接口版本
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年04月11日
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface KrtApiVersion {
    /**
     * @return 版本号
     */
    int value() default 0;

    boolean deprecated() default false;
}