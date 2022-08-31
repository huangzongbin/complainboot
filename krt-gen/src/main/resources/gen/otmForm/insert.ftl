<#include "../header.ftl">
<#include "../table.ftl">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<@header genTable 'insert'></@header>
</head>
<body>
<div class="wrapper">
    <div class="form-box">
        <form action="#" id="krtForm" class="form-horizontal">
            <table class="table table-bordered table-krt">
            <@table genTable 'insert'></@table>
            </table>
        <#if genTable.cgenTables?exists>
            <#list genTable.cgenTables as cgenTables>
                <table class="table table-bordered table-krt">
                    <tr class="${cgenTables.className?uncap_first}_tr">
                        <#list cgenTables.genTableColumns as column>
                            <#if column.isInsert=='0'><th class="active">${column.comment}</th></#if>
                        </#list>
                        <th class="active">
                            <button title="添加" type="button" onclick="add_${cgenTables.className?uncap_first}()" data-placement="left" data-toggle="tooltip" class="btn btn-success btn-sm">
                                <i class="fa fa-plus"></i> 新增
                            </button>
                        </th>
                    </tr>
                </table>
            </#list>
        </#if>
        </form>
    </div>
<#if genTable.cgenTables?exists>
    <#list genTable.cgenTables as cgenTables>
        <!-- ${cgenTables.className} 模板 -->
        <templet id="temp_${cgenTables.className?uncap_first}" style="display: none">
            <trr class="${cgenTables.className?uncap_first}_tr">
                <#list cgenTables.genTableColumns as column>
                    <#if column.isInsert=='0'>
                        <tdd>
                            <@component column cgenTables 'insert' 'xxx'></@component>
                        </tdd>
                    </#if>
                </#list>
                <tdd>
                    <input type='hidden' name='${cgenTables.className?uncap_first}[xxx].id' value='0'>
                    <input type='button' value='删除' class='btn btn-danger btn-sm' onclick='remove_${cgenTables.className?uncap_first}(this.parentNode.parentNode)'>
                </tdd>
            </trr>
        </templet>
    </#list>
</#if>
</div><!-- ./wrapper -->
<script type="text/javascript">
    <#if genTable.cgenTables?exists>
        <#list genTable.cgenTables as cgenTables>

        var ${cgenTables.className?uncap_first}_count = 0;
        function remove_${cgenTables.className?uncap_first}(obj) {
            $(obj).remove();
            ${cgenTables.className?uncap_first}_count--;
            validate();
        }
        function add_${cgenTables.className?uncap_first}() {
            var ${cgenTables.className?uncap_first}Tmpl = $("#temp_${cgenTables.className?uncap_first}").html().replace(/trr/g, 'tr').replace(/tdd/g, 'td').replace(/xxx/g, ${cgenTables.className?uncap_first}_count);
            $("tr[class^=${cgenTables.className?uncap_first}_tr]:last").after(${cgenTables.className?uncap_first}Tmpl);
            ${cgenTables.className?uncap_first}_count++;
            validate();
        }
        </#list>
    </#if>
    var validateForm;
    function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
        $.ajax({
            type: "POST",
            url: "${"$"}{basePath}${namespace}/${genTable.className?uncap_first}/insert",
            data: $('#krtForm').serialize(),// 要提交的表单
            beforeSend: function () {
                return validateForm.form() && loading();
            },
            success: function (rb) {
                closeloading();
                if (rb.code == 0) {
                    top.layer.msg(rb.msg);
                    var index = top.layer.getFrameIndex(window.name); //获取窗口索引
                    reloadTable();
                    top.layer.close(index);
                } else {
                    top.layer.msg(rb.msg);
                }
            },
            error: function () {
                closeloading();
            }
        });
    }
    function validate() {
        validateForm = $("#krtForm").validate({
            ignore: "",
            errorContainer: "#messageBox",
            errorPlacement: function (error, element) {
                $("#messageBox").text("输入有误，请先更正。");
                if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-group")) {
                    error.appendTo(element.parent().parent());
                } else {
                    error.insertAfter(element);
                }
            }
        });
    }

    $(function () {
        validate();
        //立即验证
        validateForm.form();
    });
</script>
</body>
</html>
