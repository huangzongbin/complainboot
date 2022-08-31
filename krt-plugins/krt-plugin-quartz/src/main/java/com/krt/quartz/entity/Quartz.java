package com.krt.quartz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.krt.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 定时任务
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年10月16日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("quartz_job")
public class Quartz extends BaseEntity {

    /**
     * 任务名
     */
    private String name;

    /**
     * 任务组
     */
    private String groupName;

    /**
     * 1：运行 0：停止
     */
    private String status;

    /**
     * 表达式
     */
    private String expression;

    /**
     * 描述
     */
    private String description;

    /**
     * bean
     */
    private String beanClass;

    /**
     * 是否同步
     */
    private String isConcurrent;

    /**
     * spring管理的bean
     */
    private String springId;

    /**
     * 方法
     */
    private String method;

    /**
     * 上一次执行时间
     */
    private Date previousFireTime;

    /**
     * 下一次执行时间
     */
    private Date nextFireTime;
}