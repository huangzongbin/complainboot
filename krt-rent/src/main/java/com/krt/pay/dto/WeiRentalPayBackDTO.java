package com.krt.pay.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WeiRentalPayBackDTO {

    /**
     * 账单详情id
     */
    private Integer detailId;

    /**
     * 开发者流水号，确认同一门店内唯一
     */
    private String outNo;

    @ApiModelProperty(value = "年月（yyyy-MM）")
    private String months;

    @ApiModelProperty(value = "承租人姓名")
    private String renter;

    @ApiModelProperty(value = "身份证")
    private String idCard;

    @ApiModelProperty(value = "门牌地址")
    private String numberPlate;

    @ApiModelProperty(value = "月租金")
    private String monthRental;

    @ApiModelProperty(value = "租金")
    private String rental;

    @ApiModelProperty(value = "历欠租金")
    private String oweRental;

    @ApiModelProperty(value = "物业费")
    private String property;

    @ApiModelProperty(value = "历欠物业费")
    private String oweProperty;

    @ApiModelProperty(value = "滞纳金")
    private String overdue;

    @ApiModelProperty(value = "合计租金")
    private String totalRental;

    @ApiModelProperty(value = "预缴月（个）")
    private String advanceMonths;

    @ApiModelProperty(value = "预缴总租金")
    private String advanceRentalAll;

    @ApiModelProperty(value = "预缴总物业费")
    private String advancePropertyAll;

    private String paymentTime;

    @ApiModelProperty(value = "住宅类型：0，住宅；1，非住宅")
    private String houseType = "0";

}
