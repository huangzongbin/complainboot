package com.krt.pay.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.krt.common.base.BaseServiceImpl;
import com.krt.common.bean.ReturnBean;
import com.krt.common.exception.KrtException;
import com.krt.common.util.*;
import com.krt.common.validator.Assert;
import com.krt.pay.dto.WeiRentalPayBackDTO;
import com.krt.pay.entity.PayNotify;
import com.krt.pay.entity.PayOrder;
import com.krt.pay.entity.PayStatus;
import com.krt.pay.enums.BillStatusEnums;
import com.krt.pay.enums.DataFromEnum;
import com.krt.pay.enums.PayStatusEnums;
import com.krt.pay.enums.YesOrNoEnums;
import com.krt.pay.mapper.PayOrderMapper;
import com.krt.pay.service.IPayNotifyService;
import com.krt.pay.service.IPayOrderService;
import com.krt.pay.service.IPayStatusService;
import com.krt.rent.entity.RentIncomeBase;
import com.krt.rent.entity.RentIncomeDetails;
import com.krt.rent.service.IRentIncomeBaseService;
import com.krt.rent.service.IRentIncomeDetailsService;
import com.krt.rent.utils.GroupMAS;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


/**
 * 支付-订单服务接口实现层
 *
 * @author zhangdb
 * @version 1.0
 * @date 2019年06月11日
 */
@Slf4j
@Service
public class PayOrderServiceImpl extends BaseServiceImpl<PayOrderMapper, PayOrder> implements IPayOrderService {

    @Autowired
    private IRentIncomeBaseService rentIncomeBaseService;

    @Autowired
    private IRentIncomeDetailsService rentIncomeDetailsService;

    @Autowired
    private IPayNotifyService payNotifyService;

    @Autowired
    private IPayStatusService payStatusService;

    private String aesKey = "Y2GsP8VnzFaSb+VawMughQ==";


    @Value("${krt.name}")
    private String complainsName;
    @Value("${biz.pay_back_domain}")
    private String payBackDomain;
    @Value("${biz.housing_security_server}")
    private String housingSecurityServer;

    @Value("${spring.profiles.active}")
    String profilesActive;

/*    @Override
    public String  getWeChatUuid(String uniqueId) throws Exception {
        // 通过微信接口获取用户信息
        String rs = HttpUtils.doGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + secret + "&code=" + uniqueId + "&grant_type=authorization_code", null);
        // 解析获得用户微信号唯一标识
        JSONObject jsonObject = JSONObject.parseObject(rs);
        Object openid = jsonObject.get("openid");
        System.out.println(jsonObject);
        return openid + "";
    }*/

