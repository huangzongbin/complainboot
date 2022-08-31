<#include "/common/layoutForm.ftl">
<@header></@header>
<@body>
    <div class="wrapper">
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <form role="form" class="form-horizontal krt-form krt-width-form" id="krtForm">
                        <div class="box-body">
                            <div class="row">
                                <div class="form-group">
                                    <div class="box-body box-profile">
                                        <input type="hidden" name="avatar" id="avatar"/>
                                        <div id='avatarUploader' class="uploader photo-center"></div>
                                        <script type="text/javascript">
                                            $(function () {
                                                $('#avatarUploader').krtUploader({
                                                    type: 'photo',            // 当选择文件后立即自动进行上传操作
                                                    chunk_size: "0",
                                                    url: '${basePath}/upload/fileUpload?dir=image',  // 文件上传提交地址
                                                    resultCallBack: function (data) {
                                                        $('#avatarUploader').find('.img-photo').find('img').prop('src', data);
                                                        $("#avatar").val(data);
                                                    }
                                                });
                                            })
                                        </script>
                                        <div class="form-group">
                                            <div class="col-sm-12" style="text-align: center">
                                                <input type="radio" class="icheck" name="sex" value="0" checked> 男
                                                &nbsp;&nbsp;&nbsp;&nbsp;
                                                <input type="radio" class="icheck" name="sex" value="1"> 女
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="username" class="control-label col-sm-4">
                                            <span class="form-required">*</span>用户账号：</label>
                                        <div class="col-sm-6">
                                            <div class="input-group">
                                                <input type="text" name="username" id="username"
                                                       class="form-control" placeholder="请输入用户账号"
                                                       rangelength="2,16" required>
                                                <div class="input-group-addon input-group-addon-right-radius">
                                                    <i class="fa fa-user"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="name" class="control-label col-sm-4">
                                            <span class="form-required">*</span>用户姓名：</label>
                                        <div class="col-sm-6">
                                            <input type="text" name="name" id="name" class="form-control"
                                                   placeholder="请输入用户姓名" rangelength="1,10" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="password" class="control-label col-sm-4">
                                            <span class="form-required">*</span>用户密码：</label>
                                        <div class="col-sm-6">
                                            <div class="input-group">
                                                <input type="password" name="password" id="password"
                                                       class="form-control" placeholder="请输入密码"
                                                       isPwd="true" required>
                                                <div class="input-group-addon input-group-addon-right-radius">
                                                    <i class="fa fa-unlock-alt"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="password2" class="control-label col-sm-4">
                                            <span class="form-required">*</span>重复密码：</label>
                                        <div class="col-sm-6">
                                            <div class="input-group">
                                                <input type="password" name="password2" id="password2"
                                                       class="form-control" placeholder="请再次输入密码"
                                                       equalTo="#password" isPwd="true" required>
                                                <div class="input-group-addon input-group-addon-right-radius">
                                                    <i class="fa fa-unlock-alt"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="birthday" class="control-label col-sm-4">出生日期：</label>
                                        <div class="col-sm-6">
                                            <div class="input-group">
                                                <input type="text" class="form-control pull-right"
                                                       name="birthday" id="birthday" placeholder="请输入出生日期"
                                                       readonly onClick="WdatePicker()">
                                                <div class="input-group-addon input-group-addon-right-radius">
                                                    <i class="fa fa-calendar"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="phone" class="control-label col-sm-4">手机号码：</label>
                                        <div class="col-sm-6">
                                            <div class="input-group">
                                                <input type="text" class="form-control" name="phone"
                                                       placeholder="请输入手机号码" isMobile="true">
                                                <div class="input-group-addon input-group-addon-right-radius">
                                                    <i class="fa fa-phone"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="organName" class="control-label col-sm-4">组织机构：</label>
                                        <div class="col-sm-6">
                                            <div class="input-group">
                                                <!-- 参数 -->
                                                <input type="hidden" name="organId" id="organId"
                                                       class="form-control">
                                                <input type="text" name="organName" id="organName" readonly
                                                       class="form-control">
                                                <div class="input-group-btn input-group-addon-right-radius">
                                                    <button class="btn btn-default" id="organTreeBtn" type="button">
                                                        <i class="fa fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="roleIds" class="control-label col-sm-4">所属角色：</label>
                                        <div class="col-sm-6">
                                            <#list roleList as role>
                                                <#if role.code != 'admin'>
                                                    <input name="roleIds" value="${role.id}" type="checkbox"
                                                           class="icheck" required> ${role.name}
                                                </#if>
                                            </#list>
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
                    username: {
                        remote: {
                            url: "${basePath}/sys/user/checkUsername",
                            type: "post",
                            dataType: "json",
                            async: false,
                            data: {
                                username: function () {
                                    return $("#username").val();
                                }
                            }
                        }
                    }
                },
                messages: {
                    username: {remote: "用户名已存在"}
                }
            });

            //机构
            $("#organName,#organTreeBtn").click(function () {
                var url = "${basePath}/sys/organ/organTree";
                krt.layer.open({
                    type: 2,
                    area: ['300px', '450px'],
                    title: "选择机构",
                    maxmin: false,
                    content: "${basePath}/common/treeSelect?url=" + encodeURI(url) + "&sId=code&sValue=" + $("#organId").val() + "&expandAll=true",
                    btn: ['确定', '取消', '关闭'],
                    yes: function (index, layero) {
                        var tree = layero.find("iframe")[0].contentWindow.tree;
                        var nodes = tree.getSelectedNodes();
                        if (nodes == null) {
                            krt.layer.msg("请选择机构");
                        } else {
                            $("#organName").val(nodes[0].name);
                            $("#organId").val(nodes[0].id);
                            krt.layer.close(index);
                        }
                    },
                    btn2: function () {
                        $("#organName").val("");
                        $("#organId").val("");
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
                url: "${basePath}/sys/user/insert",
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
