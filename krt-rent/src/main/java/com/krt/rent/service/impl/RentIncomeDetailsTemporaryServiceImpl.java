package com.krt.rent.service.impl;

import com.krt.rent.entity.RentIncomeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.krt.rent.entity.RentIncomeDetailsTemporary;
import com.krt.rent.mapper.RentIncomeDetailsTemporaryMapper;
import com.krt.rent.service.IRentIncomeDetailsTemporaryService;
import com.krt.common.base.BaseServiceImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * 租金管理详情 临时表（用于excel导入大量数据时做临时表使用）服务接口实现层
 *
 * @author 陈仁豪
 * @version 1.0
 * @date 2019年06月12日
 */
@Service
public class RentIncomeDetailsTemporaryServiceImpl extends BaseServiceImpl<RentIncomeDetailsTemporaryMapper, RentIncomeDetailsTemporary> implements IRentIncomeDetailsTemporaryService {

    @Autowired
    private RentIncomeDetailsTemporaryMapper temporaryMapper;

    public void insertBatchByDetails(List<RentIncomeDetails> details,int size) {
        List<RentIncomeDetails> insertData = new ArrayList();
        for(int i=0;i<details.size();i++){
            insertData.add(details.get(i));
            if(i>0 && i%size==0){
                temporaryMapper.insertBatchByDetails(insertData);
                insertData.clear();
            }
        }
        if(insertData.size()!=0){
            temporaryMapper.insertBatchByDetails(insertData);
        }
    }

    public void updateDetails(Integer baseId) {
        temporaryMapper.updateDetails(baseId);
    }
}
