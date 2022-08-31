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
                                        <input type="text" name="houseName" id="houseName" value="${(rentHouse.houseName)!}" class="form-control">
                                    </td>
                                    <td class="active width-15">
                                        <label class="pull-right">
                                            地址
                                        </label>
                                    </td>
                                    <td class="width-35">
                                        <input type="text" name="address" id="address" value="${(rentHouse.address)!}" class="form-control">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="active width-15">
                                        <label class="pull-right">
                                            姓名
                                        </label>
                                    </td>
                                    <td class="width-35">
                                        <input type="text" name="name" id="name" value="${(rentHouse.name)!}" class="form-control">
                                    </td>
                                    <td class="active width-15">
                                        <label class="pull-right">
                                            联系电话
                                        </label>
                                    </td>
                                    <td class="width-35">
                                        <input type="text" name="phone" id="phone" value="${(rentHouse.phone)!}" class="form-control">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="active width-15">
                                        <label class="pull-right">
                                            身份证号码
                                        </label>
                                    </td>
                                    <td class="width-35">
                                        <input type="text" name="idCard" id="idCard" value="${(rentHouse.idCard)!}" class="form-control"  readonly="true">
                                    </td>
                                    <td class="active width-15">
                                        <label class="pull-right">
                                            银行卡账号
                                        </label>
                                    </td>
                                    <td class="width-35">
                                        <input type="text" name="bankCard" id="bankCard" value="${(rentHouse.bankCard)!}" class="form-control">
                                    </td>
                                </tr>
                                <input type="hidden" name="id" value="${(rentHouse.id)!}">
                            </table>
                        </div>
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
            url: "${basePath}/rent/rentHouse/update",
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

