package com.krt.rent.enums;

public enum RentIncomeDetailsStatusEnum {
    STATUS_DJN("待缴纳", 0),
    STATUS_YJN("已缴纳", 1),
    STATUS_YHK("已划扣", 2);

    private String name;
    private Integer status;

    private RentIncomeDetailsStatusEnum() {
    }

    private RentIncomeDetailsStatusEnum(String name, Integer status) {
        this.name = name;
        this.status = status;
    }

    public static String getName(Integer status) {
        for (RentIncomeDetailsStatusEnum one : RentIncomeDetailsStatusEnum.values()) {

                if (one.getStatus().equals(status)) {
                    return one.name;
                }

        }
        return null;
    }

    public static Integer getStatus(String name) {
        for (RentIncomeDetailsStatusEnum one : RentIncomeDetailsStatusEnum.values()) {
            if (one.getName().equals(name)) {
                return one.status;
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
