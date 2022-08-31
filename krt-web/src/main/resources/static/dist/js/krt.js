/**
 *  框架封装公共方法
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年10月29日
 */

//全局方法
$.ajaxSetup({
    complete: function (XMLHttpRequest, textStatus) {
        if (XMLHttpRequest.getResponseHeader('Session-Status') == 'timeout') {
            krt.layer.msg("登陆超时!请重新登陆!", function () {
                top.location.href = krt.util.getBasePath() + '/login';
            });
        } else if (textStatus == "parsererror") {
            krt.layer.msg("JSON解析错误！");
        }
    },
});

//顶部layer
var layer = top.layer;
//加载框
var layer_loading;
//弹出框
var layer_window;
//询问框
var layer_confirm;

//定义krt
window.krt = {

    //封装ajax请求
    ajax: function (param) {
        //提交前操作
        if (krt.util.isUndefind(param.beforeSend)) {
            param.beforeSend = function () {
                //判断是否有表单验证操作
                if (!krt.util.isUndefind(param.validateForm)) {
                    return param.validateForm.form() && krt.layer.loading();
                } else {
                    return krt.layer.loading();
                }
            };
        }
        //成功回调
        var successCallback = param.success;
        param.success = function (result, status, xhr) {
            krt.layer.closeloading();
            successCallback(result, status, xhr);
        };
        //错误回调
        if (!krt.util.isUndefind(param.error)) {
            var errorCallback = param.error;
            param.error = function (result, status, xhr) {
                krt.layer.closeloading();
                errorCallback(result, status, xhr);
            };
        }
        $.ajax(param);
    },

    //tab菜单操作封装
    tab: {
        //打开tab页
        openTab: function (title, url) {
            var thisIframe = getActiveTab();
            var preId = thisIframe.attr("id");
            openTab(url, title, false, preId);
        },
        //获取当前iframe的contentWindow
        getContentWindow: function () {
            var thisIframe = getActiveTab();
            var contentWindow = thisIframe[0].contentWindow;
            return contentWindow;
        },
        //关闭当前tab页
        closeThisTabs: function (preTabCallback) {
            var thisIframe = getActiveTab();
            var preId = thisIframe.data("preid")
            //关闭当前tab
            $(".page-tabs-content").children(".active").find(".fa-close").trigger("click");
            //回调方法
            preId && preTabCallback && preTabCallback($('#iframe' + preId)[0].contentWindow);
            //选中父级tab
            preId && $("#tab" + preId).trigger("click")
        },
        //刷新当前tab页
        refreshIframe: function () {
            var thisIframe = getActiveTab();
            var id = thisIframe.data("id");
            var target = $('.J_iframe[data-id="' + id + '"]');
            var url = target.attr('src');
            //显示loading提示
            krt.layer.loading();
            target.attr('src', url).load(function () {
                //关闭loading提示
                krt.layer.closeloading();
            });
        },
    },

    //layer弹框封装
    layer: {
        //提示消息
        msg: function (tips) {
            layer.msg(tips, function () {
            });
        },
        //提示消息+延时函数
        msg: function (tips, fun) {
            layer.msg(tips, {time: 1000}, fun);
        },
        // 加载
        loading: function () {
            layer_loading = layer.load()
        },
        // 关闭加载
        closeloading: function () {
            layer.close(layer_loading);
        },
        //询问框
        confirm: function (tips, fun) {
            layer_confirm = layer.confirm(tips, {
                btn: ['确定', '取消']
                //按钮
            }, fun, function () {
                layer.close(layer_confirm);
            });
        },
        //弹出信息
        alert: function (param) {
            layer.alert(param);
        },
        //打开窗口
        open: function (param) {
            layer.open(param);
        },
        //关闭窗口
        close: function (index) {
            layer.close(index);
        },
        //获取窗口索引
        getFrameIndex: function () {
            return layer.getFrameIndex(window.name);
        },
        //打开对话框
        openDialog: function (title, url, width, height, isMax) {
            layer_window = layer.open({
                type: 2,
                area: [width, height],
                title: title,
                maxmin: true, //开启最大化最小化按钮
                content: url,
                btn: ['确定', '关闭'],
                yes: function (index, layero) {
                    var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                    iframeWin.contentWindow.doSubmit();
                },
                cancel: function (layer_window) {
                    layer.close(layer_window);
                }
            });
            if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {//如果是移动端，直接最大化
                layer.full(layer_window);
            } else {
                if (isMax) {
                    layer.full(layer_window);
                }
            }
        },
        //打开查看对话框
        openDialogView: function (title, url, width, height, isMax) {
            layer_window = layer.open({
                type: 2,
                area: [width, height],
                title: title,
                maxmin: true,
                content: url,
                btn: ['关闭'],
                cancel: function (layer_window) {
                    layer.close(layer_window);
                }
            });
            if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {//如果是移动端，直接最大化
                layer.full(layer_window);
            } else {
                if (isMax) {
                    layer.full(layer_window);
                }
            }
        }
    },
    //工具方法
    util: {
        //获取basePath
        getBasePath: function () {
            var local = window.location;
            var contextPath = local.pathname.split("/")[1];
            var basePath = local.protocol + "//" + local.host + "/" + contextPath;
            return basePath;
        },
        //判断字符串
        isNull: function (str) {
            if (str == null || str == 'null') {
                return "";
            } else {
                return str;
            }
        },
        //判断undefind
        isUndefind: function (exp) {
            if (typeof (exp) == "undefined") {
                return true;
            } else {
                return false;
            }
        },
        //下划线转驼峰
        underline2Camel: function (str) {
            var re = /-(\w)/g;
            return str.replace(re, function ($0, $1) {
                return $1.toUpperCase();
            });
        },
        //驼峰转下划线
        camel2Underline: function (str) {
            str = str.replace(/([A-Z])/g, "_$1").toLowerCase();
            return str;
        },
        //保存本地缓存设置有效期
        set: function (key, value) {
            var curTime = new Date().getTime();
            localStorage.setItem(key, JSON.stringify({data: value, time: curTime}));
        },
        //获取本地缓存 exp 有效期 -1 永久有效
        get: function (key, exp) {
            var data = localStorage.getItem(key);
            if (data != null) {
                var dataObj = JSON.parse(data);
                if (exp != -1) {
                    if (new Date().getTime() - dataObj.time > exp) {
                        localStorage.removeItem(key);
                        return null;
                    } else {
                        var dataObjDatatoJson = dataObj.data;
                        return dataObjDatatoJson;
                    }
                } else {
                    var dataObjDatatoJson = dataObj.data;
                    return dataObjDatatoJson;
                }
            } else {
                return null;
            }
        },
        //获取字典信息
        getDic: function (type, code) {
            if (type == "" || code == "") {
                return "";
            }
            //有效期60秒
            var key = 'Dic:' + type + ":" + code;
            var dataObjData = krt.util.get(key, 1000 * 60);
            if (dataObjData != "" && dataObjData != null) {
                //返回缓存
                return dataObjData.data;
            } else {
                var val = "";
                $.ajax({
                    url: krt.util.getBasePath() + "/common/getDic",
                    type: "GET",
                    async: false,
                    data: {"type": type, "code": code},
                    success: function (rb) {
                        if (rb.code === 200) {
                            krt.util.set(key, rb);
                            val = rb.data;
                        } else {
                            krt.layer.msg(rb.msg);
                        }
                    },
                    error: function () {
                        krt.layer.msg('查询字典错误!');
                    }
                });
                return val;
            }
        }
    },
    table: {
        //刷新表单
        reloadTable: function reloadTable(ids) {
            var datatable;
            //添加、修改
            if (window.datatable == null || window.datatable == 'undefined') {
                var target = top.$(".J_iframe:visible");
                //判断是否有tab
                if (target[0] == null || target[0] == 'undefined') {
                    datatable = parent.window.datatable;
                    datatable.ajax.reload(false);
                } else {
                    datatable = target[0].contentWindow.datatable;
                    datatable.ajax.reload(false);
                }
            } else {//删除
                datatable = window.datatable;
                var start = datatable.context[0]._iDisplayStart;
                var length = datatable.context[0]._iDisplayLength;
                var total = datatable.context[0]._iRecordsDisplay;
                var flag = false;
                if (ids == null || krt.util.isUndefind(ids)) {
                    if ((total - start) === 1) {
                        if (start > 0) {
                            flag = true;
                        }
                    }
                } else {
                    //批量删除
                    var idsArry = ids.split(",");
                    if (((total - (start + idsArry.length)) <= 0) || idsArry.length === length) {
                        if (start > 0) {
                            flag = true;
                        }
                    }
                }
                if (flag) {
                    datatable.page('previous').draw(false)
                } else {
                    datatable.ajax.reload(null, false);
                }
            }
        }
    }
}



