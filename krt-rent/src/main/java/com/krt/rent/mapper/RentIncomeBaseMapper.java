package com.krt.rent.mapper;

import com.krt.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.krt.rent.entity.RentIncomeBase;
import org.apache.ibatis.annotations.Param;

/**
 * 租金管理基础信息表映射层
 *
 * @author ylf
 * @version 1.0
 * @date 2019年06月10日
 */
@Mapper
public interface RentIncomeBaseMapper extends BaseMapper<RentIncomeBase>{

    /**
     *判断租金缴纳入口是否关闭
     * @param baseId
     * @return
     */
    boolean entranceIsOpen(@Param("baseId") Integer baseId, @Param("houseType") String houseType);
}
