package com.krt.common.constant;


/**
 * 系统全局常量
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年8月1日
 */
public class GlobalConstant {

    /**
     * 超级管理员角色code
     */
    public static final String ADMIN = "admin";

    /**
     * 定时器操作
     */
    public static final String SYSTEM = "system";


    /**
     * 重定向
     */
    public static final String REDIRECT = "redirect";

    /**
     * session用户key
     */
    public static final String SESSION_USER = "sessionUser";

    /**
     * 分页起始数
     */
    public static final String START = "start";

    /**
     * 每页显示数量
     */
    public static final String LENGTH = "length";

    /**
     * 逗号
     */
    public static final String COMMA = ",";

    /**
     * 点分隔符
     */
    public static final String POINT = ".";

    /**
     * 井号
     */
    public static final String WELL_NUMBER = "#";

    /**
     * 冒号
     */
    public static final String COLON = ":";

    /**
     * 默认父id
     */
    public static final Integer DEFAULT_PID = 0;

    /**
     * 验证码key
     */
    public final static String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";
    /**
     * 验证码ticket
     */
    public final static String KAPTCHA_SESSION_TICKET = "KAPTCHA_SESSION_TICKET";

    /**
     * 数据权限
     */
    public static final String DATA_SCOPE = "dataScope";

    /**
     * 日志常量
     */
    public static class LogType {

        /**
         * 登录日志类别
         */
        public static final int LOGIN = 0;

        /**
         * 操作日志类别
         */
        public static final int OPERATE = 1;

        /**
         * 异常日志类别
         */
        public static final int EXCEPTION = 2;

        /**
         * 定时任务
         */
        public static final int QUARTZ = 3;
    }

    /**
     * 未预缴
     */
    public static String IF_ADVANCE_NO ="0";

    /**
     * 已预缴
     */
    public static String IF_ADVANCE_YES ="1";

}
