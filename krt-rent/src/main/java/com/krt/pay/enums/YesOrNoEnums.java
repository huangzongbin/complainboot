package com.krt.pay.enums;

/**
 * @author zhangdb
 * @date 2019/6/14 15:22
 */
public enum YesOrNoEnums {

    YES(1),


    YJN(2);

    private Integer value;

    YesOrNoEnums(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
