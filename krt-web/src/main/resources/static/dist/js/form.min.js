/*! form.js
 * ===============================================
 * 表单页共用js
 * ===============================================
 *
 * @Author  殷帅
 * @version 1.0.0
 */
$.validator.setDefaults({errorContainer:"#messageBox",errorPlacement:function(a,b){$("#messageBox").text("输入有误，请先更正。");if(b.is(":checkbox")||b.is(":radio")||b.parent().is(".input-group")){a.appendTo(b.parent().parent())}else{if(b.is(".select2")){a.insertAfter(b.next());a.display="block"}else{a.insertAfter(b)}}}});$(".icheck").iCheck({checkboxClass:"icheckbox_minimal-blue",radioClass:"iradio_minimal-blue"});$(".select2").select2({language:"zh-CN",});$("#closeIframeBtn").click(function(){top.krt.tab.closeThisTabs(function(a){})});