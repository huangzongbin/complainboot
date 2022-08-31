package com.krt.gen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.krt.common.base.BaseServiceImpl;
import com.krt.gen.entity.GenScheme;
import com.krt.gen.mapper.GenSchemeMapper;
import com.krt.gen.service.IGenSchemeService;
import org.springframework.stereotype.Service;

/**
 * 生成方案服务实现层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年05月03日
 */
@Service
public class GenSchemeServiceImpl extends BaseServiceImpl<GenSchemeMapper, GenScheme> implements IGenSchemeService {


    /**
     * 根据tableId删除
     *
     * @param tableId 表格id
     */
    @Override
    public void deleteByTableId(Integer tableId) {
        LambdaQueryWrapper<GenScheme> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GenScheme::getTableId, tableId);
         delete(wrapper);
    }
}
