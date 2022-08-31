package com.krt.pay.mapper;

import com.krt.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.krt.pay.entity.PayOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 支付-订单映射层
 *
 * @author zhangdb
 * @version 1.0
 * @date 2019年06月11日
 */
@Mapper
public interface PayOrderMapper extends BaseMapper<PayOrder> {

    Integer getRecentOrdCount(@Param("detailId") Integer detailId);

    List<PayOrder> getCheckList();
}
