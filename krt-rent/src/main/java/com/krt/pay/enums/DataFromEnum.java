package com.krt.pay.enums;

/**
 * 数据来源枚举值
 * @author zhangdb
 */
public enum DataFromEnum {

    /**
     * 小程序
     */
    MINI_PROGRAM("1");

    private String status;

    DataFromEnum(String status){
        this.status = status;
    }

    public String getValue() {
        return status;
    }
}
