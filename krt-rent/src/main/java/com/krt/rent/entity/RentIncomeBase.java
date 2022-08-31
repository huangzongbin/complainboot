package com.krt.rent.entity;

import com.krt.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 租金管理基础信息表实体类
 *
 * @author ylf
 * @version 1.0
 * @date 2019年06月10日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("rent_income_base")
public class RentIncomeBase extends BaseEntity {

    /**
     * 租金缴纳年份
     */
    private String year;

    /**
     * 租金缴纳月份
     */
    private String month;

    /**
     * 导入数量
     */
    private Integer number;

    /**
     * 状态 0关闭缴费 1开始缴费
     */
    private Integer status;

    /**
     * 缴费状态 1待缴费 2缴费中 3结束缴费
     */
    private Integer payStatus;
    /**
     * 结束缴费时间
     * */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date overTime;

    @ApiModelProperty(value = "房子类型，0代表住宅 1代表非住宅")
    private String houseType;

    /**
     * 短信验证码
     */
    @TableField(exist = false)
    private String msgCode;
    /**
     * 短信类型
     */
    @TableField(exist = false)
    private String msgType;

    /**
     * 接收手机号
     */
    @TableField(exist = false)
    private String mobilePhone;

}