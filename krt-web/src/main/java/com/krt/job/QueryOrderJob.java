package com.krt.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.krt.common.bean.ReturnBean;
import com.krt.common.exception.KrtException;
import com.krt.common.util.*;
import com.krt.pay.dto.WeiRentalPayBackDTO;
import com.krt.pay.entity.PayOrder;
import com.krt.pay.entity.PayStatus;
import com.krt.pay.enums.BillStatusEnums;
import com.krt.pay.enums.PayStatusEnums;
import com.krt.pay.enums.YesOrNoEnums;
import com.krt.pay.service.IPayOrderService;
import com.krt.pay.service.IPayStatusService;
import com.krt.rent.entity.RentIncomeDetails;
import com.krt.rent.service.IRentIncomeDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangdb
 * @Description:
 * @date 2020/11/18 14:16
 */
@Slf4j
@Component
public class QueryOrderJob {

    @Autowired
    private IPayOrderService payOrderService;
    @Autowired
    private IPayStatusService payStatusService;
    @Autowired
    private IRentIncomeDetailsService rentIncomeDetailsService;
/*    @Autowired
    @Qualifier("weiRentalPayBackQueue3")
    private Queue weiRentalPayBackQueue3;

    @Autowired
    @Qualifier("jmsTemplateQueue")
    private JmsTemplate jmsTemplateQueue;*/



    /**
     * 住户管理管理页
     *
     * @return {@link String}
     */

    @Transactional(rollbackFor = Exception.class)
    public void run() throws Exception {
        int count = 0;
        List<PayOrder> payOrderList = payOrderService.getCheckList();
        for (PayOrder payOrder : payOrderList) {
            // 有待支付状态的订单
            if (PayStatusEnums.DZF.getValue().equals(payOrder.getStatus())) {
                // 查询银行接口判断该订单是否已经交易成功
                String ordNo = payOrder.getOrdNo();
                // 封装请求银行参数
                JSONObject dataObj = new JSONObject();
                dataObj.put("ord_no",ordNo);
                // aes加密data参数
                String open_key = AES2.Decrypt(PayUtils.open_key,"k8356742k8356742",2);
                String open_id = AES2.Decrypt(PayUtils.open_id,"k8356742k8356742",2);
                String data = PayUtils.getDataParams(dataObj,open_key);
                // 通过参数字符串获取sign
                String timestamp = PayUtils.getTimestamp();
                String params = "data="+data+"&open_id="+open_id+"&open_key="+open_key+"&timestamp="+ timestamp;
                String sign = Md5Utils.encoderByMd5With32Bit(DigestUtils.sha1Hex(params));

                // 请求银行接口
                String rs = "";
                try {
                    String pay_status_url = "https://api.bankgz.com/mct1/paystatus";
                    rs = HttpUtils.doPost(pay_status_url,getParamsMap(open_id,timestamp,sign,data),null);
                } catch (Exception e) {
                    log.error("订单号={}====银行查询支付状态接口异常{}",payOrder.getOrdNo(),e);
                    throw new KrtException(ReturnBean.error("银行接口异常"));
                }
                JSONObject jsonObject = JSONObject.parseObject(rs);
                String returnData = jsonObject.getString("data");

                // 验签
                Boolean verifySign = TLinx2Util.verifySign(jsonObject, open_key);

                if (verifySign && returnData != null && !"".equals(returnData)) {
                    String dataString = AES2.Decrypt(returnData,open_key, 2);
                    JSONObject returnObj = JSONObject.parseObject(dataString);
                    String status = returnObj.getString("status");
                    // 保存查询支付状态流水记录
                    PayStatus payStatus = JSON.toJavaObject(returnObj, PayStatus.class);
                    payStatus.setDetailId(payOrder.getDetailId());
                    log.info("payStatus=={}",payStatus);
                    payOrder.setStatus(status);
                    // 交易成功
                    if (PayStatusEnums.JXCG.getValue().equals(status)) {
                        boolean havePaid = payOrderService.updateById(payOrder);
                        if (havePaid) {
                            count ++;
                            // 交易成功
                            updateRentIncomeDetails(payOrder, BillStatusEnums.YJN.getValue(),true);
                            payStatus.setIsModifiedStatus(YesOrNoEnums.YES.getValue());
                            log.info("{}通过调用payCheck接口验证租金缴纳成功",payOrder.getOrdName());
                        }
                    } else if (PayStatusEnums.YQX.getValue().equals(status)) {
                        // 订单状态改为已取消
                        payOrderService.updateById(payOrder);
                    }
                    payStatusService.insert(payStatus);
                }
            }
        }
        log.error("{}条数据同步成功", count);
    }

