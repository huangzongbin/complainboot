//设置dpr与viewport
(function (win) {
    var doc = document, docEl = doc.documentElement, appVer = win.navigator.appVersion;
    var metaEl = doc.querySelector('meta[name="viewport"]');
    //var flexibleEl = doc.querySelector('meta[name="flexible"]');
    var dpr = 1, scale, tid;

    //var isAndroid = win.navigator.appVersion.match(/android/gi);
    var isIPhone = appVer.match(/iphone/gi);
    var devicePixelRatio = win.devicePixelRatio;
    if (isIPhone) {
        // iOS下，对于2和3的屏，用2倍的方案，其余的用1倍方案
        if (devicePixelRatio >= 3 && (!dpr || dpr >= 3)) {
            dpr = 3;
        } else if (devicePixelRatio >= 2 && (!dpr || dpr >= 2)) {
            dpr = 2;
        } else {
            dpr = 1;
        }
    } else {
        // 其他设备下，仍旧使用1倍的方案
        dpr = 1;
    }
    scale = 1 / dpr;

    docEl.setAttribute('data-dpr', "" + dpr);
    if (metaEl) {
        metaEl.parentNode.removeChild(metaEl);
    }
    metaEl = doc.createElement('meta');
    metaEl.setAttribute('name', 'viewport');
    metaEl.setAttribute('content', 'initial-scale=' + scale + ', maximum-scale=' + scale + ', minimum-scale=' + scale + ', user-scalable=no');
    if (docEl.firstElementChild) {
        docEl.firstElementChild.appendChild(metaEl);
    } else {
        var wrap = doc.createElement('div');
        wrap.appendChild(metaEl);
        doc.write(wrap.innerHTML);
    }
    function refreshRem() {
        var width = docEl.getBoundingClientRect().width;
        if (width / dpr > 540) {
            width = 540 * dpr;
        }
        var rem = width / 10;
        docEl.style.fontSize = rem + 'px';
        win.rem = rem;
        // flexible.rem = win.rem = rem;
    }

    win.addEventListener('resize', function () {
        clearTimeout(tid);
        tid = setTimeout(refreshRem, 300);
    }, false);
    win.addEventListener('pageshow', function (e) {
        if (e.persisted) {
            clearTimeout(tid);
            tid = setTimeout(refreshRem, 300);
        }
    }, false);

    if (doc.readyState === 'complete') {
        doc.body.style.fontSize = 12 * dpr + 'px';
    } else {
        doc.addEventListener('DOMContentLoaded', function (e) {
            doc.body.style.fontSize = 12 * dpr + 'px';
        }, false);
    }
    refreshRem();
})(window);

