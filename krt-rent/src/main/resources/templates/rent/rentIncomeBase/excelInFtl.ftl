<#include "/common/layoutForm.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
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
                                                租金缴纳月份
                                            </label>
                                        </td>
                                        <td class="width-35">
                                            <input type="text" class="form-control" name="date" value=""
                                                   autocomplete="off" onclick="WdatePicker({dateFmt:'yyyy-MM'})"
                                                   placeholder="请选择" required>
                                            <input type="hidden" id="id" name="id" class="form-control" value="${id!}">
                                        </td>
                                    </tr>
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
                                            <input type="hidden" id="msgType" name="msgType" class="form-control"
                                                   value="msg03">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="active width-15">
                                            <label class="pull-right">
                                                租金缴纳数据
                                            </label>
                                        </td>
                                        <td class="width-35">
                                            <button title="导入" type="button" id="excelIn" data-placement="left"
                                                    data-toggle="tooltip" class="btn bg-orange btn-sm">
                                                <i class="fa fa-chevron-up"></i> 选择文件
                                            </button>
                                            <a style="display: none" id="excelInBtn"></a>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <input type="hidden" name="houseType" id="houseType" value="${houseType!}">
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
        var year, month;

        var closeMessage = "${msgShow!}";

        if (closeMessage) {
            $("#msgShow").hide();
        }
        layui.use('upload', function () {
            var upload = layui.upload;
            //执行实例
            var layerload;
            var uploadInst = upload.render({
                elem: '#excelInBtn' //绑定元素
                , url: ''
                , accept: 'file' //允许上传的文件类型
                , exts: 'xlsx|xls|'        //可传输文件的后缀
                , before: function () {
                    var id = $("#id").val();
                    var msgCode = $("#msgCode").val();
                    var msgType = $("#msgType").val();
                    var mobilePhone = $("#mobilePhone").val();
                    var houseType = $("#houseType").val();
                    var para = 'year=' + year + '&month=' + month + "&id=" + id + "&msgCode=" + msgCode + "&msgType=" + msgType + "&mobilePhone=" + mobilePhone + "&houseType=" + houseType;
                    this.url = '${basePath}/rent/rentIncomeBase/excelIn?' + para;
                    layerload = layer.load();
                }
                , multipart_params: function () {
                    var data = {};
                    data.year = year;
                    data.month = month;
                    data.id = $("#id").val();
                    data.msgCode = $("#msgCode").val();
                    data.msgType = $("#msgType").val();
                    data.mobilePhone = $("#mobilePhone").val();
                    data.houseType = $("#houseType").val();
                    return data;
                }
                , done: function (res) {
                    layer.close(layerload);
//                datatable.ajax.reload();
                    if (res.code == 500) {
                        top.layer.msg(res.msg);
                        return null;
                    } else {
                        top.layer.msg("正在统计，请稍候");
                    }
                    if (res.data.PayStatus == 2) {
                        top.layer.msg("当前不可以导入，要完全结束缴费才可导入");
                        return null;
                    }
                    //上传完毕回调
                    var html = '';
                    var fileExistCards = res.data.fileExistCards;
                    var erroeIdcardList = res.data.erroeIdcardList;
                    for (var i = 0; i < fileExistCards.length; i++) {
                        html += "<li style=\"list-style-position:inside;list-style-type:decimal;height:auto;font-size:14px" +
                            ";margin-bottom:5px;padding:5px 7px;border:1px solid #d4e3f9;border-radius:5px;box-shadow: 1px 1px 3px #d4e3f9;\"" +
                            ">文件中身份证号为：" + fileExistCards[i] + " 存在多个数据</li>";
                    }
                    for ( var i = 0; i <erroeIdcardList.length; i++){
                        html += "<li style=\"list-style-position:inside;list-style-type:decimal;height:auto;font-size:14px" +
                            ";margin-bottom:5px;padding:5px 7px;border:1px solid #d4e3f9;border-radius:5px;box-shadow: 1px 1px 3px #d4e3f9;\"" +
                            ">身份证号为：" + erroeIdcardList[i] + " 的数据，预缴相关字段必填、租金总和必填或者身份证错误或者其他数据错误请检查后导入</li>";
                    }
                    if (html != '') {
                        var str = '其他数据已全部导入成功';
                        if (res.data.number == 0) {
                            str = '未有数据成功导入系统';
                        }
                        html += "<li style=\"list-style-position:inside;list-style-type:decimal;height:auto;font-size:14px" +
                            ";margin-bottom:5px;padding:5px 7px;border:1px solid #d4e3f9;border-radius:5px;box-shadow: 1px 1px 3px #d4e3f9;\"" +
                            ">" + str + "</li>";
                        html = "<ul style=\"padding-left: 0;\">" + html + "</ul>";
                        top.layer.alert(html, {
                            title: "导入结果",
                            btn: 0,
                            shade: 0
                        });
                    } else {
                        top.layer.msg("导入成功，无问题数据");
                        var index = top.layer.getFrameIndex(window.name); //获取窗口索引
                        krt.table.reloadTable();
                        top.layer.close(index);
                    }
                    krt.table.reloadTable();
                }
                , error: function () {
                    //请求异常回调
                    layer.close(layerload);
                    top.layer.msg("导入异常");
                }
            });
        });
        $("#excelIn").click(function () {
            //判断是否选择了 年月
            if (!$("input[name='date']").val()) {
                top.layer.msg("请先选择租金缴纳月份");
                return null;
            }
            if (!$("#mobilePhone").val()) {
                top.layer.msg("请选择接收短信验证码的手机号！");
                return null;
            }
            if (!$("#msgCode").val()) {
                top.layer.msg("请填写正确的短信验证码！");
                return null;
            }
            var date = $("input[name='date']").val().split("-");
            year = date[0];
            month = date[1];
            $("#excelInBtn").click();
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

