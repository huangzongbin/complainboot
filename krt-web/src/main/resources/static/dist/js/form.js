/*! form.js
 * ===============================================
 * 表单页共用js
 * ===============================================
 *
 * @Author  殷帅
 * @version 1.0.0
 */

//验证全局配置
$.validator.setDefaults({
    errorContainer: "#messageBox",
    errorPlacement: function (error, element) {
        $("#messageBox").text("输入有误，请先更正。");
        if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-group")) {
            error.appendTo(element.parent().parent());
        } else if (element.is(".select2")) {
            error.insertAfter(element.next());
            error.display = "block";
        } else {
            error.insertAfter(element);
        }
    }
});

//icheck渲染
$(".icheck").iCheck({
    checkboxClass: 'icheckbox_minimal-blue',
    radioClass: 'iradio_minimal-blue'
});

//select2渲染
$(".select2").select2({
    language: "zh-CN",
});


//关闭iframe
$("#closeIframeBtn").click(function () {
    top.krt.tab.closeThisTabs(function (contentWindow) {

    })
});