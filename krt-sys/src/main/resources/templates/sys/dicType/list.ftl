<#include "/common/layoutList.ftl">
<@header>
    <style>
        div.dataTables_scrollBody tbody tr.tfooter td {
            border-top: 1px solid #e6e6e6;;
        }
    </style>
</@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <div class="box-search">
                <div class="row row-search">
                    <div class="col-xs-12">
                        <form class="form-inline" action="">
                            <div class="form-group">
                                <label for="code" class="control-label">字典编码：</label>
                                <input type="text" class="form-control" placeholder="字典编码" id="code">
                            </div>
                            <div class="form-group">
                                <label for="name" class="control-label">字典名称：</label>
                                <input type="text" class="form-control" placeholder="字典名称" id="name">
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
                        <@shiro.hasPermission name="sys:dicType:delete">
                            <button type="button" class="btn btn-sm btn-danger" id="deleteBatchBtn">
                                <i class="fa fa-trash fa-btn"></i>批量删除
                            </button>
                        </@shiro.hasPermission>
                    </div>
                    <table id="datatable" class="table table-bordered table-hover">
                        <tbody></tbody>
                        <tr class="tfooter">
                            <td></td>
                            <td><input type="text" name="code" id="code0" class="form-control input-150"></td>
                            <td><input type="text" name="name" id="name0" class="form-control input-150"></td>
                            <td><input type="text" name="remark" id="remark0" class="form-control input-150"></td>
                            <td><input type="text" name="sort" id="sort0" class="form-control input-150"></td>
                            <td>
                                <@shiro.hasPermission name="sys:dicType:insert">
                                    <button class="btn btn-xs btn-success" id="insertBtn">
                                        <i class="fa fa-plus fa-btn"></i>新增
                                    </button>
                                </@shiro.hasPermission>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </section>
    </div>
</@body>
<@footer>
    <script language="javascript" type="text/javascript">
        var datatable;
        $(document).ready(function () {
            datatable = $('#datatable').DataTable({
                order: [[5, 'asc']],
                ajax: {
                    url: "${basePath}/sys/dicType/list",
                    type: "post",
                    data: function (d) {
                        d.code = $('#code').val();
                        d.name = $('#name').val();
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
                    {
                        title: "字典编码",
                        data: "code",
                        render: function (data, type, row) {
                            return '<input type="text" name="code" value="' + data + '" id="code' + row.id + '" readonly class="form-control input-150">';
                        }
                    },
                    {
                        title: "字典名称",
                        data: "name",
                        render: function (data, type, row) {
                            return '<input type="text" name="name" value="' + data + '" id="name' + row.id + '"  class="form-control input-150">';
                        }
                    },
                    {
                        title: "备注",
                        data: "remark",
                        render: function (data, type, row) {
                            return '<input type="text" name="remark" value="' + data + '" id="remark' + row.id + '"  class="form-control input-150">';
                        }
                    },
                    {
                        title: "排序",
                        data: "sort",
                        render: function (data, type, row) {
                            return '<input type="text" name="sort" value="' + data + '" id="sort' + row.id + '"  class="form-control input-150">';
                        }
                    },
                    {
                        title: "操作",
                        data: "id",
                        width: "400px",
                        orderable: false,
                        render: function (data, type, row) {
                            return '<input type="hidden" name="id" value="' + data + '">'
                                + '<@shiro.hasPermission name="sys:dicType:list">'
                                + '<button class="btn btn-xs btn-success dicBtn" rid="' + data + '" code="' + row.code + '">'
                                + '<i class="fa  fa-list fa-btn"></i>键值管理'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="sys:dicType:update">'
                                + '<button class="btn btn-xs btn-warning updateBtn" rid="' + data + '">'
                                + '<i class="fa fa-edit fa-btn"></i>修改'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="sys:dicType:delete">'
                                + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + data + '" code="' + row.code + '">'
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
                var code = $("#code0").val();
                if (code == null || code == '') {
                    krt.layer.msg("字典编码不可为空！");
                    return false;
                }
                var name = $("#name0").val();
                if (name == null || name == '') {
                    krt.layer.msg("字典名称不可为空！");
                    return false;
                }
                var sort = $("#sort0").val();
                if (sort == null || sort == '') {
                    krt.layer.msg("排序不可为空！");
                    return false;
                }
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/sys/dicType/insert",
                    dataType: "json",
                    data: {
                        "code": $("#code0").val(),
                        "name": $("#name0").val(),
                        "remark": $("#remark0").val(),
                        "sort": $("#sort0").val()
                    },
                    success: function (rb) {
                        if (rb.code == 200) {
                            krt.layer.msg(rb.msg);
                            $("#code0").val("");
                            $("#name0").val("");
                            $("#remark0").val("");
                            $("#sort0").val("");
                            krt.table.reloadTable();
                        } else {
                            krt.layer.msg(rb.msg);
                        }
                    }
                });
            });

            //修改
            $(document).on("click", ".updateBtn", function () {
                var id = $(this).attr("rid");
                var code = $("#code" + id).val();
                if (code == null || code == '') {
                    krt.layer.msg("字典编码不可为空！");
                    return false;
                }
                var name = $("#name" + id).val();
                if (name == null || name == '') {
                    krt.layer.msg("字典名称不可为空！");
                    return false;
                }
                var sort = $("#sort" + id).val();
                if (sort == null || sort == '') {
                    krt.layer.msg("排序不可为空！");
                    return false;
                }
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/sys/dicType/update",
                    dataType: "json",
                    data: {
                        "id": id,
                        "code": $("#code" + id).val(),
                        "name": $("#name" + id).val(),
                        "remark": $("#remark" + id).val(),
                        "sort": $("#sort" + id).val()
                    },
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
                var code = $(this).attr("code");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/sys/dicType/delete",
                        data: {"id": id, "code": code},
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
                var codes = "";
                $(".check").each(function () {
                    if ($(this).prop("checked")) {
                        ids = ids + ($(this).val()) + ","
                        codes = codes + ($(this).attr("code")) + ","
                    }
                });
                if (ids == "") {
                    krt.layer.msg("请选择要删除的数据!");
                    return false;
                } else {
                    ids = ids.substr(0, ids.length - 1);
                    codes = codes.substr(0, codes.length - 1);
                    krt.layer.confirm("你确定删除吗？", function () {
                        krt.ajax({
                            type: "POST",
                            url: "${basePath}/sys/dicType/deleteByIdsAndCodes",
                            data: {"ids": ids, "codes": codes},
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

            //键值管理
            $(document).on("click", ".dicBtn", function () {
                var type = $(this).attr("code");
                window.location.href = "${basePath}/sys/dic/list?pid=0&type=" + type;
            });
        });
    </script>
</@footer>
