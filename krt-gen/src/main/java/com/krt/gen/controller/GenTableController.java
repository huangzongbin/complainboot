package com.krt.gen.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.ReturnBean;
import com.krt.common.util.StringUtils;
import com.krt.gen.dto.GenSetDTO;
import com.krt.gen.entity.GenScheme;
import com.krt.gen.entity.GenTable;
import com.krt.gen.entity.GenTableColumn;
import com.krt.gen.enums.ShowTypeEnum;
import com.krt.gen.service.IGenSchemeService;
import com.krt.gen.service.IGenTableColumnService;
import com.krt.gen.service.IGenTableService;
import com.krt.gen.util.GenUtils;
import com.krt.sys.service.IDicTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 业务表控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年05月03日
 */
@Slf4j
@Controller
public class GenTableController extends BaseController {

    @Autowired
    private IGenTableService genTableService;

    @Autowired
    private IGenTableColumnService genTableColumnService;

    @Autowired
    private IDicTypeService dicTypeService;

    @Autowired
    private IGenSchemeService genSchemeService;

    /**
     * 业务表管理页
     */
    @RequiresPermissions("gen:genTable:list")
    @GetMapping("gen/genTable/list")
    public String list() {
        return "gen/genTable/list";
    }

    /**
     * 业务表管理
     *
     * @param para 搜索参数
     */
    @RequiresPermissions("gen:genTable:list")
    @PostMapping("gen/genTable/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage<GenTable> page = genTableService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 初始化选择数据库表
     */
    @RequiresPermissions("gen:genTable:insert")
    @GetMapping("gen/genTable/selectDbTable")
    public String selectDbTable() {
        //获取数据库表名
        List<GenTable> dbTableList = genTableService.selectDbTable();
        request.setAttribute("dbTableList", dbTableList);
        return "gen/genTable/selectDbTable";
    }

    /**
     * 新增业务表页
     */
    @RequiresPermissions("gen:genTable:insert")
    @GetMapping("gen/genTable/insert")
    public String insert(GenTable genTable) {
        //表名转大驼峰
        String className = StringUtils.underline2Camel(genTable.getName(), false);
        //表信息
        GenTable gTable = genTableService.selectDbTableInfo(genTable.getName());
        //字段信息
        List<GenTableColumn> tableColumnList = genTableColumnService.selectDbTableColumn(genTable);
        //获取数据库表
        List<GenTable> dbTableList = genTableService.selectDbTable();
        //字段类别列表
        List dicTypeList = dicTypeService.selectList();
        request.setAttribute("className", className);
        request.setAttribute("gTable", gTable);
        request.setAttribute("tableColumnList", tableColumnList);
        request.setAttribute("dbTableList", dbTableList);
        request.setAttribute("dicTypeList", dicTypeList);
        return "gen/genTable/insert";
    }

    /**
     * 添加业务表
     *
     * @param genSetDto 表设置
     */
    @KrtLog("添加业务表")
    @RequiresPermissions("gen:genTable:insert")
    @PostMapping("gen/genTable/doInsert")
    @ResponseBody
    public ReturnBean doInsert(GenSetDTO genSetDto) {
        genTableService.doInsert(genSetDto);
        return ReturnBean.ok();
    }

    /**
     * 修改业务表页
     *
     * @param genTable 业务表 id
     */
    @RequiresPermissions("gen:genTable:update")
    @GetMapping("gen/genTable/update")
    public String update(GenTable genTable) {
        //表信息
        GenTable gTable = genTableService.selectById(genTable.getId());
        //字段信息
        List<GenTableColumn> tableColumnList = genTableColumnService.selectDbTableColumn(genTable);
        //获取数据库表名
        List<GenTable> dbTableList = genTableService.selectDbTable();
        //字段类别列表
        List dicTypeList = dicTypeService.selectList();
        //规则
        LambdaQueryWrapper<GenScheme> genTableWrapper = new LambdaQueryWrapper<>();
        genTableWrapper.eq(GenScheme::getTableId, genTable.getId());
        GenScheme genScheme = genSchemeService.selectOne(genTableWrapper);
        request.setAttribute("gTable", gTable);
        request.setAttribute("tableColumnList", tableColumnList);
        request.setAttribute("dbTableList", dbTableList);
        request.setAttribute("dicTypeList", dicTypeList);
        request.setAttribute("genScheme", genScheme);
        return "gen/genTable/update";
    }

    /**
     * 修改业务表
     *
     * @param genSetDto 业务表
     */
    @KrtLog("修改业务表")
    @RequiresPermissions("gen:genTable:update")
    @PostMapping("gen/genTable/update")
    @ResponseBody
    public ReturnBean update(GenSetDTO genSetDto) {
        genTableService.doUpdate(genSetDto);
        return ReturnBean.ok();
    }

    /**
     * 删除业务表
     *
     * @param id 业务表 id
     */
    @KrtLog("删除业务表")
    @RequiresPermissions("gen:genTable:delete")
    @PostMapping("gen/genTable/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        genTableService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除业务表
     *
     * @param ids 业务表 ids
     */
    @KrtLog("批量删除业务表")
    @RequiresPermissions("gen:genTable:delete")
    @PostMapping("gen/genTable/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        genTableService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }

    /**
     * 检查表名
     *
     * @param tableName
     */
    @PostMapping("gen/genTable/checkTableName")
    @ResponseBody
    public ReturnBean checkTableName(String tableName) {
        LambdaQueryWrapper<GenTable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GenTable::getName, tableName);
        GenTable genTable = genTableService.selectOne(wrapper);
        if (genTable != null) {
            return ReturnBean.ok(genTable);
        } else {
            return ReturnBean.error();
        }
    }

    /**
     * 代码生成
     *
     * @param id 业务表 ids
     */
    @KrtLog("代码生成")
    @RequiresPermissions("gen:genTable:genCode")
    @PostMapping("gen/genTable/genCode")
    @ResponseBody
    public ReturnBean genCode(Integer id) throws IOException {
        //获取基本信息
        GenTable genTable = genTableService.selectById(id);
        //获取生成信息
        LambdaQueryWrapper<GenScheme> genTableWrapper = new LambdaQueryWrapper<>();
        genTableWrapper.eq(GenScheme::getTableId, id);
        GenScheme genScheme = genSchemeService.selectOne(genTableWrapper);
        //获取字段信息
        LambdaQueryWrapper<GenTableColumn> genTableColumnWrapper = new LambdaQueryWrapper<>();
        genTableColumnWrapper.eq(GenTableColumn::getTableId, id);
        List<GenTableColumn> genTableColumns = genTableColumnService.selectList(genTableColumnWrapper);
        //设置数据绑定
        getDataGenTable(genTableColumns);
        genTable.setGenTableColumns(genTableColumns);
        genTable.init();
        genTable.setGenScheme(genScheme);
        GenUtils.genCode(genTable);
        return ReturnBean.ok();
    }

    /**
     * 设置数据绑定组件表信息
     *
     * @param genTableColumns
     */
    public void getDataGenTable(List<GenTableColumn> genTableColumns) {
        if (genTableColumns != null) {
            for (GenTableColumn column : genTableColumns) {
                if (ShowTypeEnum.CLIST.getValue().equals(column.getShowType())
                        || ShowTypeEnum.CTREE.getValue().equals(column.getShowType())
                        || ShowTypeEnum.RLIST.getValue().equals(column.getShowType())
                        || ShowTypeEnum.RTREE.getValue().equals(column.getShowType())) {
                    //数据绑定组件
                    LambdaQueryWrapper<GenTable> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(GenTable::getName, column.getDataTable());
                    //关联表信息
                    GenTable dgenTable = genTableService.selectOne(wrapper);
                    if (dgenTable != null) {
                        //关联表生成信息
                        LambdaQueryWrapper<GenScheme> genTableWrapper = new LambdaQueryWrapper<>();
                        genTableWrapper.eq(GenScheme::getTableId, dgenTable.getId());
                        GenScheme genScheme = genSchemeService.selectOne(genTableWrapper);
                        dgenTable.setGenScheme(genScheme);
                        //关联表字段信息
                        LambdaQueryWrapper<GenTableColumn> genTableColumnWrapper = new LambdaQueryWrapper<>();
                        genTableColumnWrapper.eq(GenTableColumn::getTableId, dgenTable.getId());
                        List<GenTableColumn> cgenTableColumns = genTableColumnService.selectList(genTableColumnWrapper);
                        dgenTable.setGenTableColumns(cgenTableColumns);
                        dgenTable.init();
                        column.setGenTable(dgenTable);
                    } else {
                        log.error("{} 设置了数据绑定但绑定的数据表{}没有生成代码，请先生成数据表{}的代码", column.getName(), column.getDataTable(), column.getDataTable());
                    }
                }
            }
        }
    }
}