//基本配置参数
var baseOption = {
    jsFileName: "js/base.js",            //本文件文件名：包含父文件夹
    lcpBackFlag: "",                     //路由控制参数，如果包含源生静态页则输入项目中文缩写，否则为空
    isDownCreate: true                   //页面元素load后是否直接启用ios版按触效果
};
function getLcpBackPre() {
}
var lcp_backPre = "var _getV = getLcpBackPre();if(_getV===false){return false;}if(document.getElementById('lcp_alert')){var str = document.getElementById('lcp_mttc').getAttribute('onclick');eval(str);return false;}";
//baseTools中内置了getGPS(获取地理经纬度)，
//getDistance(获取2个地理坐标之间的直线距离)，
//imgRunLoading(滚动加载图片),
//imgSetLoading(滚动加载图片2),
//lcp_downCreate(仿ios的按触效果),
//X_run(可左右拉动式切换菜单),
//listLoadingMore(滚动加载列表),
//lcp_alert(警示弹出框),
var baseTools = (function () {
    var baseOption = "baseOption" in window ? window.baseOption : {};
    var baseName = "lcp_", jsName = "jsFileName" in baseOption ? baseOption.jsFileName : "js/base.js";
    var doc = document, head, win = window, win2 = win.parent.window, win3 = win.top.window, loc = win.localStorage;
    var i, j;
    var isTwoWindow = (function () {
        return !(win === win2)
    })();
    var getId = function (_id) {
        return doc.getElementById(_id)
    };
    var getTag = function (_tag, _dom) {
        _dom = _dom ? _dom : doc;
        return _dom.getElementsByTagName(_tag)
    };
    var getAttr = function (_dom, _name) {
        return _dom.getAttribute(_name)
    };
    head = "head" in doc ? doc.head : getTag("head", doc)[0];
    var getStyle = function (x) {
        if (doc.documentElement.currentStyle) {
            getStyle = myTools.getStyle = function (x) {
                return x.currentStyle
            }
        } else {
            if (doc.defaultView.getComputedStyle) {
                getStyle = myTools.getStyle = function (x) {
                    return doc.defaultView.getComputedStyle(x, null)
                }
            }
        }
        return getStyle(x)
    };
    var createDom = function (tag) {
        return doc.createElement(tag)
    };
    var in_array = function (_arr, _e) {
        for (i = _arr.length; i--;) {
            if (_arr[i] == _e) {
                return i
            }
        }
        return -1
    };
    var extend = function () {
        var a = arguments, b = a.length, c = a[0], d, e, f;
        if (b == 0) {
            return {}
        } else {
            if (b == 1) {
                return c
            } else {
                e = a[b - 1];
                f = typeof e == "boolean";
                if (f) {
                    b--
                }
                for (i = 1; i < b; i++) {
                    d = a[i];
                    for (j in d) {
                        if (j in c && e === false) {
                        } else {
                            if (d.hasOwnProperty(j)) {
                                c[j] = d[j]
                            }
                        }
                    }
                }
                return c
            }
        }
    };
    var getClassListDom = function (dom) {
        this.dom = dom;
        var dd, list = [], arr = dom.className.split(" "), len = arr.length, j = 0;
        for (i = 0; i < len; i++) {
            dd = arr[i];
            if (dd != "") {
                list.push(dd);
                this[j] = dd;
                j++
            }
        }
        this.list = list;
        this.length = j;
        this.item = function (index) {
            return this[index]
        };
        this.contains = function (c) {
            return in_array(this.list, c) > -1
        };
        this.add = function (c) {
            if (c.search(" ") > -1) {
                return false
            }
            if (this.contains(c)) {
                return false
            }
            this[this.length] = c;
            this.list.push(c);
            this.length++;
            this.dom.className = this.list.join(" ")
        };
        this.remove = function (c) {
            if (c.search(" ") > -1) {
                return false
            }
            var index = in_array(this.list, c);
            if (index > -1) {
                for (i = index; i < this.length - 1; i++) {
                    this[i] = this[i + 1]
                }
                delete this[i];
                this.list.splice(index, 1);
                this.length--;
                this.dom.className = this.list.join(" ")
            }
        };
        this.toggle = function (c) {
            if (this.contains(c)) {
                this.remove(c)
            } else {
                this.add(c)
            }
        }
    };
    var getClassList = function (dom) {
        if ("classList" in dom) {
            getClassList = myTools.getClassList = function (dom) {
                return dom.classList
            }
        } else {
            getClassList = myTools.getClassList = function (dom) {
                return new getClassListDom(dom)
            }
        }
        return getClassList(dom)
    };
    var getClass = function (c, dom) {
        if ("getElementsByClassName" in doc) {
            getClass = myTools.getClass = function (c, dom) {
                var p = dom ? dom : doc;
                return p.getElementsByClassName(c)
            }
        } else {
            if ("jQuery" in window) {
                getClass = myTools.getClass = function (c, dom) {
                    var p = dom ? dom : doc;
                    return jQuery("." + c, p).get()
                }
            } else {
                getClass = myTools.getClass = function (c, dom) {
                    var p = dom ? dom : doc, x = p.getElementsByTagName("*"), y = [], z;
                    for (i = 0; i < x.length; i++) {
                        z = getClassList(x.item(i));
                        if (z.contains(c)) {
                            y.push(x.item(i))
                        }
                    }
                    return y
                }
            }
        }
        return getClass(c, dom)
    };
    var createStyle = function (id, cssStr) {
        var _css = getId(id);
        if (_css) {
            _css.innerHTML = cssStr
        } else {
            _css = createDom("style");
            _css.setAttribute("type", "text/css");
            _css.setAttribute("id", id);
            _css.innerHTML = cssStr;
            head.appendChild(_css)
        }
    };
    var createScript = function (id, jsStr) {
        var _js = getId(id);
        if (_js) {
            _js.innerHTML = jsStr
        } else {
            _js = createDom("script");
            _js.id = id;
            _js.type = "text/javascript";
            _js.innerHTML = jsStr;
            head.appendChild(_js)
        }
    };
    var getLoc = function (name) {
        return loc.getItem(name)
    };
    var setLoc = function (name, value) {
        loc.setItem(name, value)
    };
    var domLoad = function (fun) {
        var func = function () {
            fun();
            document.removeEventListener("DOMContentLoaded", func, false)
        };
        document.addEventListener("DOMContentLoaded", func, false)
    };
    var createXML = function () {
        if (window.XMLHttpRequest) {
            createXML = function () {
                return new XMLHttpRequest()
            }
        } else {
            if (window.ActiveXObject) {
                try {
                    createXML = function () {
                        return new ActiveXObject("Msxml2.XMLHTTP")
                    }
                } catch (e) {
                    try {
                        createXML = function () {
                            return new ActiveXObject("Microsoft.XMLHTTP")
                        }
                    } catch (e) {
                        createXML = function () {
                            return null
                        }
                    }
                }
            } else {
                createXML = function () {
                    return null
                }
            }
        }
        return createXML()
    };
    var xmlFomat = function (xml) {
        if (xml.responseXML) {
            return xml.responseXML
        }
        var text = xml.responseText, val;
        try {
            if ("JSON" in window && "parse" in JSON) {
                val = JSON.parse(text)
            } else {
                val = eval("(" + text + ")")
            }
        } catch (e) {
            val = text
        }
        return val
    };
    var ajaxContentType = "application/x-www-form-urlencoded; charset=UTF-8";
    var easyAjax = function (url, success, error) {
        var xml = createXML();
        xml.open("GET", url, true);
        xml.setRequestHeader("Content-type", ajaxContentType);
        xml.onreadystatechange = function () {
            if (xml.readyState == 4) {
                var s = parseFloat(xml.status);
                if (xml.status == 200 || xml.status == 0) {
                    if (success) {
                        success(xmlFomat(xml))
                    }
                } else {
                    if (s > 400) {
                        if (error) {
                            error(xml, xml.status, xml.statusText)
                        }
                    }
                }
            }
        };
        xml.send(null);
        return xml
    };
    var ajax = (function () {
        var ajax = function (opts) {
            opts = opts instanceof Object ? opts : {};
            return ajax.fn.init(opts)
        };
        var option = {
            type: "get", url: null, data: "", async: true, dataType: "json", beforeSend: function () {
            }, success: function () {
            }, complete: function () {
            }, error: function () {
            }
        };
        var Func_jsonp_index = 0, Func_jsonp_dom;
        var Func_jsonp_name1 = baseName + "ajax_jsonp_callback", Func_jsonp_name2 = Func_jsonp_name1 + "2";
        var Func_jsonp = function () {
            win[Func_jsonp_name1] = function (b) {
                win[Func_jsonp_name2](b)
            };
            win[Func_jsonp_name2] = function (b) {
                Func_jsonp_dom.removeChild(getId(Func_jsonp_name1 + "_" + (Func_jsonp_index - 1)));
                ajax.fn.success(b)
            };
            var s = createDom("script"), str = "callback=" + Func_jsonp_name1, url = option.url,
                u = url.search(/\?/g) == -1 ? (url + "?" + str) : (url + "&" + str);
            s.async = true;
            s.id = Func_jsonp_name1 + "_" + Func_jsonp_index++;
            s.type = "text/javascript";
            s.src = u;
            Func_jsonp_dom = head ? head : doc.body;
            Func_jsonp_dom.appendChild(s)
        };
        ajax.fn = {
            init: function (opts) {
                option = extend(option, opts);
                if (option.dataType == "jsonp") {
                    Func_jsonp();
                    return false
                }
                var xml = createXML();
                xml.open(option.type, option.url, option.async);
                ajax.fn.setHead(xml);
                xml.onreadystatechange = function () {
                    if (xml.readyState == 4) {
                        var s = parseFloat(xml.status);
                        if (xml.status == 200 || xml.status == 0) {
                            ajax.fn.success(xmlFomat(xml))
                        } else {
                            if (s > 400) {
                                ajax.fn.error(xml, xml.status, xml.statusText)
                            }
                        }
                        ajax.fn.complete()
                    }
                };
                option.beforeSend(xml);
                xml.send(null);
                return xml
            }, setHead: function (xml) {
                if (option.async === true) {
                }
                if ("contentType" in option) {
                    xml.setRequestHeader("Content-type", option.contentType);
                    return false
                }
                if (String(option.type).search(/^post$/i) == 0) {
                    xml.setRequestHeader("Content-type", ajaxContentType)
                }
            }, success: function (data) {
                if ("success" in option) {
                    option.success(data)
                }
            }, error: function (xml, status, statusText) {
                if ("error" in option) {
                    option.error(xml, status, statusText)
                }
            }, complete: function () {
                if ("complete" in option) {
                    option.complete()
                }
            }
        };
        return ajax
    })();
    var GPSKey = "nfPeVUXFF4FUsex4gVfrIG5b";
    var getGPS = (function () {
        var GPS_id;
        var successFunc;
        var failFunc;
        var geoLocation = navigator.geolocation;

        function lcp_getGPS() {
            if (geoLocation) {
                GPS_id = geoLocation.watchPosition(showPosition, showError, {
                    enableHighAccuracy: true,
                    maximumAge: 2000
                })
            } else {
                failFunc("getCurrentPosition is not used!")
            }
        }

        function showPosition(position) {
            var xx = position.coords.latitude;
            var yy = position.coords.longitude;
            if (xx != "0") {
                geoLocation.clearWatch(GPS_id);
                pointChange(yy, xx, position.timestamp)
            }
        }

        var pointChange_num = 0;

        function pointChange(lng, lat, mm) {
            if (pointChange_num >= 3) {
                pointChange_num = 0;
                failFunc("NETWORK CONNECTION ERROR");
                return false
            }
            pointChange_num++;
            var url = "http://api.map.baidu.com/geoconv/v1/?coords=" + lng + "," + lat + "&output=json&ak=" + GPSKey;
            var ajaxObject = {
                type: "post",
                url: url,
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                dataType: "jsonp",
                success: function (data) {
                    if (data.status == 0) {
                        var p = data.result[0];
                        if (successFunc) {
                            successFunc(p.x, p.y)
                        }
                        pointChange_num = 0
                    } else {
                        pointChange(lng, lat, mm)
                    }
                },
                error: function () {
                    pointChange(lng, lat, mm)
                }
            };
            if ("jQuery" in win) {
                $.ajax(ajaxObject)
            } else {
                ajax(ajaxObject)
            }
        }

        var pointError_num = 0;

        function showError(error) {
            pointError_num++;
            if (pointError_num > 3) {
                geoLocation.clearWatch(GPS_id)
            }
            if (failFunc) {
                switch (error.code) {
                    case error.PERMISSION_DENIED:
                        failFunc("PERMISSION_DENIED");
                        break;
                    case error.TIMEOUT:
                        failFunc("TIMEOUT");
                        break;
                    default:
                        break
                }
            }
        }

        return function (func, func2) {
            if (func) {
                successFunc = func
            }
            if (func2) {
                failFunc = func2
            }
            lcp_getGPS()
        }
    })();
    var getDistance = (function () {
        var earthR = 6378.137;

        function lcp_Rad(d) {
            return d * Math.PI / 180
        }

        return function (lat1, lng1, lat2, lng2) {
            var radLat1 = lcp_Rad(lat1);
            var radLat2 = lcp_Rad(lat2);
            var a = radLat1 - radLat2;
            var b = lcp_Rad(lng1) - lcp_Rad(lng2);
            var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
            s = s * earthR;
            s = Math.round(s * 10000) / 10000;
            s = s * 1000;
            s = s.toFixed(1);
            return s
        }
    })();
    var myTools = {};
    myTools.win2 = win2;
    myTools.getId = getId;
    myTools.getTag = getTag;
    myTools.createDom = createDom;
    myTools.in_array = in_array;
    myTools.extend = extend;
    myTools.getClassList = getClassList;
    myTools.getClass = getClass;
    myTools.createStyle = createStyle;
    myTools.createScript = createScript;
    myTools.getLoc = getLoc;
    myTools.setLoc = setLoc;
    myTools.domLoad = domLoad;
    myTools.easyAjax = easyAjax;
    myTools.ajax = ajax;
    myTools.getGPS = getGPS;
    myTools.getDistance = getDistance;
    myTools.isMobile = (function () {
        var u = window.navigator.userAgent.toLowerCase();
        return !!u.match(/(Android|iPad|iPod|iPhone|ios)/i)
    })();
    myTools.isTwoWindow = isTwoWindow;
    myTools.getH = function () {
        return doc.documentElement.clientHeight
    };
    myTools.w = doc.documentElement.clientWidth;
    myTools.h = myTools.getH();
    myTools.baseHref = (function () {
        var script = getTag("script"), len = script.length;
        for (i = len; i--;) {
            var str = script[i].src, s = str.search(jsName);
            if (s > -1) {
                return str.substring(0, s)
            }
        }
        return ""
    })();
    myTools.getStyle = getStyle;
    myTools.hrefOption = (function () {
        var arr = win.location.href.split("?");
        if (arr.length == 1 || arr[1] == "") {
            return {}
        }
        var arr2 = arr[1].split("&");
        var option = {}, arr3;
        for (var i = 0; i < arr2.length; i++) {
            arr3 = arr2[i].split("=");
            option[arr3[0]] = decodeURI(arr3[1])
        }
        return option
    })();
    myTools.openMttc = function (a) {
        if (isTwoWindow && ("openMttc" in win2)) {
            win2.openMttc(a)
        }
    };
    myTools.closeMttc = function () {
        if (isTwoWindow && ("closeMttc" in win2)) {
            win2.closeMttc()
        }
    };
    win.MyJS = "MyJS" in win3 ? win3.MyJS : "MyJS" in win ? win.MyJS : undefined;
    win.BaseJS = "BaseJS" in win3 ? win3.BaseJS : "BaseJS" in win ? win.BaseJS : undefined;
    win.img_error = function (x, src) {
        x.src = src;
        x.removeAttribute("onerror")
    };
    win.img_error2 = function (x) {
        x.parentNode.removeChild(x)
    };
    win.lcp_stop = function (event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        e.stopPropagation();
        e.preventDefault()
    };
    win.lcp_inArr = in_array;
    win.lcp_addClass = function (dom, c) {
        getClassList(dom).add(c)
    };
    win.lcp_removeClass = function (dom, c) {
        getClassList(dom).remove(c)
    };
    win.lcp_toggleClass = function (dom, c) {
        getClassList(dom).toggle(c)
    };
    win.imgRunLoading = (function () {
        return function (id, isConstant) {
            var wrap = getId(id), isFull = false;
            var loading = function () {
                if (isFull) {
                    return
                }
                var h = wrap.offsetHeight, t = wrap.getBoundingClientRect().top, t2 = h + t;
                var img = getTag("img", wrap), len = img.length;
                var nullNum = 0, x, url, isNull, rect, tt, ll, hh;
                for (var _i = len; _i--;) {
                    x = img.item(_i);
                    url = x.getAttribute("data-url");
                    isNull = url === null;
                    if (isNull) {
                        nullNum++;
                        continue
                    }
                    rect = x.getBoundingClientRect();
                    tt = rect.top;
                    ll = rect.left;
                    hh = x.offsetHeight;
                    if (tt >= t && tt < t2) {
                        x.src = url;
                        x.removeAttribute("data-url")
                    } else {
                        if (tt == 0 && ll == 0) {
                        } else {
                            if (tt + hh < t) {
                                break
                            }
                        }
                    }
                }
                if (isConstant && nullNum == len) {
                    isFull = true
                }
            };
            if ("addEventListener" in window) {
                wrap.addEventListener("scroll", loading, false)
            } else {
                wrap.attachEvent("onscroll", loading)
            }
            loading();
            return {loading: loading}
        }
    })();
    win.imgSetLoading = (function () {
        return function (id, _class) {
            var wrap = getId(id);
            var setC = function (_thisC, _true) {
                var _this = $(_true === true ? _thisC : this), p = _this.parent(), _ww = p.width(), _hh = p.height(),
                    _w = _this.width(), _h = _this.height();
                _this.css({left: ((_ww - _w) / 2) + "px", top: ((_hh - _h) / 2) + "px"});
                _this.animate({opacity: 1}, 500)
            };
            var setErr = function () {
                var _this = $(this), _err = _this.attr("data-error");
                if (_err != "null" && _err != "") {
                    $(this).css({width: "100%", height: "100%"}).attr("src", _err).animate({opacity: 1}, 500)
                } else {
                    setC(this, true)
                }
            };
            var loading = function () {
                var h = wrap.offsetHeight, t = wrap.getBoundingClientRect().top, t2 = h + t;
                var img = getClass(_class, wrap), len = img.length;
                var nullNum = 0;
                for (var i = len; i--;) {
                    var x = img[i], url = getAttr(x, "data-url"), isNull = url === null || url === "";
                    if (isNull) {
                        nullNum++;
                        continue
                    }
                    var type = getAttr(x, "data-type"), rect = x.getBoundingClientRect(), tt = rect.top, ll = rect.left,
                        hh = x.offsetHeight;
                    if (tt >= t && tt < t2) {
                        x.removeAttribute("data-url");
                        var _css = getStyle(x),
                            _r = _css.borderTopLeftRadius + " " + _css.borderTopRightRadius + " " + _css.borderBottomRightRadius + " " + _css.borderBottomLeftRadius,
                            _img = $("<img src='" + url + "' data-error='" + getAttr(x, "data-error") + "' style='position:absolute;top:0;left:0;max-width:1000%;opacity:0;border-radius:" + _r + ";' />");
                        if (type === "all") {
                            _img.attr("width", "100%").attr("height", "100%");
                            _img.on("load", function () {
                                $(this).animate({opacity: 1}, 500)
                            })
                        } else {
                            _img.css({"max-height": "100%"});
                            _img.on("load", setC)
                        }
                        _img.on("error", setErr);
                        $(x).append(_img)
                    } else {
                        if (tt == 0 && ll == 0) {
                        } else {
                            if (tt + hh < t) {
                                break
                            }
                        }
                    }
                }
            };
            if ("addEventListener" in window) {
                wrap.addEventListener("scroll", loading, false)
            } else {
                wrap.attachEvent("onscroll", loading)
            }
            loading();
            return {loading: loading}
        }
    })();
    win.lcp_downCreate = (function () {
        var id = baseName + "jy_mttc";
        var id_a = baseName + "down", id_b = baseName + "down2", id_c = baseName + "down3";
        var style_check = function (x, xx) {
            var p = xx.position;
            if (p != "relative" && p != "absolute" && p != "fixed") {
                x.style.position = "relative"
            }
        };
        var setstyle = function (x, xx, f) {
            var r = xx.borderTopLeftRadius + " " + xx.borderTopRightRadius + " " + xx.borderBottomRightRadius + " " + xx.borderBottomLeftRadius,
                w = x.offsetWidth, h = x.offsetHeight,
                l = xx.borderLeftWidth == "0px" ? "0px" : ("-" + xx.borderLeftWidth),
                t = xx.borderTopWidth == "0px" ? "0px" : ("-" + xx.borderTopWidth);
            r = f == 0 ? r : f == 1 ? "5px" : "50%";
            return "width:" + w + "px;height:" + h + "px;left:" + l + ";top:" + t + ";border-radius:" + r + ";"
        };
        var downModel = function (x, f) {
            Tadd(x);
            var xx = getStyle(x);
            up();
            style_check(x, xx);
            var div = createDom("div");
            div.id = id;
            div.setAttribute("style", setstyle(x, xx, f));
            x.appendChild(div)
        };
        var down = function () {
            downModel(this, 0)
        };
        var down2 = function () {
            downModel(this, 1)
        };
        var down3 = function () {
            downModel(this, 2)
        };
        var up = function () {
            var dom = getId(id);
            if (dom) {
                dom.parentNode.removeChild(dom)
            }
            Tdel(this)
        };
        var Tadd = function (x) {
            x.addEventListener("touchmove", up, false);
            x.addEventListener("touchend", up, false);
            x.addEventListener("touchcancel", up, false)
        };
        var Tdel = function (x) {
            x.removeEventListener("touchmove", up, false);
            x.removeEventListener("touchend", up, false);
            x.removeEventListener("touchcancel", up, false)
        };
        var Tbind = function (x, f) {
            var func = f == id_a ? down : f == id_b ? down2 : down3;
            x.addEventListener("touchstart", func, false)
        };
        domLoad(function () {
            var cssId = "jy_style",
                cssStr = "#lcp_jy_mttc{background:url('" + myTools.baseHref + "images/jy_black.png');overflow:hidden;position:absolute;top:0;left:0;margin:0;padding:0;text-indent:0;font-size:0;line-height:0;z-index:1000;}";
            createStyle(cssId, cssStr);
            var y = new Image();
            y.src = myTools.baseHref + "images/jy_black.png"
        });
        var Tfind = function (f, arr) {
            var llll = arr.length;
            if (llll > 0) {
                for (var _i = llll; _i--;) {
                    if (arr[_i].islcpdownCreate == 1) {
                        continue
                    }
                    arr[_i].islcpdownCreate = 1;
                    Tbind(arr[_i], f)
                }
            }
        };
        return function (X_dom) {
            var PBody = X_dom ? X_dom : doc.body;
            var lcp_down = getClass(id_a, PBody), lcp_down2 = getClass(id_b, PBody), lcp_down3 = getClass(id_c, PBody);
            Tfind(id_a, lcp_down);
            Tfind(id_b, lcp_down2);
            Tfind(id_c, lcp_down3)
        }
    })();
    if (baseOption.isDownCreate === true) {
        domLoad(win.lcp_downCreate)
    }
    win.X_run = (function () {
        return function (opts) {
            var option = {
                id: "", index: 0, startFunc: function () {
                    return true
                }, endFunc: function (li, index) {
                }, createAndRun: false
            };
            option = extend(option, opts);
            if (!getId(option.id)) {
                if ("console" in window) {
                    window.console.error("X_run", "please set a correct id")
                }
                return false
            }
            var wrap = getId(option.id), outter = getTag("div", wrap)[0], inner = getTag("ul", outter)[0],
                line = getTag("b", wrap)[0];
            var X_run_w = myTools.w;
            var X_run_nums = [], X_run_current = 0, X_run_index = option.index, X_iw = 0, X_cc, X_run_num;
            var X_run_fun = function () {
                var index = X_run_index, domW = outter.offsetWidth, li = getTag("li", inner), len = li.length, num = 0;
                X_run_nums = [];
                for (var i = 0; i < len; i++) {
                    num += li.item(i).offsetWidth;
                    X_run_nums.push(num)
                }
                if (num < domW) {
                    var leaveW = Math.floor((domW - num) / len), leaveW_last = domW - num - leaveW * (len - 1),
                        leaveWidth, leaveLi;
                    X_run_nums = [];
                    num = 0;
                    for (i = 0; i < len; i++) {
                        leaveLi = li.item(i);
                        leaveWidth = i == (len - 1) ? (leaveLi.offsetWidth + leaveW_last) : (leaveLi.offsetWidth + leaveW);
                        leaveLi.style.width = leaveWidth + "px";
                        num += leaveWidth;
                        X_run_nums.push(num)
                    }
                }
                num = num >= domW ? (num - domW) : 0;
                var maxN = X_run_nums.length, cc = X_run_current;
                if (index == 0) {
                    cc = 0
                } else {
                    if (index == maxN - 1) {
                        if (X_run_nums[index] < X_run_w) {
                            cc = 0
                        } else {
                            cc = -num
                        }
                    } else {
                        li = getTag("li", inner);
                        var pre = li.item(index - 1).getBoundingClientRect().left,
                            next = li.item(index + 1).getBoundingClientRect().right;
                        if (pre < X_iw) {
                            cc = X_run_current + X_iw - pre
                        } else {
                            if (next > X_run_w - X_iw) {
                                cc = X_run_current + X_run_w - next - X_iw
                            }
                        }
                    }
                }
                X_run_current = cc;
                X_run_animate(cc, 200);
                X_run_check(index);
                X_run_num = num
            };
            var X_run_scrollY, X_run_dist = 0, X_run_x = 0, X_run_y = 0;

            function X_run_start() {
                var tou = event.touches[0];
                X_run_x = tou.pageX;
                X_run_y = tou.pageY;
                X_run_scrollY = undefined
            }

            function X_run_move() {
                if (event.touches.length > 1 || X_run_nums[X_run_nums.length - 1] < X_run_w) {
                    return
                }
                var tou = event.touches[0];
                X_run_dist = tou.pageX - X_run_x;
                var yy = tou.pageY - X_run_y;
                if (typeof X_run_scrollY == "undefined") {
                    X_run_scrollY = !!(X_run_scrollY || Math.abs(X_run_dist) < Math.abs(yy))
                }
                if (!X_run_scrollY) {
                    event.preventDefault();
                    var sss = X_run_current + X_run_dist;
                    X_cc = sss > 0 ? sss * 0.4 : sss < -X_run_num ? ((sss + X_run_num) * 0.4 - X_run_num) : sss;
                    X_run_animate(X_cc, 0)
                }
            }

            function X_run_animate(bbcc, time) {
                inner.style.webkitTransitionDuration = inner.style.MozTransitionDuration = inner.style.msTransitionDuration = inner.style.OTransitionDuration = inner.style.transitionDuration = time + "ms";
                inner.style.webkitTransform = "translate(" + bbcc + "px,0)translateZ(0)";
                inner.style.msTransform = inner.style.MozTransform = inner.style.OTransform = "translateX(" + bbcc + "px)"
            }

            function X_run_end() {
                if (X_run_dist == 0) {
                    return
                }
                event.preventDefault();
                if (!X_run_scrollY) {
                    var sss = X_run_current + X_run_dist;
                    X_cc = sss > 0 ? 0 : sss < -X_run_num ? -X_run_num : sss;
                    if (X_cc == 0 || X_cc == -X_run_num) {
                        X_run_animate(X_cc, 200)
                    }
                    X_run_current = X_cc
                }
                X_run_dist = 0
            }

            outter.addEventListener("touchstart", X_run_start, false);
            outter.addEventListener("touchmove", X_run_move, false);
            outter.addEventListener("touchend", X_run_end, false);
            outter.addEventListener("touchcancel", X_run_end, false);
            function X_run_check(index) {
                var li = getTag("li", inner), len = li.length, now = li.item(index);
                for (var i = len; i--;) {
                    getClassList(li.item(i)).remove("selected")
                }
                getClassList(now).add("selected");
                var w = now.offsetWidth - 4, l = index == 0 ? 2 : (X_run_nums[index - 1] + 2);
                line.style.width = w + "px";
                line.style.left = l + "px"
            }

            function X_run(x, index) {
                if (!option.startFunc(x, index)) {
                    return
                }
                var maxN = X_run_nums.length;
                var cc = X_run_current;
                if (index == 0) {
                    cc = 0
                } else {
                    if (index == maxN - 1) {
                        if (X_run_nums[index] < X_run_w) {
                            cc = 0
                        } else {
                            cc = -X_run_num
                        }
                    } else {
                        var li = getTag("li", inner), pre = li.item(index - 1).getBoundingClientRect().left,
                            next = li.item(index + 1).getBoundingClientRect().right;
                        if (pre < X_iw) {
                            cc = X_run_current + X_iw - pre
                        } else {
                            if (next > X_run_w - X_iw) {
                                cc = X_run_current + X_run_w - next - X_iw
                            }
                        }
                    }
                }
                X_run_current = cc;
                X_run_animate(cc, 200);
                X_run_index = index;
                X_run_check(index);
                option.endFunc(x, index)
            }

            X_run_fun();
            var Xli = getTag("li", inner);
            for (var i = Xli.length; i--;) {
                var Yli = Xli[i];
                Yli.setAttribute("data-xindex", i);
                Yli.addEventListener("click", function () {
                    X_run(this, parseInt(this.getAttribute("data-xindex")))
                }, false)
            }
            if (option.createAndRun) {
                var createAndRunDom = Xli[option.index];
                X_run(createAndRunDom, parseInt(createAndRunDom.getAttribute("data-xindex")))
            }
            return {refresh: X_run_fun}
        }
    })();
    win.listLoadingMore = (function () {
        return function (option) {
            var id = option.id, id2 = option.id2, action = option.action, getNew = option.getNew,
                funcArg = option.funcArg ? option.funcArg : function () {
                }, func = option.func,
                loadingMustTime = option.loadingMustTime != undefined ? option.loadingMustTime : 0,
                loadingDom = option.loadingDom, loadedDom = option.loadedDom,
                nullDataFunc = option.nullDataFunc ? option.nullDataFunc : function () {
                }, endFunc = option.endFunc ? option.endFunc : function () {
                }, errorFunc = option.errorFunc, childrenTag = option.childrenTag ? option.childrenTag : "LI",
                pageNum = option.pageNum ? option.pageNum : 10;
            var style_check = function (x, xx) {
                var position = xx.position;
                if (position != "relative" && position != "absolute" && position != "fixed") {
                    x.style.position = "relative"
                }
            };
            var wrap = getId(id), p = wrap.parentNode, style = getStyle(p);
            style_check(p, style);
            var cStyle = function () {
                var cssId = "listLoadingMore";
                if (getId(cssId)) {
                    return
                }
                var cssStr = ".listLoadingMore{ display:none; position:absolute; left:0; width:100%; line-height:20px; text-align:center; font-size:12px; color:#000;}";
                createStyle(cssId, cssStr)
            };
            var loading_div, ok_div, setHtml = function (_dom, _return) {
                var _type = typeof _return;
                if (_type == "string") {
                    _dom.innerHTML = _return
                } else {
                    if (_type == "object") {
                        _dom.appendChild(_return)
                    }
                }
            }, createDiv = function () {
                cStyle();
                var ss = getStyle(wrap), zIndex = ss.zIndex == "auto" ? "1" : (parseInt(ss.zIndex) + 1),
                    loadingHtml = "正在为您加载更多", okHtml = "加载完毕";
                loading_div = createDom("div");
                ok_div = createDom("div");
                loading_div.className = ok_div.className = "listLoadingMore";
                loading_div.style.zIndex = ok_div.style.zIndex = zIndex;
                loading_div.style.bottom = ok_div.style.bottom = "1px";
                if (typeof loadingDom == "function") {
                    setHtml(loading_div, loadingDom())
                } else {
                    loading_div.innerHTML = loadingHtml
                }
                if (typeof loadedDom == "function") {
                    setHtml(ok_div, loadedDom())
                } else {
                    ok_div.innerHTML = okHtml
                }
                p.appendChild(loading_div);
                p.appendChild(ok_div)
            };
            var isRunning = false, isFull = false, isStop = false;
            var setIsRunning = function (x) {
                isRunning = x
            };
            var setIsFull = function (x) {
                isFull = x
            };
            var setIsStop = function (x) {
                isStop = x
            };
            var startT = 0, endT, getNewBefore = function () {
                startT = +new Date();
                getNew(funcArg())
            };
            var getChildrenNum = function (p) {
                var children = p.childNodes, num = 0;
                for (var i = children.length; i--;) {
                    if (children[i].nodeName == childrenTag) {
                        num++
                    }
                }
                return num
            };
            var getData = function (str) {
                var arr = str.split("?");
                if (arr.length == 1 || arr[1] == "") {
                    return {}
                }
                var arr2 = arr[1].split("&");
                var option = {}, arr3;
                for (var i = arr2.length; i--;) {
                    arr3 = arr2[i].split("=");
                    option[arr3[0]] = decodeURI(arr3[1])
                }
                return option
            };
            var addDataListNext = function (_data, _p, _length) {
                if (_data && _data.length > 0) {
                    for (var i = 0; i < _data.length; i++) {
                        var li = func(_data[i]);
                        _p.appendChild(li)
                    }
                    if ("lcp_downCreate" in win) {
                        win.lcp_downCreate(_p)
                    }
                    if (_data.length < pageNum) {
                        setIsFull(true)
                    }
                } else {
                    if (_length == 0) {
                        isStop = true;
                        nullDataFunc()
                    }
                    setIsFull(true)
                }
                setIsRunning(false);
                loading(true);
                endFunc()
            }, addDataList = function (_data, _p, _length) {
                endT = (+new Date()) - startT;
                if (endT < loadingMustTime) {
                    setTimeout(function () {
                        addDataListNext(_data, _p, _length)
                    }, loadingMustTime - endT)
                } else {
                    addDataListNext(_data, _p, _length)
                }
            };
            getNew = getNew ? getNew : function () {
                var idX = id2(), p = getId(idX), length = p ? getChildrenNum(p) : 0;
                if (idX == "") {
                    setIsRunning(false);
                    showHide(false, false);
                    return false
                }
                if (length % pageNum != 0) {
                    setIsRunning(false);
                    setIsFull(true);
                    loading(true);
                    return false
                }
                var page = parseInt(length / pageNum) + 1;
                var href = action(), isHas = href.search("\\?");
                if (isHas == -1) {
                    href += "?"
                } else {
                    if (href.length > (isHas + 1)) {
                        href += "&"
                    }
                }
                href = href + "page=" + page + "&length=" + length;
                var ajaxObject = {
                    type: "post",
                    url: href,
                    data: getData(href),
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    async: false,
                    dataType: "json",
                    success: function (data) {
                        addDataList(data, p, length)
                    },
                    complete: function () {
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        setIsRunning(false);
                        errorFunc(XMLHttpRequest, textStatus, errorThrown)
                    }
                };
                if ("jQuery" in window) {
                    $.ajax(ajaxObject)
                } else {
                    ajax(ajaxObject)
                }
            };
            var showHide = function (f1, f2) {
                loading_div.style.display = f1 ? "block" : "none";
                ok_div.style.display = f2 ? "block" : "none"
            };
            var loading = function (flag) {
                if (isStop) {
                    return false
                }
                var x = wrap, x_sh = x.scrollHeight - x.clientHeight;
                var scroller = x.scrollTop;
                if (scroller > x_sh - 50 && x_sh > 0) {
                    if (isRunning) {
                        showHide(true, false);
                        return false
                    }
                    if (isFull) {
                        showHide(false, true);
                        return false
                    }
                    showHide(true);
                    if (flag !== true) {
                        isRunning = true;
                        getNewBefore()
                    }
                } else {
                    showHide(false, false)
                }
            };
            createDiv();
            wrap.addEventListener("scroll", loading, false);
            return {
                isFull: setIsFull,
                isRunning: setIsRunning,
                isStop: setIsStop,
                loading: loading,
                show: showHide,
                getNew: getNew
            }
        }
    })();
    var lcp_alert_flag = false, lcp_alert_time;
    win.lcp_alert = function (state,title, content, flag, fun, fun2) {
        if (lcp_alert_flag) {
            clearTimeout(lcp_alert_time);
            win.lcp_ok(true);
            win.lcp_alert.apply(win, arguments);
            return false
        }
        lcp_alert_flag = true;
        var w = myTools.w, h = myTools.h;
        var a = "lcp_ok()", b = a + ";";
        if (fun) {
            fun = b + fun
        }
        if (fun2) {
            fun2 = b + fun2
        }
        var mttc = createDom("div"), mttc_func = flag == 2 ? fun : flag == 3 ? fun2 : a;
        mttc.className = "lcp_mttc";
        mttc.id = "lcp_mttc";
        mttc.setAttribute("onclick", mttc_func);
        doc.body.appendChild(mttc);
        var alert_dom = createDom("div");
        alert_dom.className = "lcp_alert";
        alert_dom.id = "lcp_alert";
        alert_dom.setAttribute("style", "transform:scale(0,0);-webkit-transform:scale(0,0);");
        var titHTML = title === false ? "" : "<div class='lcp_alert_title'>" + title + "</div>";
        if (flag == 0) {
            if(state=="pic"){
                alert_dom.innerHTML = "<i class='lcp_alert_close lcp_down' onclick='" + mttc_func + "'>&times;</i>" + titHTML + "<div class='lcp_alert_con'>" + content + "</div><div class='lcp_alert_btn_wrap'></div>"
            }else{
                alert_dom.innerHTML = "<i class='lcp_alert_close lcp_down' onclick='" + mttc_func + "'>&times;</i>" + titHTML + "<div class='lcp_alert_con'>" + content + "</div><div class='lcp_alert_btn_wrap'><div class='lcp_alert_btn lcp_down' onclick='lcp_ok()'>确 定</div></div>"
            }
        } else {
            if (flag == 1) {
                alert_dom.innerHTML = "<i class='lcp_alert_close lcp_down' onclick='" + mttc_func + "'>&times;</i>" + titHTML + "<div class='lcp_alert_con'>" + content + "</div><div class='lcp_alert_btn_wrap'><div class='lcp_alert_btn lcp_alert_btn2 lcp_down' onclick='" + fun + "'>确 定</div><div class='lcp_alert_btn lcp_alert_btn2 lcp_down' onclick='lcp_ok()'>取 消</div></div>"
            } else {
                if (flag == 2) {
                    alert_dom.innerHTML = "<i class='lcp_alert_close lcp_down' onclick='" + mttc_func + "'>&times;</i>" + titHTML + "<div class='lcp_alert_con'>" + content + "</div><div class='lcp_alert_btn_wrap'><div class='lcp_alert_btn lcp_down' onclick='" + fun + "'>确 定</div></div>"
                } else {
                    if (flag == 3) {
                        alert_dom.innerHTML = "<i class='lcp_alert_close lcp_down' onclick='" + mttc_func + "'>&times;</i>" + titHTML + "<div class='lcp_alert_con'>" + content + "</div><div class='lcp_alert_btn_wrap'><div class='lcp_alert_btn lcp_alert_btn2 lcp_down' onclick='" + fun + "'>确 定</div><div class='lcp_alert_btn lcp_alert_btn2 lcp_down' onclick='" + fun2 + "'>取 消</div></div>"
                    } else {
                        if (flag == 4) {
                            alert_dom.innerHTML = "<i class='lcp_alert_close lcp_down' onclick='" + mttc_func + "'>&times;</i>" + titHTML + "<div class='lcp_alert_con'>" + content + "</div>"
                        }
                    }
                }
            }
        }
        doc.body.appendChild(alert_dom);
        window.lcp_downCreate(alert_dom);
        alert_dom.style.top = (h / 2 - alert_dom.offsetHeight / 2) + "px";
        alert_dom.style.left = (w / 2 - alert_dom.offsetWidth / 2) + "px";
        alert_dom.style.transform = alert_dom.style.webkitTransform = "scale(1,1)"
    };
    win.lcp_ok = function (flag) {
        var a = getId("lcp_mttc"), b = getId("lcp_alert");
        var c = function () {
            lcp_alert_flag = false;
            try {
                doc.body.removeChild(a)
            } catch (e) {
            }
            try {
                doc.body.removeChild(b)
            } catch (e) {
            }
        };
        if (a) {
            a.style.background = "transparent"
        }
        if (b) {
            b.getElementsByTagName("i").item(0).setAttribute("onclick", "");
            var domDiv = b.getElementsByTagName("div");
            for (var i = domDiv.length; i--;) {
                domDiv.item(i).setAttribute("onclick", "")
            }
            b.style.transform = b.style.webkitTransform = "scale(0,0)";
            if (flag) {
                c()
            } else {
                lcp_alert_time = setTimeout(c, 600)
            }
        }
    };
    win.lcp_back_flag = "";
    win.lcp_go = (function () {
        return function (href) {
            if (!isTwoWindow) {
                win.location.href = href
            } else {
                if ("lcp_goto" in win2) {
                    win2.lcp_goto(true, href)
                }
            }
        }
    })();
    win.lcp_back = (function () {
        if (isTwoWindow) {
            var backPre2 = function () {
                var f = "lcp_backPre" in win ? win.lcp_backPre : "if(document.getElementById('lcp_alert')){var str = document.getElementById('lcp_mttc').getAttribute('onclick');eval(str);return false;}";
                var o = getClass("lcp_back"), text = f + "MyJS.showout();", oc = "";
                if (o.length != 0) {
                    oc = o[0].getAttribute("onclick");
                    text = oc == "" || oc == null ? text : (f + oc);
                    o[0].setAttribute("onclick", "lcp_baseBack()")
                }
                createScript("lcp_backPre", "function lcp_baseBack(){" + text + "}")
            };
            domLoad(backPre2);
            return function (href) {
                if ("lcp_goto" in win2) {
                    win2.lcp_goto(false, href)
                }
            }
        } else {
            var mysplitString = "ThisisLcpInsert", myhistroy = "lcp_hrefs", myhistroy2 = "lcp_hrefs_last";
            var lcp_getBack = function () {
                var a = String(getLoc(myhistroy));
                if (a.length == 0) {
                    MyJS.showout();
                    return false
                }
                var b = a.split(mysplitString);
                if (b.length > 1) {
                    return b[b.length - 2]
                } else {
                    MyJS.showout();
                    return false
                }
            };
            var lcp_clearBack = function (href) {
                var a = String(getLoc(myhistroy)), b = a.split(mysplitString), b_len = b.length, c = href.split("?")[0];
                for (var i = 0; i < b_len; i++) {
                    var d = b[i].split("?")[0];
                    if (c == d) {
                        b.splice(i + 1);
                        setLoc(myhistroy, b.join(mysplitString));
                        setLoc(myhistroy2, b[i]);
                        break
                    }
                }
            };
            var a = String(getLoc(myhistroy));
            var b = String(getLoc(myhistroy2)), bb = b.split("?")[0];
            var c = win.location.href, cc = c.split("?")[0], ccc = c;
            if (a.length > 0 && a != "null") {
                if (bb == cc) {
                    var d = a.split(mysplitString);
                    d.splice(-1, 1);
                    d.push(ccc);
                    setLoc(myhistroy, d.join(mysplitString))
                } else {
                    setLoc(myhistroy, a + mysplitString + ccc)
                }
            } else {
                setLoc(myhistroy, ccc)
            }
            setLoc(myhistroy2, ccc);
            var backPre = function () {
                var f = "lcp_backPre" in win ? win.lcp_backPre : "if(document.getElementById('lcp_alert')){var str = document.getElementById('lcp_mttc').getAttribute('onclick');eval(str);return false;}";
                var sss = "window.localStorage.setItem('lcp_hrefs','null');window.localStorage.setItem('lcp_hrefs_last','null');";
                var xxx = "if('BaseJS' in window){" + sss + "BaseJS.gotoUrl('file:///android_asset/demo.html')}else{lcp_back();}";
                var o = getClass("lcp_back"), text = f + "MyJS.showout();", oc = "", oc2, isPhone = "BaseJS" in window;
                if (o.length == 0) {
                    setLoc(myhistroy, ccc)
                } else {
                    if (o.length != 0) {
                        oc = o[0].getAttribute("onclick");
                        oc2 = oc == "" || oc == null ? (f + xxx) : (f + oc);
                        text = oc2;
                        o[0].setAttribute("onclick", "lcp_baseBack()")
                    }
                }
                createScript("lcp_backPre", "function lcp_baseBack(){" + text + "}");
                if (isPhone && (oc == "" || oc == null)) {
                    setLoc(myhistroy, win.location.href)
                }
            };
            domLoad(backPre);
            return function (href) {
                if (href === undefined || href == "") {
                    href = lcp_getBack()
                }
                if (href === false) {
                    return false
                }
                lcp_clearBack(href);
                win.location.href = href
            }
        }
    })();
    var parentInnerMttcTime = +new Date();
    var parentInnerMttcFunc = function () {
        if (isTwoWindow && ("closeMttc" in win2)) {
            win2.closeMttc()
        }
    };
    var parentYSFunction = function () {
        if ("lcp_setImg" in win) {
            win3.lcp_setImg = function () {
                win.lcp_setImg.apply(win, arguments)
            }
        }
        if ("lcp_ewmDrapTo" in win) {
            win3.lcp_ewmDrapTo = function () {
                win.lcp_ewmDrapTo.apply(win, arguments)
            }
        }
        if ("lcp_setAudio" in win) {
            win3.lcp_setAudio = function () {
                win.lcp_setAudio.apply(win, arguments)
            }
        }
        win3.setMessage = function () {
            win.setMessage.apply(win, arguments)
        };
        win3.getMessage = function () {
            return win.getMessage.apply(win, arguments)
        };
        win3.setToken = function () {
            win.setToken.apply(win, arguments)
        };
        win3.getToken = function () {
            return win.getToken.apply(win, arguments)
        };
        win3.updateMessage = function () {
            win.updateMessage.apply(win, arguments)
        }
    };
    if (isTwoWindow) {
        if ("updateHistoryLength" in win2) {
            win2.updateHistoryLength()
        }
    }
    domLoad(function () {
        win2.isDomLoad = true;
        var time = (+new Date()) - parentInnerMttcTime;
        if (time < 100) {
            setTimeout(parentInnerMttcFunc, 100 - time)
        } else {
            parentInnerMttcFunc()
        }
        if (isTwoWindow) {
            parentYSFunction()
        }
    });
    return myTools
})();
//读取基础路径:型如-http://192.168.1.XXX:8080/XXX/XXX/skin/
var myBaseHref = baseTools.baseHref;

