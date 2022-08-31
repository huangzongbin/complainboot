<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
<div class="wrapper">
    <section class="content">
        <div class="box">
            <div class="box-body">
                <div class="table-toolbar" id="table-toolbar">
                    <@shiro.hasPermission name="sys:user:delete">
                        <button title="批量下线" class="btn btn-sm btn-danger" type="button" id="deleteBatchBtn">
                            <i class="fa fa-trash fa-btn"></i>批量下线
                        </button>
                    </@shiro.hasPermission>
                </div>
                <table id="datatable" class="table table-bordered table-hover"></table>
            </div>
            <!-- /.box-body -->
        </div>
    </section>
    </@body>
    <@footer>
        <script type="text/javascript">
            var datatable;
            $(function () {
                datatable = $('#datatable').DataTable({
                    ajax: {
                        url: "${basePath}/sys/session/list",
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
                        {title: "用户名", data: "username"},
                        {title: "主机", data: "host"},
                        {title: "登录时间", data: "startTimestamp"},
                        {title: "最后访问时间", data: "lastAccessTime"},
                        {title: "过期时间", data: "timeout"},
                        {
                            title: "操作", data: "id", orderable: false,
                            render: function (data, type, row) {
                                if (row.self) {
                                    return "";
                                } else {
                                    return '<@shiro.hasPermission  name="sys:user:delete">'
                                        + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                                        + '<i class="fa fa-trash fa-btn"></i>强制下线'
                                        + '</button>'
                                        + '</@shiro.hasPermission>';
                                }
                            }
                        }
                    ]
                });

                //删除
                $(document).on("click", ".deleteBtn", function () {
                    var id = $(this).attr("rid");
                    krt.layer.confirm("你确定强制该账号下线吗？", function () {
                        krt.layer.ajax({
                            type: "POST",
                            url: "${basePath}/sys/session/deleteUser",
                            data: {"sessionId": id},
                            success: function (rb) {
                                krt.layer.msg(rb.msg);
                                if (rb.code == 200) {
                                    reloadTable(datatable);
                                }
                            }
                        });
                    });
                });

                //批量删除
                $("#deleteBatchBtn").click(function () {
                    var ids = getIds();
                    if (ids == "") {
                        krt.layer.msg("请选择要强制下线账号!");
                        return false;
                    } else {
                        ids = ids.substr(0, ids.length - 1);
                        krt.layer.confirm("你确定强制这些账号下线吗？", function () {
                            krt.ajax({
                                type: "POST",
                                url: "${basePath}/sys/session/deleteUsers",
                                data: {"sessionIds": ids},
                                success: function (rb) {
                                    krt.layer.msg(rb.msg);
                                    if (rb.code == 200) {
                                        reloadTable(datatable, ids);
                                    }
                                }
                            });
                        });
                    }
                });
            });
        </script>
    </@footer>
