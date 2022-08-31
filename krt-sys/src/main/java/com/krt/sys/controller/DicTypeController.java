package com.krt.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.ReturnBean;
import com.krt.common.validator.ValidatorUtils;
import com.krt.common.validator.group.UpdateGroup;
import com.krt.sys.entity.DicType;
import com.krt.sys.service.IDicTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * 字典类型表控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年04月11日
 */
@Slf4j
@Controller
public class DicTypeController extends BaseController {

    @Autowired
    private IDicTypeService dicTypeService;

    /**
     * 字典类型表管理页
     */
    @RequiresPermissions("sys:dicType:list")
    @GetMapping("sys/dicType/list")
    public String list() {
        return "sys/dicType/list";
    }

    /**
     * 字典类型表管理
     */
    @RequiresPermissions("sys:dicType:list")
    @PostMapping("sys/dicType/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = dicTypeService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 添加字典类型表
     *
     * @param dicType 字典类型表
     */
    @KrtLog("添加字典类型表")
    @RequiresPermissions("sys:dicType:insert")
    @PostMapping("sys/dicType/insert")
    @ResponseBody
    public ReturnBean insert(DicType dicType) {
        ValidatorUtils.validateEntity(dicType);
        LambdaQueryWrapper<DicType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DicType::getCode, dicType.getCode());
        Integer count = dicTypeService.selectCount(queryWrapper);
        if (count == 0) {
            dicTypeService.insert(dicType);
            return ReturnBean.ok();
        } else {
            return ReturnBean.error("字典编码已存在");
        }
    }

    /**
     * 修改字典类型表
     *
     * @param dicType 字典类型表
     */
    @KrtLog("修改字典类型表")
    @RequiresPermissions("sys:dicType:update")
    @PostMapping("sys/dicType/update")
    @ResponseBody
    public ReturnBean update(DicType dicType) {
        ValidatorUtils.validateEntity(dicType, UpdateGroup.class);
        LambdaQueryWrapper<DicType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DicType::getCode, dicType.getCode());
        queryWrapper.ne(DicType::getId, dicType.getId());
        Integer count = dicTypeService.selectCount(queryWrapper);
        if (count == 0) {
            dicTypeService.updateById(dicType);
            return ReturnBean.ok();
        } else {
            return ReturnBean.error("字典编码已存在");
        }
    }

    /**
     * 删除字典类型表
     *
     * @param id   字典类型表id
     * @param code 字典类型编码
     */
    @KrtLog("删除字典类型表")
    @RequiresPermissions("sys:dicType:delete")
    @PostMapping("sys/dicType/delete")
    @ResponseBody
    public ReturnBean delete(Integer id, String code) {
        dicTypeService.deleteByIdAndCode(id, code);
        return ReturnBean.ok();
    }

    /**
     * 批量删除字典类型表
     *
     * @param ids   字典类型ids
     * @param codes 字典编码s
     */
    @KrtLog("删除字典类型表")
    @RequiresPermissions("sys:dicType:delete")
    @PostMapping("sys/dicType/deleteByIdsAndCodes")
    @ResponseBody
    public ReturnBean deleteByIdsAndCodes(Integer[] ids, String[] codes) {
        dicTypeService.deleteByIdsAndCodes(ids, codes);
        return ReturnBean.ok();
    }
}
