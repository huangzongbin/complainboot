<#include "/common/layoutList.ftl">
<@header>
</@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <div class="box-search">
                <div class="row row-search">
                    <div class="col-xs-12">
                        <form class="form-inline" action="">
                            <div class="form-group">
                                <label for="code" class="control-label">角色编码：</label>
                                <input type="text" class="form-control" placeholder="角色编码" id="code">
                            </div>
                            <div class="form-group">
                                <label for="name" class="control-label">角色名称：</label>
                                <input type="text" class="form-control" placeholder="角色名称" id="name">
                            </div>
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
            <div class="box">
                <div class="box-body">
                    <div class="table-toolbar" id="table-toolbar">
                        <@shiro.hasPermission name="sys:role:insert">
                            <button title="添加" type="button" id="insertBtn" class="btn btn-success btn-sm">
                                <i class="fa fa-plus"></i> 添加
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="sys:role:delete">
                            <button title="批量删除" class="btn btn-sm btn-danger" type="button" id="deleteBatchBtn">
                                <i class="fa fa-trash fa-btn"></i>批量删除
                            </button>
                        </@shiro.hasPermission>
                    </div>
                    <table id="datatable" class="table table-bordered table-hover"></table>
                </div>
                <!-- /.box-body -->
            </div>
        </section>
        <!-- /.content -->
    </div>
</@body>

<@footer>
    <script>
        //定义datatable
        var datatable;
        $(function () {
            //初始化datatable
            datatable = $('#datatable').DataTable({
                order: [[5, 'asc']],
                ajax: {
                    url: "${basePath}/sys/role/list",
                    type: "post",
                    data: function (d) {
                        d.code = $("#code").val();
                        d.name = $("#name").val();
                        d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                        d.orderType = d.order[0].dir;
                    }
                },
                columns: [
                    {title: 'id', data: "id", visible: false},
                    {
                        title: '<input type="checkbox" id="checkAll" class="icheck">',
                        data: "id", class: "td-center", width: "40", orderable: false,
                        render: function (data) {
                            return '<input type="checkbox" class="icheck check" value="' + data + '">';
                        }
                    },
                    {title: "角色名称", data: "name"},
                    {title: "角色编码", data: "code"},
                    {title: "备注", data: "remark"},
                    {title: "排序", data: "sort"},
                    {
                        title: "操作", orderable: false,
                        render: function (data, type, row) {
                            return '<@shiro.hasPermission name="sys:role:see">'
                                + '<button class="btn btn-xs btn-info seeBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-eye fa-btn"></i>查看'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="sys:role:update">'
                                + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>修改'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="sys:role:delete">'
                                + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-trash fa-btn"></i>删除'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="sys:role:res">'
                                + '<button class="btn btn-xs btn-success permBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>资源权限'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="sys:role:scope">'
                                + '<button class="btn btn-xs bg-purple dataScopeBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>数据权限'
                                + '</button>'
                                + '</@shiro.hasPermission>';
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
                top.krt.layer.openDialog("新增角色", "${basePath}/sys/role/insert", "400px", "510px");
            });

            //修改
            $(document).on("click", ".updateBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialog("修改角色", "${basePath}/sys/role/update?id=" + id, "400px", "510px");
            });

            //查看
            $(document).on("click", ".seeBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialogView("查看角色", "${basePath}/sys/role/see?id=" + id, "400px", "510px");
            });

            //删除
            $(document).on("click", ".deleteBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/sys/role/delete?id=" + id,
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
                            url: "${basePath}/sys/role/deleteByIds",
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

            //资源权限
            $(document).on("click", ".permBtn", function () {
                var roleId = $(this).attr("rid");
                var url = "${basePath}/sys/role/roleResTree?roleId=" + roleId;
                krt.layer.open({
                    type: 2,
                    area: ['300px', '500px'],
                    title: "资源权限",
                    maxmin: false,
                    content: "${basePath}/common/treeSelect?url=" + encodeURI(url) + "&checkbox=true",
                    btn: ['确定', '关闭'],
                    yes: function (index, layero) {
                        var tree = layero.find("iframe")[0].contentWindow.tree;
                        var nodesIds = new Array();
                        var nodes = tree.getCheckedNodes(true);
                        for (var i = 0; i < nodes.length; i++) {
                            nodesIds[i] = nodes[i].id;
                        }
                        var resIds = nodesIds.join(",");
                        //提交
                        krt.ajax({
                            type: "POST",
                            url: "${basePath}/sys/role/roleRes",
                            data: {
                                "roleId": roleId,
                                "resIds": resIds
                            },
                            success: function (rb) {
                                if (rb.code == 200) {
                                    krt.layer.msg(rb.msg, function () {
                                        krt.layer.close(index);
                                    });
                                } else {
                                    krt.layer.msg(rb.msg);
                                }
                            }
                        });
                    },
                    cancel: function (index) {
                        krt.layer.close(index);
                    }
                });
            });

            //数据权限
            $(document).on("click", ".dataScopeBtn", function () {
                var roleId = $(this).attr("rid");
                var url = "${basePath}/sys/role/roleScopeTree?roleId=" + roleId;
                krt.layer.open({
                    type: 2,
                    area: ['300px', '500px'],
                    title: "数据权限",
                    maxmin: false,
                    content: "${basePath}/common/treeSelect?url=" + encodeURI(url) + "&checkbox=true&expandAll=true&chkboxType=s-s",
                    btn: ['确定', '关闭'],
                    yes: function (index, layero) {
                        var tree = layero.find("iframe")[0].contentWindow.tree;
                        var nodeIdArr = new Array();
                        var nodes = tree.getCheckedNodes(true);
                        for (var i = 0; i < nodes.length; i++) {
                            nodeIdArr[i] = nodes[i].id;
                        }
                        var nodeIds = nodeIdArr.join(",");
                        //提交
                        krt.ajax({
                            type: "POST",
                            url: "${basePath}/sys/role/roleScope",
                            data: {
                                "roleId": roleId,
                                "organIds": nodeIds
                            },
                            success: function (rb) {
                                krt.layer.msg(rb.msg);
                                if (rb.code == 200) {
                                    krt.layer.close(index);
                                }
                            }
                        });
                    },
                    cancel: function (index) {
                        krt.layer.close(index);
                    }
                });
            });

        });
    </script>
</@footer>
