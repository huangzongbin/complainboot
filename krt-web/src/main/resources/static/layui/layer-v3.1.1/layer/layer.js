/*! layer-v3.1.1 Web弹层组件 MIT License  http://layer.layui.com/  By 贤心 */
 //;!function(e,t){"use strict";var i,n,a=e.layui&&layui.define,o={getPath:function(){var e=document.currentScript?document.currentScript.src:function(){for(var e,t=document.scripts,i=t.length-1,n=i;n>0;n--)if("interactive"===t[n].readyState){e=t[n].src;break}return e||t[i].src}();return e.substring(0,e.lastIndexOf("/")+1)}(),config:{},end:{},minIndex:0,minLeft:[],btn:["&#x786E;&#x5B9A;","&#x53D6;&#x6D88;"],type:["dialog","page","iframe","loading","tips"],getStyle:function(t,i){var n=t.currentStyle?t.currentStyle:e.getComputedStyle(t,null);return n[n.getPropertyValue?"getPropertyValue":"getAttribute"](i)},link:function(t,i,n){if(r.path){var a=document.getElementsByTagName("head")[0],s=document.createElement("link");"string"==typeof i&&(n=i);var l=(n||t).replace(/\.|\//g,""),f="layuicss-"+l,c=0;s.rel="stylesheet",s.href=r.path+t,s.id=f,document.getElementById(f)||a.appendChild(s),"function"==typeof i&&!function u(){return++c>80?e.console&&console.error("layer.css: Invalid"):void(1989===parseInt(o.getStyle(document.getElementById(f),"width"))?i():setTimeout(u,100))}()}}},r={v:"3.1.1",ie:function(){var t=navigator.userAgent.toLowerCase();return!!(e.ActiveXObject||"ActiveXObject"in e)&&((t.match(/msie\s(\d+)/)||[])[1]||"11")}(),index:e.layer&&e.layer.v?1e5:0,path:o.getPath,config:function(e,t){return e=e||{},r.cache=o.config=i.extend({},o.config,e),r.path=o.config.path||r.path,"string"==typeof e.extend&&(e.extend=[e.extend]),o.config.path&&r.ready(),e.extend?(a?layui.addcss("modules/layer/"+e.extend):o.link("theme/"+e.extend),this):this},ready:function(e){var t="layer",i="",n=(a?"modules/layer/":"theme/")+"default/layer.css?v="+r.v+i;return a?layui.addcss(n,e,t):o.link(n,e,t),this},alert:function(e,t,n){var a="function"==typeof t;return a&&(n=t),r.open(i.extend({content:e,yes:n},a?{}:t))},confirm:function(e,t,n,a){var s="function"==typeof t;return s&&(a=n,n=t),r.open(i.extend({content:e,btn:o.btn,yes:n,btn2:a},s?{}:t))},msg:function(e,n,a){var s="function"==typeof n,f=o.config.skin,c=(f?f+" "+f+"-msg":"")||"layui-layer-msg",u=l.anim.length-1;return s&&(a=n),r.open(i.extend({content:e,time:3e3,shade:!1,skin:c,title:!1,closeBtn:!1,btn:!1,resize:!1,end:a},s&&!o.config.skin?{skin:c+" layui-layer-hui",anim:u}:function(){return n=n||{},(n.icon===-1||n.icon===t&&!o.config.skin)&&(n.skin=c+" "+(n.skin||"layui-layer-hui")),n}()))},load:function(e,t){return r.open(i.extend({type:3,icon:e||0,resize:!1,shade:.01},t))},tips:function(e,t,n){return r.open(i.extend({type:4,content:[e,t],closeBtn:!1,time:3e3,shade:!1,resize:!1,fixed:!1,maxWidth:210},n))}},s=function(e){var t=this;t.index=++r.index,t.config=i.extend({},t.config,o.config,e),document.body?t.creat():setTimeout(function(){t.creat()},30)};s.pt=s.prototype;var l=["layui-layer",".layui-layer-title",".layui-layer-main",".layui-layer-dialog","layui-layer-iframe","layui-layer-content","layui-layer-btn","layui-layer-close"];l.anim=["layer-anim-00","layer-anim-01","layer-anim-02","layer-anim-03","layer-anim-04","layer-anim-05","layer-anim-06"],s.pt.config={type:0,shade:.3,fixed:!0,move:l[1],title:"&#x4FE1;&#x606F;",offset:"auto",area:"auto",closeBtn:1,time:0,zIndex:19891014,maxWidth:360,anim:0,isOutAnim:!0,icon:-1,moveType:1,resize:!0,scrollbar:!0,tips:2},s.pt.vessel=function(e,t){var n=this,a=n.index,r=n.config,s=r.zIndex+a,f="object"==typeof r.title,c=r.maxmin&&(1===r.type||2===r.type),u=r.title?'<div class="layui-layer-title" style="'+(f?r.title[1]:"")+'">'+(f?r.title[0]:r.title)+"</div>":"";return r.zIndex=s,t([r.shade?'<div class="layui-layer-shade" id="layui-layer-shade'+a+'" times="'+a+'" style="'+("z-index:"+(s-1)+"; ")+'"></div>':"",'<div class="'+l[0]+(" layui-layer-"+o.type[r.type])+(0!=r.type&&2!=r.type||r.shade?"":" layui-layer-border")+" "+(r.skin||"")+'" id="'+l[0]+a+'" type="'+o.type[r.type]+'" times="'+a+'" showtime="'+r.time+'" conType="'+(e?"object":"string")+'" style="z-index: '+s+"; width:"+r.area[0]+";height:"+r.area[1]+(r.fixed?"":";position:absolute;")+'">'+(e&&2!=r.type?"":u)+'<div id="'+(r.id||"")+'" class="layui-layer-content'+(0==r.type&&r.icon!==-1?" layui-layer-padding":"")+(3==r.type?" layui-layer-loading"+r.icon:"")+'">'+(0==r.type&&r.icon!==-1?'<i class="layui-layer-ico layui-layer-ico'+r.icon+'"></i>':"")+(1==r.type&&e?"":r.content||"")+'</div><span class="layui-layer-setwin">'+function(){var e=c?'<a class="layui-layer-min" href="javascript:;"><cite></cite></a><a class="layui-layer-ico layui-layer-max" href="javascript:;"></a>':"";return r.closeBtn&&(e+='<a class="layui-layer-ico '+l[7]+" "+l[7]+(r.title?r.closeBtn:4==r.type?"1":"2")+'" href="javascript:;"></a>'),e}()+"</span>"+(r.btn?function(){var e="";"string"==typeof r.btn&&(r.btn=[r.btn]);for(var t=0,i=r.btn.length;t<i;t++)e+='<a class="'+l[6]+t+'">'+r.btn[t]+"</a>";return'<div class="'+l[6]+" layui-layer-btn-"+(r.btnAlign||"")+'">'+e+"</div>"}():"")+(r.resize?'<span class="layui-layer-resize"></span>':"")+"</div>"],u,i('<div class="layui-layer-move"></div>')),n},s.pt.creat=function(){var e=this,t=e.config,a=e.index,s=t.content,f="object"==typeof s,c=i("body");if(!t.id||!i("#"+t.id)[0]){switch("string"==typeof t.area&&(t.area="auto"===t.area?["",""]:[t.area,""]),t.shift&&(t.anim=t.shift),6==r.ie&&(t.fixed=!1),t.type){case 0:t.btn="btn"in t?t.btn:o.btn[0],r.closeAll("dialog");break;case 2:var s=t.content=f?t.content:[t.content||"http://layer.layui.com","auto"];t.content='<iframe scrolling="'+(t.content[1]||"auto")+'" allowtransparency="true" id="'+l[4]+a+'" name="'+l[4]+a+'" onload="this.className=\'\';" class="layui-layer-load" frameborder="0" src="'+t.content[0]+'"></iframe>';break;case 3:delete t.title,delete t.closeBtn,t.icon===-1&&0===t.icon,r.closeAll("loading");break;case 4:f||(t.content=[t.content,"body"]),t.follow=t.content[1],t.content=t.content[0]+'<i class="layui-layer-TipsG"></i>',delete t.title,t.tips="object"==typeof t.tips?t.tips:[t.tips,!0],t.tipsMore||r.closeAll("tips")}if(e.vessel(f,function(n,r,u){c.append(n[0]),f?function(){2==t.type||4==t.type?function(){i("body").append(n[1])}():function(){s.parents("."+l[0])[0]||(s.data("display",s.css("display")).show().addClass("layui-layer-wrap").wrap(n[1]),i("#"+l[0]+a).find("."+l[5]).before(r))}()}():c.append(n[1]),i(".layui-layer-move")[0]||c.append(o.moveElem=u),e.layero=i("#"+l[0]+a),t.scrollbar||l.html.css("overflow","hidden").attr("layer-full",a)}).auto(a),i("#layui-layer-shade"+e.index).css({"background-color":t.shade[1]||"#000",opacity:t.shade[0]||t.shade}),2==t.type&&6==r.ie&&e.layero.find("iframe").attr("src",s[0]),4==t.type?e.tips():e.offset(),t.fixed&&n.on("resize",function(){e.offset(),(/^\d+%$/.test(t.area[0])||/^\d+%$/.test(t.area[1]))&&e.auto(a),4==t.type&&e.tips()}),t.time<=0||setTimeout(function(){r.close(e.index)},t.time),e.move().callback(),l.anim[t.anim]){var u="layer-anim "+l.anim[t.anim];e.layero.addClass(u).one("webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend",function(){i(this).removeClass(u)})}t.isOutAnim&&e.layero.data("isOutAnim",!0)}},s.pt.auto=function(e){var t=this,a=t.config,o=i("#"+l[0]+e);""===a.area[0]&&a.maxWidth>0&&(r.ie&&r.ie<8&&a.btn&&o.width(o.innerWidth()),o.outerWidth()>a.maxWidth&&o.width(a.maxWidth));var s=[o.innerWidth(),o.innerHeight()],f=o.find(l[1]).outerHeight()||0,c=o.find("."+l[6]).outerHeight()||0,u=function(e){e=o.find(e),e.height(s[1]-f-c-2*(0|parseFloat(e.css("padding-top"))))};switch(a.type){case 2:u("iframe");break;default:""===a.area[1]?a.maxHeight>0&&o.outerHeight()>a.maxHeight?(s[1]=a.maxHeight,u("."+l[5])):a.fixed&&s[1]>=n.height()&&(s[1]=n.height(),u("."+l[5])):u("."+l[5])}return t},s.pt.offset=function(){var e=this,t=e.config,i=e.layero,a=[i.outerWidth(),i.outerHeight()],o="object"==typeof t.offset;e.offsetTop=(n.height()-a[1])/2,e.offsetLeft=(n.width()-a[0])/2,o?(e.offsetTop=t.offset[0],e.offsetLeft=t.offset[1]||e.offsetLeft):"auto"!==t.offset&&("t"===t.offset?e.offsetTop=0:"r"===t.offset?e.offsetLeft=n.width()-a[0]:"b"===t.offset?e.offsetTop=n.height()-a[1]:"l"===t.offset?e.offsetLeft=0:"lt"===t.offset?(e.offsetTop=0,e.offsetLeft=0):"lb"===t.offset?(e.offsetTop=n.height()-a[1],e.offsetLeft=0):"rt"===t.offset?(e.offsetTop=0,e.offsetLeft=n.width()-a[0]):"rb"===t.offset?(e.offsetTop=n.height()-a[1],e.offsetLeft=n.width()-a[0]):e.offsetTop=t.offset),t.fixed||(e.offsetTop=/%$/.test(e.offsetTop)?n.height()*parseFloat(e.offsetTop)/100:parseFloat(e.offsetTop),e.offsetLeft=/%$/.test(e.offsetLeft)?n.width()*parseFloat(e.offsetLeft)/100:parseFloat(e.offsetLeft),e.offsetTop+=n.scrollTop(),e.offsetLeft+=n.scrollLeft()),i.attr("minLeft")&&(e.offsetTop=n.height()-(i.find(l[1]).outerHeight()||0),e.offsetLeft=i.css("left")),i.css({top:e.offsetTop,left:e.offsetLeft})},s.pt.tips=function(){var e=this,t=e.config,a=e.layero,o=[a.outerWidth(),a.outerHeight()],r=i(t.follow);r[0]||(r=i("body"));var s={width:r.outerWidth(),height:r.outerHeight(),top:r.offset().top,left:r.offset().left},f=a.find(".layui-layer-TipsG"),c=t.tips[0];t.tips[1]||f.remove(),s.autoLeft=function(){s.left+o[0]-n.width()>0?(s.tipLeft=s.left+s.width-o[0],f.css({right:12,left:"auto"})):s.tipLeft=s.left},s.where=[function(){s.autoLeft(),s.tipTop=s.top-o[1]-10,f.removeClass("layui-layer-TipsB").addClass("layui-layer-TipsT").css("border-right-color",t.tips[1])},function(){s.tipLeft=s.left+s.width+10,s.tipTop=s.top,f.removeClass("layui-layer-TipsL").addClass("layui-layer-TipsR").css("border-bottom-color",t.tips[1])},function(){s.autoLeft(),s.tipTop=s.top+s.height+10,f.removeClass("layui-layer-TipsT").addClass("layui-layer-TipsB").css("border-right-color",t.tips[1])},function(){s.tipLeft=s.left-o[0]-10,s.tipTop=s.top,f.removeClass("layui-layer-TipsR").addClass("layui-layer-TipsL").css("border-bottom-color",t.tips[1])}],s.where[c-1](),1===c?s.top-(n.scrollTop()+o[1]+16)<0&&s.where[2]():2===c?n.width()-(s.left+s.width+o[0]+16)>0||s.where[3]():3===c?s.top-n.scrollTop()+s.height+o[1]+16-n.height()>0&&s.where[0]():4===c&&o[0]+16-s.left>0&&s.where[1](),a.find("."+l[5]).css({"background-color":t.tips[1],"padding-right":t.closeBtn?"30px":""}),a.css({left:s.tipLeft-(t.fixed?n.scrollLeft():0),top:s.tipTop-(t.fixed?n.scrollTop():0)})},s.pt.move=function(){var e=this,t=e.config,a=i(document),s=e.layero,l=s.find(t.move),f=s.find(".layui-layer-resize"),c={};return t.move&&l.css("cursor","move"),l.on("mousedown",function(e){e.preventDefault(),t.move&&(c.moveStart=!0,c.offset=[e.clientX-parseFloat(s.css("left")),e.clientY-parseFloat(s.css("top"))],o.moveElem.css("cursor","move").show())}),f.on("mousedown",function(e){e.preventDefault(),c.resizeStart=!0,c.offset=[e.clientX,e.clientY],c.area=[s.outerWidth(),s.outerHeight()],o.moveElem.css("cursor","se-resize").show()}),a.on("mousemove",function(i){if(c.moveStart){var a=i.clientX-c.offset[0],o=i.clientY-c.offset[1],l="fixed"===s.css("position");if(i.preventDefault(),c.stX=l?0:n.scrollLeft(),c.stY=l?0:n.scrollTop(),!t.moveOut){var f=n.width()-s.outerWidth()+c.stX,u=n.height()-s.outerHeight()+c.stY;a<c.stX&&(a=c.stX),a>f&&(a=f),o<c.stY&&(o=c.stY),o>u&&(o=u)}s.css({left:a,top:o})}if(t.resize&&c.resizeStart){var a=i.clientX-c.offset[0],o=i.clientY-c.offset[1];i.preventDefault(),r.style(e.index,{width:c.area[0]+a,height:c.area[1]+o}),c.isResize=!0,t.resizing&&t.resizing(s)}}).on("mouseup",function(e){c.moveStart&&(delete c.moveStart,o.moveElem.hide(),t.moveEnd&&t.moveEnd(s)),c.resizeStart&&(delete c.resizeStart,o.moveElem.hide())}),e},s.pt.callback=function(){function e(){var e=a.cancel&&a.cancel(t.index,n);e===!1||r.close(t.index)}var t=this,n=t.layero,a=t.config;t.openLayer(),a.success&&(2==a.type?n.find("iframe").on("load",function(){a.success(n,t.index)}):a.success(n,t.index)),6==r.ie&&t.IE6(n),n.find("."+l[6]).children("a").on("click",function(){var e=i(this).index();if(0===e)a.yes?a.yes(t.index,n):a.btn1?a.btn1(t.index,n):r.close(t.index);else{var o=a["btn"+(e+1)]&&a["btn"+(e+1)](t.index,n);o===!1||r.close(t.index)}}),n.find("."+l[7]).on("click",e),a.shadeClose&&i("#layui-layer-shade"+t.index).on("click",function(){r.close(t.index)}),n.find(".layui-layer-min").on("click",function(){var e=a.min&&a.min(n);e===!1||r.min(t.index,a)}),n.find(".layui-layer-max").on("click",function(){i(this).hasClass("layui-layer-maxmin")?(r.restore(t.index),a.restore&&a.restore(n)):(r.full(t.index,a),setTimeout(function(){a.full&&a.full(n)},100))}),a.end&&(o.end[t.index]=a.end)},o.reselect=function(){i.each(i("select"),function(e,t){var n=i(this);n.parents("."+l[0])[0]||1==n.attr("layer")&&i("."+l[0]).length<1&&n.removeAttr("layer").show(),n=null})},s.pt.IE6=function(e){i("select").each(function(e,t){var n=i(this);n.parents("."+l[0])[0]||"none"===n.css("display")||n.attr({layer:"1"}).hide(),n=null})},s.pt.openLayer=function(){var e=this;r.zIndex=e.config.zIndex,r.setTop=function(e){var t=function(){r.zIndex++,e.css("z-index",r.zIndex+1)};return r.zIndex=parseInt(e[0].style.zIndex),e.on("mousedown",t),r.zIndex}},o.record=function(e){var t=[e.width(),e.height(),e.position().top,e.position().left+parseFloat(e.css("margin-left"))];e.find(".layui-layer-max").addClass("layui-layer-maxmin"),e.attr({area:t})},o.rescollbar=function(e){l.html.attr("layer-full")==e&&(l.html[0].style.removeProperty?l.html[0].style.removeProperty("overflow"):l.html[0].style.removeAttribute("overflow"),l.html.removeAttr("layer-full"))},e.layer=r,r.getChildFrame=function(e,t){return t=t||i("."+l[4]).attr("times"),i("#"+l[0]+t).find("iframe").contents().find(e)},r.getFrameIndex=function(e){return i("#"+e).parents("."+l[4]).attr("times")},r.iframeAuto=function(e){if(e){var t=r.getChildFrame("html",e).outerHeight(),n=i("#"+l[0]+e),a=n.find(l[1]).outerHeight()||0,o=n.find("."+l[6]).outerHeight()||0;n.css({height:t+a+o}),n.find("iframe").css({height:t})}},r.iframeSrc=function(e,t){i("#"+l[0]+e).find("iframe").attr("src",t)},r.style=function(e,t,n){var a=i("#"+l[0]+e),r=a.find(".layui-layer-content"),s=a.attr("type"),f=a.find(l[1]).outerHeight()||0,c=a.find("."+l[6]).outerHeight()||0;a.attr("minLeft");s!==o.type[3]&&s!==o.type[4]&&(n||(parseFloat(t.width)<=260&&(t.width=260),parseFloat(t.height)-f-c<=64&&(t.height=64+f+c)),a.css(t),c=a.find("."+l[6]).outerHeight(),s===o.type[2]?a.find("iframe").css({height:parseFloat(t.height)-f-c}):r.css({height:parseFloat(t.height)-f-c-parseFloat(r.css("padding-top"))-parseFloat(r.css("padding-bottom"))}))},r.min=function(e,t){var a=i("#"+l[0]+e),s=a.find(l[1]).outerHeight()||0,f=a.attr("minLeft")||181*o.minIndex+"px",c=a.css("position");o.record(a),o.minLeft[0]&&(f=o.minLeft[0],o.minLeft.shift()),a.attr("position",c),r.style(e,{width:180,height:s,left:f,top:n.height()-s,position:"fixed",overflow:"hidden"},!0),a.find(".layui-layer-min").hide(),"page"===a.attr("type")&&a.find(l[4]).hide(),o.rescollbar(e),a.attr("minLeft")||o.minIndex++,a.attr("minLeft",f)},r.restore=function(e){var t=i("#"+l[0]+e),n=t.attr("area").split(",");t.attr("type");r.style(e,{width:parseFloat(n[0]),height:parseFloat(n[1]),top:parseFloat(n[2]),left:parseFloat(n[3]),position:t.attr("position"),overflow:"visible"},!0),t.find(".layui-layer-max").removeClass("layui-layer-maxmin"),t.find(".layui-layer-min").show(),"page"===t.attr("type")&&t.find(l[4]).show(),o.rescollbar(e)},r.full=function(e){var t,a=i("#"+l[0]+e);o.record(a),l.html.attr("layer-full")||l.html.css("overflow","hidden").attr("layer-full",e),clearTimeout(t),t=setTimeout(function(){var t="fixed"===a.css("position");r.style(e,{top:t?0:n.scrollTop(),left:t?0:n.scrollLeft(),width:n.width(),height:n.height()},!0),a.find(".layui-layer-min").hide()},100)},r.title=function(e,t){var n=i("#"+l[0]+(t||r.index)).find(l[1]);n.html(e)},r.close=function(e){var t=i("#"+l[0]+e),n=t.attr("type"),a="layer-anim-close";if(t[0]){var s="layui-layer-wrap",f=function(){if(n===o.type[1]&&"object"===t.attr("conType")){t.children(":not(."+l[5]+")").remove();for(var a=t.find("."+s),r=0;r<2;r++)a.unwrap();a.css("display",a.data("display")).removeClass(s)}else{if(n===o.type[2])try{var f=i("#"+l[4]+e)[0];f.contentWindow.document.write(""),f.contentWindow.close(),t.find("."+l[5])[0].removeChild(f)}catch(c){}t[0].innerHTML="",t.remove()}"function"==typeof o.end[e]&&o.end[e](),delete o.end[e]};t.data("isOutAnim")&&t.addClass("layer-anim "+a),i("#layui-layer-moves, #layui-layer-shade"+e).remove(),6==r.ie&&o.reselect(),o.rescollbar(e),t.attr("minLeft")&&(o.minIndex--,o.minLeft.push(t.attr("minLeft"))),r.ie&&r.ie<10||!t.data("isOutAnim")?f():setTimeout(function(){f()},200)}},r.closeAll=function(e){i.each(i("."+l[0]),function(){var t=i(this),n=e?t.attr("type")===e:1;n&&r.close(t.attr("times")),n=null})};var f=r.cache||{},c=function(e){return f.skin?" "+f.skin+" "+f.skin+"-"+e:""};r.prompt=function(e,t){var a="";if(e=e||{},"function"==typeof e&&(t=e),e.area){var o=e.area;a='style="width: '+o[0]+"; height: "+o[1]+';"',delete e.area}var s,l=2==e.formType?'<textarea class="layui-layer-input"'+a+">"+(e.value||"")+"</textarea>":function(){return'<input type="'+(1==e.formType?"password":"text")+'" class="layui-layer-input" value="'+(e.value||"")+'">'}(),f=e.success;return delete e.success,r.open(i.extend({type:1,btn:["&#x786E;&#x5B9A;","&#x53D6;&#x6D88;"],content:l,skin:"layui-layer-prompt"+c("prompt"),maxWidth:n.width(),success:function(e){s=e.find(".layui-layer-input"),s.focus(),"function"==typeof f&&f(e)},resize:!1,yes:function(i){var n=s.val();""===n?s.focus():n.length>(e.maxlength||500)?r.tips("&#x6700;&#x591A;&#x8F93;&#x5165;"+(e.maxlength||500)+"&#x4E2A;&#x5B57;&#x6570;",s,{tips:1}):t&&t(n,i,s)}},e))},r.tab=function(e){e=e||{};var t=e.tab||{},n="layui-this",a=e.success;return delete e.success,r.open(i.extend({type:1,skin:"layui-layer-tab"+c("tab"),resize:!1,title:function(){var e=t.length,i=1,a="";if(e>0)for(a='<span class="'+n+'">'+t[0].title+"</span>";i<e;i++)a+="<span>"+t[i].title+"</span>";return a}(),content:'<ul class="layui-layer-tabmain">'+function(){var e=t.length,i=1,a="";if(e>0)for(a='<li class="layui-layer-tabli '+n+'">'+(t[0].content||"no content")+"</li>";i<e;i++)a+='<li class="layui-layer-tabli">'+(t[i].content||"no  content")+"</li>";return a}()+"</ul>",success:function(t){var o=t.find(".layui-layer-title").children(),r=t.find(".layui-layer-tabmain").children();o.on("mousedown",function(t){t.stopPropagation?t.stopPropagation():t.cancelBubble=!0;var a=i(this),o=a.index();a.addClass(n).siblings().removeClass(n),r.eq(o).show().siblings().hide(),"function"==typeof e.change&&e.change(o)}),"function"==typeof a&&a(t)}},e))},r.photos=function(t,n,a){function o(e,t,i){var n=new Image;return n.src=e,n.complete?t(n):(n.onload=function(){n.onload=null,t(n)},void(n.onerror=function(e){n.onerror=null,i(e)}))}var s={};if(t=t||{},t.photos){var l=t.photos.constructor===Object,f=l?t.photos:{},u=f.data||[],d=f.start||0;s.imgIndex=(0|d)+1,t.img=t.img||"img";var y=t.success;if(delete t.success,l){if(0===u.length)return r.msg("&#x6CA1;&#x6709;&#x56FE;&#x7247;")}else{var p=i(t.photos),h=function(){u=[],p.find(t.img).each(function(e){var t=i(this);t.attr("layer-index",e),u.push({alt:t.attr("alt"),pid:t.attr("layer-pid"),src:t.attr("layer-src")||t.attr("src"),thumb:t.attr("src")})})};if(h(),0===u.length)return;if(n||p.on("click",t.img,function(){var e=i(this),n=e.attr("layer-index");r.photos(i.extend(t,{photos:{start:n,data:u,tab:t.tab},full:t.full}),!0),h()}),!n)return}s.imgprev=function(e){s.imgIndex--,s.imgIndex<1&&(s.imgIndex=u.length),s.tabimg(e)},s.imgnext=function(e,t){s.imgIndex++,s.imgIndex>u.length&&(s.imgIndex=1,t)||s.tabimg(e)},s.keyup=function(e){if(!s.end){var t=e.keyCode;e.preventDefault(),37===t?s.imgprev(!0):39===t?s.imgnext(!0):27===t&&r.close(s.index)}},s.tabimg=function(e){if(!(u.length<=1))return f.start=s.imgIndex-1,r.close(s.index),r.photos(t,!0,e)},s.event=function(){s.bigimg.hover(function(){s.imgsee.show()},function(){s.imgsee.hide()}),s.bigimg.find(".layui-layer-imgprev").on("click",function(e){e.preventDefault(),s.imgprev()}),s.bigimg.find(".layui-layer-imgnext").on("click",function(e){e.preventDefault(),s.imgnext()}),i(document).on("keyup",s.keyup)},s.loadi=r.load(1,{shade:!("shade"in t)&&.9,scrollbar:!1}),o(u[d].src,function(n){r.close(s.loadi),s.index=r.open(i.extend({type:1,id:"layui-layer-photos",area:function(){var a=[n.width,n.height],o=[i(e).width()-100,i(e).height()-100];if(!t.full&&(a[0]>o[0]||a[1]>o[1])){var r=[a[0]/o[0],a[1]/o[1]];r[0]>r[1]?(a[0]=a[0]/r[0],a[1]=a[1]/r[0]):r[0]<r[1]&&(a[0]=a[0]/r[1],a[1]=a[1]/r[1])}return[a[0]+"px",a[1]+"px"]}(),title:!1,shade:.9,shadeClose:!0,closeBtn:!1,move:".layui-layer-phimg img",moveType:1,scrollbar:!1,moveOut:!0,isOutAnim:!1,skin:"layui-layer-photos"+c("photos"),content:'<div class="layui-layer-phimg"><img src="'+u[d].src+'" alt="'+(u[d].alt||"")+'" layer-pid="'+u[d].pid+'"><div class="layui-layer-imgsee">'+(u.length>1?'<span class="layui-layer-imguide"><a href="javascript:;" class="layui-layer-iconext layui-layer-imgprev"></a><a href="javascript:;" class="layui-layer-iconext layui-layer-imgnext"></a></span>':"")+'<div class="layui-layer-imgbar" style="display:'+(a?"block":"")+'"><span class="layui-layer-imgtit"><a href="javascript:;">'+(u[d].alt||"")+"</a><em>"+s.imgIndex+"/"+u.length+"</em></span></div></div></div>",success:function(e,i){s.bigimg=e.find(".layui-layer-phimg"),s.imgsee=e.find(".layui-layer-imguide,.layui-layer-imgbar"),s.event(e),t.tab&&t.tab(u[d],e),"function"==typeof y&&y(e)},end:function(){s.end=!0,i(document).off("keyup",s.keyup)}},t))},function(){r.close(s.loadi),r.msg("&#x5F53;&#x524D;&#x56FE;&#x7247;&#x5730;&#x5740;&#x5F02;&#x5E38;<br>&#x662F;&#x5426;&#x7EE7;&#x7EED;&#x67E5;&#x770B;&#x4E0B;&#x4E00;&#x5F20;&#xFF1F;",{time:3e4,btn:["&#x4E0B;&#x4E00;&#x5F20;","&#x4E0D;&#x770B;&#x4E86;"],yes:function(){u.length>1&&s.imgnext(!0,!0)}})})}},o.run=function(t){i=t,n=i(e),l.html=i("html"),r.open=function(e){var t=new s(e);return t.index}},e.layui&&layui.define?(r.ready(),layui.define("jquery",function(t){r.path=layui.cache.dir,o.run(layui.$),e.layer=r,t("layer",r)})):"function"==typeof define&&define.amd?define(["jquery"],function(){return o.run(e.jQuery),r}):function(){o.run(e.jQuery),r.ready()}()}(window);
