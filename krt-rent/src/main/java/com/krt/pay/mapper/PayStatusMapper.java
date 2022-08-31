package com.krt.pay.mapper;

import com.krt.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.krt.pay.entity.PayStatus;

/**
 * 主动查询支付状态流水记录映射层
 *
 * @author zhangdb
 * @version 1.0
 * @date 2019年07月31日
 */
@Mapper
public interface PayStatusMapper extends BaseMapper<PayStatus>{


}
