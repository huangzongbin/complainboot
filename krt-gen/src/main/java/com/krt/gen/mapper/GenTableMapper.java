package com.krt.gen.mapper;

import com.krt.common.base.BaseMapper;
import com.krt.gen.entity.GenTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;


/**
 * 业务表映射层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年05月03日
 */
@Mapper
public interface GenTableMapper extends BaseMapper<GenTable> {

    /**
     * 查询数据库表
     *
     * @return 物理表列表
     */
    List<GenTable> selectDbTable();

    /**
     * 获取表信息
     *
     * @param tableName 物理表表名
     * @return 表信息
     */
    GenTable selectDbTableInfo(@Param("tableName") String tableName);

}
