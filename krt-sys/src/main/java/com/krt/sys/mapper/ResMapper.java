package com.krt.sys.mapper;

import com.krt.common.base.BaseMapper;
import com.krt.sys.entity.Res;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资源管理映射层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月16日
 */
@Mapper
public interface ResMapper extends BaseMapper<Res> {

    /**
     * 根据父id查询资源
     *
     * @param pid 父id
     * @return List<Res>
     */
    List<Res> selectByPid(@Param("pid") Integer pid);

    /**
     * 查询全部资源树
     *
     * @return List<Res>
     */
    List<Res> selectAllTree();

    /**
     * 查询用户的菜单id
     *
     * @param userId 用户id
     * @return List<Integer>
     */
    List<Integer> selectUserUrlRes(@Param("userId") Integer userId);

    /**
     * 获取角色资源
     *
     * @param roles 角色
     * @return 角色资源
     */
    List<Res> selectRolesPermission(@Param("roles") List roles);

    /**
     * 查询角色资源
     *
     * @param roleId 角色id
     * @return {@link List<Res>}
     */
    List<Res> selectRoleRes(Integer roleId);
}