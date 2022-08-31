package com.krt.sys.mapper;

import com.krt.sys.entity.Role;
import com.krt.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色管理映射层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月16日
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询用户角色
     *
     * @param userId 用户id
     * @return List<Role>
     */
    List<Role> selectRolesByUserId(@Param("userId") Integer userId);
}