/**
 @Name：layer v3.1.2 Web弹层组件
 @Author：贤心
 @Site：http://layer.layui.com
 @License：MIT

 */
;!function(window, undefined){
    "use strict";

    var isLayui = window.layui && layui.define, $, win, ready = {
        getPath: function(){
            var jsPath = document.currentScript ? document.currentScript.src : function(){
                var js = document.scripts
                    ,last = js.length - 1
                    ,src;
                for(var i = last; i > 0; i--){
                    if(js[i].readyState === 'interactive'){
                        src = js[i].src;
                        break;
                    }
                }
                return src || js[last].src;
            }();
            return jsPath.substring(0, jsPath.lastIndexOf('/') + 1);
        }(),

        config: {}, end: {}, minIndex: 0, minLeft: [],
        btn: ['&#x786E;&#x5B9A;', '&#x53D6;&#x6D88;'],

        //五种原始层模式
        type: ['dialog', 'page', 'iframe', 'loading', 'tips'],

        //获取节点的style属性值
        getStyle: function(node, name){
            var style = node.currentStyle ? node.currentStyle : window.getComputedStyle(node, null);
            return style[style.getPropertyValue ? 'getPropertyValue' : 'getAttribute'](name);
        },

        //载入CSS配件
        link: function(href, fn, cssname){

            //未设置路径，则不主动加载css
            if(!layer.path) return;

            var head = document.getElementsByTagName("head")[0], link = document.createElement('link');
            if(typeof fn === 'string') cssname = fn;
            var app = (cssname || href).replace(/\.|\//g, '');
            var id = 'layuicss-'+ app, timeout = 0;

            link.rel = 'stylesheet';
            link.href = layer.path + href;
            link.id = id;

            if(!document.getElementById(id)){
                head.appendChild(link);
            }

            if(typeof fn !== 'function') return;

            //轮询css是否加载完毕
            (function poll() {
                if(++timeout > 8 * 1000 / 100){
                    return window.console && console.error('layer.css: Invalid');
                };
                parseInt(ready.getStyle(document.getElementById(id), 'width')) === 1989 ? fn() : setTimeout(poll, 100);
            }());
        }
    };

//默认内置方法。
    var layer = {
        v: '3.1.1',
        ie: function(){ //ie版本
            var agent = navigator.userAgent.toLowerCase();
            return (!!window.ActiveXObject || "ActiveXObject" in window) ? (
            (agent.match(/msie\s(\d+)/) || [])[1] || '11' //由于ie11并没有msie的标识
            ) : false;
        }(),
        index: (window.layer && window.layer.v) ? 100000 : 0,
        path: ready.getPath,
        config: function(options, fn){
            options = options || {};
            layer.cache = ready.config = $.extend({}, ready.config, options);
            layer.path = ready.config.path || layer.path;
            typeof options.extend === 'string' && (options.extend = [options.extend]);

            if(ready.config.path) layer.ready();

            if(!options.extend) return this;

            isLayui
                ? layui.addcss('modules/layer/' + options.extend)
                : ready.link('theme/' + options.extend);

            return this;
        },

        //主体CSS等待事件
        ready: function(callback){
            var cssname = 'layer', ver = ''
                ,path = (isLayui ? 'modules/layer/' : 'theme/') + 'default/layer.css?v='+ layer.v + ver;
            isLayui ? layui.addcss(path, callback, cssname) : ready.link(path, callback, cssname);
            return this;
        },

        //各种快捷引用
        alert: function(content, options, yes){
            var type = typeof options === 'function';
            if(type) yes = options;
            return layer.open($.extend({
                content: content,
                yes: yes
            }, type ? {} : options));
        },

        confirm: function(content, options, yes, cancel){
            var type = typeof options === 'function';
            if(type){
                cancel = yes;
                yes = options;
            }
            return layer.open($.extend({
                content: content,
                btn: ready.btn,
                yes: yes,
                btn2: cancel
            }, type ? {} : options));
        },

        msg: function(content, options, end){ //最常用提示层
            var type = typeof options === 'function', rskin = ready.config.skin;
            var skin = (rskin ? rskin + ' ' + rskin + '-msg' : '')||'layui-layer-msg';
            var anim = doms.anim.length - 1;
            if(type) end = options;
            return layer.open($.extend({
                content: content,
                time: 3000,
                shade: false,
                skin: skin,
                title: false,
                closeBtn: false,
                btn: false,
                resize: false,
                end: end
            }, (type && !ready.config.skin) ? {
                skin: skin + ' layui-layer-hui',
                anim: anim
            } : function(){
                options = options || {};
                if(options.icon === -1 || options.icon === undefined && !ready.config.skin){
                    options.skin = skin + ' ' + (options.skin||'layui-layer-hui');
                }
                return options;
            }()));
        },

        load: function(icon, options){
            return layer.open($.extend({
                type: 3,
                icon: icon || 0,
                resize: false,
                shade: 0.01
            }, options));
        },

        tips: function(content, follow, options){
            return layer.open($.extend({
                type: 4,
                content: [content, follow],
                closeBtn: false,
                time: 3000,
                shade: false,
                resize: false,
                fixed: false,
                maxWidth: 210
            }, options));
        }
    };

    var Class = function(setings){
        var that = this;
        that.index = ++layer.index;
        that.config = $.extend({}, that.config, ready.config, setings);
        document.body ? that.creat() : setTimeout(function(){
            that.creat();
        }, 30);
    };

    Class.pt = Class.prototype;

//缓存常用字符
    var doms = ['layui-layer', '.layui-layer-title', '.layui-layer-main', '.layui-layer-dialog', 'layui-layer-iframe', 'layui-layer-content', 'layui-layer-btn', 'layui-layer-close'];
    doms.anim = ['layer-anim-00', 'layer-anim-01', 'layer-anim-02', 'layer-anim-03', 'layer-anim-04', 'layer-anim-05', 'layer-anim-06'];

//默认配置
    Class.pt.config = {
        type: 0,
        shade: 0.3,
        fixed: true,
        move: doms[1],
        title: '&#x4FE1;&#x606F;',
        offset: 'auto',
        area: 'auto',
        closeBtn: 1,
        time: 0, //0表示不自动关闭
        zIndex: 19891014,
        maxWidth: 360,
        anim: 0,
        isOutAnim: true,
        icon: -1,
        moveType: 1,
        resize: true,
        scrollbar: true, //是否允许浏览器滚动条
        tips: 2
    };

//容器
    Class.pt.vessel = function(conType, callback){
        var that = this, times = that.index, config = that.config;
        var zIndex = config.zIndex + times, titype = typeof config.title === 'object';
        var ismax = config.maxmin && (config.type === 1 || config.type === 2);
        var titleHTML = (config.title ? '<div class="layui-layer-title" style="'+ (titype ? config.title[1] : '') +'">'
        + (titype ? config.title[0] : config.title)
        + '</div>' : '');

        config.zIndex = zIndex;
        callback([
            //遮罩
            config.shade ? ('<div class="layui-layer-shade" id="layui-layer-shade'+ times +'" times="'+ times +'" style="'+ ('z-index:'+ (zIndex-1) +'; ') +'"></div>') : '',

            //主体
            '<div class="'+ doms[0] + (' layui-layer-'+ready.type[config.type]) + (((config.type == 0 || config.type == 2) && !config.shade) ? ' layui-layer-border' : '') + ' ' + (config.skin||'') +'" id="'+ doms[0] + times +'" type="'+ ready.type[config.type] +'" times="'+ times +'" showtime="'+ config.time +'" conType="'+ (conType ? 'object' : 'string') +'" style="z-index: '+ zIndex +'; width:'+ config.area[0] + ';height:' + config.area[1] + (config.fixed ? '' : ';position:absolute;') +'">'
            + (conType && config.type != 2 ? '' : titleHTML)
            + '<div id="'+ (config.id||'') +'" class="layui-layer-content'+ ((config.type == 0 && config.icon !== -1) ? ' layui-layer-padding' :'') + (config.type == 3 ? ' layui-layer-loading'+config.icon : '') +'">'
            + (config.type == 0 && config.icon !== -1 ? '<i class="layui-layer-ico layui-layer-ico'+ config.icon +'"></i>' : '')
            + (config.type == 1 && conType ? '' : (config.content||''))
            + '</div>'
            + '<span class="layui-layer-setwin">'+ function(){
                var closebtn = ismax ? '<a class="layui-layer-min" href="javascript:;"><cite></cite></a><a class="layui-layer-ico layui-layer-max" href="javascript:;"></a>' : '';
                config.closeBtn && (closebtn += '<a class="layui-layer-ico '+ doms[7] +' '+ doms[7] + (config.title ? config.closeBtn : (config.type == 4 ? '1' : '2')) +'" href="javascript:;"></a>');
                return closebtn;
            }() + '</span>'
            + (config.btn ? function(){
                var button = '';
                typeof config.btn === 'string' && (config.btn = [config.btn]);
                for(var i = 0, len = config.btn.length; i < len; i++){
                    button += '<a class="'+ doms[6] +''+ i +'">'+ config.btn[i] +'</a>'
                }
                return '<div class="'+ doms[6] +' layui-layer-btn-'+ (config.btnAlign||'') +'">'+ button +'</div>'
            }() : '')
            + (config.resize ? '<span class="layui-layer-resize"></span>' : '')
            + '</div>'
        ], titleHTML, $('<div class="layui-layer-move"></div>'));
        return that;
    };

//创建骨架
    Class.pt.creat = function(){
        var that = this
            ,config = that.config
            ,times = that.index, nodeIndex
            ,content = config.content
            ,conType = typeof content === 'object'
            ,body = $('body');

        if(config.id && $('#'+config.id)[0])  return;

        if(typeof config.area === 'string'){
            config.area = config.area === 'auto' ? ['', ''] : [config.area, ''];
        }

        //anim兼容旧版shift
        if(config.shift){
            config.anim = config.shift;
        }

        if(layer.ie == 6){
            config.fixed = false;
        }

        switch(config.type){
            case 0:
                config.btn = ('btn' in config) ? config.btn : ready.btn[0];
                layer.closeAll('dialog');
                break;
            case 2:
                var content = config.content = conType ? config.content : [config.content||'http://layer.layui.com', 'auto'];
                config.content = '<iframe scrolling="'+ (config.content[1]||'auto') +'" allowtransparency="true" id="'+ doms[4] +''+ times +'" name="'+ doms[4] +''+ times +'" onload="this.className=\'\';" class="layui-layer-load" frameborder="0" src="' + config.content[0] + '"></iframe>';
                break;
            case 3:
                delete config.title;
                delete config.closeBtn;
                config.icon === -1 && (config.icon === 0);
                layer.closeAll('loading');
                break;
            case 4:
                conType || (config.content = [config.content, 'body']);
                config.follow = config.content[1];
                config.content = config.content[0] + '<i class="layui-layer-TipsG"></i>';
                delete config.title;
                config.tips = typeof config.tips === 'object' ? config.tips : [config.tips, true];
                config.tipsMore || layer.closeAll('tips');
                break;
        }

        //建立容器
        that.vessel(conType, function(html, titleHTML, moveElem){
            body.append(html[0]);
            conType ? function(){
                (config.type == 2 || config.type == 4) ? function(){
                    $('body').append(html[1]);
                }() : function(){
                    if(!content.parents('.'+doms[0])[0]){
                        content.data('display', content.css('display')).show().addClass('layui-layer-wrap').wrap(html[1]);
                        $('#'+ doms[0] + times).find('.'+doms[5]).before(titleHTML);
                    }
                }();
            }() : body.append(html[1]);
            $('.layui-layer-move')[0] || body.append(ready.moveElem = moveElem);
            that.layero = $('#'+ doms[0] + times);
            config.scrollbar || doms.html.css('overflow', 'hidden').attr('layer-full', times);
        }).auto(times);

        //遮罩
        $('#layui-layer-shade'+ that.index).css({
            'background-color': config.shade[1] || '#000'
            ,'opacity': config.shade[0]||config.shade
        });

        config.type == 2 && layer.ie == 6 && that.layero.find('iframe').attr('src', content[0]);

        //坐标自适应浏览器窗口尺寸
        config.type == 4 ? that.tips() : that.offset();
        if(config.fixed){
            win.on('resize', function(){
                that.offset();
                (/^\d+%$/.test(config.area[0]) || /^\d+%$/.test(config.area[1])) && that.auto(times);
                config.type == 4 && that.tips();
            });
        }

        config.time <= 0 || setTimeout(function(){
            layer.close(that.index)
        }, config.time);
        that.move().callback();

        //为兼容jQuery3.0的css动画影响元素尺寸计算
        if(doms.anim[config.anim]){
            var animClass = 'layer-anim '+ doms.anim[config.anim];
            that.layero.addClass(animClass).one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
                $(this).removeClass(animClass);
            });
        };

        //记录关闭动画
        if(config.isOutAnim){
            that.layero.data('isOutAnim', true);
        }
    };

