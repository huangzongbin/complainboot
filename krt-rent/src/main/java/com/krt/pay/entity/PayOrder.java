package com.krt.pay.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.krt.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付-订单实体类
 *
 * @author zhangdb
 * @version 1.0
 * @date 2019年06月11日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("pay_order")
public class PayOrder extends BaseEntity {

    /**
     * 账单详情id
     */
    private Integer detailId;

    /**
     * 开发者流水号，确认同一门店内唯一
     */
    private String outNo;

    /**
     * 付款方式编号
     */
    private String pmtTag;

    /**
     * 商户自定义付款方式名称
     */
    private String pmtName;

    /**
     * 订单名称（商品描述）,会显示到用户端支付界面
     */
    private String ordName;

    /**
     * 原始交易金额（以分为单位，没有小数点）
     */
    private String originalAmount;

    /**
     * 实际交易金额（以分为单位，没有小数点）
     */
    private String tradeAmount;

    /**
     * 支付完成后跳转到此地址
     */
    private String jumpUrl;

    /**
     * 商户流水号（从1开始自增长不重复）
     */
    private String ordMctId;

    /**
     * 门店流水号（从1开始自增长不重复）
     */
    private String ordShopId;

    /**
     * 系统订单号
     */
    private String ordNo;

    /**
     * 付款完成时间（以收单机构为准）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tradePayTime;

    /**
     * 订单状态（1交易成功，2待支付，4已取消，9等待用户输入密码确认）
     */
    private String status;

    @ApiModelProperty(value = "预缴月份（个）")
    private Integer advanceMonths;

    @ApiModelProperty(value = "预缴总金额")
    private BigDecimal advanceAmount;

    @ApiModelProperty(value = "预缴单月费用")
    private BigDecimal advancePrice;

    @ApiModelProperty(value = "预缴总租金")
    private BigDecimal advanceRentalAll;

    @ApiModelProperty(value = "预缴总物业费")
    private BigDecimal advancePropertyAll;

    @ApiModelProperty(value = "总的减去预缴的缴纳租金")
    private BigDecimal rentAll;

    @ApiModelProperty(value = "预缴开始月")
    private String advanceStartMonth;

    @ApiModelProperty(value = "预缴结束月")
    private String advanceEndMonth;

}