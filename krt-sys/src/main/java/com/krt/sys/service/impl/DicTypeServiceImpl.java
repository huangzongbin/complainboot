package com.krt.sys.service.impl;

import com.krt.common.base.BaseServiceImpl;
import com.krt.sys.entity.DicType;
import com.krt.sys.mapper.DicTypeMapper;
import com.krt.sys.service.IDicService;
import com.krt.sys.service.IDicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 字典类型表服务实现层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年04月11日
 */
@Service
public class DicTypeServiceImpl extends BaseServiceImpl<DicTypeMapper, DicType> implements IDicTypeService {

    @Autowired
    private IDicService dicService;

    /**
     * 删除字典类型
     *
     * @param id   字典类型id
     * @param code 字典类型编码
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIdAndCode(Integer id, String code) {
        //删除字典类型
        deleteById(id);
        //删除字典
        dicService.deleteByType(code);
    }

    /**
     * 批量删除字典类型
     *
     * @param ids   字典类型id
     * @param codes 字典类型编码s
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIdsAndCodes(Integer[] ids, String[] codes) {
        for (int i = 0; i < ids.length; i++) {
            deleteByIdAndCode(ids[i], codes[i]);
        }
    }
}
