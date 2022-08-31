<#include "/common/layoutForm.ftl">
<@header></@header>
<@body class="body-bg-default">
<div class="wrapper">
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <div class="box">
                    <form role="form" class="form-horizontal krt-form" id="krtForm">
                        <div class="box-body">
                            <!-- 隐藏域 -->
                            <table class="table table-bordered table-krt">
                                <tr>
                                    <td class="active width-15">
                                        <label class="pull-right">
                                            小区名称
                                        </label>
                                    </td>
                                    <td class="width-35">
                                        <input type="text" name="houseName"  id="houseName" required class="form-control">
                                    </td>
                                    <td class="active width-15">
                                        <label class="pull-right">
                                            地址
                                        </label>
                                    </td>
                                    <td class="width-35">
                                        <input type="text" name="address" id="address" class="form-control" required>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="active width-15">
                                        <label class="pull-right">
                                            姓名
                                        </label>
                                    </td>
                                    <td class="width-35">
                                        <input type="text" name="name" id="name" required class="form-control">
                                    </td>
                                    <td class="active width-15">
                                        <label class="pull-right">
                                            联系电话
                                        </label>
                                    </td>
                                    <td class="width-35">
                                        <input type="text" name="phone" id="phone" class="form-control">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="active width-15">
                                        <label class="pull-right">
                                            身份证号码
                                        </label>
                                    </td>
                                    <td class="width-35">
                                        <input type="text" name="idCard" id="idCard" required class="form-control">
                                    </td>
                                    <td class="active width-15">
                                        <label class="pull-right">
                                            银行卡账号
                                        </label>
                                    </td>
                                    <td class="width-35">
                                        <input type="text" name="bankCard" id="bankCard" class="form-control">
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <input type="hidden" name="houseType" value="${houseType!}" id="houseType" >
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
        //验证表单
        validateForm = $("#krtForm").validate({});
    });
    //提交表单
    function doSubmit() {
        krt.ajax({
            type: "POST",
            url: "${basePath}/rent/rentHouse/insert",
            data: $('#krtForm').serialize(),
            validateForm:validateForm,
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

