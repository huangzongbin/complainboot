<#include "/common/layoutForm.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">新增代码生成</h3>
                        </div>
                        <form action="${basePath}/gen/genTable/insert" id="krtForm" method="get" class="form-horizontal krt-form">
                            <div class="box-body">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label for="code" class="control-label col-sm-4">
                                                <span class="form-required">*</span>表名：
                                            </label>
                                            <div class="col-sm-5">
                                                <select name="name" id="name" class="select2 form-control" required>
                                                    <option value="">==请选择==</option>
                                                    <#if dbTableList??>
                                                        <#list dbTableList as table>
                                                            <option value="${table.name}">${table.name}</option>
                                                        </#list>
                                                    </#if>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <div class="col-sm-offset-4 col-sm-5">
                                                <button type="button" id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> 下一步</button>
                                                <button type="button" id="closeIframeBtn" class="btn btn-danger"><i class="fa fa-close"></i> 关闭</button>
                                                <button type="reset" id="resetBtn" class="btn btn-default"><i class="fa fa-refresh"></i> 重置</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <!-- /.box -->
                </div>
            </div>
        </section>
    </div>
</@body>
<@footer>
    <script type="text/javascript">
        var validateForm;
        $(function () {
            //表单验证
            validateForm = $("#krtForm").validate({});

            //提交表单
            $("#submitBtn").click(function () {
                if (validateForm.form()) {
                    krt.ajax({
                        url: "${basePath}/gen/genTable/checkTableName",
                        type: "post",
                        dataType: "json",
                        async: false,
                        data: {"tableName": $("#name").val()},
                        success: function (rb) {
                            if (rb.code == 200) {
                                window.location.href = "${basePath}/gen/genTable/update?id=" + rb.data.id + "&name=" + rb.data.name
                            } else {
                                $("#krtForm").submit();
                            }
                        }
                    })

                }
            })
        });
    </script>
</@footer>
