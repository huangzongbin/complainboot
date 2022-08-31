package com.krt.job;

import com.krt.common.annotation.KrtLog;
import com.krt.common.constant.GlobalConstant;
import com.krt.pay.service.IPayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月10日
 */
@Slf4j
@Component
public class KrtJob {

    @Autowired
    private IPayOrderService payOrderService;

    /**
     * 测试定时任务
     */
    @KrtLog(value = "测试定时任务", type = GlobalConstant.LogType.QUARTZ)
    public void job() {
        log.debug("================定时任务在执行============");
    }


    /**
     * 定时更新还没有推送给租金系统的订单
     */
    @KrtLog(value = "定时更新推送还没有推送给租金系统的订单", type = GlobalConstant.LogType.QUARTZ)
    public void updateDjnOrder() {
        log.debug("================定时更新推送还没有推送给租金系统的订单 定时任务正在执行============");
        try {
            payOrderService.updateDjnOrder();
        } catch (Exception e){
            log.error("定时更新推送还没有推送给租金系统的订单 执行失败！{}",e.getMessage());
        }
        log.debug("================定时更新推送还没有推送给租金系统的订单 定时任务 完成============");
    }




}


