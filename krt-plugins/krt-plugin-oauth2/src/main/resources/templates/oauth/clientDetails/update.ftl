<#include "/common/layoutForm.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">修改客户端</h3>
                        </div>
                        <form role="form" class="form-horizontal krt-form krt-width-form" id="krtForm">
                            <div class="box-body">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="clientId" class="control-label col-sm-4">
                                                <span class="form-required">*</span>客户端id ：
                                            </label>
                                            <div class="col-sm-8">
                                                <input type="text" name="clientId" id="clientId" value="${(clientDetails.clientId)!}" class="form-control" required>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="clientSecret" class="control-label col-sm-4">
                                                <span class="form-required">*</span>客户端秘钥 ：</label>
                                            <div class="col-sm-8">
                                                <input type="text" name="clientSecret" id="clientSecret" value="${(clientDetails.clientSecret)!}" class="form-control" required>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="clientName" class="control-label col-sm-4">
                                                <span class="form-required">*</span>客户端名称 ：
                                            </label>
                                            <div class="col-sm-8">
                                                <input type="text" name="clientName" id="clientName" value="${(clientDetails.clientName)!}" class="form-control" required>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="clientUri" class="control-label col-sm-4">客户端地址：</label>
                                            <div class="col-sm-8">
                                                <input type="text" name="clientUri" id="clientUri" value="${(clientDetails.clientUri)!}" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="scope" class="control-label col-sm-4">客户端权限：</label>
                                            <div class="col-sm-8">
                                                <select name="scope" id="scope" class="form-control select2" style="width: 100%;">
                                                    <option value="">==请选择==</option>
                                                    <option value="read" ${((clientDetails.scope=='read')?string("selected",""))!}>read</option>
                                                    <option value="write" ${((clientDetails.scope=='write')?string("selected",""))!}>write</option>
                                                    <option value="read,write" ${((clientDetails.scope=='read,write')?string("selected",""))!}>read write</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="grantTypes" class="control-label col-sm-4">授权模式：</label>
                                            <div class="col-sm-8">
                                                <select name="grantTypes" id="grantTypes" class="form-control select2" multiple="multiple" style="width: 100%;">
                                                    <option value="">==请选择==</option>
                                                    <option value="authorization_code">authorization_code</option>
                                                    <option value="password">password</option>
                                                    <option value="refresh_token">refresh_token</option>
                                                    <option value="client_credentials">client_credentials</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="redirectUri" class="control-label col-sm-4">客户端重定向url：</label>
                                            <div class="col-sm-8">
                                                <input type="text" name="redirectUri" id="redirectUri" value="${(clientDetails.redirectUri)!}" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="trusted" class="control-label col-sm-4">是否为信任：</label>
                                            <div class="col-sm-8">
                                                <input type="radio" name="trusted" value="true" class="icheck" ${(clientDetails.trusted?string('checked',''))!}> 是
                                                &nbsp;&nbsp;&nbsp;&nbsp;
                                                <input type="radio" name="trusted" value="false" class="icheck" ${(clientDetails.trusted?string('','checked'))!}> 否
                                                <span class="help-inline">信任的客户端可以跳过用户授权</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="accessTokenValidity" class="control-label col-sm-4">access_token有效期：</label>
                                            <div class="col-sm-8">
                                                <input type="text" name="accessTokenValidity" id="accessTokenValidity" value="${(clientDetails.accessTokenValidity)!}" class="form-control">
                                                <span class="help-inline">单位: 秒</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label for="refreshTokenValidity" class="control-label col-sm-4">refresh_token有效期：</label>
                                            <div class="col-sm-8">
                                                <input type="text" name="refreshTokenValidity" id="refreshTokenValidity" value="${(clientDetails.refreshTokenValidity)!}" class="form-control">
                                                <span class="help-inline">单位: 秒</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label for="description" class="control-label col-sm-2">客户端描述：</label>
                                            <div class="col-sm-10">
                                                <textarea rows="3" id="description" name="description" class="form-control">${(clientDetails.description)!}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <div class=" col-sm-offset-2 col-sm-10">
                                                    <button type="button" id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> 提交</button>
                                                    <button type="button" id="closeIframeBtn" class="btn btn-danger"><i class="fa fa-close"></i> 关闭</button>
                                                    <button type="reset" id="resetBtn" class="btn btn-default"><i class="fa fa-refresh"></i> 重置</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- 参数 -->
                            <input type="hidden" name="id" id="id" value="${(clientDetails.id)!}">
                        </form>
                    </div>
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
                    clientId: {
                        remote: {
                            url: "${basePath}/oauth/clientDetails/checkClientId",
                            type: "post",
                            dataType: "json",
                            async: false,
                            data: {
                                id: function () {
                                    return $("#id").val();
                                },
                                clientId: function () {
                                    return $("#clientId").val();
                                }
                            }
                        }
                    }
                },
                messages: {
                    username: {remote: "客户端ID已存在"}
                }
            });

            //select2
            var grantTypes = '${clientDetails.grantTypes!}';
            var arrys = grantTypes.split(",");
            $("#grantTypes").select2().val(arrys).trigger("change");

            //提交表单
            $("#submitBtn").click(function () {
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/oauth/clientDetails/update",
                    data: $('#krtForm').serialize(),
                    validateForm: validateForm,
                    success: function (rb) {
                        krt.layer.msg(rb.msg);
                        if (rb.code == 200) {
                            top.krt.tab.closeThisTabs(function (contentWindow) {
                                contentWindow.datatable.ajax.reload();
                            })
                        }
                    }
                });
            })
        });
    </script>
</@footer>
