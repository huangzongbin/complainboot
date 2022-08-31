package com.krt.gen.enums;

/**
 * 是否包含
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月14日
 */
public enum BooleanEnum {

    /**
     * yes
     */
    YES("0"),
    /**
     * no
     */
    NO("1");

    BooleanEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

}
