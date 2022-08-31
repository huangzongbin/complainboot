package com.krt.pay.service.impl;

import org.springframework.stereotype.Service;
import com.krt.pay.entity.PayStatus;
import com.krt.pay.mapper.PayStatusMapper;
import com.krt.pay.service.IPayStatusService;
import com.krt.common.base.BaseServiceImpl;


/**
 * 主动查询支付状态流水记录服务接口实现层
 *
 * @author zhangdb
 * @version 1.0
 * @date 2019年07月31日
 */
@Service
public class PayStatusServiceImpl extends BaseServiceImpl<PayStatusMapper, PayStatus> implements IPayStatusService {

}
