package com.krt.gen.enums;

/**
 * 模板文件枚举
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年05月04日
 */
public enum TemplateFileEnum {

    /**
     * 实体
     */
    ENTITY("entity.ftl"),
    /**
     * 映射文件
     */
    MAPPER("mapper.ftl"),
    /**
     * 映射xml
     */
    MAPPERXML("mapperXml.ftl"),
    /**
     * 服务接口
     */
    SERVER("service.ftl"),
    /**
     * 服务接口实现
     */
    SERVERIMPL("serviceImpl.ftl"),
    /**
     * 控制层
     */
    CONTROLLER("controller.ftl");

    TemplateFileEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
