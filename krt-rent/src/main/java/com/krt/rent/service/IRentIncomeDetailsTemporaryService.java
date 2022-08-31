package com.krt.rent.service;

import com.krt.rent.entity.RentIncomeDetails;
import com.krt.rent.entity.RentIncomeDetailsTemporary;
import com.krt.common.base.IBaseService;

import java.util.List;


/**
 * 租金管理详情 临时表（用于excel导入大量数据时做临时表使用）服务接口层
 *
 * @author 陈仁豪
 * @version 1.0
 * @date 2019年06月12日
 */
public interface IRentIncomeDetailsTemporaryService extends IBaseService<RentIncomeDetailsTemporary>{

    void insertBatchByDetails(List<RentIncomeDetails> updateDatas, int size);

    void updateDetails(Integer baseId);

}
