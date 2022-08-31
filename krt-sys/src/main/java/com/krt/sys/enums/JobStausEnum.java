package com.krt.sys.enums;

/**
 * 任务执行状态枚举
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年04月29日
 */
public enum JobStausEnum {
    /**
     * 启动任务
     */
    START("0"),
    /**
     * 停止任务
     */
    STOP("1");

    JobStausEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