    /**
     * 支付租金账单
     *
     * @param rentIncomeDetails 租金账单详情
     * @param openid
     * @param dataFrom
     * @return
     */
    @Override
    public String pay(RentIncomeDetails rentIncomeDetails, String dataFrom, String openid, String orderName, RentIncomeBase rentIncomeBase, Integer months, String totalRentalFinal) throws Exception {
        boolean isMiniProgram = DataFromEnum.MINI_PROGRAM.getValue().equals(dataFrom);
        log.info("{}开始支付", rentIncomeDetails.getIdCard());
        String jsApiPayUrl = "";
        // 请求银行参数
        String timestamp = PayUtils.getTimestamp();
        String bankPayUrl = "https://api.bankgz.com/mct1/payorder";
        // data 字段 json 数据
        JSONObject dataObj = new JSONObject();
        // 开发者流水号，确认同一门店内唯一，只允许使用数字、字母。
        dataObj.put("out_no", rentIncomeDetails.getId() + "krt8356742" + timestamp);
        // 支付方式标签
        dataObj.put("pmt_tag", "WeixinGZU0");
        dataObj.put("pmt_name", "微信支付");
        // 订单名称
        dataObj.put("ord_name", orderName);
        // 原始交易金额
        double totalRental = Double.parseDouble(totalRentalFinal) * 100;
        dataObj.put("original_amount", totalRental);
        // 交易金额
        dataObj.put("trade_amount", totalRental);
        if (isMiniProgram) {
            // 小程序支付
            Assert.isNull(openid, "openid不能为空!");
            // 小程序支付参数：填默认值 1
            dataObj.put("JSAPI", "1");
            dataObj.put("sub_appid", "wxad07703ad6657ec4");
            // otSUe5PXFgv9h6OP0SlQ_kZrkHl0
            dataObj.put("sub_openid", openid);
        } else {
            // 公众号支付
            // 支付成功返回到系统中的backUrl页面
            String jumpUrl = "";
            if("prod".equals(profilesActive)){
                jumpUrl = payBackDomain + "/rentPay/";
            }else{
                jumpUrl = payBackDomain + "/rentPay/trial/";
            }
            dataObj.put("jump_url", jumpUrl);
        }
        // 支付回调接口地址
        String notifyUrl = payBackDomain + "/" + complainsName + "/api/notify";
        dataObj.put("notify_url", notifyUrl);
        log.info("支付回调地址{}",notifyUrl);
        // aes加密data参数
        String open_key = AES2.Decrypt(PayUtils.open_key, "k8356742k8356742", 2);
        String open_id = AES2.Decrypt(PayUtils.open_id, "k8356742k8356742", 2);
        String data = PayUtils.getDataParams(dataObj, open_key);

        // 通过参数字符串获取sign
        String params = "data=" + data + "&open_id=" + open_id + "&open_key=" + open_key + "&timestamp=" + timestamp;
        String sign = Md5Utils.encoderByMd5With32Bit(DigestUtils.sha1Hex(params));


        // 请求银行接口
        String rs = "";
        try {
            rs = HttpUtils.doPost(bankPayUrl, getParamsMap(open_id, timestamp, sign, data), null);
            log.error("请求接口 pay {}", rs);
        } catch (Exception e) {
            log.error("银行支付接口异常{}", e);
            throw new KrtException(ReturnBean.error("银行接口异常"));
        }
        JSONObject jsonObject = JSONObject.parseObject(rs);
        if ("0".equals(jsonObject.getString("errcode"))) {
            // 验签
            if (!TLinx2Util.verifySign(jsonObject, open_key)) {
                // 加密后的签名不等于返回的签名
                throw new KrtException(ReturnBean.error(4, "请求有误"));
            }
        } else {
            // 请求错误
            throw new KrtException(ReturnBean.error(jsonObject.toJSONString()));
        }

        // 解析 data 获取 jsapi_pay_url 等订单信息
        String returnData = jsonObject.getString("data");
        if (returnData != null && !"".equals(returnData)) {
            // 保存订单信息
            log.error("创建订单步骤1");
            PayOrder payOrder = new PayOrder();
            String dataString = AES2.Decrypt(returnData, open_key, 2);
            JSONObject returnObj = JSONObject.parseObject(dataString);
            payOrder.setOrdNo(returnObj.getString("ord_no"));
            payOrder.setOutNo(returnObj.getString("out_no"));
            payOrder.setDetailId(rentIncomeDetails.getId());
            payOrder.setOrdName(orderName);
            payOrder.setOriginalAmount(totalRentalFinal);
            payOrder.setTradeAmount(totalRentalFinal);
            payOrder.setOrdMctId(returnObj.getString("ord_mct_id"));
            payOrder.setPmtTag(returnObj.getString("pmt_tag"));
            if(ObjectUtil.isNotNull(months) && months>0 && new BigDecimal(totalRentalFinal).compareTo(new BigDecimal(rentIncomeDetails.getTotalRental()))>0){
                log.error("创建订单步骤2-有预缴计算预缴");
                payOrder.setAdvanceAmount(BigDecimal.valueOf(months).multiply(rentIncomeDetails.getAdvancePrice()).setScale(2,   BigDecimal.ROUND_HALF_UP));
                payOrder.setAdvanceMonths(months);
                payOrder.setAdvanceStartMonth(rentIncomeBase.getYear()+"-" +(Integer.valueOf(rentIncomeBase.getMonth())+1));
                payOrder.setAdvanceEndMonth(rentIncomeBase.getYear()+"-" +(Integer.valueOf(rentIncomeBase.getMonth())+months));
                payOrder.setAdvancePrice(rentIncomeDetails.getAdvancePrice());
                payOrder.setRentAll(new BigDecimal(totalRentalFinal).subtract(payOrder.getAdvanceAmount()));
                // 计算预缴租金   计算预缴物业费
                payOrder.setAdvancePropertyAll(rentIncomeDetails.getAdvanceProperty().multiply(BigDecimal.valueOf(months)));
                payOrder.setAdvanceRentalAll(rentIncomeDetails.getAdvanceRental().multiply(BigDecimal.valueOf(months)));
                if(payOrder.getAdvanceAmount().compareTo(payOrder.getAdvanceRentalAll().add(payOrder.getAdvancePropertyAll()))!=0){
                    throw new KrtException(ReturnBean.error("预缴总费用总和不等于预缴租金总和加预缴物业费总和"));
                }
            }else{
                payOrder.setRentAll(new BigDecimal(totalRentalFinal));
            }
            log.error("创建订单步骤3");

            // 正常情况状态都是 2待支付
            String status = returnObj.getString("status");
            payOrder.setStatus(status);
            if (!status.equals(PayStatusEnums.DZF.getValue())) {
                throw new KrtException(ReturnBean.error(3, "获取订单异常"));
            }
            log.info("{}调用微信支付组件成功", rentIncomeDetails.getIdCard());
            if (isMiniProgram) {
                // 小程序支付
                JSONObject miniProgramObj = new JSONObject();
                miniProgramObj.put("appId", returnObj.getString("appId"));
                miniProgramObj.put("timeStamp", returnObj.getString("timeStamp"));
                miniProgramObj.put("nonceStr", returnObj.getString("nonceStr"));
                miniProgramObj.put("package", returnObj.getString("package"));
                miniProgramObj.put("signType", returnObj.getString("signType"));
                miniProgramObj.put("paySign", returnObj.getString("paySign"));
                jsApiPayUrl = miniProgramObj.toJSONString();
            } else {
                payOrder.setJumpUrl(jsApiPayUrl);
                jsApiPayUrl = returnObj.getString("jsapi_pay_url");
            }
            // 保存
            insert(payOrder);
        }
        return jsApiPayUrl;
    }

