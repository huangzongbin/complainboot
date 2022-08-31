<#--
 列表页通用布局
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
<link rel="stylesheet" type="text/css" href="${basePath}/plugin/datatables/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/plugin/icheck/minimal/blue.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/plugin/ztree/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="${basePath}/plugin/jquery-treetable/css/jquery.treetable.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/plugin/jquery-treetable/css/jquery.treetable.theme.default.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/plugin/kindeditor/themes/default/default.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/plugin/uploader/zui.uploader.min.css"/>
    <#nested/>
</head>
</#macro>
<#-- /header -->

<#macro body class=''>
<body class="${class}">
    <#nested/>
</#macro>
<!-- /body -->

<#macro footer>
    <#include "/common/footer.ftl"/>
<!-- 数据表格 -->
<script type="text/javascript" src="${basePath}/plugin/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${basePath}/plugin/datatables/dataTables.bootstrap.min.js"></script>
<!-- icheck.js -->
<script type="text/javascript" src="${basePath}/plugin/icheck/icheck.min.js"></script>
<!-- select2.js -->
<script type="text/javascript" src="${basePath}/plugin/select2/select2.min.js"></script>
<script type="text/javascript" src="${basePath}/plugin/select2/i18n/zh-CN.js"></script>
<!-- Wdate -->
<script type="text/javascript" src="${basePath}/plugin/My97DatePicker/WdatePicker.js"></script>
<!-- treeTable -->
<script type="text/javascript" src="${basePath}/plugin/jquery-treetable/javascripts/jquery.treetable.js"></script>
<!-- ztree -->
<script type="text/javascript" src="${basePath}/plugin/ztree/jquery.ztree.all-3.5.js"></script>
<!-- 编辑器 -->
<script type="text/javascript" src="${basePath}/plugin/kindeditor/kindeditor-min.js"></script>
<!-- 上传 -->
<script type="text/javascript" src="${basePath}/plugin/uploader/zui.uploader.min.js" charset="utf-8"></script>
<!-- 框架js -->
<script type="text/javascript" src="${basePath}/dist/js/krt.min.js"></script>
<!-- 列表js -->
<script type="text/javascript" src="${basePath}/dist/js/list.js"></script>
<#nested/>
</body>
</html>
</#macro>