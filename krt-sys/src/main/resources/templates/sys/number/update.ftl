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
                                            <span class="form-required">*</span>流水号编码：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" name="code" id="code" value="${(number.code)!}" class="form-control" placeholder="请输入流水号编码" required maxlength="10">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="name" class="control-label col-sm-4">
                                            <span class="form-required">*</span>流水号名称：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" name="name" id="name" value="${(number.name)!}" class="form-control" placeholder="请输入流水号名称" required maxlength="20">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="sort" class="control-label col-sm-4">前缀：</label>
                                        <div class="col-sm-5">
                                            <input type="text" name="prefix" id="prefix" value="${(number.prefix)!}" class="form-control" placeholder="请输入流水号前缀">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="sort" class="control-label col-sm-4">后缀：</label>
                                        <div class="col-sm-5">
                                            <input type="text" name="suffix" id="suffix" value="${(number.suffix)!}" class="form-control" placeholder="请输入流水号后缀">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="sort" class="control-label col-sm-4">
                                            <span class="form-required">*</span>流水号：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" name="num" id="num" class="form-control" value="${(number.num)!}" readonly required>
                                            <span class="help-inline">若有日期格式,则根据日期规则自增，否则一直自增</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="sort" class="control-label col-sm-4">
                                            <span class="form-required">*</span>流水号长度：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" name="numLength" id="numLength" value="${(number.numLength)!}" class="form-control" placeholder="请输入流水号长度" required rangelength="1,10">
                                            <span class="help-inline">长度不包括前、后缀和日期</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="sort" class="control-label col-sm-4">
                                            <span class="form-required">*</span>日期格式化：
                                        </label>
                                        <div class="col-sm-5">
                                            <select name="dateFormat" id="dateFormat" class="form-control select2" style="width: 100%">
                                                <option value="">==请选择==</option>
                                                <option value="%Y%m%d%H%i%s" ${(number.dateFormat=="%Y%m%d%H%i%s")?string("selected","")}>年月日时分秒</option>
                                                <option value="%Y%m%d%H%i" ${(number.dateFormat=="%Y%m%d%H%i")?string("selected","")}>年月日时分</option>
                                                <option value="%Y%m%d" ${(number.dateFormat=="%Y%m%d")?string("selected","")}>年月日</option>
                                                <option value="%Y%m" ${(number.dateFormat=="%Y%m")?string("selected","")}>年月</option>
                                                <option value="%Y" ${(number.dateFormat=="%Y")?string("selected","")}>年</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- 参数 -->
                        <input type="hidden" name="id" id="id" value="${number.id}">
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
                    code: {
                        remote: {
                            url: "${basePath}/sys/number/checkNumber",
                            type: "post",
                            dataType: "json",
                            async: false,
                            data: {
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
                    code: {remote: "流水编号已存在"}
                }
            });
            validateForm.form();
        });

        //提交
        function doSubmit() {
            krt.ajax({
                type: "POST",
                url: "${basePath}/sys/number/update",
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
