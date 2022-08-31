package com.krt.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 注入Token绑定的用户
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年06月15日
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface KrtLoginUser {

}