//页面body部分加载完毕时执行
function base_onload() {
    // 图片加载失败
    document.addEventListener("error", function (e) {
        var elem = e.target;
        if (elem.tagName.toLowerCase() === 'img') {
            elem.src = myBaseHref + "images/base.gif";  // 请保证此路径正确
        }
    }, true);
    base_resize();          //执行通用高度设置
    window.onresize = function () {
        base_resize();
    };
}
baseTools.domLoad(base_onload);
function getThisHeight() {
    var h = document.documentElement.clientHeight, prevH = baseTools.getLoc("singlePagePrevH"),
        nowH = baseTools.getLoc("singlePageNowH");
    if (prevH === null) {
        baseTools.setLoc("singlePagePrevH", h);
        baseTools.setLoc("singlePageNowH", h);
        return h;
    }
    else {
        if (Math.abs(parseInt(prevH) - h) <= 100) {
            baseTools.setLoc("singlePageNowH", h);
            return h;
        }
        else {
            return nowH;
        }
    }
}
function base_resize() {
    var body = document.body;
    baseTools.w = document.documentElement.clientWidth;
    baseTools.h = baseTools.isTwoWindow || baseTools.isMobile ? baseTools.getH() : getThisHeight();
    body.style.height = baseTools.h + "px";
}
/* 通用序列 */
function setMessage(name, value) {
    try {
        window["MyJS"]["setinfo"](name, value);
    } catch (e) {
        window.localStorage.setItem(name, value);
    }
}
function getMessage(name) {
    var val;
    try {
        val = window["MyJS"]["getinfo"](name);
    } catch (e) {
        val = window.localStorage.getItem(name);
    }
    val = val === null ? "" : val;
    return val;
}
function setToken(str) {
    window.localStorage.setItem("mytoken", str);
}
function getToken() {
    return window.localStorage.getItem("mytoken");
}
function updateMessage() {
    try {
        getMessageNow();
    } catch (e) {
    }
}
function setMessageNow(C) {
}
function lcp_tel(num) {
    var ua = window.navigator.userAgent.toLowerCase();
    var a = ua.match(/(Android)/i), b = ua.match(/MicroMessenger/i) == 'micromessenger';
    if (b) baseTools.win2.location.href = "tel:" + num;
    else if (a) window["BaseJS"]["jsPhoneCall"](num);
    else baseTools.win2.location.href = "ios/BaseJS/jsPhoneCall:" + num;
}
function lcp_share(content) {
    var ua = window.navigator.userAgent.toLowerCase();
    var a = ua.match(/(Android)/i);
    if (a) window["BaseJS"]["ShareLink"](content);
    else baseTools.win2.location.href = "ios/MyJS/sharelink:" + content;
}
function isWx() {
    var ua = window.navigator.userAgent.toLowerCase();
    return ua.match(/MicroMessenger/i) == 'micromessenger';
}