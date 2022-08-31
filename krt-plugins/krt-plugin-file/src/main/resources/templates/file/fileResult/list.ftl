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
                            <label for="name" class="control-label">文件名：</label>
                            <input type="text" id="name" name="name" placeholder="文件名" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="url" class="control-label">文件链接：</label>
                            <input type="text" id="url" name="url" placeholder="文件链接" class="form-control">
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
                <div class="table-toolbar" id="table-toolbar">
                    <@shiro.hasPermission name="file:fileResult:insert">
                        <div id='insertUploader' class="uploader" style="display: inline"></div>
                        <script type="text/javascript">
                            $(function () {
                                $('#insertUploader').krtUploader({
                                    type: 'single',            // 当选择文件后立即自动进行上传操作
                                    url: '${basePath}/upload/fileUpload?dir=all',  // 文件上传提交地址
                                    btn_set: {
                                        browse_btn_class: 'btn-success btn-sm',//上传按钮class
                                        browse_btn_text: '新增'//上传按钮文字
                                    },
                                    resultCallBack: function (data) {
                                        krt.layer.msg('文件上传成功',function () {
                                            datatable.ajax.reload();
                                        });
                                    },
                                    onError: function (error) {
                                        krt.layer.msg(error.message);
                                    }
                                });
                            });
                        </script>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="file:fileResult:delete">
                        <button class="btn btn-sm btn-danger" id="deleteBatchBtn">
                            <i class="fa fa-trash fa-btn"></i>批量删除
                        </button>
                    </@shiro.hasPermission>
                </div>
                <table id="datatable" class="table table-bordered table-hover"></table>
            </div>
        </div>
    </section>
</@body>
<@footer>
    <script type="text/javascript">
        var datatable;
        $(function () {
            //初始化datatable
            datatable = $('#datatable').DataTable({
                order: [0, 'desc'],
                ajax: {
                    url: "${basePath}/file/fileResult/list",
                    type: "post",
                    data: function (d) {
                        d.name = $("#name").val();
                        d.url = $("#url").val();
                        d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                        d.orderType = d.order[0].dir;
                    }
                },
                columns: [
                    {title: 'id', data: "id",visible:false},
                    {
                        title: '<input type="checkbox" id="checkAll" class="icheck">',
                        data: "id", class:"td-center", width:"40",orderable: false,
                        render: function (data) {
                            return '<input type="checkbox" class="icheck check" value="' + data + '">';
                        }
                    },
                    {title: "文件名", data: "name"},
                    {title: "文件大小(KB)", data: "fileLenght",},
                    {title: "文件md5", data: "md5"},
                    {title: "文件链接", data: "url"},
                    {
                        titl: "操作", data: "id",orderable: false,
                        render: function (data, type, row) {
                            return ' <@shiro.hasPermission name="file:fileResult:see">'
                                    + '<button class="btn btn-xs btn-info seeBtn" url="' + row.url + '">'
                                    + '<i class="fa fa-eye fa-btn"></i>查看'
                                    + '</button>'
                                    + '</@shiro.hasPermission>'
                                    + '<@shiro.hasPermission name="file:fileResult:delete">'
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

            //查看
            $(document).on("click", ".seeBtn", function () {
                var url = $(this).attr("url");
                window.open(url);
            });

            //删除
            $(document).on("click", ".deleteBtn", function () {
                var id = $(this).attr("rid");
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/file/fileResult/delete",
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
                            url: "${basePath}/file/fileResult/deleteByIds",
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
