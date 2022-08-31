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
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="code" class="control-label col-sm-4">
                                            <span class="form-required">*</span>角色名称：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control" id="name" name="name"
                                                   placeholder="请输入角色名称" required maxlength="10">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="name" class="control-label col-sm-4">
                                            <span class="form-required">*</span>角色编码：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control" id="code" name="code"
                                                   placeholder="请输入角色编码" required maxlength="10">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="sort" class="control-label col-sm-4">
                                            <span class="form-required">*</span>排序：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control" id="sort" name="sort"
                                                   placeholder="请输入排序" required digits="true">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="remark" class="control-label col-sm-4">备注：</label>
                                        <div class="col-sm-5">
                                            <textarea rows="4" name="remark" class="form-control" placeholder="请输入备注" maxlength="200"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
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
                            url: "${basePath}/sys/role/checkName",
                            type: "post",
                            dataType: "json",
                            async: false,
                            data: {
                                name: function () {
                                    return $("#name").val();
                                }
                            }
                        }
                    },
                    code: {
                        remote: {
                            url: "${basePath}/sys/role/checkCode",
                            type: "post",
                            dataType: "json",
                            data: {
                                code: function () {
                                    return $("#code").val();
                                }
                            }
                        }
                    },
                },
                messages: {
                    name: {remote: "角色名已存在"},
                    code: {remote: "角色编码已存在"}
                }
            });
        });

        //提交
        function doSubmit() {
            krt.ajax({
                type: "POST",
                url: "${basePath}/sys/role/insert",
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
