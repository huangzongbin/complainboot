package com.krt.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 胡国坚~
 * @date 2021/10/28 14:55
 * @description
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface KrtLock {

    /**
     * 设置锁的名称
     *
     * @return
     */
    String lockName();

    /**
     * 设置多少秒钟自动关闭锁  单位：毫秒
     * @return
     */
    long leaveTime() default 30000;


    /**
     * 设置等待获取锁的时间
     * @return
     */
    long waitTime() default 60000;


}
