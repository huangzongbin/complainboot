package com.krt.rent.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.krt.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.xml.crypto.KeySelector;

/**
 * 住户管理实体类
 *
 * @author 陈仁豪
 * @version 1.0
 * @date 2019年06月09日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("rent_house")
@ExcelTarget("RentHouse")
public class RentHouse extends BaseEntity {


    /**
     * 小区名称
     */
    @Excel(name="小区名称")
    private String houseName;

    /**
     * 地址
     */
    @Excel(name="地址")
    private String address;

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
     * 住房类型
     */
    @Excel(name="住房类型",replace = { "住宅_0", "非住宅_1" })
    private String houseType;

}