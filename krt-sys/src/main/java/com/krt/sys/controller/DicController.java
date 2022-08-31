package com.krt.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.ReturnBean;
import com.krt.common.constant.GlobalConstant;
import com.krt.common.validator.ValidatorUtils;
import com.krt.common.validator.group.InsertGroup;
import com.krt.sys.entity.Dic;
import com.krt.sys.service.IDicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;


/**
 * 字典表控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年04月11日
 */
@Slf4j
@Controller
public class DicController extends BaseController {

    @Autowired
    private IDicService dicService;

    /**
     * 字典表管理页
     */
    @RequiresPermissions("sys:dic:list")
    @GetMapping("sys/dic/list")
    public String list(Integer pid) {
        Integer fid = -1;
        if (!GlobalConstant.DEFAULT_PID.equals(pid) && pid != null) {
            Dic dic = dicService.selectById(pid);
            fid = dic.getPid();
        }
        request.setAttribute("fid", fid);
        return "sys/dic/list";
    }

    /**
     * 字典表管理
     */
    @RequiresPermissions("sys:dic:list")
    @PostMapping("sys/dic/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = dicService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 添加字典表
     *
     * @param dic 字典表
     */
    @KrtLog("添加字典表")
    @RequiresPermissions("sys:dic:insert")
    @PostMapping("sys/dic/insert")
    @ResponseBody
    public ReturnBean insert(Dic dic) {
        ValidatorUtils.validateEntity(dic, InsertGroup.class);
        LambdaQueryWrapper<Dic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dic::getCode,dic.getCode());
        queryWrapper.eq(Dic::getType,dic.getType());
        Integer count = dicService.selectCount(queryWrapper);
        if (count == 0) {
            dicService.insert(dic);
            return ReturnBean.ok();
        } else {
            return ReturnBean.error("字典编码已存在");
        }
    }

    /**
     * 修改字典表
     *
     * @param dic 字典表
     */
    @KrtLog("修改字典表")
    @RequiresPermissions("sys:dic:update")
    @PostMapping("sys/dic/update")
    @ResponseBody
    public ReturnBean update(Dic dic) {
        LambdaQueryWrapper<Dic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dic::getCode, dic.getCode());
        queryWrapper.eq(Dic::getType,dic.getType());
        queryWrapper.ne(Dic::getId, dic.getId());
        Integer count = dicService.selectCount(queryWrapper);
        if (count == 0) {
            dicService.updateById(dic);
            return ReturnBean.ok();
        } else {
            return ReturnBean.error("字典编码已存在");
        }
    }

    /**
     * 删除字典表
     *
     * @param id 字典表 id
     */
    @KrtLog("删除字典表")
    @RequiresPermissions("sys:dic:delete")
    @PostMapping("sys/dic/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        dicService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除字典表
     *
     * @param ids 字典表 ids
     */
    @KrtLog("批量删除字典表")
    @RequiresPermissions("sys:dic:delete")
    @PostMapping("sys/dic/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        dicService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }
}
