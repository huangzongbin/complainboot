package com.krt.sys.service;


import com.krt.common.base.IBaseService;
import com.krt.common.session.SessionUser;
import com.krt.sys.entity.Res;
import com.krt.sys.entity.Role;

import java.util.List;

/**
 * 资源管理服务接口层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年03月29日
 */
public interface IResService extends IBaseService<Res> {

    /**
     * 查询用户资源
     *
     * @param user 用户信息
     * @return 用户菜单资源
     */
    List<Res> selectUserUrlRes(SessionUser user);

    /**
     * 根据父id查询资源
     *  
     * @param pid 父id
     * @return {@link List<Res>}
     */
    List<Res> selectByPid(Integer pid);

    /**
     * 查询全部资源树
     *
     * @return {@link List<Res>}
     */
    List<Res> selectAllTree();

    /**
     * 获取角色权限
     *
     * @param roles 角色
     * @return List<Res> 角色资源
     */
    List<Res> selectRolesPermission(List<Role> roles);
    
    /**
     * 查询角色资源
     *
     * @param roleId
     * @return {@link List<Res>}
     */
    List<Res> selectRoleRes(Integer roleId);
}
