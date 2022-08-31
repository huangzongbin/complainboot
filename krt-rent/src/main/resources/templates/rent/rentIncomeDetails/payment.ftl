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
                                            姓名：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control" id="name" name="name"
                                                   value="${(rentIncomeDetails.name)!}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="name" class="control-label col-sm-4">
                                           缴纳方式：</label>
                                        <div class="col-sm-6">
                                            <input type="text"  readonly value="扫码盒子" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="totalRental" class="control-label col-sm-4">
                                            <span class="form-required">*</span> 合计租金(元)：
                                        </label>
                                        <div class="col-sm-5">

                                            <input type="number" class="form-control" id="totalRental" name="totalRental" required
                                                   value="${(rentIncomeDetails.totalRental)!}" min="0">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="remark" class="control-label col-sm-4">备注：</label>
                                        <div class="col-sm-5">
                                            <textarea rows="2" name="remark" class="form-control" placeholder="请输入备注" maxlength="100"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="payee" class="control-label col-sm-4">
                                            <span class="form-required">*</span>收款人：</label>
                                        <div class="col-sm-6">
                                            <input type="text" name="payee" id="payee" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
            <!-- 参数 -->
            <input type="hidden" id="id" name="id" value="${(rentIncomeDetails.id)!}">
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
                url: "${basePath}/rent/rentIncomeBase/payment",
                data: $('#krtForm').serialize(),
                validateForm:validateForm,
                success: function (rb) {
                    krt.layer.msg(rb.msg);
                    if (rb.code === 200) {
                        krt.layer.msg("缴纳成功!");
                        var index = krt.layer.getFrameIndex(); //获取窗口索引
                        krt.table.reloadTable();
                        krt.layer.close(index);
                    }
                }
            });
        }
    </script>
</@footer>
