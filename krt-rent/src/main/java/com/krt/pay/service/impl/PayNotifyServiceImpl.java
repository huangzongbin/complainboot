package com.krt.pay.service.impl;

import org.springframework.stereotype.Service;
import com.krt.pay.entity.PayNotify;
import com.krt.pay.mapper.PayNotifyMapper;
import com.krt.pay.service.IPayNotifyService;
import com.krt.common.base.BaseServiceImpl;


/**
 * 支付回调服务接口实现层
 *
 * @author zhangdb
 * @version 1.0
 * @date 2019年07月30日
 */
@Service
public class PayNotifyServiceImpl extends BaseServiceImpl<PayNotifyMapper, PayNotify> implements IPayNotifyService {

}
