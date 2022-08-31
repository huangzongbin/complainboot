<#assign basePath=request.contextPath />
<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>KrtAdmin Pro</title>
    <link rel="shortcut icon" href="${basePath}/favicon.ico">
    <link rel="stylesheet" href="${basePath}/plugin/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${basePath}/plugin/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${basePath}/plugin/tabmenu/css/tabmenu.css">
    <link rel="stylesheet" href="${basePath}/dist/css/AdminLTE.min.css">
    <link rel="stylesheet" href="${basePath}/dist/css/krt.min.css">
    <link rel="stylesheet" href="${basePath}/dist/css/skins/skin-all.min.css">
    <!--[if lt IE 9]>
    <script language="javascript" type="text/javascript">
        top.location.href = '${basePath}/html/browser.html';
    </script>
    <![endif]-->
</head>
<body class="hold-transition skin-pro sidebar-mini" style="overflow:hidden">
<div class="wrapper">
    <header class="main-header">
        <!-- Logo -->
        <a href="javascript:void(0)" class="logo">
            <span class="logo-mini"><img src="${basePath}/dist/img/logo.png" class="krt-logo"></span>
            <span class="logo-lg"><img src="${basePath}/dist/img/logo.png" class="krt-logo"><b>KrtAdmin</b>Pro</span>
        </a>
        <nav class="navbar navbar-static-top">
            <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button"></a>
            <a href="#" class="refresh_iframe" id="refreshIframe" role="button"></a>
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="javascript:void(0)" id="deleteCache"><i class="fa fa-trash"></i></a>
                    </li>
                    <li><a href="#" id="fullScreen"><i class="fa fa-expand"></i></a></li>
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <img src="${sessionUser.avatar!'${basePath}/dist/img/user.png'}" onerror="javascript:this.src='${basePath}/dist/img/user.png';" class="user-image" alt="User Image">
                            ${(sessionUser.name)!}
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="javascript:void(0)" id="userInfoBtn"><i class="fa fa-user-circle-o"></i>个人信息</a></li>
                            <li><a href="javascript:void(0)" id="updatePswBtn"><i class="fa fa-edit"></i>修改密码</a></li>
                            <li class="divider"></li>
                            <li><a href="javascript:void(0)" id="logoutBtn"><i class="fa fa-sign-out"></i>退出系统</a></li>
                        </ul>
                    </li>
                    <li><a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a></li>
                </ul>
            </div>
        </nav>
    </header>
    <!-- 左侧菜单 -->
    <aside class="main-sidebar">
        <section class="sidebar">
            <div id="sidebar">
                <!-- 菜单 -->
                <ul id="sidebar-menu" class="sidebar-menu" data-widget="tree">
                    <#if resList?? && (resList?size>0)>
                        <#list resList as res1>
                            <#if res1.child?? && (res1.child?size>0)>
                                <li class="treeview">
                                    <a href="javascript:void(0)"><i class="fa ${res1.icon!}"></i> <span>${res1.name!}</span><i class="fa fa-angle-left pull-right"></i></a>
                                    <ul class="treeview-menu">
                                        <#list res1.child as res2>
                                            <#if res2.child?? && (res2.child?size>0)>
                                                <li class="treeview">
                                                    <a href="javascript:void(0)"><i class="fa ${res2.icon!}"></i> <span>${res2.name!}</span><i class="fa fa-angle-left pull-right"></i></a>
                                                    <ul class="treeview-menu">
                                                        <#list res2.child as res3>
                                                            <#if res3.child?? && (res3.child?size>0)>
                                                                <li class="treeview">
                                                                    <a href="javascript:void(0)"><i class="fa ${res3.icon!}"></i> <span>${res3.name!}</span><i class="fa fa-angle-left pull-right"></i></a>
                                                                    <ul class="treeview-menu">
                                                                        <#list res3.child as res4>
                                                                            <#if res4.target?? && res4.target=='_blank'>
                                                                                <li><a href="${res4.url!}" target="_blank"><i class="fa ${res4.icon!}"></i><span>${res4.name!}</span></a></li>
                                                                            <#else>
                                                                                <li><a href="${res4.url!}" class="J_menuItem" icon="fa ${res4.icon!}"><i class="fa ${res4.icon!}"></i><span>${res4.name!}</span></a></li>
                                                                            </#if>
                                                                        </#list>
                                                                    </ul>
                                                                </li>
                                                            <#else>
                                                                <#if res3.target?? && res3.target=='_blank'>
                                                                    <li><a href="${res3.url!}" target="_blank"><i class="fa ${res3.icon!}"></i><span>${res3.name!}</span></a></li>
                                                                <#else>
                                                                    <li><a href="${res3.url!}" class="J_menuItem" icon="fa ${res3.icon!}"><i class="fa ${res3.icon!}"></i><span>${res3.name!}</span></a></li>
                                                                </#if>
                                                            </#if>
                                                        </#list>
                                                    </ul>
                                                </li>
                                            <#else>
                                                <#if res2.target?? && res2.target=='_blank'>
                                                    <li><a href="${res2.url!}" target="_blank"><i class="fa ${res2.icon!}"></i><span>${res2.name!}</span></a></li>
                                                <#else>
                                                    <li><a href="${res2.url!}" class="J_menuItem" icon="fa ${res2.icon!}"><i class="fa ${res2.icon!}"></i><span>${res2.name!}</span></a></li>
                                                </#if>
                                            </#if>
                                        </#list>
                                    </ul>
                                </li>
                            <#else>
                                <#if res1.target?? && res1.target=='_blank'>
                                    <li><a href="${res1.url!}" target="_blank"><i class="fa ${res1.icon!}"></i><span>${res1.name!}</span></a></li>
                                <#else>
                                    <li><a href="${res1.url!}" class="J_menuItem" icon="fa ${res1.icon!}"><i class="fa ${res1.icon!}"><span></i><span>${res1.name!}</span></a></li>
                                </#if>
                            </#if>
                        </#list>
                    </#if>
                </ul>
            </div>
        </section>
        <!-- /.sidebar -->
    </aside>

    <!-- 内容区 -->
    <div class="content-wrapper">
        <div class="content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-angle-double-left"></i></button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab home" data-id="0"><i class="fa fa-home i_menu"></i>首页</a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-angle-double-right"></i></button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown"><i class="fa fa-angle-down"></i></button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a></li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
                </ul>
            </div>
        </div>
        <div class="J_mainContent" id="content-main" style="width: 100%">
            <iframe class="J_iframe" src="${basePath}/welcome" data-id="0" name="iframe0" width="100%" height="100%" frameborder="0"></iframe>
        </div>
    </div>
    <!-- /.content-wrapper -->

    <footer class="main-footer">
        <iframe width="500" height="40" frameborder="0" scrolling="no" hspace="0" src="https://i.tianqi.com/?c=code&a=getcode&id=40&icon=1"></iframe>
        <#--        <iframe style="width: 220px;height: 18px;" allowtransparency="true" frameborder="0" scrolling="no"  src="//tianqi.eastday.com/plugin/widget_v1.html?sc=3&z=1&t=1&v=0&d=1&bd=0&k=&f=&q=1&a=1&c=54511&w=317&h=28&align=center"></iframe>-->
        <div class="pull-right hidden-xs">
            <strong>Copyright &copy; ${year} <a href="http://www.cnkrt.com/" class="company-info" target="_blank">科睿特</a>.</strong> All rights reserved.版本号: <a>${version}</a>
        </div>

    </footer>

    <!-- Control Sidebar -->
    <aside class="control-sidebar control-sidebar-dark">
        <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
        </ul>
        <div class="tab-content">
            <div class="tab-pane" id="control-sidebar-home-tab"></div>
        </div>
    </aside>
    <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->
