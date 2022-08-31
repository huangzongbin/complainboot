<#assign basePath=request.contextPath />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>500</title>
    <link rel="stylesheet" href="${basePath}/plugin/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${basePath}/dist/css/AdminLTE.css">
    <link rel="stylesheet" href="${basePath}/dist/css/krt.css">
    <!--[if lt IE 9]>
    <script src="${basePath}/plugin/html5shiv/html5shiv.min.js"></script>
    <script src="${basePath}/plugin/respond/respond.min.js"></script>
    <![endif]-->
</head>
<body class="body-bg-default">
<!-- 容器 -->
<div class="wrapper">
    <!-- 内容区 -->
    <section class="content">
        <div class="krt-error-page">
            <img class="krt-error-page-img" src="${basePath}/dist/img/ic_500.svg">
            <div class="krt-error-page-info">
                <h1>500</h1>
                <div class="krt-error-page-info-desc">诶呦，程序好像出错了~</div>
            </div>
        </div>
    </section>
</div>
<script src="${basePath}/plugin/jquery/jquery-2.1.4.min.js"></script>
<script src="${basePath}/plugin/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>