//自适应
    Class.pt.auto = function(index){
        var that = this, config = that.config, layero = $('#'+ doms[0] + index);

        if(config.area[0] === '' && config.maxWidth > 0){
            //为了修复IE7下一个让人难以理解的bug
            if(layer.ie && layer.ie < 8 && config.btn){
                layero.width(layero.innerWidth());
            }
            layero.outerWidth() > config.maxWidth && layero.width(config.maxWidth);
        }

        var area = [layero.innerWidth(), layero.innerHeight()]
            ,titHeight = layero.find(doms[1]).outerHeight() || 0
            ,btnHeight = layero.find('.'+doms[6]).outerHeight() || 0
            ,setHeight = function(elem){
                elem = layero.find(elem);
                elem.height(area[1] - titHeight - btnHeight - 2*(parseFloat(elem.css('padding-top'))|0));
            };

        switch(config.type){
            case 2:
                setHeight('iframe');
                break;
            default:
                if(config.area[1] === ''){
                    if(config.maxHeight > 0 && layero.outerHeight() > config.maxHeight){
                        area[1] = config.maxHeight;
                        setHeight('.'+doms[5]);
                    } else if(config.fixed && area[1] >= win.height()){
                        area[1] = win.height();
                        setHeight('.'+doms[5]);
                    }
                } else {
                    setHeight('.'+doms[5]);
                }
                break;
        };

        return that;
    };

