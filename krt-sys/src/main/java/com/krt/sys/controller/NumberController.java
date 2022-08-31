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
import com.krt.common.validator.group.UpdateGroup;
import com.krt.sys.entity.Number;
import com.krt.sys.service.INumberService;
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
 * 流水号控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年05月17日
 */
@Slf4j
@Controller
public class NumberController extends BaseController {

    @Autowired
    private INumberService numberService;


    /**
     * 流水号管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("sys:number:list")
    @GetMapping("sys/number/list")
    public String list() {
        return "sys/number/list";
    }

    /**
     * 流水号管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("sys:number:list")
    @PostMapping("sys/number/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = numberService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增流水号页
     *
     * @return {@link String}
     */
    @RequiresPermissions("sys:number:insert")
    @GetMapping("sys/number/insert")
    public String insert() {
        return "sys/number/insert";
    }

    /**
     * 添加流水号
     *
     * @param number 流水号
     * @return {@link ReturnBean}
     */
    @KrtLog("添加流水号")
    @RequiresPermissions("sys:number:insert")
    @PostMapping("sys/number/insert")
    @ResponseBody
    public ReturnBean insert(Number number) {
        ValidatorUtils.validateEntity(number, InsertGroup.class);
        numberService.insert(number);
        return ReturnBean.ok();
    }

    /**
     * 修改流水号页
     *
     * @param id 流水号 id
     */
    @RequiresPermissions("sys:number:update")
    @GetMapping("sys/number/update")
    public String update(Integer id) {
        Number number = numberService.selectById(id);
        request.setAttribute("number", number);
        return "sys/number/update";
    }

    /**
     * 修改流水号
     *
     * @param number 流水号
     * @return {@link ReturnBean}
     */
    @KrtLog("修改流水号")
    @RequiresPermissions("sys:number:update")
    @PostMapping("sys/number/update")
    @ResponseBody
    public ReturnBean update(Number number) {
        ValidatorUtils.validateEntity(number, UpdateGroup.class);
        numberService.updateById(number);
        return ReturnBean.ok();
    }

    /**
     * 删除流水号
     *
     * @param id 流水号 id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除流水号")
    @RequiresPermissions("sys:number:delete")
    @PostMapping("sys/number/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        numberService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除流水号
     *
     * @param ids 流水号 ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除流水号")
    @RequiresPermissions("sys:number:delete")
    @PostMapping("sys/number/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        numberService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }

    /**
     * 生成流水号
     *
     * @param code 流水号编码
     * @return {@link ReturnBean}
     */
    @KrtLog("生成流水号")
    @RequiresPermissions("sys:number:create")
    @GetMapping("sys/number/createNum")
    @ResponseBody
    public ReturnBean createNum(String code) {
        numberService.createNum(code);
        return ReturnBean.ok();
    }


    /**
     * 检查流水号编码是否重复
     *
     * @param id   规则id
     * @param code 规则编码
     * @return {@link Boolean}
     */
    @PostMapping("sys/number/checkNumber")
    @ResponseBody
    public Boolean checkNumber(Integer id, String code) {
        LambdaQueryWrapper<Number> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Number::getCode, code);
        queryWrapper.ne(!Assert.isNull(id), Number::getId, id);
        Integer count = numberService.selectCount(queryWrapper);
        return count == 0;
    }
}
