package com.krt.gen.service;

import com.krt.common.base.IBaseService;
import com.krt.gen.dto.GenSetDTO;
import com.krt.gen.entity.GenTable;

import java.util.List;

/**
 * 业务表服务接口层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年05月03日
 */
public interface IGenTableService extends IBaseService<GenTable> {

    /**
     * 查询数据库表
     *
     * @return List<GenTable>
     */
    List<GenTable> selectDbTable();

    /**
     * 表信息
     *
     * @param tableName
     * @return GenTable
     */
    GenTable selectDbTableInfo(String tableName);

    /**
     * 保存表设置
     *
     * @param genSetDto
     */
    void doInsert(GenSetDTO genSetDto);

    /**
     * 修改表设置
     *
     * @param genSetDto
     */
    void doUpdate(GenSetDTO genSetDto);

    /**
     * 根据表名查询子表表
     *
     * @param name
     * @return List
     */
    List selectChildList(String name);
}