//计算坐标
    Class.pt.offset = function(){
        var that = this, config = that.config, layero = that.layero;
        var area = [layero.outerWidth(), layero.outerHeight()];
        var type = typeof config.offset === 'object';
        that.offsetTop = (win.height() - area[1])/2;
        that.offsetLeft = (win.width() - area[0])/2;

        if(type){
            that.offsetTop = config.offset[0];
            that.offsetLeft = config.offset[1]||that.offsetLeft;
        } else if(config.offset !== 'auto'){

            if(config.offset === 't'){ //上
                that.offsetTop = 0;
            } else if(config.offset === 'r'){ //右
                that.offsetLeft = win.width() - area[0];
            } else if(config.offset === 'b'){ //下
                that.offsetTop = win.height() - area[1];
            } else if(config.offset === 'l'){ //左
                that.offsetLeft = 0;
            } else if(config.offset === 'lt'){ //左上角
                that.offsetTop = 0;
                that.offsetLeft = 0;
            } else if(config.offset === 'lb'){ //左下角
                that.offsetTop = win.height() - area[1];
                that.offsetLeft = 0;
            } else if(config.offset === 'rt'){ //右上角
                that.offsetTop = 0;
                that.offsetLeft = win.width() - area[0];
            } else if(config.offset === 'rb'){ //右下角
                that.offsetTop = win.height() - area[1];
                that.offsetLeft = win.width() - area[0];
            } else {
                that.offsetTop = config.offset;
            }

        }

        if(!config.fixed){
            that.offsetTop = /%$/.test(that.offsetTop) ?
            win.height()*parseFloat(that.offsetTop)/100
                : parseFloat(that.offsetTop);
            that.offsetLeft = /%$/.test(that.offsetLeft) ?
            win.width()*parseFloat(that.offsetLeft)/100
                : parseFloat(that.offsetLeft);
            that.offsetTop += win.scrollTop();
            that.offsetLeft += win.scrollLeft();
        }

        if(layero.attr('minLeft')){
            that.offsetTop = win.height() - (layero.find(doms[1]).outerHeight() || 0);
            that.offsetLeft = layero.css('left');
        }

        layero.css({top: that.offsetTop, left: that.offsetLeft});
    };

