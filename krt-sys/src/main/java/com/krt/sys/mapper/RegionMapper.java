package com.krt.sys.mapper;

import com.krt.sys.entity.Region;
import com.krt.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 区域映射层
 *
 * @author 殷帅
 * @version 1.0
 * @date：2016-07-26
 */
@Mapper
public interface RegionMapper extends BaseMapper<Region> {

    /**
     * 根据pid查询区域信息
     *
     * @param pid 区域pid
     * @return 区域信息
     */
    List<Region> selectByPid(Integer pid);
}
