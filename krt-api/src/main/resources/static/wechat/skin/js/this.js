/* Now it's your turn to write. */
/*
 *   tip:请保持本文件的唯一编辑权，即不要在本文件中书写任何可能需要开发人员改动的代码！
 *       也就是说：这里所存放的代码是可能出现在多个页面中的通用代码
 * */
var list_type,tab_scroller = [],pre_index = 0,tab_time = false;
function tab(x,index,flag){
    if(tab_time || (pre_index == index&&!flag)) return false;
    tab_time = true;
    var pre = pre_index;
    pre_index = index;
    var p = x.parentNode.nodeName=="UL"?x.parentNode:x.parentNode.parentNode,li = p.getElementsByTagName("li"),len = li.length, i,scroll = document.getElementById("list"),noLoad = x.getAttribute("data-noload")=="true",direction = x.getAttribute("data-direction")=="tb";
    if(tab_scroller.length == 0){   //初始化滚动条记录高度
        for(i=len;i--;) tab_scroller.push(0);
    }
    tab_scroller[list_type] = scroll.scrollTop;
    for(i=len;i--;) li.item(i).className = li.item(i).className.replace(/ selected/g,"").replace(/selected/g,"");
    if(x.nodeName == "LI") x.className = x.className+" selected";
    else x.parentNode.className = x.parentNode.className+" selected";

    list_type = index;
    var ul_p = scroll.childNodes,ul,j = 0,pre_ul,now_ul;
    len = ul_p.length;
    for(i=0;i<len;i++){
        var xx = ul_p.item(i);
        if(xx.nodeName == "UL" || xx.nodeName == "DIV"){
            if(j == index){
                now_ul = xx;
                ul = xx;
            }
            else if(pre == j){
                pre_ul = xx;
            }
            j++;
        }
    }
    try{
        //重置列表加载参数
        nowLoading.isStop(false);
        nowLoading.isFull(false);
    }catch(e){}
    if(!noLoad){

        if(ul.getElementsByTagName("li").length==0){    //一开始是空数据，请求加载初始数据
            try{
                nowLoading.getNew();
                imgRunLoading.loading();
            }catch(e){}
        }
    }
    if(pre_ul&&now_ul){
        var a = direction?"translate3d(0,-100%,0)":"translate3d(-100%,0,0)",b = "translate3d(0, 0, 0)",c = direction?"translate3d(0,100%,0)":"translate3d(100%,0,0)",sc = scroll.scrollTop;
        pre_ul.style.position = "absolute";
        //pre_ul.style.marginTop = -sc+"px";
        pre_ul.style.zIndex = 1;
        pre_ul.style.webkitTransitionDuration = pre_ul.style.transitionDuration = '500ms';
        now_ul.style.marginTop = sc-tab_scroller[index]+"px";
        now_ul.style.zIndex = 2;
        now_ul.style.webkitTransitionDuration = now_ul.style.transitionDuration = '400ms';
        pre_ul.style.transform = pre_ul.style.webkitTransform = pre<index?a:c;
        now_ul.style.display = "block";
        setTimeout(function(){
            now_ul.style.transform = now_ul.style.webkitTransform = "translate3d(0, 0, 0)";
            try{imgLoading.loading()}catch(e){}
        },50);
        setTimeout(function(){
            now_ul.style.position = "relative";
            now_ul.style.marginTop = "0";
            scroll.scrollTop = tab_scroller[index];
            pre_ul.style.display = "none";
            tab_time=false;
            try{imgLoading.loading()}catch(e){}
        },600);
        //scroll.scrollTop = tab_scroller[index];
    }
    else tab_time=false;
}
var getSmall = function(x,num){
    if(x.length>num){
        x = x.substr(0,num)+"...";
    }
    return x;
};
var preZeroCheck = function(_num){if(_num<10) _num="0"+parseInt(_num);return _num;};
var openMttc = function(zIndex){
    zIndex = zIndex?zIndex:90;
    var css = ".newLoad-mttc{position:fixed;top:0;left:0;z-index:"+zIndex+";width:100%;height:100%;background-color:rgba(0,0,0,.2)}.newLoad-wrap{width:4rem;height:4rem;border-radius:.5rem;background:#000;opacity:.8;position:absolute;left:50%;top:50%;text-align:center;margin:-2rem 0 0 -2rem}.newLoad{display:inline-block;width:2rem;height:2rem}.newLoad-div1,.newLoad-div2,.newLoad-div3{position:absolute;width:1.4rem;height:1.4rem;left:50%;top:50%;margin:-.7rem 0 0 -.7rem}.newLoad-circle1,.newLoad-circle2,.newLoad-circle3,.newLoad-circle4{width:.3rem;height:.3rem;background-color:#FFF;border-radius:100%;position:absolute;-webkit-animation:bouncedelay 1.2s infinite ease-in-out;animation:bouncedelay 1.2s infinite ease-in-out;-webkit-animation-fill-mode:both;animation-fill-mode:both}.newLoad-div2{-webkit-transform:rotateZ(45deg);transform:rotateZ(45deg)}.newLoad-div3{-webkit-transform:rotateZ(90deg);transform:rotateZ(90deg)}.newLoad-circle1{top:0;left:0}.newLoad-circle2{top:0;right:0}.newLoad-circle3{right:0;bottom:0}.newLoad-circle4{left:0;bottom:0}.newLoad-div2 .newLoad-circle1{-webkit-animation-delay:-1.1s;animation-delay:-1.1s}.newLoad-div3 .newLoad-circle1{-webkit-animation-delay:-1.0s;animation-delay:-1.0s}.newLoad-div1 .newLoad-circle2{-webkit-animation-delay:-0.9s;animation-delay:-0.9s}.newLoad-div2 .newLoad-circle2{-webkit-animation-delay:-0.8s;animation-delay:-0.8s}.newLoad-div3 .newLoad-circle2{-webkit-animation-delay:-0.7s;animation-delay:-0.7s}.newLoad-div1 .newLoad-circle3{-webkit-animation-delay:-0.6s;animation-delay:-0.6s}.newLoad-div2 .newLoad-circle3{-webkit-animation-delay:-0.5s;animation-delay:-0.5s}.newLoad-div3 .newLoad-circle3{-webkit-animation-delay:-0.4s;animation-delay:-0.4s}.newLoad-div1 .newLoad-circle4{-webkit-animation-delay:-0.3s;animation-delay:-0.3s}.newLoad-div2 .newLoad-circle4{-webkit-animation-delay:-0.2s;animation-delay:-0.2s}.newLoad-div3 .newLoad-circle4{-webkit-animation-delay:-0.1s;animation-delay:-0.1s}@-webkit-keyframes bouncedelay{0%,80%,100%{-webkit-transform:scale(0.0)}40%{-webkit-transform:scale(1.0)}}@keyframes bouncedelay{0%,80%,100%{transform:scale(0.0);-webkit-transform:scale(0.0)}40%{transform:scale(1.0);-webkit-transform:scale(1.0)}}";
    var html = "<div class='newLoad-wrap'><div class='newLoad'><div class='newLoad-div1'><div class='newLoad-circle1'></div><div class='newLoad-circle2'></div><div class='newLoad-circle3'></div><div class='newLoad-circle4'></div></div><div class='newLoad-div2'><div class='newLoad-circle1'></div><div class='newLoad-circle2'></div><div class='newLoad-circle3'></div><div class='newLoad-circle4'></div></div><div class='newLoad-div3'><div class='newLoad-circle1'></div><div class='newLoad-circle2'></div><div class='newLoad-circle3'></div><div class='newLoad-circle4'></div></div></div></div>";
    if($("#thisMyLoadStyle").length==0){$("head").append("<style type='text/css'>"+css+"</style>")}
    $("#thisMyMttc").remove();
    $("body").append("<div class='newLoad-mttc' id='thisMyMttc'>"+html+"</div>");
};
var closeMttc = function(){$("#thisMyMttc").remove()};

