<#include "../macro.ftl">
package ${genTable.genScheme.packageName}.controller;

import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.ReturnBean;
import com.krt.common.constant.GlobalConstant;
import ${genTable.genScheme.packageName}.entity.${genTable.className};
import ${genTable.genScheme.packageName}.service.I${genTable.className}Service;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
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
     * @param pid 父id
     * @return {@link ReturnBean}
     */
    @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:list")
    @PostMapping("${namespace}/${genTable.className?uncap_first}/list")
    @ResponseBody
    public ReturnBean list(Integer pid) {
        List list = ${genTable.className?uncap_first}Service.selectByPid(pid);
        return ReturnBean.ok(list);
    }
    /**
     * 新增${genTable.comment}页
     *
     * @return {@link String}
     */
    @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:insert")
    @GetMapping("${namespace}/${genTable.className?uncap_first}/insert")
    public String insert(Integer pid) {
        if (pid != null) {
            ${genTable.className} p${genTable.className} = ${genTable.className?uncap_first}Service.selectById(pid);
            request.setAttribute("p${genTable.className}", p${genTable.className});
        }
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
        if (${genTable.className?uncap_first}.getPid() == null) {
            ${genTable.className?uncap_first}.setPid(GlobalConstant.DEFAULT_PID);
        }
        ${genTable.className?uncap_first}Service.insert(${genTable.className?uncap_first});
        return ReturnBean.ok();
    }

    /**
     * 修改${genTable.comment}页
     *
     * @param id ${genTable.comment} id
     * @return {@link String}
     */
    @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:update")
    @GetMapping("${namespace}/${genTable.className?uncap_first}/update")
    public String update(Integer id) {
        ${genTable.className} ${genTable.className?uncap_first} = ${genTable.className?uncap_first}Service.selectById(id);
        Integer pid = ${genTable.className?uncap_first}.getPid();
        if (!GlobalConstant.DEFAULT_PID.equals(pid)) {
            ${genTable.className} p${genTable.className} = ${genTable.className?uncap_first}Service.selectById(pid);
            request.setAttribute("p${genTable.className}", p${genTable.className});
        }
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
     * @param id ${genTable.comment} id
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
     * 获取${genTable.comment}树数据
     *
     * @return {@link ReturnBean}
     */
    @PostMapping("${namespace}/${genTable.className?uncap_first}/${genTable.className?uncap_first}Tree")
    @ResponseBody
    public ReturnBean ${genTable.className?uncap_first}Tree() {
        List list = ${genTable.className?uncap_first}Service.selectList();
        return ReturnBean.ok(list);
    }

    <@excelInMethod genTable></@excelInMethod>

    <@excelOutMethod genTable></@excelOutMethod>
}
