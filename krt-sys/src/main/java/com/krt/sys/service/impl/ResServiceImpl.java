package com.krt.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.krt.common.base.BaseServiceImpl;
import com.krt.common.constant.GlobalConstant;
import com.krt.common.session.SessionUser;
import com.krt.sys.entity.Res;
import com.krt.sys.entity.Role;
import com.krt.sys.entity.RoleRes;
import com.krt.sys.enums.ResTypeEnum;
import com.krt.sys.mapper.ResMapper;
import com.krt.sys.mapper.RoleResMapper;
import com.krt.sys.service.IResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源管理服务实现层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年03月29日
 */
@Service
public class ResServiceImpl extends BaseServiceImpl<ResMapper, Res> implements IResService {

    @Autowired
    private RoleResMapper roleResMapper;

    /**
     * 新增资源
     *
     * @param res 资源实体
     * @return {@link boolean}
     */
    @CacheEvict(value = "resCache", allEntries = true)
    @Override
    public boolean insert(Res res) {
        if (res.getPid() == null) {
            res.setPid(GlobalConstant.DEFAULT_PID);
        }
        return super.insert(res);
    }

    /**
     * 更新资源
     *
     * @param res 资源实体
     * @return {@link boolean}
     */
    @Override
    @CacheEvict(value = "resCache", allEntries = true)
    public boolean updateById(Res res) {
        if (res.getPid() == null) {
            res.setPid(GlobalConstant.DEFAULT_PID);
        }
        return super.updateById(res);
    }


    /**
     * 递归删除模块
     *
     * @param id 资源id
     * @return {@link boolean}
     */
    @Override
    @CacheEvict(value = "resCache", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Serializable id) {
        boolean result = super.deleteById(id);
        List<Res> resList = selectList(new LambdaQueryWrapper<Res>().eq(Res::getPid, id));
        for (Res res : resList) {
            id = res.getId();
            deleteById(id);
        }
        roleResMapper.delete(new LambdaQueryWrapper<RoleRes>().eq(RoleRes::getResId, id));
        return result;
    }


    /**
     * 查询用户菜单资源
     *
     * @param user 用户信息
     * @return {@link List<Res>}
     */
    @Override
    @Cacheable(value = "resCache", key = "#user.username", unless = "#result == null")
    public List<Res> selectUserUrlRes(SessionUser user) {
        //超管有全部资源
        if (GlobalConstant.ADMIN.equals(user.getUsername())) {
            return selectAllUrlRes(null);
        } else {
            //用户菜单列表
            List<Integer> resIdList = baseMapper.selectUserUrlRes(user.getId());
            return selectAllUrlRes(resIdList);
        }
    }


    /**
     * 根据父id查询资源
     *
     * @param pid 父id
     * @return {@link List<Res>}
     */
    @Override
    @Cacheable(value = "resCache", key = "'pid:'+#pid", unless = "#result == null")
    public List<Res> selectByPid(Integer pid) {
        return baseMapper.selectByPid(pid);
    }

    /**
     * 查询全部资源树
     *
     * @return {@link List<Res>}
     */
    @Override
    @Cacheable(value = "resCache", unless = "#result == null")
    public List<Res> selectAllTree() {
        return baseMapper.selectAllTree();
    }

    /**
     * 获取角色资源
     *
     * @param roles 角色
     * @return {@link List<Res>}
     */
    @Override
    public List<Res> selectRolesPermission(List<Role> roles) {
        return baseMapper.selectRolesPermission(roles);
    }

    /**
     * 查询角色资源
     *
     * @param roleId 角色id
     * @return {@link List<Res>}
     */
    @Override
    public List<Res> selectRoleRes(Integer roleId) {
        return baseMapper.selectRoleRes(roleId);
    }

    /**
     * 查询全部菜单资源
     *
     * @param resIdList 菜单资源id
     * @return {@link List<Res>}
     */
    private List<Res> selectAllUrlRes(List<Integer> resIdList) {
        //查询根菜单列表
        List<Res> resList = selectUrlResByPid(GlobalConstant.DEFAULT_PID, resIdList);
        //递归获取子菜单
        selectResTreeList(resList, resIdList);
        return resList;
    }


    /**
     * 根据父id查询菜单资源
     *
     * @param pid       父id
     * @param resIdList 资源ids
     * @return {@link List<Res>}
     */
    private List<Res> selectUrlResByPid(Integer pid, List<Integer> resIdList) {
        List<Res> resList = selectList(Wrappers.<Res>lambdaQuery()
                .eq(Res::getPid, pid)
                .eq(Res::getType, ResTypeEnum.URL)
                .orderByAsc(Res::getSort));
        if (resIdList == null) {
            return resList;
        }
        List<Res> userResList = new ArrayList<>();
        for (Res res : resList) {
            if (resIdList.contains(res.getId())) {
                userResList.add(res);
            }
        }
        return userResList;
    }

    /**
     * 递归获取资源子菜单
     *
     * @param resList   菜单资源
     * @param resIdList 用户菜单id资源
     * @return {@link List<Res>}
     */
    private List<Res> selectResTreeList(List<Res> resList, List<Integer> resIdList) {
        List<Res> subResList = new ArrayList<>();
        for (Res res : resList) {
            //菜单
            if (ResTypeEnum.URL.getValue().equals(res.getType())) {
                List<Res> pResList = selectUrlResByPid(res.getId(), resIdList);
                List<Res> cResList = selectResTreeList(pResList, resIdList);
                res.setChild(cResList);
            }
            subResList.add(res);
        }
        return subResList;
    }
}
