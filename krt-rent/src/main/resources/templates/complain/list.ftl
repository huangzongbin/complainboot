<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
<div class="wrapper">
    <section class="content">
        <div class="box-search">
            <div class="row row-search">
                <div class="col-sm-12">
                    <form class="form-inline" action="">
                        <div class="form-group" style="margin:5px">
                            <label for="linkName" class="control-label" style="padding:0 5px">姓名</label>
                            <input type="text" class="form-control" placeholder="姓名" id="linkName">
                        </div>
                        <div class="form-group" style="margin:5px">
                            <label for="linkPhone" class="control-label" style="padding:0 5px">手机号码</label>
                            <input type="text" class="form-control" placeholder="手机号码" id="linkPhone">
                        </div>
                        <div class="form-group" style="margin:5px">
                            <label for="linkPhone" class="control-label" style="padding:0 5px">年度</label>
                            <div class="input-group input-group-addon-right-radius">
                                <input type="text" class="form-control pull-right" name="year"
                                       id="year" readonly value="${year!}"
                                       onclick="WdatePicker({dateFmt:'yyyy'})">
                                <div class="input-group-addon">
                                    <i class="fa fa-calendar"></i>
                                </div>
                            </div>
                        </div>
                        <div class="form-group" style="margin:5px">
                            <label for="linkPhone" class="control-label" style="padding:0 5px">月份</label>
                            <div class="input-group input-group-addon-right-radius">
                                <input type="text" class="form-control pull-right" name="month"
                                       id="month" readonly
                                       onclick="WdatePicker({dateFmt:'MM'})">
                                <div class="input-group-addon">
                                    <i class="fa fa-calendar"></i>
                                </div>
                            </div>
                        </div>
                        <div class="form-group" style="margin:5px">
                            <label for="status" class="control-label" style="padding:0 5px">回复状态</label>
                            <select class="form-control" id="status">
                                <option value="">全部类型</option>
                                <option value="1">待回复</option>
                                <option value="2">已回复</option>
                            </select>
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
                    <@shiro.hasPermission name="complain:complain:insert">
                        <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip" class="btn btn-success btn-sm">
                            <i class="fa fa-plus"></i> 添加
                        </button>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="complain:complain:delete">
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
    $(function () {
        //初始化datatable
        datatable = $('#datatable').DataTable({
            ajax: {
                url: "${basePath}/complain/complain/list",
                type: "post",
                data: function (d) {
                    d.linkName = $("#linkName").val();
                    d.linkPhone = $("#linkPhone").val();
                    d.status = $("#status").val();
                    d.year = $("#year").val();
                    d.month = $("#month").val();
                    d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                    d.orderType = d.order[0].dir;
                }
            },
            columns: [
    { data: "id",visible:false},
        {title: '<input type="checkbox" id="checkAll" class="icheck">',
            data: "id", class:"td-center", width:"40",orderable: false,
            render: function (data) {
                return '<input type="checkbox" class="icheck check" value="' + data + '">';
            }
        },
                {title: '姓名',"data": "linkName"},
                {title: '手机号码',"data": "linkPhone"},
                {title: '所属小区',"data": "community",
                    "render":function (data) {
                        return krt.util.getDic('village_type', data)
                    }
                },
                {title: '投诉举报内容',"data": "cpnContent",
                    "render":function (data, type, row) {
                        if(data.length>50){
                            return "<span title='"+data+"'>"+data.substring(0,50)+"...</span>"
                        }else {
                            return "<span title='"+data+"'>"+data+"</span>"
                        }
                    }
                },
                {title: '投诉举报时间',"data": "cpnTime"},
                {title: '回复状态',"data": "status",
                    "render":function (data, type, row) {
                        var str = "待回复";
                        if (data=='2'){str="已回复";}
                        return str;
                    }
                },
                {
                    title: "操作", data: "id", orderable: false,
                    "render": function (data, type, row) {
                        var btn =
    '<@shiro.hasPermission name="complain:complain:list">'
                                + '<button class="btn btn-xs btn-info seeBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-eye fa-btn"></i>查看'
                                + '</button>'
    + '</@shiro.hasPermission>';
                        if(row.status=='1'){
                            btn = btn+ '<@shiro.hasPermission name="complain:complain:update">'
                                    + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                                    + '<i class="fa fa-edit fa-btn"></i>回复'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';
                        }

                        btn = btn + '<@shiro.hasPermission name="complain:complain:delete">'
                                + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-trash fa-btn"></i>删除'
                                + '</button>'
                                + '</@shiro.hasPermission>';
                        return btn;
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
            top.krt.layer.openDialog("新增投诉信息","${basePath}/complain/complain/insert","1000px","720px");
        });

        //修改
        $(document).on("click", ".updateBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialog("修改投诉信息","${basePath}/complain/complain/update?id=" + id,"1000px","720px");
        });


        //修改
        $(document).on("click", ".seeBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialogView("查看投诉信息","${basePath}/complain/complain/see?id=" + id,"1000px","720px");
        });

        //删除
        $(document).on("click", ".deleteBtn", function () {
            var id = $(this).attr("rid");
            krt.layer.confirm("你确定删除吗？", function () {
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/complain/complain/delete",
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
                krt.layer.error("请选择要删除的数据!");
                return false;
            } else {
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/complain/complain/deleteByIds",
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
