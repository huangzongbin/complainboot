/**
 * AdminLTE Demo Menu
 * ------------------
 * You should not use this file in production.
 * This file is for demo purposes only.
 */
$(function () {
    'use strict'

    /**
     * Get access to plugins
     */

    $('[data-toggle="control-sidebar"]').controlSidebar()
    $('[data-toggle="push-menu"]').pushMenu()
    var $pushMenu = $('[data-toggle="push-menu"]').data('lte.pushmenu')
    var $controlSidebar = $('[data-toggle="control-sidebar"]').data('lte.controlsidebar')
    var $layout = $('body').data('lte.layout')
    $(window).on('load', function () {
        // Reinitialize variables on load
        $pushMenu = $('[data-toggle="push-menu"]').data('lte.pushmenu')
        $controlSidebar = $('[data-toggle="control-sidebar"]').data('lte.controlsidebar')
        $layout = $('body').data('lte.layout')
    })

    /**
     * List of all the available skins
     *
     * @type Array
     */
    var mySkins = [
        'skin-pro',
        'skin-gray-pro',
        'skin-white',
        'skin-blue-pro',
        'skin-blue',
        'skin-blue-light',
        'skin-purple-pro',
        'skin-purple',
        'skin-purple-light',
        'skin-green-pro',
        'skin-green',
        'skin-green-light',
        'skin-red-pro',
        'skin-red',
        'skin-red-light'
    ]

    /**
     * Get a prestored setting
     *
     * @param String name Name of of the setting
     * @returns String The value of the setting | null
     */
    function get(name) {
        if (typeof (Storage) !== 'undefined') {
            return localStorage.getItem(name)
        } else {
            window.alert('Please use a modern browser to properly view this template!')
        }
    }

    /**
     * Store a new settings in the browser
     *
     * @param String name Name of the setting
     * @param String val Value of the setting
     * @returns void
     */
    function store(name, val) {
        if (typeof (Storage) !== 'undefined') {
            localStorage.setItem(name, val)
        } else {
            window.alert('Please use a modern browser to properly view this template!')
        }
    }

    /**
     * Toggles layout classes
     *
     * @param String cls the layout class to toggle
     * @returns void
     */
    function changeLayout(cls) {
        $('body').toggleClass(cls)
        $layout.fixSidebar()
        if ($('body').hasClass('fixed') && cls == 'fixed') {
            $pushMenu.expandOnHover()
            $layout.activate()
        }
        $controlSidebar.fix()
    }

    /**
     * Replaces the old skin with the new skin
     * @param String cls the new skin class
     * @returns Boolean false to prevent link's default action
     */
    function changeSkin(cls) {
        $.each(mySkins, function (i) {
            $('body').removeClass(mySkins[i])
        })

        $('body').addClass(cls)
        store('skin', cls)
        return false
    }

    /**
     * Retrieve default settings and apply them to the template
     *
     * @returns void
     */
    function setup() {

        var tmp = get('skin')
        if (tmp && $.inArray(tmp, mySkins))
            changeSkin(tmp)

        // Add the change skin listener
        $('[data-skin]').on('click', function (e) {
            if ($(this).hasClass('knob'))
                return
            e.preventDefault()
            changeSkin($(this).data('skin'))
        })

        // Add the layout manager
        $('[data-layout]').on('click', function () {
            changeLayout($(this).data('layout'))
        })

        $('[data-controlsidebar]').on('click', function () {
            changeLayout($(this).data('controlsidebar'))
            var slide = !$controlSidebar.options.slide

            $controlSidebar.options.slide = slide
            if (!slide)
                $('.control-sidebar').removeClass('control-sidebar-open')
        })

        $('[data-sidebarskin="toggle"]').on('click', function () {
            var $sidebar = $('.control-sidebar')
            if ($sidebar.hasClass('control-sidebar-dark')) {
                $sidebar.removeClass('control-sidebar-dark')
                $sidebar.addClass('control-sidebar-light')
            } else {
                $sidebar.removeClass('control-sidebar-light')
                $sidebar.addClass('control-sidebar-dark')
            }
        })

        $('[data-enable="expandOnHover"]').on('click', function () {
            $(this).attr('disabled', true)
            $pushMenu.expandOnHover()
            if (!$('body').hasClass('sidebar-collapse'))
                $('[data-layout="sidebar-collapse"]').click()
        })

        //  Reset options
        if ($('body').hasClass('fixed')) {
            $('[data-layout="fixed"]').attr('checked', 'checked')
        }
        if ($('body').hasClass('layout-boxed')) {
            $('[data-layout="layout-boxed"]').attr('checked', 'checked')
        }
        if ($('body').hasClass('sidebar-collapse')) {
            $('[data-layout="sidebar-collapse"]').attr('checked', 'checked')
        }

    }

    // Create the new tab
    var $tabPane = $('<div />', {
        'id': 'control-sidebar-theme-demo-options-tab',
        'class': 'tab-pane active'
    })

    // Create the tab button
    var $tabButton = $('<li />', {'class': 'active'})
        .html('<a href=\'#control-sidebar-theme-demo-options-tab\' data-toggle=\'tab\'>'
            + '<i class="fa fa-wrench"></i>'
            + '</a>')

    // Add the tab button to the right sidebar tabs
    $('[href="#control-sidebar-home-tab"]')
        .parent()
        .before($tabButton)

    // Create the menu
    var $demoSettings = $('<div />')

    var $skinsList = $('<ul />', {'class': 'list-unstyled clearfix'})

    //Dark sidebar skins
    var skin_pro =
        $("<li />", {style: "float:left; width: 33.33333%; padding: 8px;"})
            .append("<a href='javascript:void(0);' data-skin='skin-pro' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix full-opacity-hover'>"
                + "<div><span style='display:block; width: 20%; float: left; height: 10px;background:#002140;'></span><span style='display:block; width: 80%; float: left; height: 10px;background:#fefefe;'></span></div>"
                + "<div><span style='display:block; width: 20%; float: left; height: 35px; background: #00142a;'></span><span style='display:block; width: 80%; float: left; height: 35px; background: #f2f2f2;'></span></div>"
                + "</a>"
                + "<p class='text-center no-margin' style='font-size: 12px;'>Pro</p>");
    $skinsList.append(skin_pro);
    var skin_gray_pro =
        $("<li />", {style: "float:left; width: 33.33333%; padding: 8px;"})
            .append("<a href='javascript:void(0);' data-skin='skin-gray-pro' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix full-opacity-hover'>"
                + "<div style='box-shadow: 0 0 2px rgba(0,0,0,0.1)' class='clearfix'><span style='display:block; width: 20%; float: left; height: 10px; background: #434652;'></span><span style='display:block; width: 80%; float: left; height: 10px; background: #ffffff;'></span></div>"
                + "<div><span style='display:block; width: 20%; float: left; height: 35px; background: #3A3D49;'></span><span style='display:block; width: 80%; float: left; height: 35px; background: #f2f2f2;'></span></div>"
                + "</a>"
                + "<p class='text-center no-margin'>浅灰</p>");
    $skinsList.append(skin_gray_pro);

    var skin_white =
        $("<li />", {style: "float:left; width: 33.33333%; padding: 8px;"})
            .append("<a href='javascript:void(0);' data-skin='skin-white' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix full-opacity-hover'>"
                + "<div style='box-shadow: 0 0 2px rgba(0,0,0,0.1)' class='clearfix'><span style='display:block; width: 20%; float: left; height: 10px; background: #ffffff;'></span><span style='display:block; width: 80%; float: left; height: 10px; background: #ffffff;'></span></div>"
                + "<div><span style='display:block; width: 20%; float: left; height: 35px; background: #ffffff;'></span><span style='display:block; width: 80%; float: left; height: 35px; background: #f2f2f2;'></span></div>"
                + "</a>"
                + "<p class='text-center no-margin'>白色</p>");
    $skinsList.append(skin_white);

    //============================蓝色==========================================


    var skin_blue_pro =
        $("<li />", {style: "float:left; width: 33.33333%; padding: 8px;"})
            .append("<a href='javascript:void(0);' data-skin='skin-blue-pro' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix full-opacity-hover'>"
                + "<div><span style='display:block; width: 20%; float: left; height: 10px; background: #1E9FFF;'></span><span  style='display:block; width: 80%; float: left; height: 10px; background: #ffffff'></span></div>"
                + "<div><span style='display:block; width: 20%; float: left; height: 35px; background: #0085E8'></span><span style='display:block; width: 80%; float: left; height: 35px; background: #f2f2f2;'></span></div>"
                + "</a>"
                + "<p class='text-center no-margin'>蓝色Pro</p>");
    $skinsList.append(skin_blue_pro);
    var skin_blue =
        $("<li />", {style: "float:left; width: 33.33333%; padding: 8px;"})
            .append("<a href='javascript:void(0);' data-skin='skin-blue' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix full-opacity-hover'>"
                + "<div><span style='display:block; width: 20%; float: left; height: 10px; background: #0085E8;'></span><span  style='display:block; width: 80%; float: left; height: 10px; background: #1E9FFF'></span></div>"
                + "<div><span style='display:block; width: 20%; float: left; height: 35px; background: #344058;'></span><span style='display:block; width: 80%; float: left; height: 35px; background: #f2f2f2;'></span></div>"
                + "</a>"
                + "<p class='text-center no-margin'>蓝色</p>");
    $skinsList.append(skin_blue);
    var skin_blue_light =
        $("<li />", {style: "float:left; width: 33.33333%; padding: 8px;"})
            .append("<a href='javascript:void(0);' data-skin='skin-blue-light' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix full-opacity-hover'>"
                + "<div><span style='display:block; width: 20%; float: left; height: 10px; background: #0085E8;'></span><span  style='display:block; width: 80%; float: left; height: 10px; background: #1E9FFF'></span></div>"
                + "<div><span style='display:block; width: 20%; float: left; height: 35px; background: #ffffff;'></span><span style='display:block; width: 80%; float: left; height: 35px; background: #f2f2f2;'></span></div>"
                + "</a>"
                + "<p class='text-center no-margin'>浅蓝</p>");
    $skinsList.append(skin_blue_light);

    //============================紫色==========================================
    var skin_purple_pro =
        $("<li />", {style: "float:left; width: 33.33333%; padding: 8px;"})
            .append("<a href='javascript:void(0);' data-skin='skin-purple-pro' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix full-opacity-hover'>"
                + "<div><span style='display:block; width: 20%; float: left; height: 10px; background: #6c68b9;'></span><span style='display:block; width: 80%; float: left; height: 10px;background: #fff'></span></div>"
                + "<div><span style='display:block; width: 20%; float: left; height: 35px; background: #605ca8;'></span><span style='display:block; width: 80%; float: left; height: 35px; background: #f2f2f2;'></span></div>"
                + "</a>"
                + "<p class='text-center no-margin'>紫色Pro</p>");
    $skinsList.append(skin_purple_pro);
    var skin_purple =
        $("<li />", {style: "float:left; width: 33.33333%; padding: 8px;"})
            .append("<a href='javascript:void(0);' data-skin='skin-purple' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix full-opacity-hover'>"
                + "<div><span style='display:block; width: 20%; float: left; height: 10px; background: #555299;'></span><span style='display:block; width: 80%; float: left; height: 10px;background: #605ca8'></span></div>"
                + "<div><span style='display:block; width: 20%; float: left; height: 35px; background: #344058;'></span><span style='display:block; width: 80%; float: left; height: 35px; background: #f2f2f2;'></span></div>"
                + "</a>"
                + "<p class='text-center no-margin'>紫色</p>");
    $skinsList.append(skin_purple);
    var skin_purple_light =
        $("<li />", {style: "float:left; width: 33.33333%; padding: 8px;"})
            .append("<a href='javascript:void(0);' data-skin='skin-purple-light' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix full-opacity-hover'>"
                + "<div><span style='display:block; width: 20%; float: left; height: 10px; background: #555299;'></span><span style='display:block; width: 80%; float: left; height: 10px;background: #605ca8'></span></div>"
                + "<div><span style='display:block; width: 20%; float: left; height: 35px; background: #ffffff;'></span><span style='display:block; width: 80%; float: left; height: 35px; background: #f2f2f2;'></span></div>"
                + "</a>"
                + "<p class='text-center no-margin'>浅紫</p>");
    $skinsList.append(skin_purple_light);
    //============================绿色==========================================
    var skin_green_pro =
        $("<li />", {style: "float:left; width: 33.33333%; padding: 8px;"})
            .append("<a href='javascript:void(0);' data-skin='skin-green-pro' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix full-opacity-hover'>"
                + "<div><span style='display:block; width: 20%; float: left; height: 10px; background: #26b373;'></span><span style='display:block; width: 80%; float: left; height: 10px;background: #fff'></span></div>"
                + "<div><span style='display:block; width: 20%; float: left; height: 35px; background: #008d4c;'></span><span style='display:block; width: 80%; float: left; height: 35px; background: #f2f2f2;'></span></div>"
                + "</a>"
                + "<p class='text-center no-margin'>绿色Pro</p>");
    $skinsList.append(skin_green_pro);
    var skin_green =
        $("<li />", {style: "float:left; width: 33.33333%; padding: 8px;"})
            .append("<a href='javascript:void(0);' data-skin='skin-green' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix full-opacity-hover'>"
                + "<div><span style='display:block; width: 20%; float: left; height: 10px; background: #008d4c;'></span><span style='display:block; width: 80%; float: left; height: 10px;background: #26b373'></span></div>"
                + "<div><span style='display:block; width: 20%; float: left; height: 35px; background: #344058;'></span><span style='display:block; width: 80%; float: left; height: 35px; background: #f2f2f2;'></span></div>"
                + "</a>"
                + "<p class='text-center no-margin'>绿色</p>");
    $skinsList.append(skin_green);
    var skin_green_light =
        $("<li />", {style: "float:left; width: 33.33333%; padding: 8px;"})
            .append("<a href='javascript:void(0);' data-skin='skin-green-light' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix full-opacity-hover'>"
                + "<div><span style='display:block; width: 20%; float: left; height: 10px; background: #008d4c;'></span><span style='display:block; width: 80%; float: left; height: 10px;background: #26b373'></span></div>"
                + "<div><span style='display:block; width: 20%; float: left; height: 35px; background: #ffffff;'></span><span style='display:block; width: 80%; float: left; height: 35px; background: #f2f2f2;'></span></div>"
                + "</a>"
                + "<p class='text-center no-margin'>浅绿</p>");
    $skinsList.append(skin_green_light);
    //============================绿色==========================================
    var skin_red_pro =
        $("<li />", {style: "float:left; width: 33.33333%; padding: 8px;"})
            .append("<a href='javascript:void(0);' data-skin='skin-red-pro' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix full-opacity-hover'>"
                + "<div><span style='display:block; width: 20%; float: left; height: 10px; background: #dd4b39;'></span><span style='display:block; width: 80%; float: left; height: 10px;background: #fff'></span></div>"
                + "<div><span style='display:block; width: 20%; float: left; height: 35px; background: #d73925;'></span><span style='display:block; width: 80%; float: left; height: 35px; background: #f2f2f2;'></span></div>"
                + "</a>"
                + "<p class='text-center no-margin'>红色Pro</p>");
    $skinsList.append(skin_red_pro);
    var skin_red =
        $("<li />", {style: "float:left; width: 33.33333%; padding: 8px;"})
            .append("<a href='javascript:void(0);' data-skin='skin-red' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix full-opacity-hover'>"
                + "<div><span style='display:block; width: 20%; float: left; height: 10px; background: #d73925;'></span><span style='display:block; width: 80%; float: left; height: 10px;background: #dd4b39'></span></div>"
                + "<div><span style='display:block; width: 20%; float: left; height: 35px; background: #344058;'></span><span style='display:block; width: 80%; float: left; height: 35px; background: #f2f2f2;'></span></div>"
                + "</a>"
                + "<p class='text-center no-margin'>红色</p>");
    $skinsList.append(skin_red);
    var skin_red_light =
        $("<li />", {style: "float:left; width: 33.33333%; padding: 8px;"})
            .append("<a href='javascript:void(0);' data-skin='skin-red-light' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix full-opacity-hover'>"
                + "<div><span style='display:block; width: 20%; float: left; height: 10px; background: #d73925;'></span><span style='display:block; width: 80%; float: left; height: 10px;background: #dd4b39'></span></div>"
                + "<div><span style='display:block; width: 20%; float: left; height: 35px; background: #ffffff;'></span><span style='display:block; width: 80%; float: left; height: 35px; background: #f2f2f2;'></span></div>"
                + "</a>"
                + "<p class='text-center no-margin'>浅红</p>");
    $skinsList.append(skin_red_light);


    $demoSettings.append("<h4 class='control-sidebar-heading'>设置主题</h4>");
    $demoSettings.append($skinsList);

    $tabPane.append($demoSettings);
    $("#control-sidebar-home-tab").after($tabPane);

    setup()

    $('[data-toggle="tooltip"]').tooltip()


    //侧边栏添加滚动条
    function slimScroll(sidebarHeight) {
        console.log(sidebarHeight);
        $("#sidebar").slimScroll({
            width: '',
            height: sidebarHeight + 'px', //可滚动区域高度
            size: '0px', //滚动条宽度，即组件宽度
            position: 'right', //组件位置：left/right
            distance: '0px', //组件与侧边之间的距离
            start: 'top', //默认滚动位置：top/bottom
            opacity: .4, //滚动条透明度
            railOpacity: .2, //轨道透明度
            railDraggable: true, //是否 滚动条可拖动
            allowPageScroll: true, //是否 使用滚轮到达顶端/底端时，滚动窗口
        });
    }

    function layout() {
        var headerHeight = $(".main-header").outerHeight() || 0;
        var footerHeight = $(".main-footer").outerHeight() || 0;
        $("#content-main").css("height", $(window).height() - (headerHeight + footerHeight + 40));
        slimScroll($(window).height() - headerHeight);
    }

    layout();
    //窗口重置
    $(window).resize(function () {
        layout();
    });

    //折叠菜单
    $("#sidebar-menu > li").hover(function () {
        if ($("body").hasClass("sidebar-collapse")) {
            var top = $(this).position().top + 50;
            var ul = $(this).children("ul");
            ul.css("max-height", "");
            //浏览器高度
            var wheight = $(window).height();
            var uHeight = ul.height() + 5;
            if (top > wheight / 2) {
                //底部靠齐
                uHeight = uHeight + 10;
                ul.css("top", top - uHeight + 60).css("max-height", uHeight + "px");
            } else {
                if (top < 50) {
                    top = 51;
                }
                var maxHeight = wheight - top;
                ul.css("top", top).css("max-height", maxHeight + "px");
            }
        }
    }, function () {
    });


    //全屏
    $('#fullScreen').on('click', function () {
        var ac = 'fa-expand', ic = 'fa-compress';
        var i = $(this).find("i");
        var isFullscreen = document.fullscreenElement || document.msFullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement || false;
        if (isFullscreen) {
            var efs = document.exitFullscreen || document.webkitExitFullscreen || document.mozCancelFullScreen || document.msExitFullscreen;
            if (efs) {
                efs.call(document);
            } else if (window.ActiveXObject) {
                var ws = new ActiveXObject('WScript.Shell');
                ws && ws.SendKeys('{F11}');
            }
            i.removeClass(ic).addClass(ac);

        } else {
            var el = document.documentElement;
            var rfs = el.requestFullscreen || el.webkitRequestFullscreen || el.mozRequestFullScreen || el.msRequestFullscreen;
            if (rfs) {
                rfs.call(el);
            } else if (window.ActiveXObject) {
                var ws = new ActiveXObject('WScript.Shell');
                ws && ws.SendKeys('{F11}');
            }
            i.removeClass(ac).addClass(ic);

        }
    });

    //刷新当前页
    $("#refreshIframe").click(function () {
        krt.tab.refreshIframe();
    });
})
