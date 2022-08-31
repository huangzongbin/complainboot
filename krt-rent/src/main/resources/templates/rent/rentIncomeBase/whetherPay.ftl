<#include "/common/layoutForm.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper" xmlns:c="http://www.w3.org/1999/html">
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <form role="form" class="form-horizontal krt-form" id="krtForm">
                            <div class="box-body">
                                <!-- 隐藏域 -->
                                <table class="table table-bordered table-krt">
                                    <tr>
                                        <td class="active width-15">
                                            <label class="pull-right">
                                                选择接收短信验证码的手机号码
                                            </label>
                                        </td>
                                        <td class="width-35">
                                            <#--                                        <input type="text"  id="mobilePhone"  name="mobilePhone" class="form-control" placeholder="请输入手机号码">-->
                                            <select name="mobilePhone" id="mobilePhone" class="form-control select2"
                                                    required style="width: 100%;">
                                                <option value="">==请选择==</option>
                                                <@dic type="mobile_phone";typeList>
                                                    <#list typeList as type>
                                                        <option value="${type.code}">${type.name}-${type.code}</option>
                                                    </#list>
                                                </@dic>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="active width-15">
                                            <label class="pull-right">
                                                获取验证码
                                            </label>
                                        </td>
                                        <td class="width-35" id="msgShow">
                                            <button title="发送短信验证码" type="button" id="dyMobileButton"
                                                    data-placement="left" data-toggle="tooltip"
                                                    class="btn bg-green btn-sm">
                                                <i class="fa fa-gift"></i> 获取验证码
                                            </button>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="active width-15">
                                            <label class="pull-right">
                                                接收到的短信验证码
                                            </label>
                                        </td>
                                        <td class="width-35">
                                            <input type="text" id="msgCode" name="msgCode" class="form-control"
                                                   placeholder="接收到的短信验证码">
                                            <input type="hidden" id="id" name="id" class="form-control" value="${id}">
                                            <input type="hidden" id="msgType" name="msgType" class="form-control"
                                                   value="${msgType}">
                                            <input type="hidden" id="status" name="status" class="form-control"
                                                   value="${status}">
                                            <input type="hidden" id="payStatus" name="payStatus" class="form-control"
                                                   value="${payStatus}">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="active width-15">
                                            <label class="pull-right">
                                                ${btnName}
                                            </label>
                                        </td>
                                        <td class="width-35">
                                            <button title="开始缴费" type="button" id="whetherPay" data-placement="left"
                                                    data-toggle="tooltip" class="btn bg-orange btn-sm">
                                                <i class="fa fa-edit fa-btn"></i> ${btnName}
                                            </button>
                                        </td>
                                    </tr>

                                </table>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </div>
</@body>
<@footer>
    <script src="${basePath}/layui/layui-v2.2.6/layui/layui.js"></script>
    <link rel="stylesheet" href="${basePath}/layui/layui-v2.2.6/layui/css/layui.css">
    <script type="text/javascript">

        var closeMessage = "${msgShow!}";

        if (closeMessage) {
            $("#msgShow").hide();
        }

        $("#whetherPay").click(function () {
            var mobilePhone = $("#mobilePhone").val()
            if (!mobilePhone && !closeMessage) {
                top.layer.msg("请选择接收短信验证码的手机号！");
                return null;
            }
            var msgCode = $("#msgCode").val()
            if (!msgCode && !closeMessage) {
                top.layer.msg("请填写正确的短信验证码！");
                return null;
            }
            var id = $("#id").val();
            var status = $("#status").val();
            var payStatus = $("#payStatus").val();
            var id = $("#id").val();
            var msgType = $("#msgType").val();
            var rentIncomeBase = {
                "id": id,
                "status": status,
                "payStatus": payStatus,
                "msgType": msgType,
                "mobilePhone": mobilePhone,
                "msgCode": msgCode
            };
            krt.ajax({
                type: "POST",
                url: "${basePath}/rent/rentIncomeBase/whetherPay",
                data: rentIncomeBase,
                success: function (data) {
                    if (data.code == 200) {
                        var index = krt.layer.getFrameIndex(); //获取窗口索引
                        krt.table.reloadTable();
                        krt.layer.close(index);
                    } else {
                        top.layer.msg(data.msg);
                    }

                }
            });
        })

        var time = 60;
        var flag = true;   //设置点击标记，防止60内再次点击生效

        //发送验证码
        $('#dyMobileButton').click(function () {
            var mobilePhone = $("#mobilePhone").val();
            if (!mobilePhone) {
                top.layer.msg("请选择接收短信验证码的手机号！");
                return null;
            }
            $(this).attr("disabled", true);
            var id = $("#id").val();
            var msgType = $("#msgType").val();
            if (flag) {
                var timer = setInterval(function () {
                    if (time == 60 && flag) {
                        flag = false;

                        krt.ajax({
                            type: "POST",
                            url: "${basePath}/rent/rentIncomeBase/sendMsgCode",
                            data: {"id": id, "msgType": msgType, "mobilePhone": mobilePhone},
                            success: function (data) {
                                if (data.code == 200) {
                                    top.layer.msg("短信验证码发送成功！");
                                    $("#dyMobileButton").html("已发送");
                                } else {
                                    flag = true;
                                    time = 60;
                                    clearInterval(timer);
                                    top.layer.msg("短信验证码发送异常！");
                                }
                            }
                        });
                    } else if (time == 0) {
                        $("#dyMobileButton").removeAttr("disabled");
                        $("#dyMobileButton").html("获取验证码");
                        clearInterval(timer);
                        time = 60;
                        flag = true;
                    } else {
                        $("#dyMobileButton").html(time + " s 重新发送");
                        time--;
                    }
                }, 1000);
            }
        });


    </script>
</@footer>

