package com.krt.gen.service;

import com.krt.common.base.IBaseService;
import com.krt.gen.entity.GenScheme;

/**
 * 生成方案服务层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年05月03日
 */
public interface IGenSchemeService extends IBaseService<GenScheme> {


    /**
     * 根据tableId删除
     *
     * @param tableId
     */
    void deleteByTableId(Integer tableId);
}
