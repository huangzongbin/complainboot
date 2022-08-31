package com.krt.gen.enums;

/**
 * 生成的文件类型
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年05月04日
 */
public enum FileTypeEnum {

    /**
     * 实体
     */
    ENTITY("entity"),
    /**
     * Mapper
     */
    MAPPER("mapper"),
    /**
     * mapperXml
     */
    MAPPERXML("mapperXml"),
    /**
     * service
     */
    SERVER("service"),
    /**
     * controller
     */
    CONTROLLER("controller"),
    /**
     * FTL
     */
    FTL("ftl"),
    /**
     * permission
     */
    PERMISSION("permission");

    FileTypeEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
