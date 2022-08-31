package com.krt.job;

import com.krt.common.annotation.KrtLog;
import com.krt.common.constant.GlobalConstant;
import com.krt.rent.mapper.RentIncomeDetailsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class DetailsJob {

    @Autowired
    private RentIncomeDetailsMapper detailsMapper;

    /**
     * 租金详情定时任务
     */
    @KrtLog(value = "租金详情定时任务", type = GlobalConstant.LogType.QUARTZ)
    public void detailsBeginMonth() {
       // detailsMapper.updateBeginMonth();
        log.info("租金详情定时任务执行");
    }
}
