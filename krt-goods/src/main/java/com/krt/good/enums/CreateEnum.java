package com.krt.good.enums;

import org.bouncycastle.cms.PasswordRecipientId;

public enum CreateEnum {


    INCREATE("1","加"),
    DECREACE("2","减");

    private String code;
    private String name;

    CreateEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
