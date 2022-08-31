package com.krt.rent.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.krt.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
    import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;

/**
 * 租金管理详情 临时表（用于excel导入大量数据时做临时表使用）实体类
 *
 * @author 陈仁豪
 * @version 1.0
 * @date 2019年06月12日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("rent_income_details_temporary")
public class RentIncomeDetailsTemporary extends BaseEntity {

    /**
     * rent_income_base表的id
     */
    private Integer baseId;

    /**
     * 地址
     */
    private String address;

    /**
     * 月租金
     */
    private String monthRental;

    /**
     * 租金
     */
    private String rental;

    /**
     * 历欠租金
     */
    private String oweRental;

    /**
     * 物业费
     */
    private String property;

    /**
     * 历欠物业费
     */
    private String oweProperty;

    /**
     * 滞纳金
     */
    private String overdue;

    /**
     * 合计租金
     */
    private String totalRental;

    /**
     * 姓名
     */
    private String name;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 身份证号码
     */
    private String idCard;

    /**
     * 银行卡账号
     */
    private String bankCard;

    /**
     * 缴纳状态 0待缴纳 1已缴纳 2已划扣
     */
    private Integer status;

    /**
     * 缴纳时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTiime;

    /**
     * 收款人
     */
    @Excel(name="收款人")
    private String payee;

    /**
     * 备注
     */
    @Excel(name="备注")
    private String remark;

    /**
     * 住房类型
     */
    private String houseType;


    @ApiModelProperty(name = "预缴月租金")
    private BigDecimal advanceRental;

    @ApiModelProperty(value = "预缴月物业费")
    private BigDecimal advanceProperty;

    @Excel(name = "预缴月单价合计")
    private BigDecimal advancePrice;

    @ApiModelProperty(value = "预缴月（个）")
    private Integer advanceMonths;

    @ApiModelProperty(value = "预缴开始月")
    private String advanceStartMonth;

    @ApiModelProperty(value = "预缴结束月")
    private String advanceEndMonth;

    @ApiModelProperty(value = "预缴总租金")
    private BigDecimal advanceRentalAll;

    @ApiModelProperty(value = "预缴总物业费")
    private BigDecimal advancePropertyAll;

    @ApiModelProperty(value = "预缴总金额")
    private BigDecimal advanceAmount;

    @Excel(name="合计总租金包含预缴总额")
    private BigDecimal totalRentalAll;
}