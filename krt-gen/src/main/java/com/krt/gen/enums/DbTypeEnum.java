package com.krt.gen.enums;

/**
 * 数据库类型
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月14日
 */
public enum DbTypeEnum {

    /**
     * 实体
     */
    MYSQL("mysql"),
    /**
     * sqlserver
     */
    SQLSERVER("sqlserver"),
    /**
     * oracle
     */
    OARCLE("oracle");

    DbTypeEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

}
