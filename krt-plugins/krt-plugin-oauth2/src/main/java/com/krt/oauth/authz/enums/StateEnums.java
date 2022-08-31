package com.krt.oauth.authz.enums;

/**
 * 客户端账号状态
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月30日
 */
public enum StateEnums {

    /**
     * 正常的
     */
    NORMAL("0"),
    /**
     * 禁用的
     */
    FORBIDDEN("1");

    StateEnums(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
