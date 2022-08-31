<#assign basePath=request.contextPath />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit"/>
    <link rel="shortcut icon" href="${basePath}/favicon.ico">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- jquery -->
    <script type="text/javascript" src="${basePath}/plugins/jquery/jquery-2.1.4.min.js"></script>
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="${basePath}/plugins/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${basePath}/plugins/font-awesome/css/font-awesome.min.css">
    <!-- krtadmin -->
    <link rel="stylesheet" href="${basePath}/backend/css/krtadmin.css">
    <!--[if lt IE 9]>
    <script language="javascript" type="text/javascript">
        top.location.href = '/html/browser.html';
    </script>
    <![endif]-->
    <link rel="stylesheet" href="${basePath}/plugins/animate/animate.css"/>
    <link rel="stylesheet" href="${basePath}/backend/css/login.css"/>
</head>
<body class="page-login layout-full page-dark">
<div class="page height-full">
    <div class="page-content height-full">
        <div class="page-brand-info vertical-align animated fadeInLeft hidden-xs">
            <div class="page-brand vertical-align-middle">
                <h2 class="hidden-sm">KrtAdmin 通用管理系统快速开发框架</h2>
                <ul>
                    <li>KrtAdmin 是一个基于最新 Web 技术的通用管理系统快速开发框架</li>
                    <li>您可以KrtAdmin为基础，快速开发各种web系统，如CMS、OA、CRM、ERP等</li>
                    <li>前端基于FreeMarker模板引擎+AdminLTE后台管理模板</li>
                    <li>后端基于SpringBoot2+SpringMVC+MybatisPlus+Shiro</li>
                    <li>数据库基于Mysql</li>
                    <li>支持ie9及以上浏览器和现代浏览器访问</li>
                </ul>
            </div>
        </div>
        <div class="page-login-main animation-fade">

            <div class="vertical-align">
                <div class="vertical-align-middle">
                    <h3 class="hidden-xs">
                        统一认证
                    </h3>
                    <p class="hidden-xs">请先登录系统</p>
                    <form action="${basePath}/oauth/authorize" class="login-form" method="post" id="loginForm" novalidate="novalidate">

                        <div class="form-group has-feedback">
                            <input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名">
                            <span class="glyphicon glyphicon-user form-control-feedback"></span>
                        </div>
                        <div class="form-group has-feedback">
                            <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
                            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                        </div>
                        <div class="form-group has-feedback">
                            <label class="sr-only" for="validCode">验证码：</label>
                            <div class="input-group">
                                <input type="text" class="form-control" name="verifyCode" id="verifyCode" placeholder="请输入验证码">
                                <a class="input-group-addon padding-0 reload-vify" href="javascript:;" onclick="changeImg()">
                                    <img id="imgCode" src="${basePath}/captcha.jpg" height="40">
                                </a>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary btn-block margin-top-30" id="loginBtn">立即登录</button>
                        <input type="hidden" name="client_id" value="${(RequestParameters['client_id'])!}"/>
                        <input type="hidden" name="scope" value="${(RequestParameters['scope'])!}"/>
                        <input type="hidden" name="response_type" value="${(RequestParameters['response_type'])!}"/>
                        <input type="hidden" name="redirect_uri" value="${(RequestParameters['redirect_uri'])!}"/>
                        <input type="hidden" name="state" value="${(RequestParameters['state'])!}"/>
                        <input type="hidden" name="client_secret" value="${(RequestParameters['client_secret'])!}"/>
                    </form>
                </div>
            </div>
            <footer class="page-copyright">
                <p>科睿特 ©
                    <a href="http://www.cnkrt.com/" target="_blank">cnkrt.com</a>
                </p>
            </footer>
        </div>
    </div>
</div>
<!-- Bootstrap 3.3.5 -->
<script type="text/javascript" src="${basePath}/plugins/bootstrap/js/bootstrap.min.js"></script>
<!-- pace -->
<script type="text/javascript" src="${basePath}/plugins/pace/pace.min.js"></script>
<!-- layer -->
<script type="text/javascript" src="${basePath}/plugins/layer/layer.js"></script>
<!-- select2 -->
<script type="text/javascript" src="${basePath}/plugins/select2/select2.js"></script>
<script type="text/javascript" src="${basePath}/plugins/select2/i18n/zh-CN.js"></script>
<!-- 通用js -->
<script type="text/javascript" src="${basePath}/backend/js/common.js"></script>
<script>
    if (self.location != top.location) {
        top.location.href = self.location.href;
    }
    $(function () {
        if('${oauth_login_error!}'!=''){
            layer.msg("登录失败");
        }
        if('${verifyCode_error!}'!=''){
            layer.msg("验证码错误");
        }
        $("#loginBtn").click(function () {
            var username = $("#username").val();
            var password = $("#password").val();
            var verifyCode = $("#verifyCode").val();
            if (username == null || username == '') {
                layer.msg("用户账号不可为空");
                return false;
            }
            if (password == null || password == '') {
                layer.msg("密码不可为空");
                return false;
            }
            if (verifyCode == null || verifyCode == '') {
                layer.msg("验证码不可为空");
                return false;
            }
            $("#loginForm").submit();
        });
    });
    //刷新验证码
    function changeImg() {
        var imgSrc = $("#imgCode");
        var src = imgSrc.attr("src");
        imgSrc.attr("src", chgUrl(src));
    }
    function chgUrl(url) {
        var timestamp = (new Date()).valueOf();
        var urls = url.split("?");
        url = urls[0] + "?timestamp=" + timestamp;
        return url;
    }
</script>
</body>
</html>