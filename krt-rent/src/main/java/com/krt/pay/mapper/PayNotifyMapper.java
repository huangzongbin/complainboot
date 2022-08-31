package com.krt.pay.mapper;

import com.krt.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.krt.pay.entity.PayNotify;

/**
 * 支付回调映射层
 *
 * @author zhangdb
 * @version 1.0
 * @date 2019年07月30日
 */
@Mapper
public interface PayNotifyMapper extends BaseMapper<PayNotify>{


}