//Tips
    Class.pt.tips = function(){
        var that = this, config = that.config, layero = that.layero;
        var layArea = [layero.outerWidth(), layero.outerHeight()], follow = $(config.follow);
        if(!follow[0]) follow = $('body');
        var goal = {
            width: follow.outerWidth(),
            height: follow.outerHeight(),
            top: follow.offset().top,
            left: follow.offset().left
        }, tipsG = layero.find('.layui-layer-TipsG');

        var guide = config.tips[0];
        config.tips[1] || tipsG.remove();

        goal.autoLeft = function(){
            if(goal.left + layArea[0] - win.width() > 0){
                goal.tipLeft = goal.left + goal.width - layArea[0];
                tipsG.css({right: 12, left: 'auto'});
            } else {
                //goal.tipLeft = goal.left;
                goal.tipLeft = goal.left - 20;//by 凌宇琛 2018/04/17
            };
        };

        //辨别tips的方位
        goal.where = [function(){ //上
            goal.autoLeft();
            goal.tipTop = goal.top - layArea[1] - 10;
            tipsG.removeClass('layui-layer-TipsB').addClass('layui-layer-TipsT').css('border-right-color', config.tips[1]);
        }, function(){ //右
            goal.tipLeft = goal.left + goal.width + 10;
            goal.tipTop = goal.top;
            tipsG.removeClass('layui-layer-TipsL').addClass('layui-layer-TipsR').css('border-bottom-color', config.tips[1]);
        }, function(){ //下
            goal.autoLeft();
            goal.tipTop = goal.top + goal.height + 10;
            tipsG.removeClass('layui-layer-TipsT').addClass('layui-layer-TipsB').css('border-right-color', config.tips[1]);
        }, function(){ //左
            goal.tipLeft = goal.left - layArea[0] - 10;
            goal.tipTop = goal.top;
            tipsG.removeClass('layui-layer-TipsR').addClass('layui-layer-TipsL').css('border-bottom-color', config.tips[1]);
        }];
        goal.where[guide-1]();

        /* 8*2为小三角形占据的空间 */
        if(guide === 1){
            goal.top - (win.scrollTop() + layArea[1] + 8*2) < 0 && goal.where[2]();
        } else if(guide === 2){
            win.width() - (goal.left + goal.width + layArea[0] + 8*2) > 0 || goal.where[3]()
        } else if(guide === 3){
            (goal.top - win.scrollTop() + goal.height + layArea[1] + 8*2) - win.height() > 0 && goal.where[0]();
        } else if(guide === 4){
            layArea[0] + 8*2 - goal.left > 0 && goal.where[1]()
        }

        layero.find('.'+doms[5]).css({
            'background-color': config.tips[1],
            'padding-right': (config.closeBtn ? '30px' : '')
        });
        layero.css({
            left: goal.tipLeft - (config.fixed ? win.scrollLeft() : 0),
            top: goal.tipTop  - (config.fixed ? win.scrollTop() : 0)
        });
    }

