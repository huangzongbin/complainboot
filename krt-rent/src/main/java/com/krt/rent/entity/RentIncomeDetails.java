package com.krt.rent.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.krt.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.krt.common.util.DateUtils;
import com.krt.common.util.StringUtils;
import com.krt.rent.enums.RentIncomeDetailsStatusEnum;
import com.krt.rent.utils.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
    import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;

/**
 * 租金管理详情表实体类
 *
 * @author ylf
 * @version 1.0
 * @date 2019年06月10日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("rent_income_details")
@ExcelTarget("RentIncomeDetails")
public class RentIncomeDetails extends BaseEntity {

    /**
     * rent_income_base表的id
     */
    private Integer baseId;

    /**
     * 地址
     */
    @Excel(name="地址")
    private String address;

    /**
     * 月租金
     */
    @Excel(name="应缴租金")
    private String monthRental;

    /**
     * 租金
     */
    @Excel(name="租金")
    private String rental;

    /**
     * 历欠租金
     */
    @Excel(name="历欠租金")
    private String oweRental;

    /**
     * 物业费
     */
    @Excel(name="物业费")
    private String property;

    /**
     * 历欠物业费
     */
    @Excel(name="历欠物业费")
    private String oweProperty;

    /**
     * 滞纳金
     */
    @Excel(name="滞纳金")
    private String overdue;


    /**
     * 小计租金
     */
    @Excel(name="小计")
    @NotBlank(message = "小计")
    private String totalRental;

    @Excel(name = "月租金")
    @NotBlank(message = "预缴月租金")
    private BigDecimal advanceRental;

    @ApiModelProperty(value = "月物业费")
    @Excel(name = "月物业费")
    private BigDecimal advanceProperty;

    @ApiModelProperty(name = "预缴月单价合计")
    private BigDecimal advancePrice;

    @ApiModelProperty(value = "预缴月数")
    @Excel(name = "预缴月数")
    private Integer advanceMonths;

    @ApiModelProperty(value = "预缴开始时间")
    @Excel(name = "预缴开始时间")
    private String advanceStartMonth;

    @ApiModelProperty(value = "预缴结束时间")
    @Excel(name = "预缴结束时间")
    private String advanceEndMonth;

    @ApiModelProperty(value = "预缴租金")
    @Excel(name = "预缴租金")
    private BigDecimal advanceRentalAll;

    @ApiModelProperty(value = "预缴物业费")
    @Excel(name = "预缴物业费")
    private BigDecimal advancePropertyAll;

    @ApiModelProperty(value = "预缴总金额")
    @Excel(name = "预缴总金额")
    private BigDecimal advanceAmount;

    /**
     * 合计租金
     */
    @Excel(name="合计租金")
    @NotBlank(message = "合计总租金不能未空")
    private BigDecimal totalRentalAll;

    /**
     * 姓名
     */
    @Excel(name="姓名")
    private String name;

    /**
     * 联系电话
     */
    @Excel(name="联系电话")
    private String phone;

    /**
     * 身份证号码
     */
    @Excel(name="身份证号码")
    private String idCard;

    /**
     * 银行卡账号
     */
    @Excel(name="银行卡账号")
    private String bankCard;

    /**
     * 缴纳状态
     *
     */
    private Integer status;

    /**
     * 是否推送保障房系统
     *
     */
    private Integer hasPushBzf;

    @ApiModelProperty(value = "房子类型，0代表住宅 1代表非住宅")
    private String houseType;


    public void setStatus(Integer status){
        this.statusText = RentIncomeDetailsStatusEnum.getName(status);
        this.status=status;
    }

    /**
     * 缴纳时间
     */
    //@Excel(name="缴纳时间",format="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTiime;

    public void setPaymentTiime(Date paymentTiime){
        //转换格式
        this.paymentTiime=paymentTiime;
        if(paymentTiime!=null){
            this.paymentTiimeStr=DateUtil.dateToString("yyyy-MM-dd HH:mm:ss",paymentTiime);
        }else{
            this.paymentTiimeStr= "";
        }
    }

    /**
     * 缴纳状态(excel读取冗余字段)
     */
    @Excel(name="缴纳状态")
    @TableField(exist = false)
    private String statusText;

    public void setStatusText(String statusText){
        this.status = RentIncomeDetailsStatusEnum.getStatus(statusText);
        this.statusText=statusText;
    }

    /**
     * 缴纳时间字符串(excel读取冗余字段)
     */
    @Excel(name="缴纳时间")
    @TableField(exist = false)
    private String paymentTiimeStr;

    public void setPaymentTiimeStr(String paymentTiimeStr){
        //转换格式
        this.paymentTiimeStr=paymentTiimeStr;
        if(StringUtils.isNotBlank(paymentTiimeStr)){
            this.paymentTiime=DateUtil.stringToDate("yyyy-MM-dd HH:mm:ss",paymentTiimeStr);
        }
    }

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






    @TableField(exist = false)
    @ApiModelProperty(name = "用于判断是否是重复，住宅身份证，非住宅身份证-地址")
    private String idcardAddr;

}