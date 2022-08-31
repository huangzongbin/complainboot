package com.krt.jpush.enums;

/**
 * 推送对象类型
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月03日
 */
public enum AudienceEnum {

    /**
     * 单推
     */
    ONE("one"),

    /**
     * 群发
     */
    ALL("all");

    AudienceEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