<!-- jQuery 2 -->
<script type="text/javascript" src="${basePath}/plugin/jquery/jquery-2.1.4.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script type="text/javascript" src="${basePath}/plugin/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${basePath}/plugin/bootstrap-hover-dropdown/bootstrap-hover-dropdown.js"></script>
<!-- 滚动条 -->
<script type="text/javascript" src="${basePath}/plugin/jquery-slimscroll/jquery.slimscroll2.js"></script>
<!-- layer弹框 -->
<script type="text/javascript" src="${basePath}/plugin/layer/layer.js"></script>
<!-- tab 菜单 -->
<script type="text/javascript" src="${basePath}/plugin/tabmenu/js/tabmenu.min.js"></script>
<!-- 框架js -->
<script type="text/javascript" src="${basePath}/dist/js/adminlte.min.js"></script>
<!-- 封装 -->
<script type="text/javascript" src="${basePath}/dist/js/krt.min.js"></script>
<!-- 首页js -->
<script type="text/javascript" src="${basePath}/dist/js/index.min.js"></script>
<script type="text/javascript">
    $(function () {

        //个人信息
        $("#userInfoBtn").click(function () {
            krt.layer.openDialog("个人信息", "${basePath}/sys/user/updateUserInfo?id=" +${sessionUser.id}, "780px", "600px");
        });

        //修改密码
        $("#updatePswBtn").click(function () {
            krt.layer.openDialog("修改密码", "${basePath}/sys/user/updatePsw", "400px", "450px");
        });

        //清空系统缓存
        $("#deleteCache").click(function () {
            krt.layer.confirm("你确定清空系统缓存吗？", function () {
                $.ajax({
                    type: "POST",
                    url: "${basePath}/cache/deleteAll",
                    success: function (rb) {
                        krt.layer.msg(rb.msg);
                    }
                });
            });
        });

        //退出系统
        $("#logoutBtn").click(function () {
            krt.layer.confirm("你确定退出系统吗？", function () {
                window.location.href = "${basePath}/logout";
            });
        });
    });
</script>
</body>
</html>
