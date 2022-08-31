<#--
 默认通用布局
 @author 殷帅
 @version 1.0
 @date 2018-06-21 15:50:00
 -->
<#import  "/tags/krt.ftl" as krt/>
<#assign basePath=request.contextPath />
<#macro header title=''>
<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/krt/favicon.ico">
    <!-- jquery -->
    <script type="text/javascript" src="${basePath}/plugin/jquery/jquery-2.1.4.min.js"></script>
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" type="text/css" href="${basePath}/plugin/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" type="text/css" href="${basePath}/plugin/font-awesome/css/font-awesome.min.css">
    <!-- select2 -->
    <link rel="stylesheet" type="text/css" href="${basePath}/plugin/select2/select2.css"/>
    <!-- Theme style -->
    <link rel="stylesheet" type="text/css" href="${basePath}/dist/css/AdminLTE.min.css">
    <!-- krt.css -->
    <link rel="stylesheet" type="text/css" href="${basePath}/dist/css/krt.css">
    <#nested>
    <!--[if lt IE 9]>
    <script language="javascript" type="text/javascript">
        top.location.href='${basePath}/html/browser.html';
    </script>
    <![endif]-->
</head>
</#macro>
<#-- /header -->
<#macro body>
<body>
<div class="wrapper">
    <#nested>
</div>
</#macro>
<#-- /body -->
<#macro footer>
    <#include "/common/footer.ftl">
    <#nested>
    <script type="text/javascript" src="${basePath}/dist/js/krt.min.js"></script>
</body>
</html>
</#macro>