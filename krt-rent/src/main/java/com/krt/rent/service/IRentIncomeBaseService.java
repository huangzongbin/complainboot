package com.krt.rent.service;

import com.krt.rent.entity.RentIncomeBase;
import com.krt.common.base.IBaseService;


/**
 * 租金管理基础信息表服务接口层
 *
 * @author ylf
 * @version 1.0
 * @date 2019年06月10日
 */
public interface IRentIncomeBaseService extends IBaseService<RentIncomeBase>{

    /**
     * 判断租金缴纳入口是否关闭
     * @param baseId
     * @return
     */
    boolean entranceIsOpen(Integer baseId, String houseType);
}
