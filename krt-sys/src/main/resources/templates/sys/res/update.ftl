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
                                        <label for="code" class="control-label col-sm-4">上级菜单：</label>
                                        <div class="col-sm-8">
                                            <!-- 参数 -->
                                            <input type="hidden" name="pid" id="pid" value="${(pRes.id)!}" class="form-control">
                                            <div class="input-group">
                                                <input type="text" name="pname" value="${(pRes.name)!}" id="pname" readonly class="form-control">
                                                <div class="input-group-btn">
                                                    <button class="btn btn-default" id="resTreeBtn" type="button">
                                                        <i class="fa fa-search"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="code" class="control-label col-sm-4">
                                            <span class="form-required">*</span>菜单名称：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="name" id="name" value="${(res.name)!}" class="form-control" rangelength="1,50" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="url" class="control-label col-sm-4">链接：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="url" id="url" value="${(res.url)!}" class="form-control">
                                            <span class="help-inline">点击菜单跳转的页面,按钮为空</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="permission" class="control-label col-sm-4">权限标志：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="permission" id="permission" value="${(res.permission)!}" class="form-control">
                                            <span class="help-inline">控制器中定义的权限标识 如：@RequiresPermissions("权限标识")</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="icon" class="control-label col-sm-4">图标：</label>
                                        <div class="col-sm-8">
                                            <#if res.icon??>
                                                <i id="iconIcon" class="fa ${(res.icon)!}"></i>
                                                <span id="iconIconLabel">${(res.icon)!}</span>
                                            <#else>
                                                <i id="iconIcon" class="icon- hide"></i>
                                                <span id="iconIconLabel">无</span>
                                            </#if>
                                            <input id="icon" type="hidden" value="${res.icon!}" name="icon">
                                            <a id="iconButton" class="btn btn-primary" href="javascript:">选择</a>
                                            <input id="iconclear" class="btn btn-default" type="button" value="清除">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="type" class="control-label col-sm-4">类别：</label>
                                        <div class="col-sm-8">
                                            <input type="radio" name="type" value="url" class="icheck" ${(res.type=='url')?string('checked','')}> 菜单
                                            <input type="radio" name="type" value="button" class="icheck" ${(res.type=='button')?string('checked','')}> 按钮
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="target" class="control-label col-sm-4">目标：</label>
                                        <div class="col-sm-8">
                                            <select name="target" id="target" class="form-control select2" style="width: 100%;">
                                                <option value="">iframe</option>
                                                <option value="_blank" ${((res.target=='_blank')?string('selected',''))!}>新tab页</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="sort" class="control-label col-sm-4">排序：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="sort" id="sort" value="${(res.sort)!}" class="form-control" digits="true" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="remark" class="control-label col-sm-2">备注：</label>
                                        <div class="col-sm-10">
                                            <textarea rows="3" id="remark" name="remark" class="form-control">${(res.remark)!}</textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- 参数 -->
                            <input type="hidden" name="id" id="id" value="${(res.id)!}">
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
            validateForm = $("#krtForm").validate({});

            //选择上级菜单
            $("#pname,#resTreeBtn").click(function () {
                var url = "${basePath}/sys/res/resTree";
                krt.layer.open({
                    type: 2,
                    area: ['310px', '450px'],
                    title: "选择菜单",
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

            //选择图标
            $("#iconButton").click(function () {
                krt.layer.open({
                    type: 2,
                    title: "选择图标",
                    area: ['800px', '500px'],
                    content: '${basePath}/sys/res/resIcon?value="+$("#icon").val()',
                    btn: ['确定', '关闭'],
                    maxmin: true,
                    yes: function (index, layero) {
                        var icon = layero.find("iframe")[0].contentWindow.$("#icon").val();
                        $("#iconIcon").attr("class", "fa " + icon);
                        $("#iconIconLabel").text(icon);
                        $("#icon").val(icon);
                        krt.layer.close(index);
                    }, cancel: function (index) {
                        krt.layer.close(index);
                    }
                });
            });

            //清除图标
            $("#iconclear").click(function () {
                $("#iconIcon").attr("class", "icon- hide");
                $("#iconIconLabel").text("无");
                $("#icon").val("");
            });
        });

        //提交
        function doSubmit() {
            krt.ajax({
                type: "POST",
                url: "${basePath}/sys/res/update",
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
