package com.krt.sys.enums;

/**
 * 资源类别
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年04月29日
 */
public enum ResTypeEnum {

    /**
     * url
     */
    URL("url"),

    /**
     * 按钮
     */
    BUTTON("button");

    ResTypeEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
