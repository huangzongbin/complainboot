<#include "/common/layoutForm.ftl">
<@header>
    <style>
        .pic{
            max-width: 100px;
            max-height: 100px;
            /*overflow: hidden;
            margin-right: 10px;*/
            padding-left: 10px;
        }

        .uploader .file-list .file-wrapper {
            width: auto;
            position: relative;
            z-index: 2;
            display: table;
            table-layout: fixed;
            -webkit-transition: background .4s;
            -o-transition: background .4s;
            transition: background .4s;
        }
    </style>

<link rel="stylesheet" href="${basePath}/magnify/jquery.magnify.css">
<script src="${basePath}/magnify/jquery.magnify.js"></script>

</@header>
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
                                    <input type="text" name="linkName" id="linkName" value="${complain.linkName}" class="form-control" readonly>
                                </td>
                            <tr>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        投诉人电话
                                    </label>
                                </td>
                                <td class="width-35">
                                    <input type="text" name="linkPhone" id="linkPhone" value="${complain.linkPhone}" class="form-control" readonly>
                                </td>
                            </tr>
                           <#-- <tr>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        电话回复
                                    </label>
                                </td>
                                <td class="width-35">
                                    <input type="radio" name="isPhoneReply" id="isPhoneReply" value="1" checked class="icheck"> 否
                                    <input type="radio" name="isPhoneReply" id="isPhoneReply" value="2" class="icheck"> 是
                                </td>
                            </tr>-->
                            <tr>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        所属小区
                                    </label>
                                </td>
                                <td class="width-35">
                                    <select disabled class="form-control select2" name="community" required style="width: 100%;">
                                        <option value="">==请选择==</option>
                                <@dic type="village_type";typeList>
                                    <#list typeList as type>
                                        <option value="${type.code}"
                                            <#if complain.community?? && complain.community == type.code>
                                                selected
                                            </#if>
                                        >${type.name}</option>
                                    </#list>
                                </@dic>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        投诉举报内容
                                    </label>
                                </td>
                                <td class="width-35">
                                    <textarea cols="50" rows="8" name="cpnContent" id="cpnContent" class="form-control" readonly>${complain.cpnContent}</textarea>
                                </td>
                            <tr>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        证明图片
                                    </label>
                                </td>

                                <td class="width-35">
                                    <#list cpnImgs as cpnImg>
                                        <a data-magnify="gallery" data-caption="${cpnImg}" href="${cpnImg}">
                                            <img class="pic" src="${cpnImg}" title="${cpnImg}"/>
                                        </a>
                                    </#list>
                                </td>
                            </tr>
                            <tr>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        <span style="color: red;">*</span>办理情况
                                    </label>
                                </td>
                                <td class="width-35">
                                    <textarea cols="50" rows="8" name="returnContent"  id="returnContent" class="form-control" required></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        佐证材料
                                    </label>
                                </td>
                                <td class="width-35">
                                    <div id='uploader-file-assistTransactionFile' class="uploader"></div>
                                    <input type="hidden" id="file" name="file">
                                    <script type="text/javascript">
                                        $(function () {
                                            $('#uploader-file-assistTransactionFile').krtUploader({
                                                type: 'lg-list',
                                                url: '${basePath}/upload/fileUpload?dir=all',
                                                autoUpload: true,//自动上传
                                                chunk_size: "0",
                                                limitFilesCount: 5, //限定数量
                                                deleteActionOnDone: function (file, doRemoveFile) {
                                                    doRemoveFile();
                                                },
                                                deleteConfirm: '是否删除文件',
                                                resultAllCallBack: function (data) {
                                                    $("#file").val(data);
                                                },
                                            });
                                        });
                                    </script>
                                </td>
                            </tr>
                            <tr>
                                <td class="active width-15">
                                    <label class="pull-right">
                                        回复人
                                    </label>
                                </td>
                                <td class="width-35">
                                    <input type="text" name="optName" id="optName" value="${currentUser.name!}" class="form-control">
                                </td>
                            </tr>
                        </table>
                            <!-- 参数 -->
                            <input type="hidden" name="id" value="${complain.id}">
                            <input type="hidden" name="status" value="2">
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

        initMagnify();

    });
    //提交
    function doSubmit() {
        krt.ajax({
            type: "POST",
            url: "${basePath}/complain/complain/update",
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

    function initMagnify() {
        $('[data-magnify]').magnify({
            headToolbar: [
                'close'
            ],
            footToolbar: [
                'zoomIn',
                'zoomOut',
                'prev',
                'next',
                'actualSize',
                'rotateRight'
            ],
            title: true
        });
    }
</script>
</@footer>

