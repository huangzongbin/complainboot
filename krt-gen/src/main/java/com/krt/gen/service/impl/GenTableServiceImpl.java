package com.krt.gen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.krt.common.base.BaseServiceImpl;
import com.krt.gen.dto.GenSetDTO;
import com.krt.gen.entity.GenScheme;
import com.krt.gen.entity.GenTable;
import com.krt.gen.entity.GenTableColumn;
import com.krt.gen.mapper.GenTableMapper;
import com.krt.gen.service.IGenSchemeService;
import com.krt.gen.service.IGenTableColumnService;
import com.krt.gen.service.IGenTableService;
import com.krt.sys.entity.Dic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 业务表服务实现层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年05月03日
 */
@Slf4j
@Service
public class GenTableServiceImpl extends BaseServiceImpl<GenTableMapper, GenTable> implements IGenTableService {

    @Autowired
    private IGenTableColumnService genTableColumnService;

    @Autowired
    private IGenSchemeService genSchemeService;

    /**
     * 查询数据库表
     */
    @Override
    public List<GenTable> selectDbTable() {
        return baseMapper.selectDbTable();
    }

    /**
     * 获取表信息
     *
     * @param tableName 表名
     */
    @Override
    public GenTable selectDbTableInfo(String tableName) {
        return baseMapper.selectDbTableInfo(tableName);
    }

    /**
     * 保存设置
     *
     * @param genSetDto 表设置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doInsert(GenSetDTO genSetDto) {
        GenTable genTable = genSetDto.getGenTable();
        super.insert(genTable);
        GenScheme genScheme = genSetDto.getGenScheme();
        genScheme.setTableId(genTable.getId());
        genSchemeService.insert(genScheme);
        List<GenTableColumn> genTableColumns = genSetDto.getGenTableColumns();
        for (GenTableColumn genTableColumn : genTableColumns) {
            genTableColumn.setTableId(genTable.getId());
            genTableColumnService.insert(genTableColumn);
        }
    }

    /**
     * 修改表设置
     *
     * @param genSetDto 表设置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doUpdate(GenSetDTO genSetDto) {
        GenTable genTable = genSetDto.getGenTable();
        super.deleteById(genTable.getId());
        genTable.setId(null);
        super.insert(genTable);
        GenScheme genScheme = genSetDto.getGenScheme();
        genScheme.setTableId(genTable.getId());
        genSchemeService.insert(genScheme);
        List<GenTableColumn> genTableColumns = genSetDto.getGenTableColumns();
        for (GenTableColumn genTableColumn : genTableColumns) {
            genTableColumn.setTableId(genTable.getId());
            genTableColumnService.insert(genTableColumn);
        }
    }

    /**
     * 查询子表
     *
     * @param tableName
     */
    @Override
    public List selectChildList(String tableName) {
        LambdaQueryWrapper<GenTable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GenTable::getParentTable, tableName);
        return selectList(wrapper);
    }


    /**
     * 删除
     *
     * @param id 数据表id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Serializable id) {
        GenTable genTable = selectById(id);
        //删除字段信息
        genTableColumnService.deleteByTableId(Integer.valueOf(id.toString()));
        //删除方案信息
        genSchemeService.deleteByTableId(Integer.valueOf(id.toString()));
        //查询子表
        List<GenTable> cGenTableList = selectChildList(genTable.getName());
        for (GenTable cGenTable : cGenTableList) {
            deleteById(cGenTable.getId());
        }
        //删除表信息
        return super.deleteById(genTable.getId());
    }

}
