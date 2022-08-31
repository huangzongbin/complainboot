package com.krt.common.datascope;

import org.springframework.core.NamedThreadLocal;

/**
 * 数据权限工具类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2019年01月02日
 */
public class DataScopeHelper {

    /**
     * 线程变量
     */
    private static final ThreadLocal<Boolean> IF_DATA_SCOPE = new NamedThreadLocal<>("ThreadLocal DataScope");

    /**
     * 开启数据权限
     */
    public static void start() {
        IF_DATA_SCOPE.set(true);
    }

    /**
     * 获取是否开启数据权限
     */
    public static boolean get() {
        return IF_DATA_SCOPE.get() == null ? false : IF_DATA_SCOPE.get();
    }

    /**
     * 关闭数据权限
     */
    public static void remove() {
        IF_DATA_SCOPE.remove();
    }
}
