<#assign basePath=request.contextPath />
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title> KrtAdmin Pro</title>
    <link rel="shortcut icon" href="${basePath}/dist/img/favicon.ico">
    <link rel="stylesheet" href="${basePath}/plugin/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${basePath}/plugin/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${basePath}/plugin/Ionicons/css/ionicons.min.css">
    <link rel="stylesheet" href="${basePath}/dist/css/AdminLTE.css">
    <link rel="stylesheet" href="${basePath}/plugin/jigsaw/jigsaw.css">
    <link rel="stylesheet" href="${basePath}/dist/css/krt.css">
    <link rel="stylesheet" href="${basePath}/dist/css/login.css">
    <!--[if lt IE 9]>
    <script language="javascript" type="text/javascript">
        top.location.href='${basePath}/html/browser.html';
    </script>
    <![endif]-->
    <script>
        if (self.location != top.location) {
            top.location.href = self.location.href;
        }
    </script>
</head>

<div class="login-wrapper">
    <div class="login-header">
        <div class="login-tip">
            <img src="${basePath}/dist/img/logo.png" class="krt-login-logo">
            <span class="login-title">KrtAdmin Pro</span>
        </div>
        <div class="login-info">KrtAdmin Pro 通用管理系统快速开发框架</div>
    </div>
    <div class="login-body">
        <div class="box">
            <div class="box-header with-border">
                用户登录
            </div>
            <div class="box-body">
                <form role="form">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="input-group form-group">
                                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                <input type="text" id="username" class="form-control" placeholder="用户名">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="input-group form-group">
                                <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                                <input type="password" class="form-control" id="password" placeholder="密码">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div id="captcha" style="position: relative;margin-bottom: 15px;"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="form-group">
                                <button type="button" id="loginBtn" class="btn btn-primary btn-block">登录</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="login-footer">
        <p>© 2019 <a href="http://www.cnkrt.com/" target="_blank">科睿特版权所有</a></p>
    </div>
</div>
<script src="${basePath}/plugin/jquery/jquery-2.1.4.min.js"></script>
<script src="${basePath}/plugin/layer/layer.js"></script>
<script src="${basePath}/dist/js/krt.js"></script>
<script src="${basePath}/plugin/jigsaw/jigsaw.js"></script>
<script>
    //图片x轴
    var checkCodeResult = false;
    var ticket = "";

    //验证验证码
    function checkCode(x,_this){
        $.ajax({
            url: "${basePath}/checkSliderImage?x="+x+'&t='+new Date().getTime(),
            type: 'get',
            success: function(rb){
                if(rb.code==200) {
                    ticket = rb.data;
                    checkCodeResult = true;
                }else{
                    checkCodeResult = false;
                }
                if(checkCodeResult){
                    _this.success();
                }else{
                    _this.fail();
                }
            }
        })
    }

    var jigsaw1 = jigsaw.init(document.getElementById('captcha'), function (x) {
        //停止滑动验证验证码
        checkCode(x,this);
    }, function(){
        var _this = this;
        $.ajax({
            url: "${basePath}/sliderImage?t="+new Date().getTime(),
            type: 'get',
            success: function(rb){
                if(rb.code === 200){
                    _this.handleImg(rb.data.bigImg, rb.data.smallImg);
                }else{
                    krt.layer.msg("点击刷新重新验证码");
                }
            }
        })
    });

    $(function () {
        //键盘登录
        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                $("#loginBtn").click();
            }
        });
        //登录事件
        $("#loginBtn").click(function () {
//            if(!checkCodeResult){
//                krt.layer.msg("请拖动验证码");
//                return false;
//            }
            var username = $("#username").val();
            var password = $("#password").val();
            if (username == null || username == '') {
                krt.layer.msg("用户账号不可为空");
                return false;
            }
            if (password == null || password == '') {
                krt.layer.msg("密码不可为空");
                return false;
            }
            krt.ajax({
                url: "${basePath}/login",
                type: "POST",
                data: {
                    "username": username,
                    "password": password,
                    "ticket" : ticket
                },
                success: function (rb) {
                    if (rb.code == 200) {
                        location.href = "${basePath}/index";
                    } else {
                        krt.layer.msg(rb.msg);
                        jigsaw1.reset();
                    }
                }
            });
        });
    });

</script>
</body>
</html>
