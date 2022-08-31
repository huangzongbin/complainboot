package com.krt.rent.service.impl;

import org.springframework.stereotype.Service;
import com.krt.rent.entity.RentIncomeBase;
import com.krt.rent.mapper.RentIncomeBaseMapper;
import com.krt.rent.service.IRentIncomeBaseService;
import com.krt.common.base.BaseServiceImpl;


/**
 * 租金管理基础信息表服务接口实现层
 *
 * @author ylf
 * @version 1.0
 * @date 2019年06月10日
 */
@Service
public class RentIncomeBaseServiceImpl extends BaseServiceImpl<RentIncomeBaseMapper, RentIncomeBase> implements IRentIncomeBaseService {

    @Override
    public boolean entranceIsOpen(Integer baseId, String houseType) {
        return baseMapper.entranceIsOpen(baseId, houseType);
    }
}
