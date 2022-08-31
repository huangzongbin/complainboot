<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <div class="row row-space15">
                <div class="col-sm-2">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">机构树</h3>
                            <div class="box-tools">
                                <button class="btn btn-box-tool" data-widget="collapse" id="expandBtn" title="展开">
                                    <i class="fa  fa-chevron-down"></i>
                                </button>
                                <button class="btn btn-box-tool" data-widget="collapse" id="collapseBtn" title="折叠"
                                        style="display:none;">
                                    <i class="fa  fa-chevron-up"></i>
                                </button>
                                <button class="btn btn-box-tool" data-widget="collapse" id="refBtn" title="刷新">
                                    <i class="fa fa-refresh"></i>
                                </button>
                            </div>
                        </div>
                        <div class="box-body">
                            <div id="ztree" class="ztree"></div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-10">
                    <div class="box-search">
                        <div class="row row-search">
                            <div class="col-xs-12">
                                <form class="form-inline" action="">
                                    <div class="form-group">
                                        <label for="username" class="control-label">用户账号：</label>
                                        <input type="text" class="form-control" placeholder="用户账号" id="username">
                                    </div>
                                    <div class="form-group">
                                        <label for="name" class="control-label">用户姓名：</label>
                                        <input type="text" class="form-control" placeholder="用户姓名" id="name">
                                    </div>
                                    <div class="form-group">
                                        <label for="status" class="control-label">账号状态：</label>
                                        <div class="form-group">
                                            <select class="form-control select2" id="status" style="width: 100%;">
                                                <option value="">==全部==</option>
                                                <option value="0">正常</option>
                                                <option value="1">禁用</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="text-center">
                                            <button type="button" id="searchBtn" class="btn btn-primary btn-sm">
                                                <i class="fa fa-search fa-btn"></i>搜索
                                            </button>
                                        </div>
                                    </div>
                                    <!-- 参数 -->
                                    <input type="hidden" name="organCode" id="organCode">
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="box">
                        <div class="box-body">
                            <div class="table-toolbar" id="table-toolbar">
                                <@shiro.hasPermission name="sys:user:insert">
                                    <button title="添加" type="button" id="insertBtn" class="btn btn-success btn-sm">
                                        <i class="fa fa-plus"></i> 添加
                                    </button>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="sys:user:delete">
                                    <button title="批量删除" class="btn btn-sm btn-danger" type="button"
                                            id="deleteBatchBtn">
                                        <i class="fa fa-trash fa-btn"></i>批量删除
                                    </button>
                                </@shiro.hasPermission>
                            </div>
                            <table id="datatable" class="table table-bordered table-hover"></table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</@body>
<@footer>
    <script type="text/javascript">

        /******************************** tree事件 *************************************/
            //ztree设置
        var setting = {
                view: {
                    selectedMulti: false,
                    dblClickExpand: false
                },
                data: {
                    simpleData: {
                        enable: true,
                        pIdKey: "pid",
                    }
                },
                callback: {
                    onClick: zTreeOnClick
                }
            };

        //ztree点击事件
        function zTreeOnClick(event, treeId, treeNode) {
            var organCode = treeNode.code;
            $("#organCode").val(organCode);
            datatable.ajax.reload();
        }

        //加载树数据
        function initTree() {
            krt.ajax({
                type: "POST",
                url: "${basePath}/sys/organ/organTree",
                async: false,
                success: function (rb) {
                    if (rb.code == 200) {
                        $.fn.zTree.init($("#ztree"), setting, rb.data);
                    } else {
                        krt.layer.msg(rb.msg);
                    }
                }
            });
        }

        //刷新树
        $("#refBtn").click(function () {
            $("#organCode").val("");
            initTree();
        });

        //展开
        $("#expandBtn").click(function () {
            tree.expandAll(true);
            $(this).hide();
            $('#collapseBtn').show();
        });

        //折叠
        $("#collapseBtn").click(function () {
            tree.expandAll(false);
            $(this).hide();
            $('#expandBtn').show();
        });

        //初始化tree
        initTree();
        var tree = $.fn.zTree.getZTreeObj("ztree");
        $("#expandBtn").click();

        var datatable;
        $(function () {
            datatable = $('#datatable').DataTable({
                ajax: {
                    url: "${basePath}/sys/user/list",
                    type: "post",
                    data: function (d) {
                        d.username = $('#username').val();
                        d.name = $('#name').val();
                        d.status = $('#status').val();
                        d.organCode = $('#organCode').val();
                        d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                        d.orderType = d.order[0].dir;
                    }
                },
                "columns": [
                    {title: 'id', data: "id", visible: false},
                    {
                        title: '<input type="checkbox" id="checkAll" class="icheck">',
                        data: "id", class: "td-center", width: "40", orderable: false,
                        render: function (data) {
                            return '<input type="checkbox" class="icheck check" value="' + data + '">';
                        }
                    },
                    {title: '用户账号', data: "username"},
                    {title: '用户姓名', data: "name"},
                    {
                        title: '性别', data: "sex",
                        render: function (data) {

                            if (data == "0") {
                                return "男";
                            }else if(data == "1"){
                                return "女";
                            }else {
                                return "";
                            }
                        }
                    },
                    {title: '手机号码', data: "phone"},
                    {
                        title: '账号状态', data: "status",
                        render: function (data) {
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
                            if (row.status == '0') {
                                button = '<@shiro.hasPermission  name="sys:user:status">'
                                    + '<button class="btn btn-xs bg-maroon cancelBtn" rid="' + row.id + '" status="1">'
                                    + '<i class="fa fa-close fa-btn"></i>禁用'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';
                            } else {
                                button = '<@shiro.hasPermission  name="sys:user:status">'
                                    + '<button class="btn btn-xs btn-success cancelBtn" rid="' + row.id + '" status="0">'
                                    + '<i class="fa fa-check fa-btn"></i>启用'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';
                            }
                            return '<@shiro.hasPermission  name="sys:user:see">'
                                + '<button class="btn btn-xs btn-info seeBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-eye fa-btn"></i>查看'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission  name="sys:user:update">'
                                + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>修改'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + button
                                + '<@shiro.hasPermission  name="sys:user:delete">'
                                + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-trash fa-btn"></i>删除'
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
                top.krt.layer.openDialog("新增用户", "${basePath}/sys/user/insert", "780px", "600px");
            });

            //查看
            $(document).on("click", ".seeBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialogView("查看用户", "${basePath}/sys/user/see?id=" + id, "780px", "600px");
            });

            //修改
            $(document).on("click", ".updateBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialog("修改用户", "${basePath}/sys/user/update?id=" + id, "780px", "600px");
            });

            //删除
            $(document).on("click", ".deleteBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/sys/user/delete",
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
                    top.layer.msg("请选择要删除的数据!");
                    return false;
                } else {
                    krt.layer.confirm("你确定删除吗？", function () {
                        krt.ajax({
                            type: "POST",
                            url: "${basePath}/sys/user/deleteByIds",
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
                var status = $(this).attr("status");
                var tip = "启用";
                if (status == '1') {
                    tip = "禁用";
                }
                krt.layer.confirm("你确定" + tip + "该用户吗？", function () {
                    $.ajax({
                        type: "POST",
                        url: "${basePath}/sys/user/status",
                        data: {"id": id, "status": status},
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