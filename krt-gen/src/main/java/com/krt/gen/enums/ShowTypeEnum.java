package com.krt.gen.enums;

/**
 * 表单显示组件
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年05月04日
 */
public enum ShowTypeEnum {

    /**
     * 单选树
     */
    RTREE("rTree"),
    /**
     * 多选树
     */
    CTREE("cTree"),
    /**
     * 单选List
     */
    CLIST("cList"),
    /**
     * 多选List
     */
    RLIST("rList");

    ShowTypeEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
