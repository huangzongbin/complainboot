package com.krt.sys.service;

import com.krt.common.base.IBaseService;
import com.krt.sys.entity.Dic;

import java.util.List;
import java.util.Map;

/**
 * 字典表服务接口层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年04月11日
 */
public interface IDicService extends IBaseService<Dic> {

    /**
     * 根据类型删除
     *
     * @param type
     */
    void deleteByType(String type);

    /**
     * 根据字典类型和pid查询字典
     *
     * @param type 字典类型
     * @param pid  字典pid
     * @return 字典列表
     */
    List<Dic> selectByTypeAndPid(String type, Integer pid);

    /**
     * 根据字典类型和code查询字典
     *
     * @param type 字典类型
     * @param code 字典编码
     * @return 字典
     */
    Dic selectByTypeAndCode(String type, String code);

    Map getDictionaries(String types);
}
