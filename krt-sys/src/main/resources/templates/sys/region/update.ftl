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
                                        <label for="pname" class="control-label col-sm-4">
                                            上级区域
                                            ：</label>
                                        <div class="col-sm-5">
                                            <!-- 参数 -->
                                            <input type="hidden" name="pid" id="pid" value="${(pRegion.id)!}" class="form-control">
                                            <div class="input-group">
                                                <input type="text" name="pname" value="${(pRegion.name)!}" id="pname" readonly class="form-control">
                                                <div class="input-group-btn">
                                                    <button class="btn btn-default" id="regionTreeBtn" type="button">
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
                                        <label for="type" class="control-label col-sm-4">
                                            <span class="form-required">*</span>区域类型
                                            ：</label>
                                        <div class="col-sm-5">
                                            <select class="form-control select2" name="type" required style="width: 100%;">
                                                <option value="">==请选择==</option>
                                                <@dic type="region_type";typeList>
                                                    <#list typeList as type>
                                                        <option value="${type.code}" ${(type.code==region.type)?string("selected","")}>${type.name}</option>
                                                    </#list>
                                                </@dic>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="name" class="control-label col-sm-4">
                                            <span class="form-required">*</span>区域名称
                                            ：</label>
                                        <div class="col-sm-5">
                                            <input type="text" name="name" id="name" value="${(region.name)!}" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="code" class="control-label col-sm-4">
                                            <span class="form-required">*</span>区域编码
                                            ：</label>
                                        <div class="col-sm-5">
                                            <input type="text" name="code" id="code" value="${(region.code)!}" class="form-control" rangelength="2,20" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- 参数 -->
                        <input type="hidden" id="id" name="id" value="${(region.id)!}">
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
                            url: "${basePath}/sys/region/checkCode",     //后台处理程序
                            type: "post",               //数据发送方式
                            dataType: "json",           //接受数据格式
                            data: {                     //要传递的数据
                                id: function () {
                                    return $("#id").val();
                                },
                                code: function () {
                                    return $("#code").val();
                                }
                            }
                        }
                    }
                },
                messages: {
                    code: {remote: "区域编码已存在"}
                },
            });

            //区域
            $("#pname,#regionTreeBtn").click(function () {
                var url = "${basePath}/sys/region/regionTree";
                krt.layer.open({
                    type: 2,
                    area: ['300px', '450px'],
                    title: "选择区域",
                    maxmin: false,
                    content: "${basePath}/common/treeSelect?url=" + encodeURI(url) + "&sValue=" + $("#pid").val(),
                    btn: ['确定', '取消', '关闭'],
                    yes: function (index, layero) {
                        var tree = layero.find("iframe")[0].contentWindow.tree;
                        var nodes = tree.getSelectedNodes();
                        if (nodes == null || nodes == '') {
                            krt.layer.msg("请选择资源");
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
        });

        //提交
        function doSubmit() {
            krt.ajax({
                type: "POST",
                url: "${basePath}/sys/region/update",
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

