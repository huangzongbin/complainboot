package com.krt.good.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(value = "商品详情")
public class GoodsDetailDTO implements Serializable {

    private Integer id;

    /**
     * 商家
     */
    @ApiModelProperty(value = "商家code")
    @NotBlank(message = "商家code不可以为空")
    private String store;

    @ApiModelProperty(value = "商家名称")
    @NotBlank(message = "商家名称不可以为空")
    private String storeName;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    @NotBlank(message = "商品名称不可以为空")
    private String name;

    /**
     * 商品编号
     */
    @ApiModelProperty(value = "商品编号")
    private String code;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private BigDecimal amount;

    /**
     * 1:表示样衣 2 表示商品货号
     */
    @ApiModelProperty(value = "1:表示样衣 2 表示商品货号")
    @NotBlank(message = "货物类型不可以为空")
    private String type;

    /**
     * 增减类型： 1表示发货  2表示退货
     */
    @ApiModelProperty(value = "1表示发货  2表示退货")
    @NotBlank(message = "增减类型不可以为空")
    private String creaseType;

    /**
     * 单价
     */
    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    /**
     * 填报时间
     */
    @ApiModelProperty(value = "填报时间  格式：yyyy-MM-dd")
    @NotBlank(message = "填报时间不可以为空")
    private String sendTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 备注
     */
    @ApiModelProperty(value = "图片")
    private String photoUrl;
}
