<#include "/common/layoutForm.ftl">
<@header></@header>
<@body >
<div class="wrapper">
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                    <form role="form" class="form-horizontal krt-form" id="krtForm">
                        <div class="box-body">
    <!-- 隐藏域 --><table class="table table-bordered table-krt">
                            <tr>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        投诉人姓名
                                    </label>
                                </td>
                                <td class="width-35">
                                    <input type="text" name="linkName" id="linkName" class="form-control">
                                </td>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        投诉人电话
                                    </label>
                                </td>
                                <td class="width-35">
                                    <input type="text" name="linkPhone" id="linkPhone" class="form-control">
                                </td>
                            </tr>
                            <tr>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        投诉举报内容
                                    </label>
                                </td>
                                <td class="width-35">
                                    <textarea name="cpnContent" id="cpnContent" class="form-control"></textarea>
                                </td>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        证明图片
                                    </label>
                                </td>
                                <td class="width-35">
                                    <input type="text" name="cpnImg" id="cpnImg" class="form-control">
                                </td>
                            </tr>
                            <tr>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        投诉举报时间
                                    </label>
                                </td>
                                <td class="width-35">
                                    <input type="text" name="cpnTime" id="cpnTime" class="form-control">
                                </td>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        状态：1待回复2已回复
                                    </label>
                                </td>
                                <td class="width-35">
                                    <input type="text" name="status" id="status" class="form-control">
                                </td>
                            </tr>
                            <tr>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        回复人
                                    </label>
                                </td>
                                <td class="width-35">
                                    <input type="text" name="optName" id="optName" class="form-control">
                                </td>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        回复名称
                                    </label>
                                </td>
                                <td class="width-35">
                                    <input type="text" name="optTime" id="optTime" class="form-control">
                                </td>
                            </tr>
                        </table>
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
        //验证表单
        validateForm = $("#krtForm").validate({});

    });
    //提交
    function doSubmit() {
        krt.ajax({
            type: "POST",
            url: "${basePath}/complain/complain/insert",
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

