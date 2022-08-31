package com.krt.rent.enums;

public enum RentIncomeBaseStatusEnum {
    END("关闭缴费", 0),
    START("开始缴费", 1),
    pay_Cost("待缴费", 1),
    paying("缴费中", 2),
    pay_START("结束缴费", 3);


    private String name;
    private Integer status;

    private RentIncomeBaseStatusEnum() {
    }

    private RentIncomeBaseStatusEnum(String name, Integer status) {
        this.name = name;
        this.status = status;
    }

    public static String getName(Integer status) {
        for (RentIncomeBaseStatusEnum one : RentIncomeBaseStatusEnum.values()) {

            if (one.getStatus().equals(status)) {
                return one.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
