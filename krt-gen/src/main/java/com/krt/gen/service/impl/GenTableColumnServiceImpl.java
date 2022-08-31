package com.krt.gen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.krt.common.base.BaseServiceImpl;
import com.krt.common.util.StringUtils;
import com.krt.common.validator.Assert;
import com.krt.gen.entity.GenScheme;
import com.krt.gen.entity.GenTable;
import com.krt.gen.entity.GenTableColumn;
import com.krt.gen.mapper.GenTableColumnMapper;
import com.krt.gen.properties.GenProperties;
import com.krt.gen.service.IGenTableColumnService;
import com.krt.gen.util.DbUtils;
import com.krt.gen.util.GenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 业务表字段服务层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年05月03日
 */
@Slf4j
@Service
public class GenTableColumnServiceImpl extends BaseServiceImpl<GenTableColumnMapper, GenTableColumn> implements IGenTableColumnService {

    @Autowired
    private GenProperties genProperties;

    /**
     * 获取表字段设置信息
     *
     * @param genTable
     */
    @Override
    public List<GenTableColumn> selectDbTableColumn(GenTable genTable) {
        // 查询物理表列
        List<GenTableColumn> dbColumnList = baseMapper.selectDbTableColumn(genTable.getName());
        if (Assert.isNull(genTable.getId())) {
            //新增
            for (GenTableColumn genTableColumn : dbColumnList) {
                String jdbcType = genTableColumn.getJdbcType().split("\\(")[0];
                genTableColumn.setJavaType(DbUtils.sqlType2JavaType(genProperties.getDbType(), jdbcType));
                genTableColumn.setJdbcType(GenUtils.getJdbcType(jdbcType));
                genTableColumn.setJavaField(StringUtils.underline2Camel(genTableColumn.getName(), true));
                if (StringUtils.isBlank(genTableColumn.getComment())) {
                    genTableColumn.setComment(genTableColumn.getName());
                }
            }
            return dbColumnList;
        } else {
            //修改
            LambdaQueryWrapper<GenTableColumn> genTableColumnWrapper = new LambdaQueryWrapper<>();
            genTableColumnWrapper.eq(GenTableColumn::getTableId, genTable.getId());
            List<GenTableColumn> columnList = selectList(genTableColumnWrapper);
            List<GenTableColumn> list = new ArrayList();
            for (GenTableColumn dbTableColumn : dbColumnList) {
                for (GenTableColumn genTableColumn : columnList) {
                    if (dbTableColumn.getName().equalsIgnoreCase(genTableColumn.getName())) {
                        String jdbcType = dbTableColumn.getJdbcType().split("\\(")[0];
                        genTableColumn.setJdbcType(GenUtils.getJdbcType(jdbcType));
                        genTableColumn.setJavaType(DbUtils.sqlType2JavaType(genProperties.getDbType(), jdbcType));
                        genTableColumn.setJavaField(StringUtils.underline2Camel(dbTableColumn.getName(), true));
                        dbTableColumn = genTableColumn;
                    }
                }
                if (StringUtils.isBlank(dbTableColumn.getJavaType())) {
                    String jdbcType = dbTableColumn.getJdbcType().split("\\(")[0];
                    dbTableColumn.setJdbcType(GenUtils.getJdbcType(jdbcType));
                    dbTableColumn.setJavaType(DbUtils.sqlType2JavaType(genProperties.getDbType(), jdbcType));
                    dbTableColumn.setJavaField(StringUtils.underline2Camel(dbTableColumn.getName(), true));
                }
                list.add(dbTableColumn);
            }
            //排序
            Collections.sort(list, (o1, o2) -> {
                int i = o1.getSort() - o2.getSort();
                if (i == 0) {
                    return 0;
                }
                return i;
            });
            return list;
        }
    }

    /**
     * 根据tableId删除
     *
     * @param tableId 表id
     */
    @Override
    public void deleteByTableId(Integer tableId) {
        LambdaQueryWrapper<GenTableColumn> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GenTableColumn::getTableId, tableId);
        delete(wrapper);
    }


}
