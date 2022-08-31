package com.krt.rent.mapper;

import com.krt.common.base.BaseMapper;
import com.krt.rent.entity.RentIncomeDetails;
import org.apache.ibatis.annotations.Mapper;
import com.krt.rent.entity.RentIncomeDetailsTemporary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 租金管理详情 临时表（用于excel导入大量数据时做临时表使用）映射层
 *
 * @author 陈仁豪
 * @version 1.0
 * @date 2019年06月12日
 */
@Mapper
public interface RentIncomeDetailsTemporaryMapper extends BaseMapper<RentIncomeDetailsTemporary>{


    void insertBatchByDetails(List<RentIncomeDetails> list);

    void updateDetails(@Param("baseId") Integer baseId);
}
