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
                                            <span class="form-required">*</span>用户账号：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" name="username" id="username" class="form-control" value="${(sessionUser.username)!}" readonly>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="oldPassword" class="control-label col-sm-4">
                                            <span class="form-required">*</span>原密码：</label>
                                        <div class="col-sm-5">
                                            <input type="password" name="oldPassword" id="oldPassword" class="form-control" placeholder="请输入原密码" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="password" class="control-label col-sm-4">
                                            <span class="form-required">*</span>新密码：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="password" name="password" id="password" class="form-control" placeholder="请输入新密码" isPwd="true" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="password2" class="control-label col-sm-4">
                                            <span class="form-required">*</span>重复密码：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="password" name="password2" id="password2" class="form-control" equalTo="#password" placeholder="请输入重复密码" isPwd="true" required>
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
            validateForm = $("#krtForm").validate({
                rules: {
                    oldPassword: {
                        remote: {
                            url: "${basePath}/sys/user/checkPsw",
                            type: "post",
                            dataType: "json",
                            async: false,
                            data: {
                                oldPassword: function () {
                                    return $("#oldPassword").val();
                                }
                            }
                        }
                    }
                },
                messages: {
                    oldPassword: {remote: "原密码错误"}
                }
            });
        });

        function doSubmit() {
            krt.ajax({
                type: "POST",
                url: "${basePath}/sys/user/updatePsw",
                data: $('#krtForm').serialize(),
                validateForm: validateForm,
                success: function (rb) {
                    if (rb.code === 200) {
                        krt.layer.msg("操作成功,请重新登录！", function () {
                            top.window.location.href = "${basePath}/logout";
                        });
                    } else {
                        krt.layer.msg(rb.msg);
                    }
                }
            });
        }
    </script>
</@footer>
