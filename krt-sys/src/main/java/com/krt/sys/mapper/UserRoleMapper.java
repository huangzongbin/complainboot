package com.krt.sys.mapper;

import com.krt.common.base.BaseMapper;
import com.krt.sys.entity.Role;
import com.krt.sys.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;

/**
 * 用户角色映射层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年03月9:44日
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 获取用户角色
     *  
     * @param userId 用户id
     * @return {@link List<Role>}
     */
    List<Role> selectUserRoles(Integer userId);
}
