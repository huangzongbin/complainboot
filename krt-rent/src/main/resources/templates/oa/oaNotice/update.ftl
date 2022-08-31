<#include "/common/layoutForm.ftl">
<@header></@header>
<link rel="stylesheet" href="${basePath}assets/plugins/kindeditor/themes/default/default.css"/>
<style>
    div.upload> .ke-upload-area>.ke-button-common{
        background: transparent;
        height: 20px;
    }
    div.upload> .ke-upload-area>.ke-button-common >.ke-button{
        background: transparent;
        color:white;
    }
</style>
<@body >
<div class="wrapper">
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <form role="form" class="form-horizontal krt-form" id="krtForm">
                    <div class="box-body">
                        <!-- 隐藏域 -->
                        <table class="table table-bordered table-krt">
                            <tr>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        标题
                                    </label>
                                </td>
                                <td class="width-35"><input type="text" name="title" id="title" value="${oaNotice.title}" class="form-control" rangelength="1,50" required ></td>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        图片
                                    </label>
                                </td>
                                <td class="width-35">
                                    <div class="input-group">
                                        <input class="form-control" type="text" id="img" name="img"  class="form-control"  value="${oaNotice.img}" readonly="readonly" />
                                        <span class="input-group-btn">
                              <button class="btn btn-primary btn-flat upload" id="uploadButton" type="button" value="上传" ignore>上传</button>
                            </span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        内容
                                    </label>
                                </td>
                                <td colspan="3">
                                    <textarea type="text" name="content" id="content" class="form-control" required>${oaNotice.content}</textarea>
                                </td>
                            </tr>
                            <!-- 参数 -->
                            <input type="hidden" name="author" value="${currentUser.name}"/>
                            <input type="hidden" name="id" value="${oaNotice.id}"/>
                        </table>
                    </div>
                </form>
            </div>
        </div>
    </section>
</div>
</@body>
<@footer>
<script src="${basePath}/kindeditor/kindeditor-min.js"></script>
<script>
    var editor;
    KindEditor.ready(function(K) {
        editor = K.create('textarea[name="content"]', {
            resizeType : 1,
            resizeType : 1,
            allowPreviewEmoticons: false,
            allowImageUpload:true,//允许上传图片
            allowFileManager:true, //允许对上传图片进行管理
            uploadJson:'${basePath}/kindeditor/fileUpload', //上传图片的java代码，只不过放在jsp中
            fileManagerJson:'${basePath}/kindeditor/fileManager',
            afterUpload: function(){this.sync();}, //图片上传后，将上传内容同步到textarea中
            afterBlur: function(){this.sync();},   ////失去焦点时，将上传内容同步到textarea中
            width:'100%',
            height:'300px',
            formatUploadUrl:false,
            //urlType:'domain'
        });

        var uploadbutton = K.uploadbutton({
            button: K('#uploadButton')[0],
            fieldName: 'imgFile',
            url: '${basePath}/kindeditor/fileUpload?dir=image',
            afterUpload: function (msg) {
                if (msg.error == '0') {
                    var url = K.formatUrl(msg.url, 'absolute');
                    K('#img').val(msg.url);
                } else {
                    layer.msg(msg.message);
                }
            }
        });
        uploadbutton.fileBox.change(function (e) {
            uploadbutton.submit();
        });
    });
</script>
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
            url: "${basePath}/oa/oaNotice/update",
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

