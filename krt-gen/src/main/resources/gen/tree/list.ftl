<#include "../macro.ftl">
${'<#include "/common/layoutList.ftl">'}
${"<@header>"}${"</@header>"}
${'<@body class="body-bg-default">'}
<div class="wrapper">
    <section class="content">
        <div class="row">
            <div class="col-sm-12">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">${(genTable.comment)!}管理</h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body" id="boxbody">
                        <div class="table-toolbar" id="table-toolbar">
                            <${"@"}shiro.hasPermission name="<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:insert">
                                <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip" class="btn btn-success btn-sm">
                                    <i class="fa fa-plus"></i> 添加
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
                            <button id="expandTableBtn" data-placement="left" class="btn bg-orange btn-sm">
                                <i class="fa fa-chevron-down"></i> 全部展开
                            </button>
                            <button id="collapseTableBtn" data-placement="left" class="btn bg-purple btn-sm" style="display:none;">
                                <i class="fa fa-chevron-up"></i> 全部收缩
                            </button>
                        </div>
                        <div class="table-responsive" id="table-body">
                            <table id="tree-table" class="table table-bordered table-hover"></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
${"</@body>"}
${"<@footer>"}
<script type="text/javascript">
    /******************************** treetable事件 ********************************/

    //初始化treeTable
    function initTreeTable() {
        $("#tree-table").treetable({
            expandable: true,
            onNodeExpand: nodeExpand,
            onNodeCollapse: nodeCollapse
        });
    }

    //刷新table
    function reloadTable() {
        initTable(0);
    }

    //展开事件
    function nodeExpand() {
        getNodeViaAjax(this.id);
    }

    //收缩事件
    function nodeCollapse() {

    }

    //加载父表
    function initTable(pid) {
        $("#pid").val(pid);
        krt.ajax({
            type: "POST",
            url: "${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/list",
            data: {"pid": pid},
            success: function (rb) {
                if (rb.code == 200) {
                    var treeList = rb.data;
                    var tbody = '';
                    for (var i = 0; i < treeList.length; i++) {
                        var row = treeList[i];
                        var hasChild = "";
                        if (row.hasChild == 'true') {
                            hasChild = 'data-tt-branch="true"';
                        }
                        var tr = '<tr data-tt-id="' + row.id + '" ' + hasChild + '>'
                            <#list genTable.genTableColumns as column>
                            <#if column.isList=='0'>
                            <#if column.dicType?? && column.dicType!=''>
                            + "<td>" + krt.util.getDic('${column.dicType}', row.${column.javaField}) + "</td>"
                            <#else>
                            + "<td>" + krt.util.isNull(row.${column.javaField}) + "</td>"
                            </#if>
                            </#if>
                            </#list>
                            + "<td>"
                            + '<${"@"}shiro.hasPermission name="<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:update"><button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '" pid="' + row.pid + '"><i class="fa fa-edit fa-btn"></i>修改</button></${"@"}shiro.hasPermission>'
                            + '<${"@"}shiro.hasPermission name="<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:delete"><button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '"><i class="fa fa-trash fa-btn"></i>删除</button></${"@"}shiro.hasPermission>'
                            + '<${"@"}shiro.hasPermission name="<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:insert"><button class="btn btn-xs btn-success addBtn" rid="' + row.id + '"><i class="fa fa-plus fa-btn"></i>添加子集</button></${"@"}shiro.hasPermission>'
                            + "</td>"
                            + "</tr>";
                        tbody = tbody + tr;
                    }
                    $("#tree-table").remove();
                    var html = '<table id="tree-table" class="table table-bordered table-hover table-krt"><thead><tr><#list genTable.genTableColumns as column><#if column.isList=='0'><th>${column.comment}</th></#if></#list><th>操作</th></tr></thead><tbody id="tbody">' + tbody + '</tbody></table>';
                    $("#table-body").append(html);
                    initTreeTable();
                } else {
                    krt.layer.msg(rb.msg);
                }
            }
        });
    }

    initTable(0);

    //加载子节点
    function getNodeViaAjax(pid) {
        krt.ajax({
            type: 'POST',
            url: "${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/list?pid=" + pid,
            dataType: 'json',
            success: function (rb) {
                if (rb.code == 200) {
                    var childNodes = rb.data;
                    if (childNodes) {
                        var parentNode = $("#tree-table").treetable("node", pid);
                        for (var i = 0; i < childNodes.length; i++) {
                            var node = childNodes[i];
                            var nodeToAdd = $("#tree-table").treetable("node", node.id);
                            if (!nodeToAdd) {
                                var hasChild = "";
                                if (node.hasChild == 'true') {
                                    hasChild = 'data-tt-branch="true"';
                                }
                                var row = '<tr data-tt-id="' + node.id + '" ' + hasChild + ' data-tt-parent-id="' + pid + '">'
                                    <#list genTable.genTableColumns as column>
                                    <#if column.isList=='0'>
                                    <#if column.dicType?? && column.dicType!=''>
                                    + "<td>" + krt.util.getDic('${column.dicType}', node.${column.javaField}) + "</td>"
                                    <#else>
                                    + "<td>" + krt.util.isNull(node.${column.javaField}) + "</td>"
                                    </#if>
                                    </#if>
                                    </#list>
                                    + "<td>"
                                    + '<${"@"}shiro.hasPermission name="<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:update"><button class="btn btn-xs btn-warning updateBtn" rid="' + node.id + '" pid="' + node.pid + '"><i class="fa fa-edit fa-btn"></i>修改</button></${"@"}shiro.hasPermission>'
                                    + '<${"@"}shiro.hasPermission name="<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:delete"><button class="btn btn-xs btn-danger deleteBtn" rid="' + node.id + '"><i class="fa fa-trash fa-btn"></i>删除</button></${"@"}shiro.hasPermission>'
                                    + '<${"@"}shiro.hasPermission name="<#if genTable.genScheme.permissionName!=''>${genTable.genScheme.permissionName}:</#if>${genTable.className?uncap_first}:insert"><button class="btn btn-xs btn-success addBtn" rid="' + node.id + '"><i class="fa fa-plus fa-btn"></i>添加子集</button></${"@"}shiro.hasPermission>'
                                    + "</td>"
                                    + "</tr>";
                                $("#tree-table").treetable("loadBranch", parentNode, row);
                            }
                        }
                    }
                } else {
                    krt.layer.msg(rb.msg);
                }
            }
        });
    }

    //加载默认
    initTable(0);

    //收缩
    $("#collapseTableBtn").click(function () {
        $('#tree-table').treetable('collapseAll');
        $(this).hide();
        $('#expandTableBtn').show();
    });

    //展开
    $("#expandTableBtn").click(function () {
        $('#tree-table').treetable('expandAll');
        $(this).hide();
        $('#collapseTableBtn').show();
    })

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

    //添加下一级
    $(document).on("click", ".addBtn", function () {
        var id = $(this).attr("rid");
        <#if formType=='tab'>
        top.krt.tab.openTab("新增${genTable.comment}","${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/insert?pid=" + id);
        </#if>
        <#if formType=='dialog'>
        top.krt.layer.openDialog("新增${genTable.comment}","${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/insert?pid=" + id,"1000px","500px");
        </#if>
    });

    //删除
    $(document).on("click", ".deleteBtn", function () {
        var id = $(this).attr("rid");
        krt.layer.confirm("你确定删除此记录及下级记录吗？", function () {
            krt.ajax({
                type: "POST",
                url: "${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/delete",
                data: {"id": id},
                success: function (rb) {
                    krt.layer.msg(rb.msg);
                    if (rb.code == 200) {
                        var pid = $("#pid").val();
                        initTable(pid);
                    }
                }
            });
        });
    });
</script>
${"</@footer>"}

