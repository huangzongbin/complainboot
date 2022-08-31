package com.krt.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.krt.common.constant.GlobalConstant;
import com.krt.common.validator.Assert;
import com.krt.sys.entity.Region;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.ReturnBean;
import com.krt.common.validator.ValidatorUtils;
import com.krt.common.validator.group.InsertGroup;
import com.krt.common.validator.group.UpdateGroup;
import com.krt.sys.service.IRegionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 区域控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016-07-26
 */
@Slf4j
@Controller
public class RegionController extends BaseController {

    @Autowired
    private IRegionService regionService;


    /**
     * 区域管理
     *
     * @return {@link String}
     */
    @RequiresPermissions("sys:region:list")
    @GetMapping("sys/region/list")
    public String list() {
        return "sys/region/list";
    }

    /**
     * 区域管理
     *
     * @param pid 区域pid
     * @return {@link List}
     */
    @PostMapping("sys/region/list")
    @ResponseBody
    public ReturnBean list(Integer pid) {
        List<Region> list =  regionService.selectByPid(pid);
        return ReturnBean.ok(list);
    }

    /**
     * 新增区域页
     *
     * @param pid 区域pid
     * @return {@link String}
     */
    @RequiresPermissions("sys:region:insert")
    @GetMapping("sys/region/insert")
    public String insert(Integer pid) {
        if (pid != null) {
            Region pRegion = regionService.selectById(pid);
            request.setAttribute("pRegion", pRegion);
        }
        return "sys/region/insert";
    }

    /**
     * 添加区域
     *
     * @param region 区域
     * @return {@link ReturnBean}
     */
    @KrtLog("添加区域")
    @RequiresPermissions("sys:region:insert")
    @PostMapping("sys/region/insert")
    @ResponseBody
    public ReturnBean insert(Region region) {
        ValidatorUtils.validateEntity(region, InsertGroup.class);
        regionService.insert(region);
        return ReturnBean.ok();
    }

    /**
     * 修改区域页
     *
     * @param id 区域 id
     * @return {@link String}
     */
    @RequiresPermissions("sys:region:update")
    @GetMapping("sys/region/update")
    public String update(Integer id) {
        Region region = regionService.selectById(id);
        Integer pid = region.getPid();
        if (!GlobalConstant.DEFAULT_PID.equals(pid)) {
            Region pRegion = regionService.selectById(pid);
            request.setAttribute("pRegion", pRegion);
        }
        request.setAttribute("region", region);
        return "sys/region/update";
    }

    /**
     * 修改区域
     *
     * @param region 区域
     * @return {@link ReturnBean}
     */
    @KrtLog("修改区域")
    @RequiresPermissions("sys:region:update")
    @PostMapping("sys/region/update")
    @ResponseBody
    public ReturnBean update(Region region) {
        ValidatorUtils.validateEntity(region, UpdateGroup.class);
        regionService.updateById(region);
        return ReturnBean.ok();
    }

    /**
     * 删除区域
     *
     * @param id 区域 id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除区域")
    @RequiresPermissions("sys:region:delete")
    @PostMapping("sys/region/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        regionService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 查看区域页
     *
     * @param id 区域 id
     * @return {@link String}
     */
    @RequiresPermissions("sys:region:see")
    @GetMapping("sys/region/see")
    public String see(Integer id) {
        Region region = regionService.selectById(id);
        Integer pid = region.getPid();
        if (!GlobalConstant.DEFAULT_PID.equals(pid)) {
            Region pRegion = regionService.selectById(pid);
            request.setAttribute("pRegion", pRegion);
        }
        request.setAttribute("region", region);
        return "sys/region/see";
    }

    /**
     * 组织机构树
     *
     * @return {@link ReturnBean}
     */
    @PostMapping("sys/region/regionTree")
    @ResponseBody
    public ReturnBean regionTree() {
        List list = regionService.selectList();
        return ReturnBean.ok(list);
    }

    /**
     * 检测区域编码
     *
     * @param id   区域id
     * @param code 区域编码
     * @return {@link boolean}
     */
    @PostMapping("sys/region/checkCode")
    @ResponseBody
    public boolean checkCode(Integer id, String code) {
        LambdaQueryWrapper<Region> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Region::getCode, code);
        queryWrapper.ne(!Assert.isNull(id), Region::getId, id);
        Integer count = regionService.selectCount(queryWrapper);
        return count == 0;
    }
}
