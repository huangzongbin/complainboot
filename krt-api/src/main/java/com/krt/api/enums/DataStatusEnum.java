package com.krt.api.enums;

/**
 * 数据状态枚举值
 * @author zhangdb
 */
public enum DataStatusEnum {

    /**
     * 有效
     */
    VALID(1),

    /**
     * 无效
     */
    INVALID(2);

    private Integer status;

    DataStatusEnum(Integer status){
        this.status = status;
    }

    public Integer getValue() {
        return status;
    }
}