var getBMapPoint = (function(){
    var lng,lat,zIndex,btnColor,successFunc,isOk,isGPS = false,isGPSOver = false;
    var doc,body,id,wrap,btnOk,btnCancel,
        w = document.documentElement.clientWidth,h = document.documentElement.clientHeight;
    var map,center,thisMarker,thisFixed,thisLng = "",thisLat = "",type = "normal";
    var closeWrap = function(){
        try{
            body.removeChild(wrap);
            body.removeChild(btnOk);
            body.removeChild(btnCancel);
            body.removeChild(thisFixed);
        }catch(e){}
    };
    var success = function(_lng,_lat){thisLng = _lng;thisLat = _lat};
    var thisSuccess = function(){
        closeWrap();
        if(type == "fixed"){        //marker中心固定：fixed；
            var _p = map.getCenter();
            successFunc(_p.lng,_p.lat);
        }
        else{
            successFunc(thisLng,thisLat);
        }
    };
    var createHTML = function(){
        baseTools.createStyle("BMapWrapStyle","#"+id+" img{ max-width:none;}");
        wrap = doc.createElement("div");
        btnOk = doc.createElement("div");
        btnCancel = doc.createElement("div");
        wrap.id = id;
        wrap.setAttribute("style","position:absolute;left:0;top:0;z-index:"+zIndex+";width:"+w+"px;height:"+h+"px;background-color:#FFF;");
        btnOk.setAttribute("style","position:absolute;left:10%;bottom:30px;z-index:"+zIndex+";width:37%; height:36px;line-height:36px;text-align:center;font-size:16px;color:#FFF;border-radius:5px;background-color:"+btnColor+";");
        btnCancel.setAttribute("style","position:absolute;right:10%;bottom:30px;z-index:"+zIndex+";width:37%; height:36px;line-height:36px;text-align:center;font-size:16px;color:#FFF;border-radius:5px;background-color:"+btnColor+";");
        btnOk.innerHTML = "确<span style=\"padding:0 .5em;\"></span>定";
        btnCancel.innerHTML = "关<span style=\"padding:0 .5em;\"></span>闭";
        btnOk.addEventListener("click",thisSuccess,false);
        btnCancel.addEventListener("click",closeWrap,false);
        doc.documentElement.style.position = body.style.position = "relative";
        body.appendChild(wrap);
        body.appendChild(btnOk);
        body.appendChild(btnCancel);
        if("lcp_downCreate" in window){
            btnOk.className = btnCancel.className = "lcp_down";
            window["lcp_downCreate"]();
        }
        if(!isOk){
            if("console" in window) window["console"].error("缺少百度api，请导入http://api.map.baidu.com/api?ak=nfPeVUXFF4FUsex4gVfrIG5b&v=2.0");
        }
        else mapStart();
    };
    var initMarker = function(_lng,_lat){
        map.clearOverlays();
        thisMarker = new BMap.Marker(new BMap.Point(_lng,_lat));
        thisMarker.setTitle("当前标注点");
        thisMarker.enableDragging();
        thisMarker.addEventListener("dragend", function(e){
            thisMarker.setPosition(new BMap.Point(e.point.lng, e.point.lat));
            success(e.point.lng,e.point.lat);
        });
        map.addOverlay(thisMarker);
    };
    var setMarker = function(_lng,_lat){
        if(!thisMarker){
            initMarker(_lng,_lat);
        }
        else{
            thisMarker.setPosition(new BMap.Point(_lng,_lat));
        }
        success(_lng,_lat);
    };
    var mapStart = function(){
        map = new BMap.Map(id,{enableMapClick:true});
        map.addControl(new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT}));
        if(lng!="" && lat!=""){
            center = new BMap.Point(lng, lat);
        }
        else{
            center = new BMap.Point(114.941691, 25.837959);
        }
        map.centerAndZoom(center, 13);

        if(type == "fixed"){        //marker中心固定：fixed；
            var _l = (w-19)/2,_t = h/2-25;
            thisFixed = doc.createElement("div");
            thisFixed.setAttribute("style","position: absolute;left:"+_l+"px; top:"+_t+"px;z-index:"+zIndex+"; width: 19px; height: 25px;");
            thisFixed.innerHTML = "<div style='position: absolute; margin: 0; padding: 0; width: 19px; height: 25px; overflow: hidden;'>"+
            "<img src='http://api0.map.bdimg.com/images/marker_red_sprite.png' style='display: block;max-width:none; border:none;margin-left:0; margin-top:0;'></div>";
            body.appendChild(thisFixed);
        }
        else{                       //marker点击：normal；
            if(lng!="" && lat!=""){
                success(lng,lat);
                initMarker(lng,lat);
            }
            else thisMarker = undefined;
            map.addEventListener("click", function(e){
                var point = e.point,_lng = point.lng,_lat = point.lat;
                setMarker(_lng,_lat);
            });
            if(isGPS){  //如果开启了自动定位
                baseTools.getGPS(function(_lng,_lat){
                    if(!thisMarker || (thisMarker && isGPSOver)){    //如果没有标注  或   已标注但强制定位覆盖
                        setMarker(_lng,_lat);
                        map.centerAndZoom(new BMap.Point(_lng,_lat), map.getZoom());
                    }
                },function(){});
            }
        }
    };
    return function(option){
        closeWrap();
        type = option.type?option.type:"normal";
        lng = option.lng;lat = option.lat;zIndex = option.zIndex;btnColor = option.btnColor;successFunc = option.success;isOk = "BMap" in window;isGPS = option.GPS?true:false;isGPSOver = option.GPSOver?true:false;
        doc=document;body = doc.body;id = "BMapWrap"+(+new Date());thisLng = "";thisLat = "";
        createHTML();
    };
})();

