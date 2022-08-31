package com.krt.jpush.enums;

/**
 * 推送消息类型
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月03日
 */
public enum PushTypeEnum {

    /**
     * alert消息
     */
    ALERT("alert"),

    /**
     * message消息
     */
    MESSAGE("message");

    PushTypeEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
