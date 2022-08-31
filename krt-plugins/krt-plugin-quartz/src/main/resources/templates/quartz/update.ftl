<#include "/common/layoutForm.ftl">
<@header></@header>
<@body>
    <div class="wrapper">
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <form role="form" class="form-horizontal krt-form" id="krtForm">
                        <div class="box-body">
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="code" class="control-label col-sm-4">
                                            <span class="form-required">*</span>组名：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="groupName" id="groupName" value="${(quartz.groupName)!}" class="form-control" placeholder="请输入组名" required maxlength="10">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="name" class="control-label col-sm-4">
                                            <span class="form-required">*</span>任务名：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="name" id="name" value="${(quartz.name)!}" class="form-control" placeholder="请输入任务名" required maxlength="10">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="sort" class="control-label col-sm-4">
                                            <span class="form-required">*</span>cron表达式：</label>
                                        <div class="col-sm-8">
                                            <div class="input-group">
                                                <input type="text" name="expression" id="expression" value="${(quartz.expression)!}" placeholder="请输入cron表达式" class="form-control" readonly required>
                                                <div class="input-group-btn">
                                                    <button class="btn btn-default" id="cronBtn" type="button">
                                                        <i class="fa fa-cog"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="sort" class="control-label col-sm-4">
                                            <span class="form-required">*</span>是否同步：</label>
                                        <div class="col-sm-8">
                                            <input type="radio" name="isConcurrent" value="1" class="icheck"> 是
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="radio" name="isConcurrent" value="0" class="icheck" checked="checked"> 否
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="sort" class="control-label col-sm-4">
                                            <span class="form-required">*</span>springId：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="springId" id="springId" value="${(quartz.springId)!}" placeholder="请输入springId" class="form-control" required>
                                            <span class="help-inline">spring管理的bean名称</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="sort" class="control-label col-sm-4">
                                            <span class="form-required">*</span>方法名：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="method" id="method" value="${(quartz.method)!}" placeholder="请输入方法名" class="form-control" required>
                                            <span class="help-inline">执行任务的方法名</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="remark" class="control-label col-sm-2">描述：</label>
                                        <div class="col-sm-10">
                                            <textarea rows="4" name="description" class="form-control" placeholder="请输入描述" maxlength="200">${(quartz.description)!}</textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- /.box-body -->
                        <!-- 参数 -->
                        <input type="hidden" name="id" id="id" value="${quartz.id}"/>
                    </form>
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
            validateForm = $("#krtForm").validate({
                rules: {
                    name: {
                        remote: {
                            url: "${basePath}/quartz/checkName",
                            type: "post",
                            dataType: "json",
                            data: {
                                id: function () {
                                    return $("#id").val();
                                },
                                name: function () {
                                    return $("#name").val();
                                },
                                groupName: function () {
                                    return $("#groupName").val();
                                }
                            }
                        }
                    },
                },
                messages: {
                    name: {remote: "组名和任务名组合重复"}
                }
            });

            //表达式
            $("#expression,#cronBtn").click(function () {
                krt.layer.open({
                    type: 2,
                    title: "生成con表达式",
                    area: ['820px', '600px'],
                    content: '${basePath}/quartz/quartzCron',
                    btn: ['确定', '取消', '关闭'],
                    yes: function (index, layero) {
                        var cron = layero.find("iframe")[0].contentWindow.$("#cron").val();
                        $("#expression").val(cron);
                        validateForm.form();
                        krt.layer.close(index);
                    },
                    btn2: function () {
                        $("#expression").val("");
                    },
                    cancel: function (index) {
                        krt.layer.close(index);
                    }
                });
            });
        });

        //提交
        function doSubmit() {
            krt.ajax({
                type: "POST",
                url: "${basePath}/quartz/update",
                data: $('#krtForm').serialize(),
                validateForm: validateForm,
                success: function (rb) {
                    krt.layer.msg(rb.msg);
                    if (rb.code === 200) {
                        var index = krt.layer.getFrameIndex(); //获取窗口索引
                        krt.table.reloadTable();
                        krt.layer.close(index);
                    }
                }
            });
        }
    </script>
</@footer>