//拖拽层
    Class.pt.move = function(){
        var that = this
            ,config = that.config
            ,_DOC = $(document)
            ,layero = that.layero
            ,moveElem = layero.find(config.move)
            ,resizeElem = layero.find('.layui-layer-resize')
            ,dict = {};

        if(config.move){
            moveElem.css('cursor', 'move');
        }

        moveElem.on('mousedown', function(e){
            e.preventDefault();
            if(config.move){
                dict.moveStart = true;
                dict.offset = [
                    e.clientX - parseFloat(layero.css('left'))
                    ,e.clientY - parseFloat(layero.css('top'))
                ];
                ready.moveElem.css('cursor', 'move').show();
            }
        });

        resizeElem.on('mousedown', function(e){
            e.preventDefault();
            dict.resizeStart = true;
            dict.offset = [e.clientX, e.clientY];
            dict.area = [
                layero.outerWidth()
                ,layero.outerHeight()
            ];
            ready.moveElem.css('cursor', 'se-resize').show();
        });

        _DOC.on('mousemove', function(e){

            //拖拽移动
            if(dict.moveStart){
                var X = e.clientX - dict.offset[0]
                    ,Y = e.clientY - dict.offset[1]
                    ,fixed = layero.css('position') === 'fixed';

                e.preventDefault();

                dict.stX = fixed ? 0 : win.scrollLeft();
                dict.stY = fixed ? 0 : win.scrollTop();

                //控制元素不被拖出窗口外
                if(!config.moveOut){
                    var setRig = win.width() - layero.outerWidth() + dict.stX
                        ,setBot = win.height() - layero.outerHeight() + dict.stY;
                    X < dict.stX && (X = dict.stX);
                    X > setRig && (X = setRig);
                    Y < dict.stY && (Y = dict.stY);
                    Y > setBot && (Y = setBot);
                }

                layero.css({
                    left: X
                    ,top: Y
                });
            }

            //Resize
            if(config.resize && dict.resizeStart){
                var X = e.clientX - dict.offset[0]
                    ,Y = e.clientY - dict.offset[1];

                e.preventDefault();

                layer.style(that.index, {
                    width: dict.area[0] + X
                    ,height: dict.area[1] + Y
                })
                dict.isResize = true;
                config.resizing && config.resizing(layero);
            }
        }).on('mouseup', function(e){
            if(dict.moveStart){
                delete dict.moveStart;
                ready.moveElem.hide();
                config.moveEnd && config.moveEnd(layero);
            }
            if(dict.resizeStart){
                delete dict.resizeStart;
                ready.moveElem.hide();
            }
        });

        return that;
    };

    Class.pt.callback = function(){
        var that = this, layero = that.layero, config = that.config;
        that.openLayer();
        if(config.success){
            if(config.type == 2){
                layero.find('iframe').on('load', function(){
                    config.success(layero, that.index);
                });
            } else {
                config.success(layero, that.index);
            }
        }
        layer.ie == 6 && that.IE6(layero);

        //按钮
        layero.find('.'+ doms[6]).children('a').on('click', function(){
            var index = $(this).index();
            if(index === 0){
                if(config.yes){
                    config.yes(that.index, layero)
                } else if(config['btn1']){
                    config['btn1'](that.index, layero)
                } else {
                    layer.close(that.index);
                }
            } else {
                var close = config['btn'+(index+1)] && config['btn'+(index+1)](that.index, layero);
                close === false || layer.close(that.index);
            }
        });

        //取消
        function cancel(){
            var close = config.cancel && config.cancel(that.index, layero);
            close === false || layer.close(that.index);
        }

        //右上角关闭回调
        layero.find('.'+ doms[7]).on('click', cancel);

        //点遮罩关闭
        if(config.shadeClose){
            $('#layui-layer-shade'+ that.index).on('click', function(){
                layer.close(that.index);
            });
        }

        //最小化
        layero.find('.layui-layer-min').on('click', function(){
            var min = config.min && config.min(layero);
            min === false || layer.min(that.index, config);
        });

        //全屏/还原
        layero.find('.layui-layer-max').on('click', function(){
            if($(this).hasClass('layui-layer-maxmin')){
                layer.restore(that.index);
                config.restore && config.restore(layero);
            } else {
                layer.full(that.index, config);
                setTimeout(function(){
                    config.full && config.full(layero);
                }, 100);
            }
        });

        config.end && (ready.end[that.index] = config.end);
    };

//for ie6 恢复select
    ready.reselect = function(){
        $.each($('select'), function(index , value){
            var sthis = $(this);
            if(!sthis.parents('.'+doms[0])[0]){
                (sthis.attr('layer') == 1 && $('.'+doms[0]).length < 1) && sthis.removeAttr('layer').show();
            }
            sthis = null;
        });
    };

    Class.pt.IE6 = function(layero){
        //隐藏select
        $('select').each(function(index , value){
            var sthis = $(this);
            if(!sthis.parents('.'+doms[0])[0]){
                sthis.css('display') === 'none' || sthis.attr({'layer' : '1'}).hide();
            }
            sthis = null;
        });
    };

