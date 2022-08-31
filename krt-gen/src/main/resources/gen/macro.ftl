<#include "component.ftl">
<#--
 @author 殷帅
 @version 1.0
 @Description: 公共的宏标签
 @date 2018-06-06 15:50:00
 -->

<#-- --------------------------------MapperXml--------------------------------------- -->

<#-- 分页查询条件 -->
<#macro mapperQuery genTable>
    <#list genTable.genTableColumns as column>
        <#if column.isQuery?? && column.isQuery=='0'>
            <if test="${column.javaField} !=null and ${column.javaField}!=''">
                <#if column.queryType?? && column.queryType=='1'>
                    <#if column.showType=='checkbox'>
                        and find_in_set(${column.name},${"#"}{${column.javaField}})
                    </#if>
                    <#if column.showType!='checkbox'>
                        and ${column.name} = ${"#"}{${column.javaField}}
                    </#if>
                </#if>
                <#if column.queryType?? && column.queryType=='2'>
                    and ${column.name} != ${"#"}{${column.javaField}}
                </#if>
                <#if column.queryType?? && column.queryType=='3'>
                    <![CDATA[ and ${column.name} > ${"#"}{${column.javaField}} ]]>
                </#if>
                <#if column.queryType?? && column.queryType=='4'>
                    <![CDATA[ and ${column.name} >= ${"#"}{${column.javaField}} ]]>
                </#if>
                <#if column.queryType?? && column.queryType=='5'>
                    <![CDATA[ and ${column.name} < ${"#"}{${column.javaField}} ]]>
                </#if>
                <#if column.queryType?? && column.queryType=='6'>
                    <![CDATA[ and ${column.name} <= ${"#"}{${column.javaField}} ]]>
                </#if>
                <#if column.queryType?? && column.queryType=='7'>
                    and ${column.name} like concat('%',${"#"}{${column.javaField}},'%')
                </#if>
                <#if column.queryType?? && column.queryType=='8'>
                    and ${column.name} like concat('%',${"#"}{${column.javaField}})
                </#if>
                <#if column.queryType?? && column.queryType=='9'>
                    and ${column.name} like concat(${"#"}{${column.javaField}},'%')
                </#if>
            </if>
        </#if>
    </#list>
</#macro>

<#-- 导出excel -->
<#macro excelOut genTable>
    <#if genTable.excelOut=='0'>
        <!-- 导出数据 -->
        <select id="selectExcelList" resultType="java.util.Map">
            select
            <#list genTable.genTableColumns as column><#if column.excelOut='0'>${column.name},</#if></#list>'1'
            from ${genTable.name}
        </select>
    </#if>
</#macro>

<#-- --------------------------------controller--------------------------------------- -->

<#--
 导出需要引入包
-->
<#macro excelOutImport genTable>
    <#if genTable.excelOut=='0'>
import java.util.ArrayList;
        <#if genTable.excelIn=='1'>
import java.util.List;
        </#if>
import org.springframework.ui.ModelMap;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
    </#if>
</#macro>

<#-- 导入需要引入包 -->
<#macro excelInImport genTable>
    <#if genTable.excelIn=='0'>
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.krt.common.util.ServletUtils;
import org.springframework.web.multipart.MultipartFile;
import com.krt.common.validator.Assert;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
    </#if>
</#macro>


<#-- 单选、多选Tree/List需要引入包 -->
<#macro dataImport genTable>
    <#if genTable.RTree=='0' || genTable.CTree=='0' || genTable.RList=='0' || genTable.CList=='0'>
        import com.krt.common.util.StringUtils;
    </#if>
</#macro>

<#-- 导入模板 -->
<#macro excelInMethod genTable>
    <#if genTable.excelIn=='0'>
        /**
        * 导入${genTable.comment}
        *
        * @param file excel文件
        * @throws Exception 异常
        */
        @KrtLog("导入${genTable.comment}")
        @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:excelIn")
        @PostMapping("${namespace}/${genTable.className?uncap_first}/excelIn")
        public void excelIn(@RequestParam("file") MultipartFile file) throws Exception {
            Assert.isExcel(file);
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setNeedSave(false);
            //读取excel
            List<${genTable.className}> dataList = ExcelImportUtil.importExcel(file.getInputStream(), ${genTable.className}.class, params);
            ${genTable.className?uncap_first}Service.insertBatch(dataList);
            ServletUtils.printText(response, JSON.toJSONString(ReturnBean.ok()));
        }
    </#if>
</#macro>


<#-- 导出模板 -->
<#macro excelOutMethod genTable>
    <#if genTable.excelOut=='0'>
        /**
        * 导出${genTable.comment}
        *
        * @param modelMap 返回模型
        * @param para 参数
        */
        @KrtLog("导出${genTable.comment}")
        @RequiresPermissions("<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:excelOut")
        @GetMapping("${namespace}/${genTable.className?uncap_first}/excelOut")
        public String excelOut(ModelMap modelMap,Map para) {
            List${"<"}ExcelExportEntity${">"} entityList = new ArrayList<>();
        <#list genTable.genTableColumns as column>
            <#if column.excelOut=='0'>
                entityList.add(new ExcelExportEntity("${column.comment}", "${column.javaField}", 15));
            </#if>
        </#list>
            List dataResult = ${genTable.className?uncap_first}Service.selectExcelList(para);
            modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
            modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
            modelMap.put(MapExcelConstants.FILE_NAME, "${genTable.comment}");
            modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("${genTable.comment}", "${genTable.comment}"));
            return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
        }
    </#if>
</#macro>

<#-- --------------------------------list--------------------------------------- -->


<#--
 搜索条件

 @author 殷帅
 @version 1.0
 @date 2018-07-14 15:50:00
 -->
