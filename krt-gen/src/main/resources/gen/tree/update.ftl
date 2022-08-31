<#include "../macro.ftl">
${'<#include "/common/layoutForm.ftl">'}
${"<@header>"}${"</@header>"}
<${"@"}body <#if formType=='tab'>class="body-bg-default"</#if>>
<div class="wrapper">
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <#if formType=='tab'>
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">修改${(genTable.comment)!}</h3>
                    </div>
                </#if>
                    <form role="form" class="form-horizontal krt-form" id="krtForm">
                        <div class="box-body">
                            <@form genTable 'update'></@form>
                        </div>
                        <#if formType=='tab'>
                        <div class="box-footer">
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <@formButton genTable></@formButton>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </#if>
                    </form>
                <#if formType=='tab'>
                </div>
                </#if>
            </div>
        </div>
    </section>
</div>
${"</@body>"}
${"<@footer>"}
<script type="text/javascript">
    var validateForm;
    $(function () {
        //验证表单
        validateForm = $("#krtForm").validate({});

        <#if formType=='tab'>
        //提交表单
        $("#submitBtn").click(function () {
            krt.ajax({
                type: "POST",
                url: "${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/update",
                data: $('#krtForm').serialize(),
                validateForm:validateForm,
                success: function (rb) {
                    krt.layer.msg(rb.msg);
                    if (rb.code === 200) {
                        top.krt.tab.closeThisTabs(function (contentWindow) {
                            contentWindow.reloadTable();
                        })
                    }
                }
            });
        });
        </#if>
    });
    <#if formType=='dialog'>
    //提交
    function doSubmit() {
        krt.ajax({
            type: "POST",
            url: "${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/update",
            data: $('#krtForm').serialize(),
            validateForm: validateForm,
            success: function (rb) {
                krt.layer.msg(rb.msg);
                if (rb.code === 200) {
                    var index = krt.layer.getFrameIndex(); //获取窗口索引
                    var contentWindow = top.krt.tab.getContentWindow();
                    contentWindow.reloadTable();
                    krt.layer.close(index);
                }
            }
        });
    }
    </#if>
</script>
${"</@footer>"}

