<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <div class="box-search">
                <div class="row row-search">
                    <div class="col-xs-12">
                        <form class="form-inline" action="">
                            <div class="form-group" style="margin:5px">
                                <label for="clientId" class="control-label" style="padding:0 5px">客户端id：</label>
                                <input type="text" class="form-control" placeholder="客户端id" id="clientId">
                            </div>
                            <div class="form-group" style="margin:5px">
                                <label for="clientName" class="control-label" style="padding:0 5px">客户端名称：</label>
                                <input type="text" class="form-control" placeholder="客户端名称" id="clientName">
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
            <!-- 列表数据区 -->
            <div class="box">
                <div class="box-body">
                    <!-- 工具按钮区 -->
                    <div class="table-toolbar" id="table-toolbar">
                        <@shiro.hasPermission name="oauth:clientDetails:insert">
                            <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip" class="btn btn-success btn-sm">
                                <i class="fa fa-plus"></i> 添加
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="oauth:clientDetails:delete">
                            <button class="btn btn-sm btn-danger" id="deleteBatchBtn">
                                <i class="fa fa-trash fa-btn"></i>批量删除
                            </button>
                        </@shiro.hasPermission>
                    </div>
                    <table id="datatable" class="table table-bordered table-hover"></table>
                </div>
            </div>
        </section>
    </div>
</@body>
<@footer>
    <script type="text/javascript">
        var datatable;

        function initDatatable() {
            datatable = $('#datatable').DataTable({
                ajax: {
                    url: "${basePath}/oauth/clientDetails/list",
                    type: "post",
                    data: function (d) {
                        d.clientName = $("#clientName").val();
                        d.clientId = $("#clientId").val();
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
                    {title: "客户端id", data: "clientId"},
                    {title: "客户端秘钥", data: "clientSecret"},
                    {title: "客户端名称", data: "clientName"},
                    {title: "授权模式", data: "grantTypes"},
                    {title: "重定向url", data: "redirectUri"},
                    {
                        title: "状态", data: "state",
                        render: function (data, type, row) {
                            if (data == '0') {
                                return '<span class="badge bg-green">正常</span>';
                            } else {
                                return '<span class="badge bg-maroon">禁用</span>';
                            }
                        }
                    },
                    {
                        title: "操作", orderable: false,
                        render: function (data, type, row) {
                            var button = "";
                            if (row.state == '0') {
                                button = '<@shiro.hasPermission  name="oauth:clientDetails:state">'
                                    + '<button class="btn btn-xs bg-maroon cancelBtn" rid="' + row.id + '" state="1">'
                                    + '<i class="fa fa-close fa-btn"></i>禁用'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';
                            } else {
                                button = '<@shiro.hasPermission  name="oauth:clientDetails:state">'
                                    + '<button class="btn btn-xs btn-success cancelBtn" rid="' + row.id + '" state="0">'
                                    + '<i class="fa fa-check fa-btn"></i>启用'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';
                            }
                            return '<@shiro.hasPermission name="oauth:clientDetails:update">'
                                + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>修改'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + button
                                + '<@shiro.hasPermission name="oauth:clientDetails:delete">'
                                + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-trash fa-btn"></i>删除'
                                + '</button>'
                                + '</@shiro.hasPermission>';
                        }
                    }
                ]
            });
        }

        $(function () {

            //初始化datatable
            initDatatable();

            //搜索
            $("#searchBtn").on('click', function () {
                datatable.ajax.reload();
            });

            //新增
            $("#insertBtn").click(function () {
                top.krt.tab.openTab("新增客户端", "${basePath}/oauth/clientDetails/insert");
            });

            //修改
            $(document).on("click", ".updateBtn", function () {
                var id = $(this).attr("rid");
                top.krt.tab.openTab("修改客户端", "${basePath}/oauth/clientDetails/update?id=" + id);
            });

            //删除
            $(document).on("click", ".deleteBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/oauth/clientDetails/delete",
                        data: {"id": id},
                        success: function (rb) {
                            top.layer.msg(rb.msg);
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
                    top.layer.msg("请选择要删除的数据!");
                    return false;
                } else {
                    krt.layer.confirm("你确定删除吗？", function () {
                        krt.ajax({
                            type: "POST",
                            url: "${basePath}/oauth/clientDetails/deleteByIds",
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

            //禁用
            $(document).on("click", ".cancelBtn", function () {
                var id = $(this).attr("rid");
                var state = $(this).attr("state");
                var tip = "启用";
                if (state == '1') {
                    tip = "禁用";
                }
                krt.layer.confirm("你确定" + tip + "该客户端吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/oauth/clientDetails/state",
                        data: {"id": id, "state": state},
                        success: function (rb) {
                            krt.layer.msg(rb.msg);
                            if (rb.code == 200) {
                                krt.table.reloadTable();
                            }
                        }
                    });
                });
            });
        });
    </script>
</@footer>
