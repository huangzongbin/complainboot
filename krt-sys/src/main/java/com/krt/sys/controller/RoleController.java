package com.krt.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.ReturnBean;
import com.krt.common.validator.Assert;
import com.krt.common.validator.ValidatorUtils;
import com.krt.common.validator.group.InsertGroup;
import com.krt.sys.entity.Role;
import com.krt.sys.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
 * 角色管理控制层
 *
 * @author 殷帅
 * @version 1.0
 */
@Slf4j
@Controller
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    /**
     * 角色管理页
     *
     * @return String {@link String}
     */
    @RequiresPermissions("sys:role:list")
    @GetMapping("sys/role/list")
    public String list() {
        return "sys/role/list";
    }

    /**
     * 角色管理
     *
     * @return DataTable {@link DataTable<Role>}
     */
    @RequiresPermissions("sys:role:list")
    @PostMapping("sys/role/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = roleService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 添加角色页
     *
     * @return String {@link String}
     */
    @RequiresPermissions("sys:role:insert")
    @GetMapping("sys/role/insert")
    public String insert() {
        return "sys/role/insert";
    }

    /**
     * 添加角色
     *
     * @param role 角色
     * @return ReturnBean {@link ReturnBean}
     */
    @KrtLog("添加角色")
    @RequiresPermissions("sys:role:insert")
    @PostMapping("sys/role/insert")
    @ResponseBody
    public ReturnBean insert(Role role) {
        ValidatorUtils.validateEntity(role, InsertGroup.class);
        roleService.insert(role);
        return ReturnBean.ok();
    }

    /**
     * 修改角色信息
     *
     * @param id 角色id
     * @return String {@link String}
     */
    @RequiresPermissions("sys:role:update")
    @GetMapping("sys/role/update")
    public String update(Integer id) {
        Role role = roleService.selectById(id);
        request.setAttribute("role", role);
        return "sys/role/update";
    }

    /**
     * 修改角色
     *
     * @param role 角色
     * @return ReturnBean {@link ReturnBean}
     */
    @KrtLog("修改角色")
    @RequiresPermissions("sys:role:update")
    @PostMapping("sys/role/update")
    @ResponseBody
    public ReturnBean update(Role role) {
        roleService.updateById(role);
        return ReturnBean.ok();
    }

    /**
     * 删除角色
     *
     * @param id 角色id
     * @return ReturnBean {@link ReturnBean}
     */
    @KrtLog("删除角色")
    @RequiresPermissions("sys:role:delete")
    @PostMapping("sys/role/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        roleService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除角色
     *
     * @param ids 角色ids
     * @return ReturnBean {@link ReturnBean}
     */
    @KrtLog("批量删除角色")
    @RequiresPermissions("sys:role:delete")
    @PostMapping("sys/role/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        roleService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }

    /**
     * 查看角色信息
     *
     * @param id 角色id
     * @return String {@link String}
     */
    @RequiresPermissions("sys:role:see")
    @GetMapping("sys/role/see")
    public String see(Integer id) {
        Role role = roleService.selectById(id);
        request.setAttribute("role", role);
        return "sys/role/see";
    }

    /**
     * 检测角色名
     *
     * @param name 角色名
     * @param id   角色id
     * @return {@link Boolean}
     */
    @PostMapping("sys/role/checkName")
    @ResponseBody
    public Boolean checkName(String name, Integer id) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getName, name);
        queryWrapper.ne(!Assert.isNull(id), Role::getId, id);
        Integer count = roleService.selectCount(queryWrapper);
        return count == 0;
    }

    /**
     * 检测角色编码
     *
     * @param code 角色编码
     * @param id   角色id
     * @return {@link Boolean}
     */
    @PostMapping("sys/role/checkCode")
    @ResponseBody
    public Boolean checkCode(String code, Integer id) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getCode, code);
        queryWrapper.ne(!Assert.isNull(id), Role::getId, id);
        Integer count = roleService.selectCount(queryWrapper);
        return count == 0;
    }

    /**
     * 获取角色资源权限
     *
     * @param roleId 角色id
     * @return {@link ReturnBean}
     */
    @RequiresPermissions("sys:role:res")
    @PostMapping("sys/role/roleResTree")
    @ResponseBody
    public ReturnBean roleResTree(Integer roleId) {
        List list = roleService.selectRoleResTree(roleId);
        return ReturnBean.ok(list);
    }

    /**
     * 资源权限设置
     *
     * @param roleId 角色id
     * @param resIds 资源ids
     * @return {@link ReturnBean}
     */
    @KrtLog("资源权限设置")
    @RequiresPermissions("sys:role:res")
    @PostMapping("sys/role/roleRes")
    @ResponseBody
    public ReturnBean roleRes(Integer roleId, Integer[] resIds) {
        roleService.updateRoleRes(roleId, resIds);
        return ReturnBean.ok();
    }


    /**
     * 获取角色数据权限
     *
     * @param roleId 角色id
     * @return {@link ReturnBean}
     */
    @RequiresPermissions("sys:role:scope")
    @PostMapping("sys/role/roleScopeTree")
    @ResponseBody
    public ReturnBean roleScopeTree(Integer roleId) {
        List list = roleService.selectScopeTree(roleId);
        return ReturnBean.ok(list);
    }

    /**
     * 设置数据权限
     *
     * @param roleId   角色id
     * @param organIds 机构id
     * @return {@link ReturnBean}
     */
    @KrtLog("设置数据权限")
    @RequiresPermissions("sys:role:scope")
    @PostMapping("sys/role/roleScope")
    @ResponseBody
    public ReturnBean roleScope(Integer roleId, Integer[] organIds) {
        roleService.updateRoleScope(roleId, organIds);
        return ReturnBean.ok();
    }

}
