<#include "../controller.ftl">
package ${genTable.genScheme.packageName}.controller;

import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.ReturnBean;
import ${genTable.genScheme.packageName}.entity.${genTable.className};
import ${genTable.genScheme.packageName}.service.${genTable.className}Service;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;
<#if genTable.cgenTables?exists>
<#list genTable.cgenTables as cgenTables>
import ${cgenTables.genScheme.packageName}.service.${cgenTables.className}Service;
</#list>
</#if>
<@dataImport genTable></@dataImport>
<@excelInImport genTable></@excelInImport>
<@excelOutImport genTable></@excelOutImport>

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
    private ${genTable.className}Service ${genTable.className?uncap_first}Service;
<#if genTable.cgenTables?exists>
    <#list genTable.cgenTables as cgenTables>

    @Autowired
    private ${cgenTables.className}Service ${cgenTables.className?uncap_first}Service;
    </#list>
</#if>

    /**
     * ${genTable.comment}管理页
     *
     * @return
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
     * @return
     */
    @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:list")
    @PostMapping("${namespace}/${genTable.className?uncap_first}/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        DataTable dt = ${genTable.className?uncap_first}Service.selectList(para);
        return dt;
    }

    /**
     * 新增${genTable.comment}页
     *
     * @return
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
     * @return
     */
    @KrtLog(description = "添加${genTable.comment}")
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
     * @param id ${genTable.comment} id
     * @return
     */
    @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:update")
    @GetMapping("${namespace}/${genTable.className?uncap_first}/update")
    public String update(Integer id) {
        Map ${genTable.className?uncap_first}Map = ${genTable.className?uncap_first}Service.selectById(id);
        request.setAttribute("${genTable.className?uncap_first}", ${genTable.className?uncap_first}Map);
<#if genTable.cgenTables?exists>
    <#list genTable.cgenTables as cgenTables>
        List ${cgenTables.className?uncap_first}List = ${cgenTables.className?uncap_first}Service.selectBy${cgenTables.parentTableFkTf?cap_first}(id);
        request.setAttribute("${cgenTables.className?uncap_first}List", ${cgenTables.className?uncap_first}List);
    </#list>
</#if>
        return "${namespace}/${genTable.className?uncap_first}/update";
    }

    /**
     * 修改${genTable.comment}
     *
     * @param ${genTable.className?uncap_first} ${genTable.comment}
     * @return
     */
    @KrtLog(description = "修改${genTable.comment}")
    @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:update")
    @PostMapping("${namespace}/${genTable.className?uncap_first}/update")
    @ResponseBody
    public ReturnBean update(${genTable.className} ${genTable.className?uncap_first}) {
        ${genTable.className?uncap_first}Service.update(${genTable.className?uncap_first});
        return ReturnBean.ok();
    }

    /**
     * 删除${genTable.comment}
     *
     * @param id ${genTable.comment} id
     * @return
     */
    @KrtLog(description = "删除${genTable.comment}")
    @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:delete")
    @PostMapping("${namespace}/${genTable.className?uncap_first}/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        ${genTable.className?uncap_first}Service.delete(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除${genTable.comment}
     *
     * @param ids ${genTable.comment} ids
     * @return
     */
    @KrtLog(description = "批量删除${genTable.comment}")
    @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:delete")
    @PostMapping("${namespace}/${genTable.className?uncap_first}/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(String ids) {
        ${genTable.className?uncap_first}Service.deleteByIds(ids);
        return ReturnBean.ok();
    }

    <@excelInMethod genTable></@excelInMethod>

    <@excelOutMethod genTable></@excelOutMethod>
}
