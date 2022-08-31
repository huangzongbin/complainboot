package com.krt.pay.enums;

/**
 * @author zhangdb
 * @date 2019/6/14 15:22
 */
public enum BillStatusEnums {

    /**
     * 0待缴纳
     */
    DJN(0),

    /**
     * 1已缴纳
     */
    YJN(1),

    /**
     * 2已划扣
     */
    YHQ(2);

    private Integer value;

    BillStatusEnums(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
