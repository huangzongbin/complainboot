<#include "./macro.ftl">
${'<#include "/common/layoutList.ftl">'}
${"<@header>"}${"</@header>"}
${'<@body class="body-bg-default">'}
<div class="wrapper">
    <section class="content">
        <!-- 参数 -->
        <input type="hidden" id="sName" value="${"$"}{RequestParameters['sName']!}"/>
        <input type="hidden" id="sId" value="${"$"}{RequestParameters['sId']!}"/>
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
                url: "${"$"}{basePath}/${namespace}/${column.genTable.className?uncap_first}/list",
                type: "post",
                data: function (d) {
                    <@datatableParam column.genTable></@datatableParam>
                }
            },
            columns: [
                <@datatableData column.genTable '1'></@datatableData>
                <#assign attrName ='id'/>
                <#list column.genTable.genTableColumns as column>
                <#if column.isList=='0' && attrName=='id'>
                <#assign attrName=column.javaField/>
                </#if>
                </#list>
                {
                    <#if column.showType == 'rList'>
                    title: "操作",
                    </#if>
                    <#if column.showType == 'cList'>
                    title: '<input type="checkbox" id="checkAll" class="icheck">',
                    </#if>
                    data: "id", orderable: false, class:"td-center", width:"40",
                    "render": function (data, type, row) {
                        var checked = "";
                        <#if column.showType=='cList'>
                        var sId = "," + $("#sId").val() + ",";
                        var id = "," + row.id + ",";
                        if (sId.indexOf(id) > -1) {
                            checked = "checked";
                        }
                        return '<input type="checkbox" ' + checked + ' class="icheck cCheck" value="' + data + '" rname="' + row.${attrName!} + '" rid="' + row.id + '">';
                        </#if>
                        <#if column.showType=='rList'>
                        var sId = $("#sId").val();
                        if (sId == data) {
                            checked = "checked";
                        }
                        return '<input type="radio" ' + checked + ' class="icheck rCheck" name="check" value="' + data + '" rname="' + row.${attrName!} + '" rid="' + row.id + '">';
                        </#if>
                    }
                }
            ],
            "fnDrawCallback": function () {
                <#if column.showType=='cList'>
                cCheck();
                </#if>
                <#if column.showType=='rList'>
                rCheck();
                </#if>
            }
        });

        $("#searchBtn").on('click', function () {
            $("#sName").val("");
            $("#sId").val("");
            datatable.ajax.reload();
        });
    })
</script>
${"</@footer>"}
