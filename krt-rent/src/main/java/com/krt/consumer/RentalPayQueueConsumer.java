package com.krt.consumer;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.krt.common.bean.ReturnBean;
import com.krt.common.exception.KrtException;
import com.krt.common.util.HttpUtils;
import com.krt.common.util.StringUtils;
import com.krt.pay.dto.WeiRentalPayBackDTO;
import com.krt.pay.enums.BillStatusEnums;
import com.krt.pay.enums.YesOrNoEnums;
import com.krt.rent.entity.RentIncomeDetails;
import com.krt.rent.service.IRentIncomeDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;

/**
 * 推送给租金系统
 *
 * @author 胡国坚
 * @version 1.0
 * @date 2021-05-07 09:00:00
 */
@Slf4j
@Component
public class RentalPayQueueConsumer {

    @Value("${spring.profiles.active}")
    private String active;

    @Value("${biz.housing_security_server}")
    private String housingSecurityServer;

    private String aesKey = "Y2GsP8VnzFaSb+VawMughQ==";

    @Autowired
    private IRentIncomeDetailsService rentIncomeDetailsService;

/*
    @JmsListener(destination = "dlqQueue", containerFactory = "jmsListenerContainerQueue")
    public void dlqQueue(TextMessage message, Session session) {
        log.info("---dlqQueue start---");
        RentIncomeDetails rentIncomeDetails = null;
        //设置本地联调状态
        try {
            String resultStr = message.getText();
            log.info("--- dlqQueue  Start---:{}", resultStr);
            rentIncomeDetails.setStatus(1);
            message.acknowledge();
        } catch (Exception e) {
            log.error("mq通知保障房系统异常{}", e.getMessage());
            try {
                session.recover();
            } catch (Exception e1) {
                log.error("", e1);
            }
            log.error("dlqQueue   End", e);
        }
    }*/


// @JmsListener(destination = "weiRentalPayBackQueue3", containerFactory = "jmsListenerContainerQueue")



    public static void main(String[] args) {

       // WeiRentalPayBackDTO weiRentalPayBackDTO = JSONUtil.toBean("{\"detailId\":1053241,\"houseType\":\"0\",\"idCard\":\"362101196812120343\",\"monthRental\":\"244.8\",\"months\":\"2022-02\",\"numberPlate\":\"金月里15栋708室\",\"outNo\":\"1053241krt83567421645076945\",\"overdue\":\"0\",\"oweProperty\":\"0\",\"oweRental\":\"0\",\"paymentTime\":\"2022-2-17\",\"property\":\"49\",\"rental\":\"195.8\",\"renter\":\"李国花\",\"totalRental\":\"244.8\"}", WeiRentalPayBackDTO.class);
        String jsonStr = SecureUtil.aes(Base64.decode("Y2GsP8VnzFaSb+VawMughQ==")).encryptBase64("01111");
        System.out.println(jsonStr);
        System.out.println( SecureUtil.aes(Base64.decode("Y2GsP8VnzFaSb+VawMughQ==")).decryptStr(jsonStr));
//随机生成密钥

    }
}
