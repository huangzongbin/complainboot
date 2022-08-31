package com.krt.quartz.enums;

/**
 * 任务同步状态
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月13日
 */
public enum JobConcurrentEnum {

    /**
     * 同步
     */
    YES("1"),
    /**
     * 不同步
     */
    NO("0");

    JobConcurrentEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
