package com.krt.gen.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.ReturnBean;
import com.krt.gen.entity.GenTableColumn;
import com.krt.gen.service.IGenTableColumnService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;


/**
 * 业务表字段控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年05月03日
 */
@Controller
public class GenTableColumnController extends BaseController {

    @Resource
    private IGenTableColumnService genTableColumnService;

    /**
     * 业务表字段管理页
     */
    @RequiresPermissions("genTableColumn:list")
    @GetMapping("gen/genTableColumn/list")
    public String list() {
        return "gen/genTableColumn/list";
    }

    /**
     * 业务表字段管理
     *
     * @param para 搜索参数
     */
    @RequiresPermissions("genTableColumn:list")
    @PostMapping("gen/genTableColumn/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage<GenTableColumn> page = genTableColumnService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增业务表字段页
     */
    @RequiresPermissions("genTableColumn:insert")
    @GetMapping("gen/genTableColumn/insert")
    public String insert() {
        return "gen/genTableColumn/insert";
    }

    /**
     * 添加业务表字段
     *
     * @param genTableColumn 业务表字段
     */
    @KrtLog("添加业务表字段")
    @RequiresPermissions("genTableColumn:insert")
    @PostMapping("gen/genTableColumn/insert")
    @ResponseBody
    public ReturnBean insert(GenTableColumn genTableColumn) {
        genTableColumnService.insert(genTableColumn);
        return ReturnBean.ok();
    }

    /**
     * 修改业务表字段页
     *
     * @param id 业务表字段 id
     */
    @RequiresPermissions("genTableColumn:update")
    @GetMapping("gen/genTableColumn/update")
    public String update(Integer id) {
        GenTableColumn genTableColumn = genTableColumnService.selectById(id);
        request.setAttribute("genTableColumn", genTableColumn);
        return "gen/genTableColumn/update";
    }

    /**
     * 修改业务表字段
     *
     * @param genTableColumn 业务表字段
     */
    @KrtLog("修改业务表字段")
    @RequiresPermissions("genTableColumn:update")
    @PostMapping("gen/genTableColumn/update")
    @ResponseBody
    public ReturnBean update(GenTableColumn genTableColumn) {
        genTableColumnService.updateById(genTableColumn);
        return ReturnBean.ok();
    }

    /**
     * 删除业务表字段
     *
     * @param id 业务表字段 id
     */
    @KrtLog("删除业务表字段")
    @RequiresPermissions("genTableColumn:delete")
    @PostMapping("gen/genTableColumn/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        genTableColumnService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除业务表字段
     *
     * @param ids 业务表字段 ids
     */
    @KrtLog("批量删除业务表字段")
    @RequiresPermissions("genTableColumn:delete")
    @PostMapping("gen/genTableColumn/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        genTableColumnService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }

}
