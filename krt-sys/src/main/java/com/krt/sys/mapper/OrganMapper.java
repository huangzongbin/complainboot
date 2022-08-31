package com.krt.sys.mapper;


import com.krt.common.base.BaseMapper;
import com.krt.sys.entity.Organ;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构映射层
 *
 * @author 殷帅
 * @version 1.0
 * @date：2016-07-26
 */
@Mapper
public interface OrganMapper extends BaseMapper<Organ> {

    /**
     * 根据pid查询组织机构
     *
     * @param pid 机构pid
     * @return 组织机构
     */
    List<Organ> selectByPid(@Param("pid") Integer pid);

    /**
     * 查询角色部门资源
     *
     * @param roleId 角色id
     * @return List<Organ>
     */
    List<Organ> selectRoleOrgan(Integer roleId);
}
