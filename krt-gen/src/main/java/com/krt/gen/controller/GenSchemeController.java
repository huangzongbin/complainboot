package com.krt.gen.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.ReturnBean;
import com.krt.gen.entity.GenScheme;
import com.krt.gen.service.IGenSchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;


/**
 * 生成方案控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年05月03日
 */
@Controller
public class GenSchemeController extends BaseController {

    @Autowired
    private IGenSchemeService genSchemeService;

    /**
     * 生成方案管理页
     */
    @GetMapping("gen/genScheme/list")
    public String list() {
        return "gen/genScheme/list";
    }

    /**
     * 生成方案管理
     *
     * @param para 搜索参数
     */
    @PostMapping("gen/genScheme/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage<GenScheme> page = genSchemeService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增生成方案页
     */
    @GetMapping("gen/genScheme/insert")
    public String insert() {
        return "gen/genScheme/insert";
    }

    /**
     * 添加生成方案
     *
     * @param genScheme 生成方案
     */
    @PostMapping("gen/genScheme/insert")
    @ResponseBody
    public ReturnBean insert(GenScheme genScheme) {
        genSchemeService.insert(genScheme);
        return ReturnBean.ok();
    }

    /**
     * 修改生成方案页
     *
     * @param id 生成方案 id
     */
    @GetMapping("gen/genScheme/update")
    public String update(Integer id) {
        GenScheme genScheme = genSchemeService.selectById(id);
        request.setAttribute("genScheme", genScheme);
        return "gen/genScheme/update";
    }

    /**
     * 修改生成方案
     *
     * @param genScheme 生成方案
     */
    @PostMapping("gen/genScheme/update")
    @ResponseBody
    public ReturnBean update(GenScheme genScheme) {
        genSchemeService.updateById(genScheme);
        return ReturnBean.ok();
    }

    /**
     * 删除生成方案
     *
     * @param id 生成方案 id
     */
    @PostMapping("gen/genScheme/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        genSchemeService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除生成方案
     *
     * @param ids 生成方案 ids
     */
    @PostMapping("gen/genScheme/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        genSchemeService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


}
