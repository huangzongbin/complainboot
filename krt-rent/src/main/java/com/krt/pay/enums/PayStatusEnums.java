package com.krt.pay.enums;

/**
 * @author zhangdb
 * @date 2019/6/14 15:22
 */
public enum  PayStatusEnums {

    /**
     * 交易成功
     */
    JXCG("1"),

    /**
     * 待支付
     */
    DZF("2"),

    /**
     * 已取消
     */
    YQX("4"),

    /**
     * 等待用户输入密码确认
     */
    DDYHSRMMQR("9");

    private String value;

    PayStatusEnums(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
