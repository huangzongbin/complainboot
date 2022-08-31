package com.krt.sys.service;


import com.krt.common.base.IBaseService;
import com.krt.sys.entity.User;

/**
 * 用户服务接口层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年03月29日
 */
public interface IUserService extends IBaseService<User> {

    /**
     * 新增用户
     *
     * @param user    用户
     * @param roleIds 角色ids
     */
    void insertUser(User user, Integer[] roleIds);

    /**
     * 更新用户
     *
     * @param user    用户
     * @param roleIds 角色ids
     */
    void updateUser(User user, Integer[] roleIds);

    /**
     * 更新密码
     *
     * @param password 密码
     */
    void updatePsw(String password);

    /**
     * 检测用户密码
     *
     * @param oldPassword 旧密码
     * @return {@link boolean}
     */
    boolean checkPsw(String oldPassword);

    /**
     * 根据username查询用户
     *
     * @param User 用户
     * @renturn User 用户
     */
    User selectByUsername(User User);

    /**
     * 根据idcard删除
     * @param user
     * @return
     */
    int deleteByIdcard(User user);

    /**
     * 通过idcard查找
     * @param user
     * @return
     */
    User selectByIdcard(User user);
}
