<#assign basePath=request.contextPath />
<#assign uploadPath = basePath + "/kindeditor/fileUpload" />
<#assign fileManagerPath = basePath + "/kindeditor/fileManager" />
<#--
 编辑器宏标签
-->
<#macro editor name , width='100%' , height = '300px',value='' ,validate=''>
<textarea id="${name}" name="${name}" class="form-control" ${validate!}>${value!}</textarea>
<script type="text/javascript">
    $(function () {
        KindEditor.ready(function (K) {
            var ${name}Editor = K.create('textarea[id="${name}"]', {
                resizeType: 1,
                allowPreviewEmoticons: false,
                allowImageUpload: true,//允许上传图片
                allowFileManager: true, //允许对上传图片进行管理
                uploadJson: '${uploadPath}',
                fileManagerJson: '${fileManagerPath}',
                afterUpload: function () {
                    this.sync();
                },
                afterBlur: function () {
                    this.sync();
                },
                width: '${width}',
                height: '${height}',
                formatUploadUrl: false
            });
        });
    })
</script>
</#macro>

<#--
导出宏标签
-->
<#macro excelOut id='',url='',btnClass='bg-orange',btnText='导出',onClick=''>
<button type="button" id="${id}" class="btn btn-sm ${btnClass}">
    <i class="fa fa-cloud-upload fa-btn"></i> ${btnText}
</button>
<script type="text/javascript">
    $("#${id}").click(function () {
        <#if onClick??>
            window.location.href = "${url}";
        <#else>
            eval("${onClick}()");
        </#if>
    });
</script>
</#macro>

<#--
导入宏标签
-->
<#macro excelIn id='',url='',btnClass='btn-sm bg-purple excelOut' btnText='导入', sucessMsg='导入成功'>
<div id='${id}' class="uploader" style="display: inline"></div>
<script type="text/javascript">
    $(function(){
        $('#${id}').krtUploader({
            type: 'single',            // 当选择文件后立即自动进行上传操作
            chunk_size: "0",  //不切片
            url: "${url}",  // 文件上传提交地址
            btn_set:{
                browse_btn_class:'${btnClass}',//上传按钮class
                browse_btn_text:'${btnText}'//上传按钮文字
            },
            resultCallBack:function(data){
                krt.layer.msg('${sucessMsg}');
                if($("#datatable").length>0) {
                    datatable.ajax.reload();
                }
            },
            onError:function(error){
                krt.layer.msg(error.message);
            }
        });
    });
</script>
</#macro>
