<#--
 表单页通用布局
 @author 殷帅
 @version 1.0
 @date 2018-06-21 15:50:00
 -->
<#macro header title=''>
<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
    <#include "/common/header.ftl"/>
<!-- icheck -->
<link rel="stylesheet" type="text/css" href="${basePath}/plugin/icheck/minimal/blue.css"/>
<!-- 编辑器 -->
<link rel="stylesheet" type="text/css" href="${basePath}/plugin/kindeditor/themes/default/default.css"/>
<!-- 上传 -->
<link rel="stylesheet" type="text/css" href="${basePath}/plugin/uploader/zui.uploader.min.css"/>
    <#nested/>
</head>
</#macro>
<#-- /header -->

<#macro body class=''>
<body class="${class}">
    <#nested/>
</#macro>
<#-- /body -->

<#macro footer>
    <#include "/common/footer.ftl"/>
<!-- select2.js -->
<script type="text/javascript" src="${basePath}/plugin/select2/select2.min.js"></script>
<script type="text/javascript" src="${basePath}/plugin/select2/i18n/zh-CN.js"></script>
<!-- icheck.js -->
<script type="text/javascript" src="${basePath}/plugin/icheck/icheck.min.js"></script>
<!-- Wdate -->
<script type="text/javascript" src="${basePath}/plugin/My97DatePicker/WdatePicker.js"></script>
<!-- 编辑器 -->
<script type="text/javascript" src="${basePath}/plugin/kindeditor/kindeditor-min.js"></script>
<!-- 上传 -->
<script type="text/javascript" src="${basePath}/plugin/uploader/zui.uploader.min.js" charset="utf-8"></script>
<!-- 表单验证 -->
<script type="text/javascript" src="${basePath}/plugin/jquery-validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${basePath}/plugin/jquery-validate/additional-methods.js"></script>
<script type="text/javascript" src="${basePath}/plugin/jquery-validate/localization/messages_zh.js"></script>
<!-- 框架js -->
<script type="text/javascript" src="${basePath}/dist/js/krt.min.js"></script>
<!-- form.js -->
<script type="text/javascript" src="${basePath}/dist/js/form.min.js"></script>
    <#nested/>
</body>
</html>
</#macro>