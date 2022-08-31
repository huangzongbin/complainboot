<#include "/common/layoutForm.ftl">
<@header></@header>
<@body >
<div class="wrapper">
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                    <form role="form" class="form-horizontal krt-form" id="krtForm">
                        <div class="box-body">
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         商家：
                    </label>
                    <div class="col-sm-8">
    <select class="form-control select2" style="width: 100%" id="store" name="store">
        <option value="">==请选择==</option>
            <@dic type="" ; dicList>
                <#list dicList as item>
                    <option value="${item.code}" ${((goodsDetail.store==item.code)?string("selected",""))!}>${item.name}</option>
                </#list>
            </@dic>
    </select>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         商品名称：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="name" name="name" value="${goodsDetail.name!}" class="form-control">
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         商品编号：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="code" name="code" value="${goodsDetail.code!}" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         数量：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="amount" name="amount" value="${goodsDetail.amount!}" class="form-control">
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         1:表示样衣 2 表示商品货号：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="type" name="type" value="${goodsDetail.type!}" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         增减类型： 1表示发货  2表示退货：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="creaseType" name="creaseType" value="${goodsDetail.creaseType!}" class="form-control">
                    </div>
                </div>
            </div>
                </div>
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         单价：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="price" name="price" value="${goodsDetail.price!}" class="form-control">
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="pname" class="control-label col-sm-4">
                         总价：
                    </label>
                    <div class="col-sm-8">
    <input type="text" id="priceTotal" name="priceTotal" value="${goodsDetail.priceTotal!}" class="form-control">
                    </div>
                </div>
            </div>
                </div>
    <!-- 隐藏域 -->
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
            url: "${basePath}/goodsDetail/goodsDetail/update",
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

