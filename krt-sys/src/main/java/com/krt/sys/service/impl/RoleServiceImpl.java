package com.krt.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.krt.common.base.BaseServiceImpl;
import com.krt.common.bean.TreeBean;
import com.krt.common.validator.Assert;
import com.krt.sys.entity.*;
import com.krt.sys.mapper.RoleMapper;
import com.krt.sys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 角色管理服务实现层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月16日
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IResService resService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleResService roleResService;

    @Autowired
    private IRoleScopeService roleScopeService;

    @Autowired
    private IOrganService organService;


    /**
     * 查询用户角色
     *
     * @param userId 用户id
     * @return {@link List < Role >}
     */
    @Override
    public List<Role> selectUserRoles(Integer userId) {
        return baseMapper.selectRolesByUserId(userId);
    }

    /**
     * 获取角色资源树
     *
     * @param roleId 角色id
     */
    @Override
    public List<TreeBean> selectRoleResTree(Integer roleId) {
        // 查询全部资源
        List<Res> allList = resService.selectList();
        // 查询角色资源
        List<Res> roleResList = resService.selectRoleRes(roleId);
        List<TreeBean> treeBeanList = new ArrayList<>();
        if (allList != null && allList.size() > 0) {
            for (Res res1 : allList) {
                TreeBean treeBean = new TreeBean();
                int resId1 = res1.getId();
                treeBean.setId(resId1);
                treeBean.setName(res1.getName());
                treeBean.setPid(res1.getPid());
                if (roleResList != null && roleResList.size() > 0) {
                    for (Res res2 : roleResList) {
                        int resId2 = res2.getId();
                        if (resId1 == resId2) {
                            treeBean.setOpen(true);
                            treeBean.setChecked(true);
                        }
                    }
                }
                treeBeanList.add(treeBean);
            }
        }
        return treeBeanList;
    }

    /**
     * 修改角色权限
     *
     * @param roleId 角色id
     * @param resIds 资源ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "resCache", allEntries = true)
    public void updateRoleRes(Integer roleId, Integer[] resIds) {
        //先删除角色资源
        roleResService.delete(new LambdaQueryWrapper<RoleRes>().eq(RoleRes::getRoleId, roleId));
        //保存角色资源
        if (!Assert.isEmpty(resIds)) {
            List<RoleRes> list = new ArrayList<>();
            for (Integer resId : resIds) {
                RoleRes roleRes = new RoleRes();
                roleRes.setRoleId(roleId);
                roleRes.setResId(resId);
                list.add(roleRes);
            }
            if (list.size() > 0) {
                roleResService.insertBatch(list);
            }
        }
    }

    /**
     * 获取角色数据权限
     *
     * @param roleId 角色id
     * @return List<Role>
     */
    @Override
    public List<TreeBean> selectScopeTree(Integer roleId) {
        // 查询全部数据资源
        List<Organ> allList = organService.selectList();
        // 查询角色数据资源
        List<Organ> roleOrganList = organService.selectRoleOrgan(roleId);
        List<TreeBean> treeBeanList = new ArrayList<>();
        if (allList != null && allList.size() > 0) {
            for (Organ organ1 : allList) {
                TreeBean treeBean = new TreeBean();
                Integer id1 = organ1.getId();
                treeBean.setId(organ1.getId());
                treeBean.setName(organ1.getName());
                treeBean.setPid(organ1.getPid());
                if (roleOrganList != null && roleOrganList.size() > 0) {
                    for (Organ organ2 : roleOrganList) {
                        Integer id2 = organ2.getId();
                        if (id1.equals(id2)) {
                            treeBean.setOpen(true);
                            treeBean.setChecked(true);
                        }
                    }
                }
                treeBeanList.add(treeBean);
            }
        }
        return treeBeanList;
    }


    /**
     * 设置角色的数据权限
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleScope(Integer roleId, Integer[] organIds) {
        //先删除角色数据权限
        roleScopeService.delete(new LambdaQueryWrapper<RoleScope>().eq(RoleScope::getRoleId, roleId));
        //保存角色资源数据权限
        if (organIds != null && organIds.length > 0) {
            List<RoleScope> list = new ArrayList<>();
            for (Integer organId : organIds) {
                RoleScope roleOrgan = new RoleScope();
                roleOrgan.setRoleId(roleId);
                roleOrgan.setDataId(organId.toString());
                list.add(roleOrgan);
            }
            if (list.size() > 0) {
                roleScopeService.insertBatch(list);
            }
        }
    }

    /**
     * 根据 ID 删除
     *
     * @param id 数据库id
     * @return true:成功 false:失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Serializable id) {
        //删除角色用户关联
        userRoleService.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleId, id));
        //删除角色资源关联
        roleResService.delete(new LambdaQueryWrapper<RoleRes>().eq(RoleRes::getRoleId, id));
        //删除角色数据权限关联
        roleScopeService.delete(new LambdaQueryWrapper<RoleScope>().eq(RoleScope::getRoleId, id));
        return super.deleteById(id);
    }

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     * @return true:成功 false:失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByIds(Collection<? extends Serializable> idList) {
        //删除角色用户关联
        userRoleService.delete(new LambdaQueryWrapper<UserRole>().in(UserRole::getRoleId, idList));
        //删除角色资源关联
        roleResService.delete(new LambdaQueryWrapper<RoleRes>().in(RoleRes::getRoleId, idList));
        //删除角色数据权限关联
        roleScopeService.delete(new LambdaQueryWrapper<RoleScope>().in(RoleScope::getRoleId, idList));
        //批量删除角色
        return super.deleteByIds(idList);
    }


}
