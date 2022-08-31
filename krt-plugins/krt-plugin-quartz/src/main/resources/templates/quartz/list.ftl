<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <div class="box">
                <div class="box-body">
                    <div class="table-toolbar" id="table-toolbar">
                        <@shiro.hasPermission name="quartz:insert">
                            <button title="添加" id="insertBtn" data-placement="left" data-toggle="tooltip" class="btn btn-success btn-sm">
                                <i class="fa fa-plus"></i> 添加
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="quartz:delete">
                            <button class="btn btn-sm btn-danger" id="deleteBatchBtn">
                                <i class="fa fa-trash fa-btn"></i>批量删除
                            </button>
                        </@shiro.hasPermission>
                        <table id="datatable" class="table table-bordered table-hover"></table>
                    </div>
                </div>
            </div>
        </section>
    </div>
</@body>
<@footer>
    <script type="text/javascript">
        var datatable;
        $(function () {
            datatable = $('#datatable').DataTable({
                ajax: {
                    url: "${basePath}/quartz/list",
                    type: "post",
                    data: function (d) {
                        d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                        d.orderType = d.order[0].dir;
                    }
                },
                columns: [
                    {title: 'id', data: "id", visible: false},
                    {
                        title: '<input type="checkbox" id="checkAll" class="icheck">',
                        data: "id", class: "td-center", width: "40", orderable: false,
                        render: function (data, type, row) {
                            return '<input type="checkbox" class="icheck check" status="' + row.status + '" value="' + data + '">';
                        }
                    },
                    {title: "任务名", data: "name"},
                    {title: "组名", data: "groupName"},
                    {title: "cron表达式", data: "expression"},
                    {title: "springId", data: "springId"},
                    {title: "方法名", data: "method"},
                    {title: "上次执行时间", data: "previousFireTime"},
                    {title: "下次执行时间", data: "nextFireTime"},
                    {
                        "title": "状态",
                        "data": "status",
                        render: function (data) {
                            if (data == '1') {
                                return '<span class="badge bg-green">正在运行</span>';
                            } else {
                                return '<span class="badge bg-red">停止中</span>';
                            }
                        }
                    },
                    {
                        title: "操作",
                        orderable: false,
                        render: function (data, type, row) {
                            if (row.status == '0') {
                                return '<@shiro.hasPermission name="quartz:startOrstop">'
                                    + '<button class="btn btn-xs btn-info startOrstopBtn" rid="' + row.id + '" status="' + row.status + '">'
                                    + '<i class="fa fa-eye fa-btn"></i>启动任务'
                                    + '</button>'
                                    + '</@shiro.hasPermission>'
                                    + '<@shiro.hasPermission name="quartz:update">'
                                    + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                                    + '<i class="fa fa-edit fa-btn"></i>修改'
                                    + '</button>'
                                    + '</@shiro.hasPermission>'
                                    + '<@shiro.hasPermission name="quartz:delete">'
                                    + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '" status="' + row.status + '">'
                                    + '<i class="fa fa-trash fa-btn"></i>删除'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';
                            } else if (row.status == '1') {
                                return '<@shiro.hasPermission name="quartz:startOrstop">'
                                    + '<button class="btn btn-xs btn-info startOrstopBtn" rid="' + row.id + '" status="' + row.status + '">'
                                    + '<i class="fa fa-eye fa-btn"></i>停止任务'
                                    + '</button>'
                                    + '</@shiro.hasPermission>'
                                    + '<@shiro.hasPermission name="quartz:doOnce">'
                                    + '<button class="btn btn-xs btn-success doOnceBtn" rid="' + row.id + '" status="' + row.status + '">'
                                    + '<i class="fa fa-bol fa-btn"></i>立即执行一次'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';
                            }
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
                top.krt.layer.openDialog("新增定时器", "${basePath}/quartz/insert", "1000px", "450px");
            });
            //修改
            $(document).on("click", ".updateBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialog("修改定时器", "${basePath}/quartz/update?id=" + id, "1000px", "450px");
            });
            //启动or停止任务
            $(document).on("click", ".startOrstopBtn", function () {
                var id = $(this).attr("rid");
                var status = $(this).attr("status");
                var tip = "";
                if (status == '0') {
                    tip = "启动";
                } else {
                    tip = "停止";
                }
                krt.layer.confirm("你确定" + tip + "任务吗？", function () {
                    $.ajax({
                        type: "POST",
                        url: "${basePath}/quartz/startOrStop?id=" + id + "&status=" + status,
                        data: $('#krtForm').serialize(),// 要提交的表单
                        success: function (rb) {
                            krt.layer.msg(rb.msg);
                            if (rb.code == 200) {
                                krt.table.reloadTable();
                            }
                        }
                    });
                });

            });
            //删除
            $(document).on("click", ".deleteBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/quartz/delete",
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
                var ids = "";
                var flag = true;
                $(".check").each(function () {
                    if ($(this).prop("checked")) {
                        var status = $(this).attr("status");
                        if (status == '1') {
                            flag = false;
                            krt.layer.msg("请先停止任务!");
                            return false;
                        }
                        ids = ids + ($(this).val()) + ",";
                    }
                });
                if (ids == "" && flag) {
                    krt.layer.msg("请选择要删除的数据!");
                    return false;
                } else if (flag) {
                    ids = ids.substr(0, ids.length - 1);
                    krt.layer.confirm("你确定删除吗？", function () {
                        krt.ajax({
                            type: "POST",
                            url: "${basePath}/quartz/deleteByIds",
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

            //立即执行
            $(document).on("click", ".doOnceBtn", function () {
                var id = $(this).attr("rid");
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/quartz/doOnce",
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
    </script>
</@footer>