//需依赖原型的对外方法
    Class.pt.openLayer = function(){
        var that = this;

        //置顶当前窗口
        layer.zIndex = that.config.zIndex;
        layer.setTop = function(layero){
            var setZindex = function(){
                layer.zIndex++;
                layero.css('z-index', layer.zIndex + 1);
            };
            layer.zIndex = parseInt(layero[0].style.zIndex);
            layero.on('mousedown', setZindex);
            return layer.zIndex;
        };
    };

    ready.record = function(layero){
        var area = [
            layero.width(),
            layero.height(),
            layero.position().top,
            layero.position().left + parseFloat(layero.css('margin-left'))
        ];
        layero.find('.layui-layer-max').addClass('layui-layer-maxmin');
        layero.attr({area: area});
    };

    ready.rescollbar = function(index){
        if(doms.html.attr('layer-full') == index){
            if(doms.html[0].style.removeProperty){
                doms.html[0].style.removeProperty('overflow');
            } else {
                doms.html[0].style.removeAttribute('overflow');
            }
            doms.html.removeAttr('layer-full');
        }
    };

    /** 内置成员 */

    window.layer = layer;

//获取子iframe的DOM
    layer.getChildFrame = function(selector, index){
        index = index || $('.'+doms[4]).attr('times');
        return $('#'+ doms[0] + index).find('iframe').contents().find(selector);
    };

//得到当前iframe层的索引，子iframe时使用
    layer.getFrameIndex = function(name){
        return $('#'+ name).parents('.'+doms[4]).attr('times');
    };

//iframe层自适应宽高
    layer.iframeAuto = function(index){
        if(!index) return;
        var heg = layer.getChildFrame('html', index).outerHeight();
        var layero = $('#'+ doms[0] + index);
        var titHeight = layero.find(doms[1]).outerHeight() || 0;
        var btnHeight = layero.find('.'+doms[6]).outerHeight() || 0;
        layero.css({height: heg + titHeight + btnHeight});
        layero.find('iframe').css({height: heg});
    };

//重置iframe url
    layer.iframeSrc = function(index, url){
        $('#'+ doms[0] + index).find('iframe').attr('src', url);
    };

//设定层的样式
    layer.style = function(index, options, limit){
        var layero = $('#'+ doms[0] + index)
            ,contElem = layero.find('.layui-layer-content')
            ,type = layero.attr('type')
            ,titHeight = layero.find(doms[1]).outerHeight() || 0
            ,btnHeight = layero.find('.'+doms[6]).outerHeight() || 0
            ,minLeft = layero.attr('minLeft');

        if(type === ready.type[3] || type === ready.type[4]){
            return;
        }

        if(!limit){
            if(parseFloat(options.width) <= 260){
                options.width = 260;
            };

            if(parseFloat(options.height) - titHeight - btnHeight <= 64){
                options.height = 64 + titHeight + btnHeight;
            };
        }

        layero.css(options);
        btnHeight = layero.find('.'+doms[6]).outerHeight();

        if(type === ready.type[2]){
            layero.find('iframe').css({
                height: parseFloat(options.height) - titHeight - btnHeight
            });
        } else {
            contElem.css({
                height: parseFloat(options.height) - titHeight - btnHeight
                - parseFloat(contElem.css('padding-top'))
                - parseFloat(contElem.css('padding-bottom'))
            })
        }
    };

//最小化
    layer.min = function(index, options){
        var layero = $('#'+ doms[0] + index)
            ,titHeight = layero.find(doms[1]).outerHeight() || 0
            ,left = layero.attr('minLeft') || (181*ready.minIndex)+'px'
            ,position = layero.css('position');

        ready.record(layero);

        if(ready.minLeft[0]){
            left = ready.minLeft[0];
            ready.minLeft.shift();
        }

        layero.attr('position', position);

        layer.style(index, {
            width: 180
            ,height: titHeight
            ,left: left
            ,top: win.height() - titHeight
            ,position: 'fixed'
            ,overflow: 'hidden'
        }, true);

        layero.find('.layui-layer-min').hide();
        layero.attr('type') === 'page' && layero.find(doms[4]).hide();
        ready.rescollbar(index);

        if(!layero.attr('minLeft')){
            ready.minIndex++;
        }
        layero.attr('minLeft', left);
    };

//还原
    layer.restore = function(index){
        var layero = $('#'+ doms[0] + index), area = layero.attr('area').split(',');
        var type = layero.attr('type');
        layer.style(index, {
            width: parseFloat(area[0]),
            height: parseFloat(area[1]),
            top: parseFloat(area[2]),
            left: parseFloat(area[3]),
            position: layero.attr('position'),
            overflow: 'visible'
        }, true);
        layero.find('.layui-layer-max').removeClass('layui-layer-maxmin');
        layero.find('.layui-layer-min').show();
        layero.attr('type') === 'page' && layero.find(doms[4]).show();
        ready.rescollbar(index);
    };

//全屏
    layer.full = function(index){
        var layero = $('#'+ doms[0] + index), timer;
        ready.record(layero);
        if(!doms.html.attr('layer-full')){
            doms.html.css('overflow','hidden').attr('layer-full', index);
        }
        clearTimeout(timer);
        timer = setTimeout(function(){
            var isfix = layero.css('position') === 'fixed';
            layer.style(index, {
                top: isfix ? 0 : win.scrollTop(),
                left: isfix ? 0 : win.scrollLeft(),
                width: win.width(),
                height: win.height()
            }, true);
            layero.find('.layui-layer-min').hide();
        }, 100);
    };

//改变title
    layer.title = function(name, index){
        var title = $('#'+ doms[0] + (index||layer.index)).find(doms[1]);
        title.html(name);
    };

//关闭layer总方法
    layer.close = function(index){
        var layero = $('#'+ doms[0] + index), type = layero.attr('type'), closeAnim = 'layer-anim-close';
        if(!layero[0]) return;
        var WRAP = 'layui-layer-wrap', remove = function(){
            if(type === ready.type[1] && layero.attr('conType') === 'object'){
                layero.children(':not(.'+ doms[5] +')').remove();
                var wrap = layero.find('.'+WRAP);
                for(var i = 0; i < 2; i++){
                    wrap.unwrap();
                }
                wrap.css('display', wrap.data('display')).removeClass(WRAP);
            } else {
                //低版本IE 回收 iframe
                if(type === ready.type[2]){
                    try {
                        var iframe = $('#'+doms[4]+index)[0];
                        iframe.contentWindow.document.write('');
                        iframe.contentWindow.close();
                        layero.find('.'+doms[5])[0].removeChild(iframe);
                    } catch(e){}
                }
                layero[0].innerHTML = '';
                layero.remove();
            }
            typeof ready.end[index] === 'function' && ready.end[index]();
            delete ready.end[index];
        };

        if(layero.data('isOutAnim')){
            layero.addClass('layer-anim '+ closeAnim);
        }

        $('#layui-layer-moves, #layui-layer-shade' + index).remove();
        layer.ie == 6 && ready.reselect();
        ready.rescollbar(index);
        if(layero.attr('minLeft')){
            ready.minIndex--;
            ready.minLeft.push(layero.attr('minLeft'));
        }

        if((layer.ie && layer.ie < 10) || !layero.data('isOutAnim')){
            remove()
        } else {
            setTimeout(function(){
                remove();
            }, 200);
        }
    };

//关闭所有层
    layer.closeAll = function(type){
        $.each($('.'+doms[0]), function(){
            var othis = $(this);
            var is = type ? (othis.attr('type') === type) : 1;
            is && layer.close(othis.attr('times'));
            is = null;
        });
    };

    /**
     拓展模块，layui开始合并在一起
     */

    var cache = layer.cache||{}, skin = function(type){
        return (cache.skin ? (' ' + cache.skin + ' ' + cache.skin + '-'+type) : '');
    };

//仿系统prompt
    layer.prompt = function(options, yes){
        var style = '';
        options = options || {};

        if(typeof options === 'function') yes = options;

        if(options.area){
            var area = options.area;
            style = 'style="width: '+ area[0] +'; height: '+ area[1] + ';"';
            delete options.area;
        }
        var prompt, content = options.formType == 2 ? '<textarea class="layui-layer-input"' + style +'></textarea>' : function(){
            return '<input type="'+ (options.formType == 1 ? 'password' : 'text') +'" class="layui-layer-input">';
        }();

        var success = options.success;
        delete options.success;

        return layer.open($.extend({
            type: 1
            ,btn: ['&#x786E;&#x5B9A;','&#x53D6;&#x6D88;']
            ,content: content
            ,skin: 'layui-layer-prompt' + skin('prompt')
            ,maxWidth: win.width()
            ,success: function(layero){
                prompt = layero.find('.layui-layer-input');
                prompt.val(options.value || '').focus();
                typeof success === 'function' && success(layero);
            }
            ,resize: false
            ,yes: function(index){
                var value = prompt.val();
                if(value === ''){
                    prompt.focus();
                } else if(value.length > (options.maxlength||500)) {
                    layer.tips('&#x6700;&#x591A;&#x8F93;&#x5165;'+ (options.maxlength || 500) +'&#x4E2A;&#x5B57;&#x6570;', prompt, {tips: 1});
                } else {
                    yes && yes(value, index, prompt);
                }
            }
        }, options));
    };