    /**
     * 更新账单详情状态
     *
     * @param payOrder
     * @param value    状态
     */
    private Boolean updateRentIncomeDetails(PayOrder payOrder, Integer value, Boolean pushHouseSecurity) {
        //更新缴费状态
        RentIncomeDetails rentIncomeDetails = new RentIncomeDetails();
        rentIncomeDetails.setAdvanceMonths(payOrder.getAdvanceMonths());
        rentIncomeDetails.setAdvanceAmount(payOrder.getAdvanceAmount());
        rentIncomeDetails.setAdvanceStartMonth(payOrder.getAdvanceStartMonth());
        rentIncomeDetails.setAdvanceEndMonth(payOrder.getAdvanceEndMonth());
        rentIncomeDetails.setTotalRentalAll(new BigDecimal(payOrder.getTradeAmount()));
        rentIncomeDetails.setAdvancePropertyAll(payOrder.getAdvancePropertyAll());
        rentIncomeDetails.setAdvanceRentalAll(payOrder.getAdvanceRentalAll());
        rentIncomeDetails.setId(payOrder.getDetailId());
        rentIncomeDetails.setStatus(value);

        /**
         *  更新账单缴纳状态
         */
        // 缴纳时间
        rentIncomeDetails.setPaymentTiime(new Date());
        boolean updateSuccess = rentIncomeDetailsService.updateById(rentIncomeDetails);
        log.info(" 更新账单缴纳状态 id===={} rentIncomeDetails---  {}   updateSuccess---{}", rentIncomeDetails.getId(), rentIncomeDetails, updateSuccess);
        if (updateSuccess && pushHouseSecurity) {
            // 通知保障房系统
            try {
                WeiRentalPayBackDTO weiRentalPayBackDTO = rentIncomeDetailsService.selectRentalDTO(payOrder.getDetailId());
                weiRentalPayBackDTO.setPaymentTime(DateUtils.getDate());
                weiRentalPayBackDTO.setOutNo(payOrder.getOutNo());
                weiRentalPayBackDTO.setDetailId(payOrder.getDetailId());
                log.debug("推送到保障房系统数据{}", JSON.toJSONString(weiRentalPayBackDTO));
                //jmsTemplateQueue.convertAndSend(weiRentalPayBackQueue3, JSON.toJSONString(weiRentalPayBackDTO));
               /* WeiRentalPayBackDTO weiRentalPayBackDTO = rentIncomeDetailsService.selectRentalDTO(payOrder.getDetailId());
                JSONObject object = new JSONObject();
                String url = housingSecurityServer + "api/rentalRecord/insertWeixinPay";
                //aes加密
                String jsonStr = SecureUtil.aes(aesKey.getBytes()).encryptBase64(JSON.toJSONString(weiRentalPayBackDTO));
                object.put("payStr", jsonStr);
                HttpUtils.doPostJsonStr(url, object.toJSONString(), null);*/
            } catch (Exception e) {
                log.error("mq通知保障房系统异常{}", e.getMessage());
            }
        }
        return updateSuccess;
    }

    /**
     * 封装参数
     * @param open_id
     * @param timestamp
     * @param sign
     * @param data
     * @return
     */
    private static Map<String,Object> getParamsMap(String open_id, String timestamp, String sign, String data) {
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("open_id",open_id);
        paramsMap.put("timestamp", timestamp);
        paramsMap.put("sign",sign);
        paramsMap.put("data",data);
        return paramsMap;
    }

}
