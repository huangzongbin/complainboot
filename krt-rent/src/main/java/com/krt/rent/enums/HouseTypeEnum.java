package com.krt.rent.enums;

/**
 * 住宅类型枚举类
 * @author zhangdb
 * @date 2020/2/3 9:51
 */
public enum HouseTypeEnum {

    /**
     * 住宅
     */
    ZZ("0", "住宅"),

    /**
     * 非住宅
     */
    FZZ("1", "非住宅");

    private String value;

    private String name;

    public String getValue() {
        return value;
    }

    public String getName() {return name;}

    HouseTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public boolean equalsValue(String value) {
        if (value != null) {
            return value.equals(this.value);
        }
        return false;
    }

    public static String getName(String value) {
        if (value != null) {
            for (HouseTypeEnum type : HouseTypeEnum.values()) {
                if (type.equalsValue(value)) {
                    return type.getName();
                }
            }
        }
        return null;
    }
}
