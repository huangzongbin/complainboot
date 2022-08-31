package com.krt.rent.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 租金详情vo
 *
 * @author hzb
 * @date 2022-1-5
 */
@Data
public class RentIncomeDetailVO implements Serializable {


    /**
     *
     */
    private Integer id;

    @ApiModelProperty(name = "应缴租金")
    private String monthRental;

    @ApiModelProperty(name = "租金")
    private String rental;

    @ApiModelProperty(name = "历欠租金")
    private String oweRental;

    @ApiModelProperty(name = "物业费")
    private String property;

    @ApiModelProperty(name = "历欠物业费")
    private String oweProperty;


    @ApiModelProperty(name = "滞纳金")
    private String overdue;


    @ApiModelProperty(name = "合计租金")
    private String totalRental;


    @ApiModelProperty(name = "支付状态 0待缴纳 1已缴纳 2已划扣")
    private String payStatus;

    @ApiModelProperty(name = "支付状态name")
    private String payStatusName;

    @ApiModelProperty(name = "房源类型")
    private String houseType;

    @ApiModelProperty(value = "预缴月（个）")
    private Integer advanceMonths;

    @ApiModelProperty(name = "预缴月单价")
    private String advancePrice;

    @ApiModelProperty(value = "预缴总金额")
    private String advanceAmount;

    @ApiModelProperty(value = "预缴开始年月")
    private String advanceStartMonth;

    @ApiModelProperty(value = "预缴结束年月")
    private String advanceEndMonth;

    @ApiModelProperty(value = "是否缴纳预缴， 0未缴纳 1缴纳")
    private String ifAdvance;

    @ApiModelProperty(value = "是否已经支付")
    private String hasToBePaidOrder;

    @ApiModelProperty(value = "是否可以缴费 1是 0否")
    private String payRentEntranceStatus;

    /**
     *  可以缴费的时间
     * payRentEntranceStatus==1
     * or   payRentEntranceStatus ==0 && isOver ==0
     */
    @ApiModelProperty(value = "时间是否过去 payRentEntranceStatus状态是0是 并且时间要过了当天就不可以缴费， 1表示超出时间  0表示为超出")
    private String isOver;

    @ApiModelProperty(value = "年")
    private String year;

    @ApiModelProperty(value = "月")
    private String month;


}