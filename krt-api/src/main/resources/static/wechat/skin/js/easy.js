var easyOption = {
    goType:"loaded"         //normal:侧滑后在加载页面，loaded:加载完后在侧滑。
};
+function(){var R=window,k=document,Y=k.documentElement.clientHeight,o=R.localStorage;var s="THISISLCP",U="DOMContentLoaded",i="style",c="type",a="text/css",u="id",v="innerStyle",T="html,body,.inner{height:",G="px;}",q="CH-lcp",M="inner-mttc",p="isDomLoad",S="openMttc",B="closeMttc",F="lcp_back",H="lcp_go",K="lcp_goto",d="MyJS",D="showout",N="lcp_baseBack",V="now",C="myCHHref",ab="updateHistoryLength",w="data-isNoBack",J=easyOption.goType;var ae="<div class='newLoad-wrap'><div class='newLoad'><div class='newLoad-div1'><div class='newLoad-circle1'></div><div class='newLoad-circle2'></div><div class='newLoad-circle3'></div><div class='newLoad-circle4'></div></div><div class='newLoad-div2'><div class='newLoad-circle1'></div><div class='newLoad-circle2'></div><div class='newLoad-circle3'></div><div class='newLoad-circle4'></div></div><div class='newLoad-div3'><div class='newLoad-circle1'></div><div class='newLoad-circle2'></div><div class='newLoad-circle3'></div><div class='newLoad-circle4'></div></div></div></div></div>";var L=function(h,af){af=af?af:k;return af.getElementsByTagName(h)};var ad=function(h){return k.getElementById(h)};var W=function(h){return k.createElement(h)};var f=function(h){return o.getItem(h)};var t=function(h,af){o.setItem(h,af)};var x=function(h){var af=function(){h();k.removeEventListener(U,af,false)};k.addEventListener(U,af,false)};var E=function(ag,h){var af=ad(ag);if(af){af.innerHTML=h}else{af=W(i);af.setAttribute(c,a);af.setAttribute(u,ag);af.innerHTML=h;"head" in k?k.head.appendChild(af):L("head",k)[0].appendChild(af)}};var X=function(h){E(v,T+h+G)};X(Y);window.onresize=function(){var h=k.documentElement.clientHeight,af=Math.abs(h-Y)<100;if(af){X(h)}};var g=true;var A=function(h){return"transform:"+h+";-webkit-transform:"+h+";"};var ac="translate3d(0,0,0)",aa="translate3d(-100%,0,0)",Z="translate3d(100%,0,0)",Q=A(aa),O=A(Z);var I=0,z=undefined,m={};var n=function(h,ag){if(!g){return}g=false;var al=W("div"),ah=ad(q),ak=I==0?V:V+I,af=ad(ak),ai=af.parentNode;al.className="inner add";al.setAttribute(i,ag?O:Q);I++;al.innerHTML="<iframe id='"+V+I+"' name='"+V+I+"' src='' width='100%' height='100%' frameborder='0' scrolling='yes'></iframe>"+(J!="loaded"?("<div class='inner-mttc' id='inner-mttc'>"+ae+"</div>"):"");ah.appendChild(al);var aj=ad(V+I);R[p]=undefined;aj.addEventListener("load",b,false);if(J=="loaded"){m={div:al,p:ai,isGo:ag,wrap:ah,IFR_DOM:aj};aj.setAttribute("src",h);setTimeout(function(){if("div" in m){R[S]()}},300)}else{setTimeout(function(){al.style.transform=al.style.webkitTransform=ac;ai.style.transform=ai.style.webkitTransform=ag?aa:Z},100);setTimeout(function(){ah.removeChild(ai);al.className="inner";aj.setAttribute("src",""+(z?z:h));if(z){z=undefined}g=true},520)}};var l=function(h){h.style.transition=h.style.webkitTransition="opacity 300ms ease-in-out";h.style.opacity=0;setTimeout(function(){try{h.parentNode.removeChild(h)}catch(af){}},310)};var b=function(){if(!R[p]){var h=ad(M);if(h){h.parentNode.removeChild(h)}if("div" in m){R[B]()}}};R[S]=function(h){if(ad(M)){return}var ag=W("div"),af=L("div",ad(q))[0];ag.id=ag.className=M;ag.setAttribute(w,!!h+"");ag.innerHTML=ae;af.appendChild(ag)};R[B]=function(){var af=ad(M);if("div" in m){var aj=m.div,ah=m.p,h=m.isGo,ag=m.wrap,ai=m.IFR_DOM;af=ad(M);if(af){l(af)}setTimeout(function(){aj.style.transform=aj.style.webkitTransform=ac;ah.style.transform=ah.style.webkitTransform=h?aa:Z},100);setTimeout(function(){ag.removeChild(ah);aj.className="inner";if(z){ai.setAttribute("src",""+z);z=undefined}g=true},520);m={}}else{if(af){af.parentNode.removeChild(af)}}};R[F]=function(h){n(h,false)};R[H]=function(h){n(h,true)};R[K]=function(ag,af){if(ag){e(af);R[H](af)}else{var h=j.length;if(h<=1){R[d][D]()}else{af=af?af:j[h-2];e(af);R[F](af)}}};R[N]=function(){if(!g){return false}var h=ad(M);if(h&&h.getAttribute(w)=="true"){return}var ah=I==0?V:V+I,ag=R.frames[ah].window;try{ag[N]()}catch(af){R[K](false)}};R[ab]=function(){};var j=[];var e=function(ag){var af=ag.split("?")[0],aj,h=j.length,ah=true;if(r<h-1){j.splice(r+1)}h=j.length;for(var ai=0;ai<h;ai++){aj=j[ai].split("?")[0];if(af==aj){j.splice(ai+1);ah=false;break}}if(ah){j.push(ag)}r=h=j.length-1;P=false;R.location.hash=h==0?"":h;t(C,j.join(s))};var r=0;var y=function(){var ah=R.location.hash;if(ah==""||ah=="#"){var ag=ad(V).getAttribute("src");j.push(ag);r=0;t(C,j.join(s))}else{ah=parseInt(ah.replace("#",""));r=ah;var h=f(C);j=h===null?[]:h.split(s);if(r<=j.length-1){ad(V).setAttribute("src",j[r])}}var af=ad(M);if(af){af.parentNode.removeChild(af)}};var P=true;R.onhashchange=function(){if(!P){P=true;return}var ag=String(R.location.hash).replace("#",""),h=j.length-1;ag=ag==""?0:parseInt(ag);if(ag>h){}else{if(ag<=j.length-1){var af=ag>=r;r=ag;if(!g){z=j[r]}else{n(j[r],af)}}else{}}};x(y)}();