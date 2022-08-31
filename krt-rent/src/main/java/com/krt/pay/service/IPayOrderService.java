package com.krt.pay.service;

import com.krt.pay.entity.PayNotify;
import com.krt.pay.entity.PayOrder;
import com.krt.common.base.IBaseService;
import com.krt.rent.entity.RentIncomeBase;
import com.krt.rent.entity.RentIncomeDetails;

import java.util.List;


/**
 * 支付-订单服务接口层
 *
 * @author zhangdb
 * @version 1.0
 * @date 2019年06月11日
 */
public interface IPayOrderService extends IBaseService<PayOrder>{

/*    *//**
     * 获取微信号唯一标识
     * @param uniqueId
     * @return
     *//*
    String getWeChatUuid(String uniqueId) throws Exception;*/

    /**
     * 支付租金账单
     * @param rentIncomeDetails
     * @param dataFrom
     * @param openid
     * @return
     */
    String pay(RentIncomeDetails rentIncomeDetails, String dataFrom, String openid, String orderName, RentIncomeBase rentIncomeBase, Integer months, String totalRental) throws Exception;

    /**
     * 支付回调
     * @param payNotify
     * @return
     */
    Boolean payNotify(PayNotify payNotify);

    /**
     * 校验该账单是否已经支付
     * @param billDetail
     */
    Boolean payCheck(RentIncomeDetails billDetail) throws Exception;

    Integer getRecentOrdCount(Integer detailId);

    List<PayOrder> getCheckList();

    /**
     *  定时更新推送还没有推送给租金系统的订单
     */
    void updateDjnOrder();
}
