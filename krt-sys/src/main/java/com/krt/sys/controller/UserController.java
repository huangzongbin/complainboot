package com.krt.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.PageHelper;
import com.krt.common.bean.Query;
import com.krt.common.bean.ReturnBean;
import com.krt.common.constant.GlobalConstant;
import com.krt.common.datascope.DataScopeHelper;
import com.krt.common.session.SessionUser;
import com.krt.common.util.ShiroUtils;
import com.krt.common.validator.Assert;
import com.krt.sys.entity.Organ;
import com.krt.sys.entity.Role;
import com.krt.sys.entity.User;
import com.krt.sys.service.IOrganService;
import com.krt.sys.service.IRoleService;
import com.krt.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 用户管理控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月16日
 */
@Slf4j
@Controller
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IOrganService organService;

    /**
     * 用户管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("sys:user:list")
    @GetMapping("sys/user/list")
    public String list() {
        List<Role> roleList = roleService.selectList();
        request.setAttribute("roleList", roleList);
        return "sys/user/list";
    }

    /**
     * 用户管理
     *
     * @param para 参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("sys:user:list")
    @PostMapping("sys/user/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = userService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增用户页
     *
     * @return {@link String}
     */
    @RequiresPermissions("sys:user:insert")
    @GetMapping("sys/user/insert")
    public String insert() {
        List<Role> roleList = roleService.selectList();
        request.setAttribute("roleList", roleList);
        return "sys/user/insert";
    }

    /**
     * 添加用户信息
     *
     * @param user    用户
     * @param roleIds 角色ids
     * @return {@link ReturnBean}
     */
    @KrtLog("添加用户")
    @RequiresPermissions("sys:user:insert")
    @PostMapping("sys/user/insert")
    @ResponseBody
    public ReturnBean insert(User user, Integer[] roleIds) {
        userService.insertUser(user, roleIds);
        return ReturnBean.ok();
    }

    /**
     * 修改用户信息
     *
     * @param id 用户id
     * @return {@link String}
     */
    @RequiresPermissions("sys:user:update")
    @GetMapping("sys/user/update")
    public String update(Integer id) {
        List roleList = roleService.selectList();
        User user = userService.selectById(id);
        List<Role> roles = roleService.selectUserRoles(user.getId());
        user.setRoles(roles);
        Integer organId  = user.getOrganId();
        if (organId != null) {
            Organ organ = organService.selectById(organId);
            request.setAttribute("organ", organ);
        }
        request.setAttribute("roleList", roleList);
        request.setAttribute("user", user);
        return "sys/user/update";
    }

    /**
     * 修改用户
     *
     * @param user    用户
     * @param roleIds 角色ids
     * @return {@link ReturnBean}
     */
    @KrtLog("修改用户")
    @RequiresPermissions("sys:user:update")
    @PostMapping("sys/user/update")
    @ResponseBody
    public ReturnBean update(User user, Integer[] roleIds) {
        userService.updateUser(user, roleIds);
        return ReturnBean.ok();
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除用户")
    @RequiresPermissions("sys:user:delete")
    @PostMapping("sys/user/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        userService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 删除用户
     *
     * @param ids 用户ids
     */
    @KrtLog("批量删除用户")
    @RequiresPermissions("sys:user:delete")
    @PostMapping("sys/user/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        userService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


    /**
     * 禁用||启用用户
     *
     * @param id     用户id
     * @param status 用户状态
     * @return {@link ReturnBean}
     */
    @KrtLog("禁用||启用用户")
    @RequiresPermissions("sys:user:status")
    @PostMapping("sys/user/status")
    @ResponseBody
    public ReturnBean status(Integer id, String status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        userService.updateById(user);
        return ReturnBean.ok();
    }

    /**
     * 查看用户信息
     *
     * @param id 用户id
     * @return {@link String}
     */
    @RequiresPermissions("sys:user:see")
    @GetMapping("sys/user/see")
    public String see(Integer id) {
        List roleList = roleService.selectList();
        User user = userService.selectById(id);
        List<Role> roles = roleService.selectUserRoles(user.getId());
        user.setRoles(roles);
        Integer organId  = user.getOrganId();
        if (organId != null) {
            Organ organ = organService.selectById(organId);
            request.setAttribute("organ", organ);
        }
        request.setAttribute("roleList", roleList);
        request.setAttribute("user", user);
        return "sys/user/see";
    }

    /**
     * 检测用户名
     *
     * @param username 用户名
     * @param id       用户id
     * @return {@link Boolean}
     */
    @PostMapping("sys/user/checkUsername")
    @ResponseBody
    public Boolean checkUsername(String username, Integer id) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        queryWrapper.ne(!Assert.isNull(id), User::getId, id);
        Integer count = userService.selectCount(queryWrapper);
        return count == 0;
    }

    /**
     * 修改密码页
     *
     * @return {@link String}
     */
    @GetMapping("sys/user/updatePsw")
    public String updatePsw() {
        return "sys/user/updatePsw";
    }

    /**
     * 修改密码
     *
     * @param password 用户密码
     * @return {@link ReturnBean}
     */
    @KrtLog("修改密码")
    @PostMapping("sys/user/updatePsw")
    @ResponseBody
    public ReturnBean updatePsw(String password) {
        userService.updatePsw(password);
        return ReturnBean.ok();
    }

    /**
     * 修改用户信息
     *
     * @param id 用户id
     */
    @GetMapping("sys/user/updateUserInfo")
    public String userInfo(Integer id) {
        List roleList = roleService.selectList();
        User user = userService.selectById(id);
        List<Role> roles = roleService.selectUserRoles(user.getId());
        user.setRoles(roles);
        Integer organId  = user.getOrganId();
        if (organId != null) {
            Organ organ = organService.selectById(organId);
            request.setAttribute("organ", organ);
        }
        request.setAttribute("roleList", roleList);
        request.setAttribute("user", user);
        return "sys/user/updateUserInfo";
    }

    /**
     * 修改个人信息
     *
     * @param user 用户
     */
    @KrtLog("修改个人信息")
    @PostMapping("sys/user/updateUserInfo")
    @ResponseBody
    public ReturnBean userInfo(User user) {
        userService.updateById(user);
        //更新session
        user = userService.selectById(user.getId());
        SessionUser sessionUser = new SessionUser();
        BeanUtils.copyProperties(user, sessionUser);
        ShiroUtils.setSessionAttribute(GlobalConstant.SESSION_USER, sessionUser);
        return ReturnBean.ok();
    }

    /**
     * 检测用户密码
     *
     * @param oldPassword 用户密码
     */
    @PostMapping("sys/user/checkPsw")
    @ResponseBody
    public boolean checkPsw(String oldPassword) {
        return userService.checkPsw(oldPassword);
    }

}
