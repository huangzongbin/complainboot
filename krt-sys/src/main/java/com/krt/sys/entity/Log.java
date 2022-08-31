package com.krt.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.krt.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * 系统日志实体类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年10月16日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_log")
public class Log extends BaseEntity {

    /**
     * 用户
     */
    private String username;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求方式(GET,POST,DELETE,PUT)
     */
    private String requestMethod;

    /**
     * 请求方法全称
     */
    private String requestMethodName;

    /**
     * 请求id
     */
    private String requestIp;

    /**
     * 描述
     */
    private String description;

    /**
     * 耗时 单位毫秒
     */
    private Long useTime;

    /**
     * 0:登录日志 1：操作日志 2:异常日志 3:定时日志
     */
    private Integer type;

    /**
     * 请求uri
     */
    private String requestUrl;

    /**
     * 异常编码
     */
    private String exceptionCode;

    /**
     * 异常详情
     */
    private String exceptionDetail;

    /**
     * 客户端操作系统
     */
    private String os;

    /**
     * 设备
     */
    private String device;

    /**
     * 浏览器
     */
    private String browser;
}