package com.krt.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.krt.common.base.BaseServiceImpl;
import com.krt.common.constant.GlobalConstant;
import com.krt.common.validator.Assert;
import com.krt.sys.entity.Region;
import com.krt.sys.mapper.RegionMapper;
import com.krt.sys.service.IRegionService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 区域服务实现层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016-07-26
 */
@Service
public class RegionServiceImpl extends BaseServiceImpl<RegionMapper, Region> implements IRegionService {

    /**
     * 保存区域信息
     *
     * @param region 区域信息
     * @return true 修改成功 false 修改失败
     */
    @Override
    @CacheEvict(value = "regionCache", allEntries = true)
    public boolean insert(Region region) {
        if (Assert.isNull(region.getPid())) {
            region.setPid(GlobalConstant.DEFAULT_PID);
        }
        return super.insert(region);
    }

    /**
     * 修改区域信息
     *
     * @param region 区域信息
     * @return true 修改成功 false 修改失败
     */
    @Override
    @CacheEvict(value = "regionCache", allEntries = true)
    public boolean updateById(Region region) {
        if (Assert.isNull(region.getPid())) {
            region.setPid(GlobalConstant.DEFAULT_PID);
        }
        return super.updateById(region);
    }

    /**
     * 删除区域信息
     *
     * @param id 区域id
     * @return true删除成功 false删除失败
     */
    @Override
    @CacheEvict(value = "regionCache", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Serializable id) {
        boolean result = super.deleteById(id);
        List<Region> regionList = selectList(new LambdaQueryWrapper<Region>().eq(Region::getPid, id));
        for (Region region : regionList) {
            deleteById(region.getId());
        }
        return result;
    }

    /**
     * 查询全部
     *
     * @return List<T>
     */
    @Override
    @Cacheable(value = "regionCache", unless = "#result == null")
    public List<Region> selectList() {
        return super.selectList();
    }

    /**
     * 根据pid查询区域信息
     *
     * @param pid 区域pid
     * @return 区域信息
     */
    @Override
    @Cacheable(value = "regionCache", unless = "#result == null")
    public List<Region> selectByPid(Integer pid) {
        return baseMapper.selectByPid(pid);
    }
}
