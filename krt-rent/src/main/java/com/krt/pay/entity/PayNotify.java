package com.krt.pay.entity;

import com.krt.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 支付回调实体类
 *
 * @author zhangdb
 * @version 1.0
 * @date 2019年07月30日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("pay_notify")
public class PayNotify extends BaseEntity {

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 随机字符串
     */
    private String randStr;

    /**
     * 开发者流水号（订单号）
     */
    private String outNo;

    /**
     * 订单号
     */
    private String ordNo;

    /**
     * 订单状态（1 支付成功；4 取消）
     */
    private String status;

    /**
     * 签名
     */
    private String sign;

}