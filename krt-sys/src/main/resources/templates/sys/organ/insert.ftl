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
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="pname" class="control-label col-sm-4">
                                            上级机构：
                                        </label>
                                        <div class="col-sm-8">
                                            <!-- 参数 -->
                                            <input type="hidden" name="pid" id="pid" value="${(pOrgan.id)!}"
                                                   class="form-control">
                                            <div class="input-group">
                                                <input type="text" name="pname" value="${(pOrgan.name)!}" id="pname"
                                                       readonly class="form-control">
                                                <div class="input-group-btn">
                                                    <button class="btn btn-default" id="organTreeBtn" type="button">
                                                        <i class="fa fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="name" class="control-label col-sm-4">
                                            <span class="form-required">*</span>机构名称：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" name="name" id="name" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="code" class="control-label col-sm-4">
                                            <span class="form-required">*</span>机构代码：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" name="code" id="code" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="type" class="control-label col-sm-4">
                                            <span class="form-required">*</span>机构类别：
                                        </label>
                                        <div class="col-sm-8">
                                            <select name="type" id="type" class="form-control select2" required
                                                    style="width: 100%;">
                                                <option value="">==请选择==</option>
                                                <@dic type="organ_type";typeList>
                                                    <#list typeList as type>
                                                        <option value="${type.code}">${type.name}</option>
                                                    </#list>
                                                </@dic>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="type" class="control-label col-sm-4">
                                            <span class="form-required">*</span>机构类型：
                                        </label>
                                        <div class="col-sm-8">
                                            <select name="grade" id="grade" class="form-control select2" required
                                                    style="width: 100%;">
                                                <option value="">==请选择==</option>
                                                <@dic type="organ_grade";gradeList>
                                                    <#list gradeList as grade>
                                                        <option value="${grade.code}">${grade.name}</option>
                                                    </#list>
                                                </@dic>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="primaryMan" class="control-label col-sm-4">主要负责人：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="primaryMan" id="primaryMan"
                                                   class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="viceMan" class="control-label col-sm-4">副负责人：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="viceMan" id="viceMan" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="linkMan" class="control-label col-sm-4">联系人：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="linkMan" id="linkMan" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="zipcode" class="control-label col-sm-4">邮编：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="zipcode" id="zipcode" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="phone" class="control-label col-sm-4">手机号码：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="phone" id="phone" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="email" class="control-label col-sm-4">邮箱：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="email" id="email" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="regionId" class="control-label col-sm-4">区域信息：</label>
                                        <div class="col-sm-8">
                                            <div class="input-group">
                                                <input type="text" name="regionName" id="regionName" readonly
                                                       class="form-control">
                                                <div class="input-group-btn">
                                                    <button class="btn btn-default" id="regionTreeBtn"
                                                            type="button">
                                                        <i class="fa fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- 参数 -->
                                        <input type="hidden" name="regionId" id="regionId" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="address" class="control-label col-sm-2">联系地址：</label>
                                        <div class="col-sm-10">
                                            <input type="text" name="address" id="address" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="remark" class="control-label col-sm-2">备注：</label>
                                        <div class="col-sm-10">
                                                <textarea type="text" name="remark" id="remark" rows="4"
                                                          class="form-control"></textarea>
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
                    code: {
                        remote: {
                            url: "${basePath}/sys/organ/checkCode",
                            type: "post",
                            dataType: "json",
                            async: false,
                            data: {
                                code: function () {
                                    return $("#code").val();
                                }
                            }
                        }
                    }
                },
                messages: {
                    code: {remote: "机构代码已存在"}
                }
            });

            //机构
            $("#pname,#organTreeBtn").click(function () {
                var url = "${basePath}/sys/organ/organTree";
                krt.layer.open({
                    type: 2,
                    area: ['300px', '450px'],
                    title: "选择机构",
                    maxmin: false,
                    content: "${basePath}/common/treeSelect?url=" + encodeURI(url) + "&sValue=" + $("#pid").val(),
                    btn: ['确定', '取消', '关闭'],
                    yes: function (index, layero) {
                        var tree = layero.find("iframe")[0].contentWindow.tree;
                        var nodes = tree.getSelectedNodes();
                        if (nodes == null || nodes == '') {
                            krt.layer.msg("请选择机构");
                        } else {
                            $("#pname").val(nodes[0].name);
                            $("#pid").val(nodes[0].id);
                            krt.layer.close(index);
                        }
                    },
                    btn2: function () {
                        $("#pname").val("");
                        $("#pid").val("");
                    },
                    cancel: function (index) {
                        krt.layer.close(index);
                    }
                });
            });

            //区域
            $("#regionName,#regionTreeBtn").click(function () {
                var url = "${basePath}/sys/region/regionTree";
                krt.layer.open({
                    type: 2,
                    area: ['300px', '500px'],
                    title: "选择区域",
                    maxmin: false, //开启最大化最小化按钮
                    content: "${basePath}/common/treeSelect?url=" + encodeURI(url) + "&sValue=" + $("#regionId").val(),
                    btn: ['确定', '取消', '关闭'],
                    yes: function (index, layero) {
                        var tree = layero.find("iframe")[0].contentWindow.tree;
                        var nodes = tree.getSelectedNodes();
                        if (nodes == null || nodes == '') {
                            krt.layer.msg("请选择区域");
                        } else {
                            $("#regionName").val(nodes[0].name);
                            $("#regionId").val(nodes[0].id);
                            krt.layer.close(index);
                        }
                    },
                    btn2: function () {
                        $("#regionName").val("");
                        $("#regionId").val("");
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
                url: "${basePath}/sys/organ/insert",
                data: $('#krtForm').serialize(),
                validateForm: validateForm,
                success: function (rb) {
                    krt.layer.msg(rb.msg);
                    if (rb.code === 200) {
                        var index = krt.layer.getFrameIndex(); //获取窗口索引
                        var contentWindow = top.krt.tab.getContentWindow();
                        contentWindow.reloadTable();
                        contentWindow.initTree();
                        krt.layer.close(index);
                    }
                }
            });
        }
    </script>
</@footer>

