package com.krt.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krt.common.base.BaseServiceImpl;
import com.krt.common.bean.PageHelper;
import com.krt.common.bean.Query;
import com.krt.common.datascope.DataScopeHelper;
import com.krt.common.session.SessionUser;
import com.krt.common.util.IdUtils;
import com.krt.common.util.ShiroUtils;
import com.krt.common.validator.Assert;
import com.krt.sys.entity.User;
import com.krt.sys.entity.UserRole;
import com.krt.sys.enums.UserStatusEnum;
import com.krt.sys.mapper.UserMapper;
import com.krt.sys.service.IUserRoleService;
import com.krt.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户服务实现层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年03月29日
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private UserMapper userMapper;

    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return true 添加成功 false 添加失败
     */
    @Override
    public boolean insert(User user) {
        user.setStatus(UserStatusEnum.NORMAL.getValue());
        String password = user.getPassword();
        //获取加密盐
        String salt = IdUtils.getUUID();
        user.setSalt(salt);
        String newPsw = ShiroUtils.getMD5(password, salt);
        user.setPassword(newPsw);
        return super.insert(user);
    }


    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return true 修改成功 false 修改失败
     */
    @Override
    public boolean updateById(User user) {
        String password = user.getPassword();
        //新密码为空则保持原密码不变
        if (!Assert.isBlank(password)) {
            //获取加密盐
            String salt = IdUtils.getUUID();
            user.setSalt(salt);
            String newPsw = ShiroUtils.getMD5(password, salt);
            user.setPassword(newPsw);
        } else {
            User u = selectById(user.getId());
            user.setPassword(u.getPassword());
        }
        return super.updateById(user);
    }

    /**
     * 添加用户角色
     *
     * @param user    用户信息
     * @param roleIds 用户角色ids
     */
    public void insertUserRole(User user, Integer[] roleIds) {
        if (roleIds != null && roleIds.length > 0) {
            List<UserRole> userRoleList = new ArrayList<>();
            for (Integer roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(roleId);
                userRoleList.add(userRole);
            }
            if (userRoleList.size() > 0) {
                userRoleService.insertBatch(userRoleList);
            }
        }
    }

    /**
     * 新增用户
     *
     * @param user    用户
     * @param roleIds 角色ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUser(User user, Integer[] roleIds) {
        user.setStatus(UserStatusEnum.NORMAL.getValue());
        User userTemp = selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        Assert.isNotNull(userTemp, "用户已存在");
        boolean flag = insert(user);
        if (flag) {
            insertUserRole(user, roleIds);
        }


    }

    /**
     * 更新用户
     *
     * @param user    用户
     * @param roleIds 角色ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user, Integer[] roleIds) {
        boolean flag = updateById(user);
        if (flag) {
            //删除用户角色
            userRoleService.delete(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, user.getId()));
            insertUserRole(user, roleIds);
        }
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return true 删除成功 false删除失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Serializable id) {
        //删除用户角色关联
        userRoleService.delete(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, id));
        //删除用户
        return super.deleteById(id);
    }

    /**
     * 批量删除
     *
     * @param idList 数据ids
     * @return true 删除成功 false 删除失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByIds(Collection<? extends Serializable> idList) {
        //删除用户角色关联
        userRoleService.delete(Wrappers.<UserRole>lambdaQuery().in(UserRole::getUserId, idList));
        //删除用户
        return super.deleteByIds(idList);
    }


    /**
     * 更新密码
     *
     * @param password 密码
     */
    @Override
    public void updatePsw(String password) {
        SessionUser sessionUser = ShiroUtils.getSessionUser();
        User user = new User();
        if (sessionUser != null) {
            user.setId(sessionUser.getId());
            //获取加密盐
            String salt = IdUtils.getUUID();
            user.setSalt(salt);
            String newPsw = ShiroUtils.getMD5(password, salt);
            user.setPassword(newPsw);
            super.updateById(user);
        }
    }

    /**
     * 检测用户密码
     *
     * @param oldPassword 旧密码
     * @return {@link boolean}
     */
    @Override
    public boolean checkPsw(String oldPassword) {
        SessionUser sessionUser = ShiroUtils.getSessionUser();
        if (sessionUser != null) {
            User user = selectById(sessionUser.getId());
            String psw = user.getPassword();
            // 密码加密
            String salt = user.getSalt();
            oldPassword = ShiroUtils.getMD5(oldPassword, salt);
            return psw.equals(oldPassword);
        }
        return false;
    }

    /**
     * 自定义分页 （PageHelper模式）
     *
     * @param para 分页参数
     * @return {@link IPage}
     */
    @Override
    public IPage selectPageList(Map para) {
        Query query = new Query(para);
        Page page = query.getPage();
        PageHelper.startPage(page);
        DataScopeHelper.start();
        List list = baseMapper.selectPageList(para);
        DataScopeHelper.remove();
        page.setRecords(list);
        return page;
    }

    /**
     * 根据username查询数据库记录
     */
    @Override
    public User selectByUsername(User user){
        return userMapper.selectByUsername(user);
    }

    /**
     * 根据idcard删除
     * @param user
     * @return
     */
    public int deleteByIdcard(User user){
        return userMapper.deleteByIdcard(user);
    }

    @Override
    public User selectByIdcard(User user) {
        return userMapper.selectByIdcard(user);
    }

}