    /**
     * 支付回调
     */
    @Override
    public Boolean payNotify(PayNotify payNotify) {
        // 保存对方推送的消息到数据库
        log.info("PayNotify:{}", payNotify);
        payNotifyService.insert(payNotify);
        Boolean updateFlag = false;
        // 根据订单号开发者流水号获取订单实体
        String ordNo = payNotify.getOrdNo();
        String outNo = payNotify.getOutNo();
        PayOrder payOrder = new PayOrder();
        try {
            LambdaQueryWrapper<PayOrder> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PayOrder::getOrdNo, ordNo)

                    .eq(PayOrder::getOutNo, outNo);
            payOrder = selectOne(queryWrapper);

            // 更新订单表
            String status = payNotify.getStatus();
            payOrder.setStatus(status);
            payOrder.setUpdateTime(new Date());
            updateFlag = updateById(payOrder);
            // 更新租金账单详情表
            if (updateFlag && PayStatusEnums.JXCG.getValue().equals(status)) {
                // 交易成功
                updateFlag = updateRentIncomeDetails(payOrder, BillStatusEnums.YJN.getValue(), true);
                // 发送消息到保障房系统
                log.info("{}租金缴纳成功", payOrder.getOrdName());
            }
        } catch (Exception e) {
            GroupMAS.sendMsg("17370700580", payOrder.getOrdName() + "租金缴纳异常");
            log.info(e.getMessage());
            throw new RuntimeException();
        }
        return updateFlag;
    }

    @Override
    public Boolean payCheck(RentIncomeDetails billDetail) throws Exception {
        Boolean havePaid = false;
        // 近期调用了支付接口
        LambdaQueryWrapper<PayOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayOrder::getDetailId, billDetail.getId())
                .orderByDesc(PayOrder::getId);
        List<PayOrder> payOrderList = selectList(queryWrapper);
        for (PayOrder payOrder : payOrderList) {
            log.info("{} 校验是否已支付租金，ordNo = {}", billDetail.getIdCard(), payOrder.getOrdNo());
            // 有待支付状态的订单
            if (PayStatusEnums.DZF.getValue().equals(payOrder.getStatus())) {
                // 查询银行接口判断该订单是否已经交易成功
                String ordNo = payOrder.getOrdNo();
                // 封装请求银行参数
                JSONObject dataObj = new JSONObject();
                dataObj.put("ord_no", ordNo);
                // aes加密data参数
                String open_key = AES2.Decrypt(PayUtils.open_key, "k8356742k8356742", 2);
                String open_id = AES2.Decrypt(PayUtils.open_id, "k8356742k8356742", 2);
                String data = PayUtils.getDataParams(dataObj, open_key);
                // 通过参数字符串获取sign
                String timestamp = PayUtils.getTimestamp();
                String params = "data=" + data + "&open_id=" + open_id + "&open_key=" + open_key + "&timestamp=" + timestamp;
                String sign = Md5Utils.encoderByMd5With32Bit(DigestUtils.sha1Hex(params));

                // 请求银行接口
                String rs = "";
                try {
                    String payStatusUrl = "https://api.bankgz.com/mct1/paystatus";
                    rs = HttpUtils.doPost(payStatusUrl, getParamsMap(open_id, timestamp, sign, data), null);
                } catch (Exception e) {
                    log.error("银行查询支付状态接口异常{}", e);
                    throw new KrtException(ReturnBean.error("银行接口异常"));
                }
                JSONObject jsonObject = JSONObject.parseObject(rs);
                String returnData = jsonObject.getString("data");

                // 验签
                Boolean verifySign = TLinx2Util.verifySign(jsonObject, open_key);

                if (verifySign && returnData != null && !"".equals(returnData)) {
                    String dataString = AES2.Decrypt(returnData, open_key, 2);
                    JSONObject returnObj = JSONObject.parseObject(dataString);
                    String status = returnObj.getString("status");
                    // 保存查询支付状态流水记录
                    PayStatus payStatus = JSON.toJavaObject(returnObj, PayStatus.class);
                    payStatus.setDetailId(billDetail.getId());
                    log.info("payStatus=={}", payStatus);
                    // 交易成功
                    if (PayStatusEnums.JXCG.getValue().equals(status)) {
                        // 修改订单表状态为交易成功
                        payOrder.setStatus(status);
                        havePaid = updateById(payOrder);
                        if (havePaid) {
                            // 交易成功
                            updateRentIncomeDetails(payOrder, BillStatusEnums.YJN.getValue(), false);
                            payStatus.setIsModifiedStatus(YesOrNoEnums.YES.getValue());
                            log.info("{}通过调用payCheck接口验证租金缴纳成功", payOrder.getOrdName());
                        }
                    }
                    payStatusService.insert(payStatus);
                    break;
                }
            }
        }
        return havePaid;
    }

    public static void main(String[] args) throws Exception {
        // 查询银行接口判断该订单是否已经交易成功
        String ordNo = "9163540845994800416236367";
        // 封装请求银行参数
        JSONObject dataObj = new JSONObject();
        dataObj.put("ord_no", ordNo);
        // aes加密data参数
        String open_key = AES2.Decrypt(PayUtils.open_key, "k8356742k8356742", 2);
        String open_id = AES2.Decrypt(PayUtils.open_id, "k8356742k8356742", 2);
        String data = PayUtils.getDataParams(dataObj, open_key);
        // 通过参数字符串获取sign
        String timestamp = PayUtils.getTimestamp();
        String params = "data=" + data + "&open_id=" + open_id + "&open_key=" + open_key + "&timestamp=" + timestamp;
        String sign = Md5Utils.encoderByMd5With32Bit(DigestUtils.sha1Hex(params));

        // 请求银行接口
        String rs = "";
        try {
            String payStatusUrl = "https://api.bankgz.com/mct1/paystatus";
            rs = HttpUtils.doPost(payStatusUrl, getParamsMap(open_id, timestamp, sign, data), null);
        } catch (Exception e) {
            log.error("银行查询支付状态接口异常{}", e);
            throw new KrtException(ReturnBean.error("银行接口异常"));
        }

        JSONObject jsonObject = JSONObject.parseObject(rs);
        String returnData = jsonObject.getString("data");

        // 验签
        Boolean verifySign = TLinx2Util.verifySign(jsonObject, open_key);

        if (verifySign && returnData != null && !"".equals(returnData)) {
            String dataString = AES2.Decrypt(returnData, open_key, 2);

            JSONObject returnObj = JSONObject.parseObject(dataString);

            System.err.println(returnObj);

            System.err.println("=================");


            System.err.println(returnObj.getString("status"));
        }
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
         *  更新账单缴纳状态  rentIncomeDetails---  RentIncomeDetails(baseId=null, address=null, monthRental=null,
         *  rental=null, oweRental=null, property=null, oweProperty=null, overdue=null, totalRental=null, name=null,
         *  phone=null, idCard=null, bankCard=null, status=1, hasPushBzf=null, paymentTiime=Mon Sep 06 13:02:24 CST 2021,
         *  statusText=已缴纳, paymentTiimeStr=2021-09-06 13:02:24, payee=null, remark=null)   updateSuccess---true
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
               // jmsTemplateQueue.convertAndSend(weiRentalPayBackQueue3, JSON.toJSONString(weiRentalPayBackDTO));
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

   /* public static void main(String[] args) {
        WeiRentalPayBackDTO weiRentalPayBackDTO = new WeiRentalPayBackDTO();
        weiRentalPayBackDTO.setHouseType("0");
        weiRentalPayBackDTO.setPaymentTime("2021-3-22");
        weiRentalPayBackDTO.setIdCard("362101196302110612");
        weiRentalPayBackDTO.setMonthRental("0.01");
        weiRentalPayBackDTO.setMonths("2021-03");
        weiRentalPayBackDTO.setOverdue("0");
        weiRentalPayBackDTO.setNumberPlate("章江南苑18栋408室");
        weiRentalPayBackDTO.setRenter("曾凡作");
        weiRentalPayBackDTO.setOweProperty("0");
        weiRentalPayBackDTO.setOweRental("0.01");
        weiRentalPayBackDTO.setProperty("0");
        weiRentalPayBackDTO.setTotalRental("0.01");
        weiRentalPayBackDTO.setRental("0");
        JSONObject object = new JSONObject();
        String url = "http://zfbz.gzbzxzf.cn:8099/housing_security_test/" + "api/rentalRecord/insertWeixinPay";
        //aes加密
        // String jsonStr = SecureUtil.aes(Base64.encode("Y2GsP8VnzFaSb+VawMughQ==").getBytes()).encryptBase64(JSON.toJSONString(weiRentalPayBackDTO));
        String jsonStr = SecureUtil.aes("Y2GsP8VnzFaSb+VawMughQ==".getBytes()).encryptBase64(JSON.toJSONString(weiRentalPayBackDTO));
        object.put("payStr", jsonStr);
        log.info("payStr1:"+jsonStr);

        //aes解密
        String jsonStr2 = SecureUtil.aes("Y2GsP8VnzFaSb+VawMughQ==".getBytes()).decryptStr(jsonStr);
        log.info("jsonStr2:"+jsonStr2);


        try {
            //aes加密
            object.put("payStr", jsonStr);
            url = "http://zfbz.gzbzxzf.cn:8099/housing_security_test/" + "api/rentalRecord/insertWeixinPay";
            String s = HttpUtils.doPostJsonStr(url, object.toJSONString(), null);
            log.info("{}", s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    /**
     * 封装参数
     *
     * @param open_id
     * @param timestamp
     * @param sign
     * @param data
     * @return
     */
    private static Map<String, Object> getParamsMap(String open_id, String timestamp, String sign, String data) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("open_id", open_id);
        paramsMap.put("timestamp", timestamp);
        paramsMap.put("sign", sign);
        paramsMap.put("data", data);
        return paramsMap;
    }


    @Override
    public Integer getRecentOrdCount(Integer detailId) {
        return baseMapper.getRecentOrdCount(detailId);
    }

    @Override
    public List<PayOrder> getCheckList() {
        return baseMapper.getCheckList();
    }

    @Override
    public void updateDjnOrder() {
        List<RentIncomeDetails> rentIncomeDetailsList = rentIncomeDetailsService.selectList(Wrappers.<RentIncomeDetails>lambdaQuery()
                .eq(RentIncomeDetails::getStatus, BillStatusEnums.YJN.getValue())
                .and(o->o.eq(RentIncomeDetails::getHasPushBzf, YesOrNoEnums.YJN.getValue())
                .or().isNull(RentIncomeDetails::getHasPushBzf))
                .last("AND DATE_FORMAT(insert_time,'%Y-%m') = DATE_FORMAT(now(),'%Y-%m')")
        );

        Iterator<RentIncomeDetails> rentIncomeDetailsIterator = rentIncomeDetailsList.iterator();
        while (rentIncomeDetailsIterator.hasNext()) {
            RentIncomeDetails rentIncomeDetails = rentIncomeDetailsIterator.next();
            PayOrder payOrder = this.selectOne(Wrappers.<PayOrder>lambdaQuery()
                    .eq(PayOrder::getDetailId, rentIncomeDetails.getId()).last(" limit 1")
            );
            if (null != payOrder) {
                // 通知保障房系统
                WeiRentalPayBackDTO weiRentalPayBackDTO = rentIncomeDetailsService.selectRentalDTO(payOrder.getDetailId());
                weiRentalPayBackDTO.setPaymentTime(DateUtils.getDate());
                weiRentalPayBackDTO.setOutNo(payOrder.getOutNo());
                weiRentalPayBackDTO.setDetailId(payOrder.getDetailId());
                log.debug("推送到保障房系统数据{}", JSON.toJSONString(weiRentalPayBackDTO));
                //jmsTemplateQueue.convertAndSend(weiRentalPayBackQueue3, JSON.toJSONString(weiRentalPayBackDTO));
            }
        }
    }
}
