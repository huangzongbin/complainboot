package com.krt.sys.mapper;

import com.krt.common.base.BaseMapper;
import com.krt.sys.entity.RoleScope;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色数据资源
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月30日
 */
@Mapper
public interface RoleScopeMapper extends BaseMapper<RoleScope> {


    /**
     * 获取角色数据权限
     *
     * @param roleIds
     * @return {@link List<String>}
     */
    List<String> selectRoleDataId(Integer[] roleIds);
}
