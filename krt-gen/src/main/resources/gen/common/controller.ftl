<#include "../macro.ftl">
package ${genTable.genScheme.packageName}.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.ReturnBean;
import ${genTable.genScheme.packageName}.entity.${genTable.className};
import ${genTable.genScheme.packageName}.service.I${genTable.className}Service;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;
import java.util.Map;
<@excelInImport genTable></@excelInImport>
<@excelOutImport genTable></@excelOutImport>
<@dataImport genTable></@dataImport>

/**
 * ${genTable.comment}控制层
 *
 * @author ${genTable.genScheme.coder}
 * @version 1.0
 * @date ${.now?string("yyyy年MM月dd日")}
 */
@Controller
public class ${genTable.className}Controller extends BaseController {

    @Autowired
    private I${genTable.className}Service ${genTable.className?uncap_first}Service;

    /**
     * ${genTable.comment}管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:list")
    @GetMapping("${namespace}/${genTable.className?uncap_first}/list")
    public String list() {
        return "${namespace}/${genTable.className?uncap_first}/list";
    }

    /**
     * ${genTable.comment}管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:list")
    @PostMapping("${namespace}/${genTable.className?uncap_first}/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = ${genTable.className?uncap_first}Service.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增${genTable.comment}页
     *
     * @return {@link String}
     */
    @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:insert")
    @GetMapping("${namespace}/${genTable.className?uncap_first}/insert")
    public String insert() {
        return "${namespace}/${genTable.className?uncap_first}/insert";
    }

    /**
     * 添加${genTable.comment}
     *
     * @param ${genTable.className?uncap_first} ${genTable.comment}
     * @return {@link ReturnBean}
     */
    @KrtLog("添加${genTable.comment}")
    @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:insert")
    @PostMapping("${namespace}/${genTable.className?uncap_first}/insert")
    @ResponseBody
    public ReturnBean insert(${genTable.className} ${genTable.className?uncap_first}) {
        ${genTable.className?uncap_first}Service.insert(${genTable.className?uncap_first});
        return ReturnBean.ok();
    }

    /**
     * 修改${genTable.comment}页
     *
     * @param id ${genTable.comment}id
     * @return {@link String}
     */
    @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:update")
    @GetMapping("${namespace}/${genTable.className?uncap_first}/update")
    public String update(Integer id) {
        ${genTable.className} ${genTable.className?uncap_first} = ${genTable.className?uncap_first}Service.selectById(id);
        request.setAttribute("${genTable.className?uncap_first}", ${genTable.className?uncap_first});
        return "${namespace}/${genTable.className?uncap_first}/update";
    }

    /**
     * 修改${genTable.comment}
     *
     * @param ${genTable.className?uncap_first} ${genTable.comment}
     * @return {@link ReturnBean}
     */
    @KrtLog("修改${genTable.comment}")
    @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:update")
    @PostMapping("${namespace}/${genTable.className?uncap_first}/update")
    @ResponseBody
    public ReturnBean update(${genTable.className} ${genTable.className?uncap_first}) {
        ${genTable.className?uncap_first}Service.updateById(${genTable.className?uncap_first});
        return ReturnBean.ok();
    }

    /**
     * 删除${genTable.comment}
     *
     * @param id ${genTable.comment}id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除${genTable.comment}")
    @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:delete")
    @PostMapping("${namespace}/${genTable.className?uncap_first}/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        ${genTable.className?uncap_first}Service.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除${genTable.comment}
     *
     * @param ids ${genTable.comment}ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除${genTable.comment}")
    @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:delete")
    @PostMapping("${namespace}/${genTable.className?uncap_first}/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        ${genTable.className?uncap_first}Service.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }

    <@excelInMethod genTable></@excelInMethod>

    <@excelOutMethod genTable></@excelOutMethod>
}
