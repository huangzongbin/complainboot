<#include "../header.ftl">
<#include "../query.ftl">
<#include "../list.ftl">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<@header genTable 'list'></@header>
</head>
<body class="bg-color">
<div class="wrapper">
    <!-- Main content -->
    <section class="content">
    <#assign HasQuery = false>
    <#list genTable.genTableColumns as column>
        <#if column.isQuery=='0'>
            <#assign HasQuery = true>
        </#if>
    </#list>
    <#if HasQuery>
        <!-- 搜索条件区 -->
        <div class="search" id="search">
            <div class="panel-body">
                <div class="col-sm-12">
                    <form class="form-inline" action="">
                        <@query genTable></@query>
                        <div class="form-group">
                            <div class="text-center">
                                <button type="button" id="searchBtn" class="btn btn-primary btn-sm">
                                    <i class="fa fa-search fa-btn"></i>搜索
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </#if>
        <!-- 列表数据区 -->
        <div class="box-krt">
            <div class="box-body">
                <div class="table-responsive">
                    <!-- 工具按钮区 -->
                    <div class="table-button">
                        <shiro:hasPermission name="<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:insert">
                            <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip" class="btn btn-success btn-sm">
                                <i class="fa fa-plus"></i> 添加
                            </button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:delete">
                            <button class="btn btn-sm btn-danger" id="deleteBatchBtn">
                                <i class="fa fa-trash fa-btn"></i>批量删除
                            </button>
                        </shiro:hasPermission>
                    <#if genTable.excelOut=='0'>
                        <shiro:hasPermission name="<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:excelOut">
                            <tags:excelOut id="excelOutBtn" url="${"$"}{basePath}${namespace}/${genTable.className?uncap_first}/excelOut"/>
                        </shiro:hasPermission>
                    </#if>
                    <#if genTable.excelIn=='0'>
                        <shiro:hasPermission name="<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:excelIn">
                            <tags:excelIn id="excelInBtn" url="${"$"}{basePath}${namespace}/${genTable.className?uncap_first}/excelIn"/>
                        </shiro:hasPermission>
                    </#if>
                    </div>
                    <table id="datatable" class="table table-bordered table-hover table-krt">
                        <thead>
                        <tr>
                            <th><input type="checkbox" id="checkAll" class="iCheck"></th>
                        <@list genTable></@list>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
</div>
<script type="text/javascript">
    var datatable;
    function initDatatable() {
        datatable = $('#datatable').DataTable({
        <@datatableOrder genTable></@datatableOrder>
            "ajax": {
                "url": "${"$"}{basePath}${namespace}/${genTable.className?uncap_first}/list",
                "type": "post",
                "data": function (d) {
                <@datatableParam genTable></@datatableParam>
                }
            },
            "columns": [
            <@datatableData genTable '0'></@datatableData>
                {
                    "title": "操作",
                    "data": "id",
                    <#if genTable.order=='0'>
                        "orderable": false,
                    </#if>
                    "render": function (data, type, row) {
                        return ' <shiro:hasPermission name="<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:update">'
                                + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>修改'
                                + '</button>'
                                + '</shiro:hasPermission>'
                                + '<shiro:hasPermission name="<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:delete">'
                                + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-trash fa-btn"></i>删除'
                                + '</button>'
                                + '</shiro:hasPermission>';
                    }
                }
            ]
        });
    }

    $(function () {

        //初始化datatable
        initDatatable();

        $("#searchBtn").on('click', function () {
            datatable.ajax.reload();
        });

        //新增
        $("#insertBtn").click(function () {
            openDialog("新增表单", "${"$"}{basePath}${namespace}/${genTable.className?uncap_first}/insert", "1000px", "600px");
        });

        //修改
        $(document).on("click", ".updateBtn", function () {
            var id = $(this).attr("rid");
            openDialog("修改表单", "${"$"}{basePath}${namespace}/${genTable.className?uncap_first}/update?id=" + id, "1000px", "600px");
        });

        //删除
        $(document).on("click", ".deleteBtn", function () {
            var id = $(this).attr("rid");
            confirmx("你确定删除吗？", function () {
                $.ajax({
                    type: "POST",
                    url: "${"$"}{basePath}${namespace}/${genTable.className?uncap_first}/delete",
                    data: {"id": id},
                    beforeSend: function () {
                        return loading();
                    },
                    success: function (rb) {
                        closeloading();
                        if (rb.code == 0) {
                            top.layer.msg(rb.msg);
                            reloadTable(datatable);
                        } else {
                            top.layer.msg(rb.msg);
                        }
                    },
                    error: function () {
                        closeloading();
                    }
                });
            });
        });

        //批量删除
        $("#deleteBatchBtn").click(function () {
            var ids = getIds();
            if (ids == "") {
                top.layer.msg("请选择要删除的数据!");
                return false;
            } else {
                ids = ids.substr(0, ids.length - 1);
                confirmx("你确定删除吗？", function () {
                    $.ajax({
                        type: "POST",
                        url: "${"$"}{basePath}${namespace}/${genTable.className?uncap_first}/deleteByIds",
                        data: {"ids": ids},
                        beforeSend: function () {
                            return loading();
                        },
                        success: function (rb) {
                            closeloading();
                            if (rb.code == 0) {
                                top.layer.msg(rb.msg);
                                reloadTable(datatable);
                            } else {
                                top.layer.msg(rb.msg);
                            }
                        },
                        error: function () {
                            closeloading();
                        }
                    });
                });
            }
        });
    });
</script>
</body>
</html>