//图片滚动加载
var imgSetLoading = (function(){
    return function(id){
        var getId = baseTools.getId,getClass = baseTools.getClass,getStyle = baseTools.getStyle,
            getAttr = function(_dom,_name){return _dom.getAttribute(_name)};
        var wrap = getId(id);
        var setC = function(_thisC,_true){
            var _this = $(_true===true?_thisC:this),p = _this.parent(),_ww = p.width(),_hh = p.height(),_w = _this.width(),_h = _this.height();
            _this.css({"left":((_ww-_w)/2)+"px","top":((_hh-_h)/2)+"px"});
            _this.animate({"opacity":1},500);
        };
        var setErr = function(){
            var _this = $(this),_err = _this.attr("data-error");
            if(_err != "null" && _err != ""){
                $(this).css({"width":"100%","height":"100%"}).attr("src",_err).animate({"opacity":1},500);
            }
            else setC(this,true);
        };
        var loading = function(){
            var h = wrap.offsetHeight,t = wrap.getBoundingClientRect().top,t2 = h+t;
            var img = getClass("lcp_photo",wrap),len = img.length;
            var nullNum = 0;
            for(var i=len;i--;){
                var x = img[i],url = getAttr(x,"data-url"),isNull = url === null || url === "";
                if(isNull){
                    nullNum++;
                    continue;
                }
                var type = getAttr(x,"data-type"),
                    rect = x.getBoundingClientRect(),tt = rect.top,ll = rect.left,hh = x.offsetHeight;
                if(tt>=t && tt<t2){
                    x.removeAttribute("data-url");
                    var _css = getStyle(x),_r = _css.borderTopLeftRadius+" "+_css.borderTopRightRadius+" "+_css.borderBottomRightRadius+" "+_css.borderBottomLeftRadius,
                        _img = $("<img src='"+url+"' data-error='"+getAttr(x,"data-error")+"' style='position:absolute;top:0;left:0;max-width:1000%;opacity:0;border-radius:"+_r+";' />");
                    if(type === "all"){
                        _img.attr("width","100%").attr("height","100%");
                        _img.on("load",function(){$(this).animate({"opacity":1},500);});
                    }
                    else{
                        _img.css({"max-height":"100%"});
                        _img.on("load",setC);
                    }
                    _img.on("error",setErr);
                    $(x).append(_img);
                }
                else if(tt == 0 && ll == 0){}           //如果图片是隐藏的则过滤该图片
                else if(tt+hh<t) break;                 //如果图片底部都小于t
            }
        };
        if("addEventListener" in window){
            wrap.addEventListener("scroll",loading,false);
        }
        else{
            wrap.attachEvent("onscroll",loading);
        }
        loading();
        return {loading:loading};
    }
})();