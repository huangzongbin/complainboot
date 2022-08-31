<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <div class="box-search">
                <div class="row row-search">
                    <div class="col-xs-12">
                        <form class="form-inline" action="#">
                            <div class="form-group">
                                <label for="name" class="control-label">表名:</label>
                                <input type="text" class="form-control" placeholder="表名" id="name">
                            </div>
                            <div class="form-group">
                                <div class="text-center">
                                    <button type="button" id="searchBtn" class="btn btn-primary btn-sm"><i class="fa fa-search fa-btn"></i>搜索</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <!-- 列表数据区 -->
            <div class="box">
                <div class="box-body">
                    <div class="table-toolbar" id="table-toolbar">
                        <@shiro.hasPermission name="gen:genTable:insert">
                            <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip" class="btn btn-success btn-sm">
                                <i class="fa fa-plus"></i> 添加
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="gen:genTable:delete">
                            <button class="btn btn-sm btn-danger" id="deleteBatchBtn">
                                <i class="fa fa-trash fa-btn"></i>批量删除
                            </button>
                        </@shiro.hasPermission>
                    </div>
                    <table id="datatable" class="table table-bordered table-hover table-krt">
                    </table>
                </div>
            </div>
    </div>
    </section>
</@body>
<@footer>
    <script type="text/javascript">
        var datatable;

        function initDatatable() {
            datatable = $('#datatable').DataTable({
                "ajax": {
                    "url": "${basePath}/gen/genTable/list",
                    "type": "post",
                    "data": function (d) {
                        d.name = $("#name").val();
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
                    {title: "注释", data: "comment"},
                    {title: "表名", data: "name"},
                    {title: "类名", data: "className"},
                    {title: "子表数量", data: "ccount"},
                    {title: "程序员", data: "coder"},
                    {
                        title: "操作", data: "id", orderable: false,
                        render: function (data, type, row) {
                            return ' <@shiro.hasPermission name="gen:genTable:update">'
                                + '<button class="btn btn-xs btn-warning updateBtn" rname="' + row.name + '" rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>修改'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="gen:genTable:genCode">'
                                + '<button class="btn btn-xs btn-success genCodeBtn" rname="' + row.name + '" rid="' + row.id + '">'
                                + '<i class="fa fa-rocket fa-btn"></i>代码生成'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="gen:genTable:delete">'
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
                top.krt.tab.openTab("新增规则", "${basePath}/gen/genTable/selectDbTable");
            });

            //修改
            $(document).on("click", ".updateBtn", function () {
                var name = $(this).attr("rname");
                var id = $(this).attr("rid");
                top.krt.tab.openTab("修改规则", "${basePath}/gen/genTable/update?id=" + id + "&name=" + name);
            });

            //生成代码
            $(document).on("click", ".genCodeBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("请确认你已做好备份!不要被覆盖了(*>﹏<*)", function () {
                    $.ajax({
                        type: "POST",
                        url: "${basePath}/gen/genTable/genCode",
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

            //删除
            $(document).on("click", ".deleteBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/gen/genTable/delete",
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
                $(".check").each(function () {
                    if ($(this).prop("checked")) {
                        ids = ids + ($(this).val()) + ","
                    }
                });
                if (ids == "") {
                    top.layer.msg("请选择要删除的数据!");
                    return false;
                } else {
                    ids = ids.substr(0, ids.length - 1);
                    krt.layer.confirm("你确定删除吗？", function () {
                        krt.ajax({
                            type: "POST",
                            url: "${basePath}/gen/genTable/deleteByIds",
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
</@footer>
