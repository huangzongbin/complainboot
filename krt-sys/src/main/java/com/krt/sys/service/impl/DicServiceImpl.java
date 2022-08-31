package com.krt.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.krt.common.base.BaseServiceImpl;
import com.krt.sys.entity.Dic;
import com.krt.sys.mapper.DicMapper;
import com.krt.sys.service.IDicService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典表服务实现层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年04月11日
 */
@Service
public class DicServiceImpl extends BaseServiceImpl<DicMapper, Dic> implements IDicService {

    /**
     * 新增字典
     *
     * @param entity 字典信息
     */
    @Override
    @CacheEvict(value = "dicCache", allEntries = true)
    public boolean insert(Dic entity) {
        return super.insert(entity);
    }

    /**
     * 修改字典
     *
     * @param entity 字典信息
     */
    @Override
    @CacheEvict(value = "dicCache", allEntries = true)
    public boolean updateById(Dic entity) {
        return super.updateById(entity);
    }


    /**
     * 根据类型删除
     *
     * @param type 字典类型
     */
    @Override
    @CacheEvict(value = "dicCache", allEntries = true)
    public void deleteByType(String type) {
        LambdaQueryWrapper<Dic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dic::getType, type);
        delete(wrapper);
    }

    /**
     * 根据字典类型和pid查询字典
     *
     * @param type 字典类型
     * @param pid  字典pid
     * @return 字典列表
     */
    @Override
    @Cacheable(value = "dicCache", unless = "#result == null")
    public List<Dic> selectByTypeAndPid(String type, Integer pid) {
        LambdaQueryWrapper<Dic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dic::getType, type);
        wrapper.eq(Dic::getPid, pid);
        return selectList(wrapper);
    }

    /**
     * 根据字典类型和code查询字典
     *
     * @param type 字典类型
     * @param code 字典编码
     * @return 字典
     */
    @Override
    @Cacheable(value = "dicCache", unless = "#result == null")
    public Dic selectByTypeAndCode(String type, String code) {
        LambdaQueryWrapper<Dic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dic::getType, type);
        wrapper.eq(Dic::getCode, code);
        return selectOne(wrapper);
    }

    /**
     * 删除字典
     *
     * @param id 字典id
     * @return true 删除成功 false 删除失败
     */
    @Override
    @CacheEvict(value = "dicCache", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Serializable id) {
        // 查询子集
        LambdaQueryWrapper<Dic> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dic::getPid, Integer.parseInt(id.toString()));
        List<Dic> dicList = selectList(wrapper);
        for (Dic dic : dicList) {
            deleteById(dic.getId());
        }
        return super.deleteById(id);
    }

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     * @return true:成功 false:失败
     */
    @Override
    @CacheEvict(value = "dicCache", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByIds(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            deleteById(id);
        }
        return true;
    }

    /**
     * 获取多个字典
     * @param types
     * @return
     */
    @Override
    @Cacheable(value = "dicCache")
    public Map<String,Object> getDictionaries(String types) {
        Map<String,Object> resultMap = new HashMap<>();
        if (types != null && !"".equals(types)) {
            String[] typeArray = types.split(",");
            for (String type : typeArray) {
                List dicList = selectByTypeAndPid(type, 0);
                resultMap.put(type,dicList);
            }
        }
        return resultMap;
    }
}
