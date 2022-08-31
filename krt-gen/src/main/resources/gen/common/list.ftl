<#include "../macro.ftl">
${'<#include "/common/layoutList.ftl">'}
${"<@header>"}${"</@header>"}
${'<@body class="body-bg-default">'}
<div class="wrapper">
    <section class="content">
        <#assign HasQuery = false>
        <#list genTable.genTableColumns as column>
            <#if column.isQuery=='0'>
                <#assign HasQuery = true>
            </#if>
        </#list>
        <#if HasQuery>
            <!-- 搜索条件区 -->
            <div class="box-search">
                <div class="row row-search">
                    <div class="col-xs-12">
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
        <div class="box">
            <div class="box-body">
                <!-- 工具按钮区 -->
                <div class="table-toolbar" id="table-toolbar">
                    <${"@"}shiro.hasPermission name="<#if genTable.genScheme.permissionName??>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:insert">
                        <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip" class="btn btn-success btn-sm">
                            <i class="fa fa-plus"></i> 添加
                        </button>
                    </${"@"}shiro.hasPermission>
                    <${"@"}shiro.hasPermission name="<#if genTable.genScheme.permissionName??>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:delete">
                        <button class="btn btn-sm btn-danger" id="deleteBatchBtn">
                            <i class="fa fa-trash fa-btn"></i>批量删除
                        </button>
                    </${"@"}shiro.hasPermission>
                    <#if genTable.excelOut=='0'>
                        <${"@"}shiro.hasPermission name="<#if genTable.genScheme.permissionName??>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:excelOut">
                            <${"@"}krt.excelOut id="excelOutBtn" url="${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/excelOut"></${"@"}krt.excelOut>
                        </${"@"}shiro.hasPermission>
                    </#if>
                    <#if genTable.excelIn=='0'>
                        <${"@"}shiro.hasPermission name="<#if genTable.genScheme.permissionName??>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:excelIn">
                            <${"@"}krt.excelIn id="excelInBtn" url="${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/excelIn"></${"@"}krt.excelIn>
                        </${"@"}shiro.hasPermission>
                    </#if>
                </div>
                <table id="datatable" class="table table-bordered table-hover"></table>
            </div>
        </div>
    </section>
</div>
${"</@body>"}
${"<@footer>"}
<script type="text/javascript">
    var datatable;
    $(function () {
        //初始化datatable
        datatable = $('#datatable').DataTable({
            ajax: {
                url: "${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/list",
                type: "post",
                data: function (d) {
                    <@datatableParam genTable></@datatableParam>
                }
            },
            columns: [
                <@datatableData genTable '0'></@datatableData>
                {
                    title: "操作", data: "id", orderable: false,
                    "render": function (data, type, row) {
                        return ' <${"@"}shiro.hasPermission name="<#if genTable.genScheme.permissionName??>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:update">'
                            + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                            + '<i class="fa fa-edit fa-btn"></i>修改'
                            + '</button>'
                            + '</${"@"}shiro.hasPermission>'
                            + '<${"@"}shiro.hasPermission name="<#if genTable.genScheme.permissionName??>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:delete">'
                            + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                            + '<i class="fa fa-trash fa-btn"></i>删除'
                            + '</button>'
                            + '</${"@"}shiro.hasPermission>';
                    }
                }
            ]
        });

        //搜索
        $("#searchBtn").on('click', function () {
            datatable.ajax.reload();
        });

        //新增
        $("#insertBtn").click(function () {
            <#if formType=='tab'>
            top.krt.tab.openTab("新增${genTable.comment}","${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/insert");
            </#if>
            <#if formType=='dialog'>
            top.krt.layer.openDialog("新增${genTable.comment}","${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/insert","1000px","500px");
            </#if>
        });

        //修改
        $(document).on("click", ".updateBtn", function () {
            var id = $(this).attr("rid");
            <#if formType=='tab'>
            top.krt.tab.openTab("修改${genTable.comment}","${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/update?id=" + id);
            </#if>
            <#if formType=='dialog'>
            top.krt.layer.openDialog("修改${genTable.comment}","${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/update?id=" + id,"1000px","500px");
            </#if>
        });

        //删除
        $(document).on("click", ".deleteBtn", function () {
            var id = $(this).attr("rid");
            krt.layer.confirm("你确定删除吗？", function () {
                krt.ajax({
                    type: "POST",
                    url: "${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/delete",
                    data: {"id": id},
                    success: function (rb) {
                        krt.layer.msg(rb.msg);
                        if (rb.code == 200) {
                            krt.table.reloadTable();
                        }
                    }
                });
            });
        });

        //批量删除
        $("#deleteBatchBtn").click(function () {
            var ids = getIds();
            if (ids == "") {
                krt.layer.error("请选择要删除的数据!");
                return false;
            } else {
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/deleteByIds",
                        data: {"ids": ids},
                        success: function (rb) {
                            krt.layer.msg(rb.msg);
                            if (rb.code == 200) {
                                krt.table.reloadTable(ids);
                            }
                        }
                    });
                });
            }
        });
    });
</script>
${"</@footer>"}