//tab层
    layer.tab = function(options){
        options = options || {};

        var tab = options.tab || {}
            ,THIS = 'layui-this'
            ,success = options.success;

        delete options.success;

        return layer.open($.extend({
            type: 1,
            skin: 'layui-layer-tab' + skin('tab'),
            resize: false,
            title: function(){
                var len = tab.length, ii = 1, str = '';
                if(len > 0){
                    str = '<span class="'+ THIS +'">'+ tab[0].title +'</span>';
                    for(; ii < len; ii++){
                        str += '<span>'+ tab[ii].title +'</span>';
                    }
                }
                return str;
            }(),
            content: '<ul class="layui-layer-tabmain">'+ function(){
                var len = tab.length, ii = 1, str = '';
                if(len > 0){
                    str = '<li class="layui-layer-tabli '+ THIS +'">'+ (tab[0].content || 'no content') +'</li>';
                    for(; ii < len; ii++){
                        str += '<li class="layui-layer-tabli">'+ (tab[ii].content || 'no  content') +'</li>';
                    }
                }
                return str;
            }() +'</ul>',
            success: function(layero){
                var btn = layero.find('.layui-layer-title').children();
                var main = layero.find('.layui-layer-tabmain').children();
                btn.on('mousedown', function(e){
                    e.stopPropagation ? e.stopPropagation() : e.cancelBubble = true;
                    var othis = $(this), index = othis.index();
                    othis.addClass(THIS).siblings().removeClass(THIS);
                    main.eq(index).show().siblings().hide();
                    typeof options.change === 'function' && options.change(index);
                });
                typeof success === 'function' && success(layero);
            }
        }, options));
    };

//相册层
    layer.photos = function(options, loop, key){
        var dict = {};
        options = options || {};
        if(!options.photos) return;
        var type = options.photos.constructor === Object;
        var photos = type ? options.photos : {}, data = photos.data || [];
        var start = photos.start || 0;
        dict.imgIndex = (start|0) + 1;

        options.img = options.img || 'img';

        var success = options.success;
        delete options.success;

        if(!type){ //页面直接获取
            var parent = $(options.photos), pushData = function(){
                data = [];
                parent.find(options.img).each(function(index){
                    var othis = $(this);
                    othis.attr('layer-index', index);
                    data.push({
                        alt: othis.attr('alt'),
                        pid: othis.attr('layer-pid'),
                        src: othis.attr('layer-src') || othis.attr('src'),
                        thumb: othis.attr('src')
                    });
                })
            };

            pushData();

            if (data.length === 0) return;

            loop || parent.on('click', options.img, function(){
                var othis = $(this), index = othis.attr('layer-index');
                layer.photos($.extend(options, {
                    photos: {
                        start: index,
                        data: data,
                        tab: options.tab
                    },
                    full: options.full
                }), true);
                pushData();
            })

            //不直接弹出
            if(!loop) return;

        } else if (data.length === 0){
            return layer.msg('&#x6CA1;&#x6709;&#x56FE;&#x7247;');
        }

        //上一张
        dict.imgprev = function(key){
            dict.imgIndex--;
            if(dict.imgIndex < 1){
                dict.imgIndex = data.length;
            }
            dict.tabimg(key);
        };

        //下一张
        dict.imgnext = function(key,errorMsg){
            dict.imgIndex++;
            if(dict.imgIndex > data.length){
                dict.imgIndex = 1;
                if (errorMsg) {return};
            }
            dict.tabimg(key)
        };

        //方向键
        dict.keyup = function(event){
            if(!dict.end){
                var code = event.keyCode;
                event.preventDefault();
                if(code === 37){
                    dict.imgprev(true);
                } else if(code === 39) {
                    dict.imgnext(true);
                } else if(code === 27) {
                    layer.close(dict.index);
                }
            }
        }

        //切换
        dict.tabimg = function(key){
            if(data.length <= 1) return;
            photos.start = dict.imgIndex - 1;
            layer.close(dict.index);
            return layer.photos(options, true, key);
            setTimeout(function(){
                layer.photos(options, true, key);
            }, 200);
        }

        //一些动作
        dict.event = function(){
            dict.bigimg.hover(function(){
                dict.imgsee.show();
            }, function(){
                dict.imgsee.hide();
            });

            dict.bigimg.find('.layui-layer-imgprev').on('click', function(event){
                event.preventDefault();
                dict.imgprev();
            });

            dict.bigimg.find('.layui-layer-imgnext').on('click', function(event){
                event.preventDefault();
                dict.imgnext();
            });

            $(document).on('keyup', dict.keyup);
        };

        //图片预加载
        function loadImage(url, callback, error) {
            var img = new Image();
            img.src = url;
            if(img.complete){
                return callback(img);
            }
            img.onload = function(){
                img.onload = null;
                callback(img);
            };
            img.onerror = function(e){
                img.onerror = null;
                error(e);
            };
        };

        dict.loadi = layer.load(1, {
            shade: 'shade' in options ? false : 0.9,
            scrollbar: false
        });

        loadImage(data[start].src, function(img){
            layer.close(dict.loadi);
            dict.index = layer.open($.extend({
                type: 1,
                id: 'layui-layer-photos',
                area: function(){
                    var imgarea = [img.width, img.height];
                    var winarea = [$(window).width() - 100, $(window).height() - 100];

                    //如果 实际图片的宽或者高比 屏幕大（那么进行缩放）
                    if(!options.full && (imgarea[0]>winarea[0]||imgarea[1]>winarea[1])){
                        var wh = [imgarea[0]/winarea[0],imgarea[1]/winarea[1]];//取宽度缩放比例、高度缩放比例
                        if(wh[0] > wh[1]){//取缩放比例最大的进行缩放
                            imgarea[0] = imgarea[0]/wh[0];
                            imgarea[1] = imgarea[1]/wh[0];
                        } else if(wh[0] < wh[1]){
                            imgarea[0] = imgarea[0]/wh[1];
                            imgarea[1] = imgarea[1]/wh[1];
                        }
                    }

                    return [imgarea[0]+'px', imgarea[1]+'px'];
                }(),
                title: false,
                shade: 0.9,
                shadeClose: true,
                closeBtn: false,
                move: '.layui-layer-phimg img',
                moveType: 1,
                scrollbar: false,
                moveOut: true,
                //anim: Math.random()*5|0,
                isOutAnim: false,
                skin: 'layui-layer-photos' + skin('photos'),
                content: '<div class="layui-layer-phimg">'
                +'<img src="'+ data[start].src +'" alt="'+ (data[start].alt||'') +'" layer-pid="'+ data[start].pid +'">'
                +'<div class="layui-layer-imgsee">'
                +(data.length > 1 ? '<span class="layui-layer-imguide"><a href="javascript:;" class="layui-layer-iconext layui-layer-imgprev"></a><a href="javascript:;" class="layui-layer-iconext layui-layer-imgnext"></a></span>' : '')
                +'<div class="layui-layer-imgbar" style="display:'+ (key ? 'block' : '') +'"><span class="layui-layer-imgtit"><a href="javascript:;">'+ (data[start].alt||'') +'</a><em>'+ dict.imgIndex +'/'+ data.length +'</em></span></div>'
                +'</div>'
                +'</div>',
                success: function(layero, index){
                    dict.bigimg = layero.find('.layui-layer-phimg');
                    dict.imgsee = layero.find('.layui-layer-imguide,.layui-layer-imgbar');
                    dict.event(layero);
                    options.tab && options.tab(data[start], layero);
                    typeof success === 'function' && success(layero);
                }, end: function(){
                    dict.end = true;
                    $(document).off('keyup', dict.keyup);
                }
            }, options));
        }, function(){
            layer.close(dict.loadi);
            layer.msg('&#x5F53;&#x524D;&#x56FE;&#x7247;&#x5730;&#x5740;&#x5F02;&#x5E38;<br>&#x662F;&#x5426;&#x7EE7;&#x7EED;&#x67E5;&#x770B;&#x4E0B;&#x4E00;&#x5F20;&#xFF1F;', {
                time: 30000,
                btn: ['&#x4E0B;&#x4E00;&#x5F20;', '&#x4E0D;&#x770B;&#x4E86;'],
                yes: function(){
                    data.length > 1 && dict.imgnext(true,true);
                }
            });
        });
    };

//主入口
    ready.run = function(_$){
        $ = _$;
        win = $(window);
        doms.html = $('html');
        layer.open = function(deliver){
            var o = new Class(deliver);
            return o.index;
        };
    };

//加载方式
    window.layui && layui.define ? (
        layer.ready()
            ,layui.define('jquery', function(exports){ //layui加载
            layer.path = layui.cache.dir;
            ready.run(layui.$);

            //暴露模块
            window.layer = layer;
            exports('layer', layer);
        })
    ) : (
        (typeof define === 'function' && define.amd) ? define(['jquery'], function(){ //requirejs加载
            ready.run(window.jQuery);
            return layer;
        }) : function(){ //普通script标签加载
            ready.run(window.jQuery);
            layer.ready();
        }()
    );

}(window);