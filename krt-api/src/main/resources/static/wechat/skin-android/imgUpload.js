var imgUpload = (function () {
    return function (_opts) {
        var Img = function (opts) {
            return new Img.fn.init(opts)
        };
        Img.view = {
            ifrNum: 0, createView: function () {
                Img.view.createStyle();
                var wrap = document.createElement("div");
                wrap.className = "imgUpload_wrapper";
                wrap.id = "imgUpload_" + Img.fn.rad;
                wrap.style.zIndex = Img.fn.zIndex;
                var ifhtml = "", imgHtml = "";
                ifhtml += Img.view.getOneIframe(Img.view.ifrNum++, -1);
                for (var i = 1, len = Img.fn.num; i <= len; i++) {
                    imgHtml += Img.view.getOneImg(i);
                    Img.fn.imgHref.push("")
                }
                if (len % 3 != 0) {
                    var llll = 3 - len % 3;
                    for (var i = llll; i--;) {
                        imgHtml += Img.view.getOneImg(i, true)
                    }
                }
                var footerHtml = "<div class='imgUpload_foot'><div class='imgUpload_btn'>提交</div><div class='imgUpload_btn'>返回</div></div>";
                var messageHtml = Img.view.getMessage();
                wrap.innerHTML = ifhtml + "<div class='imgUpload_img_ul'>" + imgHtml + "</div>" + messageHtml + footerHtml;
                Img.fn.wrap = wrap;
                document.body.appendChild(wrap);
                Img.controller.bindAll()
            }, createStyle: function () {
                var w = document.documentElement.clientWidth, a = document.createElement("style");
                var li_wh = Math.round(w * 28 / 100);
                var s = ".imgUpload_img{width:" + li_wh + "px; height:" + li_wh + "px;}.imgUpload_img_loading{z-index:" + (Img.fn.zIndex + 2) + ";}.imgUpload_img>img{z-index:" + (Img.fn.zIndex + 1) + ";}.imgUpload_img>i{z-index:" + (Img.fn.zIndex + 3) + ";}#imgUpload_mttc{z-index:" + (Img.fn.zIndex + 1) + ";}";
                a.setAttribute("type", "text/css");
                try {
                    a.innerHTML = s
                } catch (e) {
                    if (a.styleSheet) {
                        a.styleSheet.cssText = s
                    } else {
                        a.appendChild(document.createTextNode(s))
                    }
                }
                document.getElementsByTagName("head").item(0).appendChild(a)
            }, getMessage: function () {
                return "<div class='imgUpload_message'></div>"
            }, getOneImg: function (index, isNone) {
                var hasImg = Img.fn.editNum >= index ? true : false, style = hasImg ? " style='display:block;'" : "";
                var img = hasImg ? "<img src='" + Img.fn.edit[index - 1] + "' />" : "";
                Img.fn.imgHref[index - 1] = Img.fn.edit[index - 1];
                var c = isNone ? "imgUpload_img none" : "imgUpload_img";
                var form = "<form method='post' enctype='multipart/form-data' target='imgUpload_" + Img.fn.rad + index + "' data-index='" + index + "' action='" + Img.fn.url + "'><input type='file' name='" + Img.fn.fileName + "' tabindex='-1' /><input type='hidden' name='" + Img.fn.myFileName + "' value='' /><input type='hidden' name='" + Img.fn.dir + "' value='' /></form><div class='imgUpload_img_loading'></div><i" + style + "></i>" + img;
                if (isNone) {
                    form = "";
                    index = -1
                }
                var one = "<div class='" + c + "' data-index='" + index + "'>" + form + "</div>";
                return one
            }, getOneIframe: function (_id, _index) {
                return "<iframe src='" + Img.fn.baseHref + "empty.html' id='imgUpload_" + Img.fn.rad + _id + "' data-index='" + _index + "' name='imgUpload_" + Img.fn.rad + _id + "'></iframe>"
            }
        };
        Img.funs = {
            getCss: function (x) {
                if (document.documentElement.currentStyle) {
                    return x.currentStyle
                } else {
                    if (document.defaultView.getComputedStyle) {
                        return document.defaultView.getComputedStyle(x, null)
                    }
                }
            }, down: function (x) {
                Img.funs.up();
                var xx = Img.funs.getCss(x);
                if (xx.position != "relative" && xx.position != "absolute" && xx.position != "fixed") {
                    x.style.position = "relative"
                }
                var div = document.createElement("div");
                div.id = "imgUpload_mttc";
                var w = x.offsetWidth - 2;
                var h = x.offsetHeight - 2;
                var str = "width:" + w + "px;height:" + h + "px;left:0;top:0; border-radius:3px; -moz-border-radius:3px;";
                div.setAttribute("style", str);
                x.appendChild(div)
            }, up: function () {
                var m = document.getElementById("imgUpload_mttc");
                if (m) {
                    m.parentNode.removeChild(m)
                }
            }, bind: function (dom, ev, func) {
                if (window.addEventListener) {
                    dom.addEventListener(ev, func, false)
                } else {
                    dom.attachEvent("on" + ev, func)
                }
            }, getBaseHref: function () {
                var link = document.getElementsByTagName("link"), len = link.length;
                for (var i = len; i--;) {
                    var str = link[i].href, s = str.search("/imgUpload.css");
                    if (s > -1) {
                        return str.substring(0, s)
                    }
                }
                return ""
            }, isImageFile: function (file) {
                return /(jpg|jpeg|png|gif)$/i.test(file)
            }, addZero: function (number) {
                if (number < 10) {
                    number = "0" + number
                }
                return number
            }
        };
        Img.controller = {
            bindAll: function () {
                var sons = Img.fn.wrap.childNodes, len = sons.length;
                var ok, cancel, img = [], del = [], wins = [];
                for (var i = len; i--;) {
                    var x = sons.item(i);
                    if (x.nodeName == "DIV" && x.className == "imgUpload_foot") {
                        var div = x.getElementsByTagName("div");
                        ok = div.item(0);
                        cancel = div.item(1)
                    } else {
                        if (x.nodeName == "DIV" && x.className == "imgUpload_img_ul") {
                            Img.fn.ul = x;
                            var ff = x.getElementsByTagName("form"), lllll = ff.length;
                            for (var k = lllll; k--;) {
                                img.push(ff.item(k).getElementsByTagName("input").item(0));
                                del.push(ff.item(k).parentNode.getElementsByTagName("i").item(0))
                            }
                        } else {
                            if (x.nodeName == "DIV" && x.className == "imgUpload_message") {
                                Img.fn.messageDom = x
                            } else {
                                if (x.nodeName == "IFRAME") {
                                    wins.push(x)
                                }
                            }
                        }
                    }
                }
                Img.funs.bind(ok, "click", Img.controller.setOk);
                Img.funs.bind(ok, "touchstart", function () {
                    Img.funs.down(ok)
                });
                Img.funs.bind(ok, "touchend", Img.funs.up);
                Img.funs.bind(ok, "touchcancel", Img.funs.up);
                Img.funs.bind(cancel, "click", Img.fn.myHide);
                Img.funs.bind(cancel, "touchstart", function () {
                    Img.funs.down(cancel)
                });
                Img.funs.bind(cancel, "touchend", Img.funs.up);
                Img.funs.bind(cancel, "touchcancel", Img.funs.up);
                for (var i = img.length; i--;) {
                    Img.funs.bind(img[i], "change", Img.controller.change);
                    Img.funs.bind(del[i], "click", Img.controller.del)
                }
                for (var i = wins.length; i--;) {
                    Img.funs.bind(wins[i], "load", Img.controller.load)
                }
                Img.funs.bind(Img.fn.messageDom, "webkitAnimationEnd", Img.controller.messageAnimationEnd);
                Img.funs.bind(Img.fn.messageDom, "animationend", Img.controller.messageAnimationEnd)
            }, messageAnimationEnd: function () {
                this.className = "imgUpload_message";
                this.style.display = "none"
            }, del: function () {
                var p = this.parentNode, img = p.getElementsByTagName("img").item(0),
                    index = p.getAttribute("data-index");
                p.removeChild(img);
                p.getElementsByTagName("div").item(0).style.display = "none";
                p.getElementsByTagName("i").item(0).style.display = "none";
                Img.fn.imgHref[index - 1] = "";
                Img.controller.checksite()
            }, change: function () {
                var file = "files" in this ? this.files[0].type : this.value, form = this.parentNode,
                    index = form.getAttribute("data-index");
                if (Img.funs.isImageFile(file)) {
                    var name = form.getElementsByTagName("input").item(1), d = new Date();
                    var picName = d.getFullYear() + Img.funs.addZero(d.getMonth() + 1) + Img.funs.addZero(d.getDate()) + Img.funs.addZero(d.getHours()) + Img.funs.addZero(d.getMinutes()) + Img.funs.addZero(d.getSeconds()) + "_" + Math.floor(Math.random() * 1000) + ".jpg";
                    name.value = picName;
                    var newIfr = document.createElement("div"), _id = Img.view.ifrNum++;
                    newIfr.style.display = "none";
                    newIfr.innerHTML = Img.view.getOneIframe(_id, index);
                    Img.fn.wrap.appendChild(newIfr);
                    Img.funs.bind(newIfr.getElementsByTagName("iframe")[0], "load", Img.controller.load);
                    form.setAttribute("target", "imgUpload_" + Img.fn.rad + _id);
                    form.submit();
                    form.reset();
                    form.parentNode.getElementsByTagName("div").item(0).style.display = "block"
                } else {
                    form.reset();
                    Img.fn.message("请选择图片文件")
                }
            }, loadImgOne: function (url, index) {
                var sons = Img.fn.ul.childNodes, len = sons.length;
                for (var i = 0; i < len; i++) {
                    if (sons.item(i).nodeName == "DIV") {
                        if (sons.item(i).getAttribute("data-index") == index) {
                            var p = sons.item(i), isNull = p.getElementsByTagName("img").length == 0;
                            if (isNull) {
                                var img = document.createElement("img");
                                img.src = url;
                                Img.funs.bind(img, "load", Img.controller.loadImgOneok);
                                p.appendChild(img);
                                p.getElementsByTagName("div").item(0).style.display = "block";
                                p.getElementsByTagName("i").item(0).style.display = "block";
                                Img.controller.checksite()
                            } else {
                                p.getElementsByTagName("img").item(0).src = url
                            }
                        }
                    }
                }
            }, checksite: function () {
                var sons = Img.fn.ul.childNodes, len = sons.length;
                for (var i = 0, k = 0; i < len; i++) {
                    if (sons.item(i).nodeName == "DIV" && sons.item(i).getElementsByTagName("img").length != 0) {
                        k++;
                        Img.fn.ul.insertBefore(sons.item(i), sons.item(k - 1))
                    }
                }
            }, loadImgOneok: function () {
                this.parentNode.getElementsByTagName("div").item(0).style.display = "none"
            }, load: function () {
                var rs = this.contentDocument ? this.contentDocument.body.innerHTML : window.frames[this.name].document.body.innerHTML;
                if (rs == "") {
                    return false
                }
                try {
                    rs = rs.replace(/<[^>]+>/g, "");
                    var data = eval("(" + rs + ")");
                    if (data.imgUpload_v && data.imgUpload_v == Img.fn.imgUpload) {
                    } else {
                        var url = Img.fn.getUrl(data), index = this.getAttribute("data-index");
                        if (url == "error") {
                            Img.fn.message("图片上传出现异常")
                        } else {
                            Img.fn.imgHref[index - 1] = url;
                            Img.controller.loadImgOne(url, index)
                        }
                    }
                } catch (e) {
                    Img.fn.error({body: rs, message: "图片上传地址出错,返回格式异常,或网络连接中断！", error: e.message})
                }
            }, setOk: function () {
                var hrefs = [];
                var sons = Img.fn.ul.childNodes, len = sons.length;
                for (var i = 0; i < len; i++) {
                    if (sons.item(i).nodeName == "DIV" && sons.item(i).getElementsByTagName("img").length != 0) {
                        var index = parseInt(sons.item(i).getAttribute("data-index")) - 1;
                        hrefs.push(Img.fn.imgHref[index])
                    }
                }
                Img.fn.myHide();
                Img.fn.ok(hrefs)
            }
        };
        Img.fn = Img.prototype = {
            num: 3,
            zIndex: 99,
            url: "url",
            fileName: "fileName",
            rad: +new Date(),
            imgHref: [],
            imgStr: "",
            edit: [],
            editNum: 0,
            imgUpload: "1.1",
            init: function (opts) {
                for (var i in opts) {
                    Img.fn[i] = opts[i]
                }
                Img.fn.rad = +new Date() + "" + Math.round(Math.random() * 10000);
                Img.fn.baseHref = Img.funs.getBaseHref() + "/";
                if (Img.fn.imgStr != "") {
                    var edit = Img.fn.imgStr.split(","), len = edit.length;
                    Img.fn.edit = edit;
                    Img.fn.editNum = len
                }
                Img.view.createView();
                return Img.fn
            },
            message: function (content) {
                if (Img.fn.messageDom.className != "imgUpload_message") {
                    var div = document.createElement("div");
                    div.className = "imgUpload_message";
                    Img.fn.wrap.removeChild(Img.fn.messageDom);
                    Img.fn.wrap.appendChild(div);
                    Img.fn.messageDom = div;
                    Img.funs.bind(div, "webkitAnimationEnd", Img.controller.messageAnimationEnd);
                    Img.funs.bind(div, "animationend", Img.controller.messageAnimationEnd)
                }
                Img.fn.messageDom.innerHTML = "<span>" + content + "</span>";
                Img.fn.messageDom.style.display = "block";
                Img.fn.messageDom.className = "imgUpload_message imgUpload_messageFlash"
            },
            myShow: function () {
                Img.fn.wrap.style.display = "block"
            },
            myHide: function () {
                Img.fn.wrap.style.display = "none";
                Img.fn.hide()
            },
            destroy: function () {
                Img.fn.wrap.parentNode.removeChild(Img.fn.wrap)
            },
            getUrl: function () {
                return ""
            },
            success: function () {
            },
            error: function () {
            },
            hide: function () {
            },
            ok: function () {
            }
        };
        return new Img(_opts)
    }
})();