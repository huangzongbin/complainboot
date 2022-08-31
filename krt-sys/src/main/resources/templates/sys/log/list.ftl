<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
<div class="wrapper">
    <section class="content">
        <div class="box-search">
            <div class="row row-search">
                <div class="col-xs-12">
                    <form class="form-inline" action="">
                        <div class="form-group">
                            <label for="username" class="control-label">用户账号：</label>
                            <input type="text" class="form-control" placeholder="用户账号" id="username">
                        </div>
                        <div class="form-group">
                            <label for="description" class="control-label">操作内容：</label>
                            <input type="text" class="form-control" placeholder="操作内容" id="description">
                        </div>
                        <div class="form-group">
                            <label for="type" class="control-label">日志类别：</label>
                            <div class="form-group">
                                <select class="form-control select2" id="type" style="width: 100%">>
                                    <option value="">==请选择==</option>
                                    <option value="0">登录日志</option>
                                    <option value="1">操作日志</option>
                                    <option value="2">异常日志</option>
                                    <option value="3">定时任务</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="useTime" class="control-label">耗时：</label>
                            <div class="form-group">
                                <select class="form-control select2" id="useTime" style="width: 100%">>
                                    <option value="">==请选择==</option>
                                    <option value="10000">大于10秒</option>
                                    <option value="5000">大于5秒</option>
                                    <option value="3000">大于4秒</option>
                                    <option value="3000">大于3秒</option>
                                    <option value="2000">大于2秒</option>
                                    <option value="1000">大于1秒</option>
                                </select>
                            </div>
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
        <div class="box">
            <div class="box-body">
                <div class="table-toolbar" id="table-toolbar">
                    <@shiro.hasRole name="admin">
                        <button type="button" class="btn btn-sm btn-danger" id="deleteBtn">
                            <i class="fa fa-trash fa-btn"></i>清空所有日志
                        </button>
                    </@shiro.hasRole>
                </div>
                <table id="datatable" class="table table-bordered table-hover">
                </table>
            </div><!-- /.box-body -->
        </div>
    </section>
</div>
</@body>
<@footer>
<script type="text/javascript">
    var datatable;
    /**
     * 格式化
     * @param d
     * @returns {string}
     */
    function formatData(d) {
        var type = "";
        if (d.type == '0') {
            type = '登录日志';
        } else if (d.type == '1') {
            type = '操作日志';
        } else if (d.type == '2') {
            type = '异常日志';
        } else if (d.type == '3') {
            type = '定时任务';
        }
        var result = '<b>用户名:</b>' + d.username + '<br/>'
                + '<b>请求URL:</b>' + d.requestUrl + '<br/>'
                + '<b>请求参数:</b>' + d.requestParams + '<br/>'
                + '<b>请求方式:</b>' + d.requestMethod + '<br/>'
                + '<b>请求方法全称:</b>' + d.requestMethodName + '<br/>'
                + '<b>请求IP:</b>' + d.requestIp + '<br/>'
                + '<b>设备:</b>' + d.device + '<br/>'
                + '<b>设备系统:</b>' + d.os + '<br/>'
                + '<b>浏览器:</b>' + d.browser + '<br/>'
                + '<b>描述:</b>' + d.description + '<br/>'
                + '<b>耗时:</b>' + d.useTime + '<br/>'
                + '<b>日志类别:</b>' + type + '<br/>'
                + '<b>异常编码:</b>' + d.exceptionCode + '<br/>'
                + '<b>异常详情:</b>' + d.exceptionDetail + '<br/>';
        return result;
    }

    $(function () {
        datatable = $('#datatable').DataTable({
            order: [[11, 'desc']],
            ajax: {
                url: "${basePath}/sys/log/list",
                type: "post",
                data: function (d) {
                    d.username = $('#username').val();
                    d.description = $('#description').val();
                    d.type = $('#type').val();
                    d.useTime = $("#useTime").val();
                    d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                    d.orderType = d.order[0].dir;
                }
            },
            "columns": [
                {title: 'id', data: "id",visible:false},
                {title: "#",class: 'details-control td-center', width:'40', orderable: false, defaultContent: ''},
                {title: "用户账号", data: "username"},
                {title: "操作内容", width: "100px", data: "description", class: "td-responsive"},
                {
                    title: "操作类别",
                    data: "type",
                    render: function (data) {
                        if (data == '0') {
                            return '<span class="badge bg-yellow">登录日志</span>';
                        } else if (data == '1') {
                            return '<span class="badge bg-green">操作日志</span>';
                        } else if (data == '2') {
                            return '<span class="badge bg-red">异常日志</span>';
                        } else if (data == '3') {
                            return '<span class="badge bg-purple">定时任务</span>';
                        }
                    }
                },
                {title: "请求URL", data: "requestUrl"},
                {title: "请求ip", data: "requestIp"},
                {title: "设备", data: "device"},
                {title: "设备系统", data: "os"},
                {title: "浏览器", data: "browser"},
                {title: "耗时(毫秒)", data: "useTime"},
                {title: "发生时间", data: "insertTime"}
            ]
        });

        //详情
        $('#datatable tbody').on('click', 'td.details-control', function () {
            var tr = $(this).closest('tr');
            var row = datatable.row(tr);
            if (row.child.isShown()) {
                row.child.hide();
                tr.removeClass('details');
            } else {
                var result = formatData(row.data());
                row.child(result).show();
                tr.addClass('details');
            }
        });

        //搜索
        $("#searchBtn").on('click', function () {
            datatable.ajax.reload();
        });

        //删除
        $("#deleteBtn").click(function () {
            krt.layer.confirm("你确定清空系统日志吗？", function () {
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/sys/log/deleteAll",
                    success: function (rb) {
                        if (rb.code == 200) {
                            krt.layer.msg(rb.msg,function () {
                                krt.table.reloadTable();
                            });
                        }else{
                            krt.layer.msg(rb.msg);
                        }
                    }
                });
            });
        });

    });
</script>
</@footer>

