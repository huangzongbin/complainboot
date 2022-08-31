package com.krt.gen.enums;

/**
 * 模板路径
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年05月04日
 */
public enum TemplatePathEnum {

    /**
     * 单表
     */
    COMMON("common"),
    /**
     * 树表
     */
    TREE("tree"),
    /**
     * 左树右表
     */
    TREELIST("treeList"),
    /**
     * 一对多
     */
    OTMFORM("otmForm");

    TemplatePathEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