<#macro query genTable>
    <#list genTable.genTableColumns as column>
        <#if column.isQuery=='0'>
            <div class="form-group">
                <label <#if column.showType!='radio' && column.showType!='checkbox'>for="${column.javaField}"</#if> class="control-label ">${column.comment}:</label>
                <@component column genTable 'query' 'n'></@component>
            </div>
        </#if>
    </#list>
</#macro>
<#--
 datatable获取查询参数组件
 genTable 表属性

 @author 殷帅
 @version 1.0
 @date 2018-07-14 15:50:00
 -->
<#macro datatableParam genTable>
    <#list genTable.genTableColumns as column>
        <#if column.isQuery=='0'>
            <#if column.showType=='checkbox'>
                d.${column.javaField} = function () {
                var ${column.javaField}CheVal = "";
                $('input[name="${column.javaField}"]:checked').each(function(){
                ${column.javaField}CheVal = ${column.javaField}CheVal + ${"$"}(this).val() + ",";
                });
                if(${column.javaField}CheVal.length>0){
                ${column.javaField}CheVal = ${column.javaField}CheVal.substr(0,${column.javaField}CheVal.length-1);
                }
                return ${column.javaField}CheVal;
                };
            </#if>
            <#if column.showType=='radio'>
                d.${column.javaField} = ${"$"}("input[name='${column.javaField}']:checked").val();
            </#if>
            <#if column.showType!='checkbox' && column.showType!='radio'>
                d.${column.javaField} = ${"$"}("#${column.javaField}").val();
            </#if>
        </#if>
    </#list>
    d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
    d.orderType = d.order[0].dir;
</#macro>


<#--
 datatable循环数据组件
 genTable 表属性
 hasCheck: 是否有复选框 '0' 有

 @author 殷帅
 @version 1.0
 @date 2018-07-14 15:50:00
 -->
<#macro datatableData genTable hasCheckBox>
    {title: 'id', data: "id",visible:false},
    <#if hasCheckBox=='0'>
        {title: '<input type="checkbox" id="checkAll" class="icheck">',
            data: "id", class:"td-center", width:"40",orderable: false,
            render: function (data) {
                return '<input type="checkbox" class="icheck check" value="' + data + '">';
            }
        },
    </#if>
    <#list genTable.genTableColumns as column>
        <#if column.isList=='0'>
            <#if column.dicType?? && column.dicType!=''>
                {title: "${column.comment}",data: "${column.javaField}",
                    render: function (data) {
                        return krt.util.getDic('${column.dicType}',data);
                    }
                },
            <#else>
                {title: "${column.comment}",data: "${column.javaField}"},
            </#if>
        </#if>
    </#list>
</#macro>

<#-- --------------------------------insert or update--------------------------------------- -->
<#--
表单宏
genTable：表信息
doType 操作类型 insert：添加 update:修改

@author 殷帅
@version 1.0
@date 2019-01-07 14:50:00
-->
<#macro form genTable doType>
    <#assign isEnd = true>
    <#assign gAll = 0>
    <#list genTable.genTableColumns as column>
        <#list column.grid?split("/") as col>
         <#if col_index==0><#assign g0=col/></#if>
         <#if col_index==1><#assign g1=col/></#if>
         <#if col_index==2><#assign g2=col/></#if>
        </#list>
        <#if column.isInsert=='0' && column.showType?? && column.showType !='hidden'>
        <#if gAll==0>
        <div class="row">
        </#if>
        <#if gAll!=0 && g0?number ==12>
        <#assign gAll = 0>
        </div>
        <div class="row">
        </#if>
            <div class="col-sm-${g0}">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-${g1}">
                         <#if (column.validate)?? && (column.validate)?contains("required")><span class="form-required">*</span></#if>${column.comment}：
                    </label>
                    <div class="col-sm-${g2}">
                        <@component column genTable doType '-1'/>
                    </div>
                </div>
            </div>
            <#assign gAll = gAll+g0?number>
            <#if gAll==12>
                </div>
                <#assign isEnd = !isEnd>
                <#assign gAll = 0>
            </#if>
        </#if>
    </#list>
    <!-- 隐藏域 -->
    <#list genTable.genTableColumns as column>
        <#if column.isInsert=='0' && column.showType?? && column.showType=='hidden' && doType=='insert'>
            <input type="hidden" name="${column.javaField}" id="${column.javaField}">
        </#if>
        <#if column.isEdit=='0' && column.showType?? && column.showType=='hidden' && doType=='update'>
            <input type="hidden" name="${column.javaField}" id="${column.javaField}" value="${"$"}{${genTable.className?uncap_first}.${column.javaField}!}">
        </#if>
    </#list>
</#macro>

<#--
表单按钮宏
genTable：表信息

@author 殷帅
@version 1.0
@date 2019-01-07 19:50:00
-->
<#macro formButton genTable>
    <#list genTable.genTableColumns as column>
        <#if column.showType?? && column.showType!='hidden'>
                <#list column.grid?split("/") as col>
                 <#if col_index==0><#assign g0=col/></#if>
                 <#if col_index==1><#assign g1=col/></#if>
                 <#if col_index==2><#assign g2=col/></#if>
                </#list>
             <#break>
        </#if>
    </#list>
    <#assign g3=((g1?number*g0?number)/12)/>
    <div class=" col-sm-offset-${g3} col-sm-${12-g3?number}">
        <button type="button" id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> 提交</button>
        <button type="button" id="closeIframeBtn" class="btn btn-danger"><i class="fa fa-close"></i> 关闭</button>
        <button type="reset" id="resetBtn" class="btn btn-default"><i class="fa fa-refresh"></i> 重置</button>
    </div>
</#macro>