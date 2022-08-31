<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <div class="box">
                <div class="box-body">
                    <div class="table-toolbar" id="table-toolbar">
                        <@shiro.hasPermission name="sys:number:insert">
                            <button title="添加" type="button" id="insertBtn" class="btn btn-success btn-sm">
                                <i class="fa fa-plus"></i> 添加
                            </button>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="sys:number:delete">
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
                    url: "${basePath}/sys/number/list",
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
                        render: function (data) {
                            return '<input type="checkbox" class="icheck check" value="' + data + '">';
                        }
                    },
                    {title: "流水号编码", data: "code"},
                    {title: "流水号名称", data: "name"},
                    {title: "当前流水号", data: "num"},
                    {title: "示例", data: "formatNum"},
                    {
                        title: "操作", orderable: false,
                        render: function (data, type, row) {
                            return '<@shiro.hasPermission name="sys:number:create">'
                                + '<button class="btn btn-xs btn-success createBtn" rid="' + row.id + '" rcode="' + row.code + '">'
                                + '<i class="fa fa-check fa-btn"></i>生成流水号'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="sys:number:update">'
                                + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>修改'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="sys:number:delete">'
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
                top.krt.layer.openDialog("新增流水号", "${basePath}/sys/number/insert", "480px", "720px");
            });
            //修改
            $(document).on("click", ".updateBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialog("修改流水号", "${basePath}/sys/number/update?id=" + id, "480px", "720px");
            });
            //生成流水号
            $(document).on("click", ".createBtn", function () {
                var code = $(this).attr("rcode");
                krt.ajax({
                    type: "get",
                    url: "${basePath}/sys/number/createNum",
                    data: {"code": code},
                    success: function (rb) {
                        krt.layer.msg(rb.msg);
                        if (rb.code == 200) {
                            krt.table.reloadTable();
                        }
                    }
                });
            });
            //删除
            $(document).on("click", ".deleteBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/sys/number/delete",
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
                    krt.layer.msg("请选择要删除的数据!");
                    return false;
                } else {
                    krt.layer.confirm("你确定删除吗？", function () {
                        krt.ajax({
                            type: "POST",
                            url: "${basePath}/sys/number/deleteByIds",
                            data: {"ids": ids},
                            success: function (rb) {
                                krt.layer.msg(rb.msg);
                                if (rb.code == 200) {
                                    reloadTable(datatable);
                                }
                            }
                        });
                    });
                }
            });
        });
    </script>
</@footer>

