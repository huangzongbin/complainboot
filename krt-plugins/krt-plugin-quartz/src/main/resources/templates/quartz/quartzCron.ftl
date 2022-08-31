<!DOCTYPE html>
<html>
<head>
<#include "/common/header.ftl">
    <style type="text/css">
        .form-control {
            display: inline;
            height: 25px;
        }

        .line {
            height: 35px;
        }
    </style>
</head>
<body class="body-bg-default">
<div class="wrapper">
    <section class="content">
        <div class="row">
            <div class="col-xs-12">
                <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#second" data-toggle="tab">秒</a></li>
                        <li><a href="#minute" data-toggle="tab">分钟</a></li>
                        <li><a href="#hour" data-toggle="tab">小时</a></li>
                        <li><a href="#day" data-toggle="tab">日</a></li>
                        <li><a href="#month" data-toggle="tab">月</a></li>
                        <li><a href="#week" data-toggle="tab">周</a></li>
                        <li><a href="#year" data-toggle="tab">年</a></li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="second">
                            <div class="line">
                                <input type="radio" checked="checked" name="second"
                                       onclick="everyTime(this)"> 每秒 允许的通配符[, - * /]
                            </div>
                            <div class="line">
                                <input type="radio" name="second" onclick="cycle(this)">
                                周期从 <input class="numberspinner form-control"
                                           style="width:60px;" data-options="min:1,max:58" value="1">
                                - <input class="numberspinner form-control" style="width:60px;"
                                         data-options="min:2,max:59" value="2"> 秒
                            </div>
                            <div class="line">
                                <input type="radio" name="second" onclick="startOn(this)">
                                从 <input class="numberspinner form-control" style="width:60px;"
                                         data-options="min:0,max:59" value="0"> 秒开始,每 <input
                                    class="numberspinner form-control" style="width:60px;"
                                    data-options="min:1,max:59" value="1"> 秒执行一次
                            </div>
                            <div class="line">
                                <input type="radio" name="second" id="sencond_appoint">
                                指定
                            </div>
                            <div class="imp secondList">
                                <input type="checkbox" value="1">01
                                <input type="checkbox" value="2">02
                                <input type="checkbox" value="3">03
                                <input type="checkbox" value="4">04
                                <input type="checkbox" value="5">05
                                <input type="checkbox" value="6">06
                                <input type="checkbox" value="7">07
                                <input type="checkbox" value="8">08
                                <input type="checkbox" value="9">09
                                <input type="checkbox" value="10">10
                            </div>
                            <div class="imp secondList">
                                <input type="checkbox" value="11">11
                                <input type="checkbox" value="12">12
                                <input type="checkbox" value="13">13
                                <input type="checkbox" value="14">14
                                <input type="checkbox" value="15">15
                                <input type="checkbox" value="16">16
                                <input type="checkbox" value="17">17
                                <input type="checkbox" value="18">18
                                <input type="checkbox" value="19">19
                                <input type="checkbox" value="20">20
                            </div>
                            <div class="imp secondList">
                                <input type="checkbox" value="21">21
                                <input type="checkbox" value="22">22
                                <input type="checkbox" value="23">23
                                <input type="checkbox" value="24">24
                                <input type="checkbox" value="25">25
                                <input type="checkbox" value="26">26
                                <input type="checkbox" value="27">27
                                <input type="checkbox" value="28">28
                                <input type="checkbox" value="29">29
                                <input type="checkbox" value="30">30
                            </div>
                            <div class="imp secondList">
                                <input type="checkbox" value="31">31
                                <input type="checkbox" value="32">32
                                <input type="checkbox" value="33">33
                                <input type="checkbox" value="34">34
                                <input type="checkbox" value="35">35
                                <input type="checkbox" value="36">36
                                <input type="checkbox" value="37">37
                                <input type="checkbox" value="38">38
                                <input type="checkbox" value="39">39
                                <input type="checkbox" value="40">40
                            </div>
                            <div class="imp secondList">
                                <input type="checkbox" value="41">41
                                <input type="checkbox" value="42">42
                                <input type="checkbox" value="43">43
                                <input type="checkbox" value="44">44
                                <input type="checkbox" value="45">45
                                <input type="checkbox" value="46">46
                                <input type="checkbox" value="47">47
                                <input type="checkbox" value="48">48
                                <input type="checkbox" value="49">49
                                <input type="checkbox" value="50">50
                            </div>
                            <div class="imp secondList">
                                <input type="checkbox" value="51">51
                                <input type="checkbox" value="52">52
                                <input type="checkbox" value="53">53
                                <input type="checkbox" value="54">54
                                <input type="checkbox" value="55">55
                                <input type="checkbox" value="56">56
                                <input type="checkbox" value="57">57
                                <input type="checkbox" value="58">58
                                <input type="checkbox" value="59">59
                            </div>
                        </div>
                        <div class="tab-pane" id="minute">
                            <div class="line">
                                <input type="radio" checked="checked" name="min"
                                       onclick="everyTime(this)"> 分钟 允许的通配符[, - * /]
                            </div>
                            <div class="line">
                                <input type="radio" name="min" onclick="cycle(this)">
                                周期从 <input class="numberspinner form-control"
                                           style="width:60px;" data-options="min:1,max:58" value="1">
                                - <input class="numberspinner form-control" style="width:60px;"
                                         data-options="min:2,max:59" value="2"> 分钟
                            </div>
                            <div class="line">
                                <input type="radio" name="min" onclick="startOn(this)">
                                从 <input class="numberspinner form-control" style="width:60px;"
                                         data-options="min:0,max:59" value="0"> 分钟开始,每
                                <input class="numberspinner form-control" style="width:60px;"
                                       data-options="min:1,max:59" value="1"> 分钟执行一次
                            </div>
                            <div class="line">
                                <input type="radio" name="min" id="min_appoint"> 指定
                            </div>
                            <div class="imp minList">
                                <input type="checkbox" value="1">01
                                <input type="checkbox" value="2">02
                                <input type="checkbox" value="3">03
                                <input type="checkbox" value="4">04
                                <input type="checkbox" value="5">05
                                <input type="checkbox" value="6">06
                                <input type="checkbox" value="7">07
                                <input type="checkbox" value="8">08
                                <input type="checkbox" value="9">09
                                <input type="checkbox" value="10">10
                            </div>
                            <div class="imp minList">
                                <input type="checkbox" value="11">11
                                <input type="checkbox" value="12">12
                                <input type="checkbox" value="13">13
                                <input type="checkbox" value="14">14
                                <input type="checkbox" value="15">15
                                <input type="checkbox" value="16">16
                                <input type="checkbox" value="17">17
                                <input type="checkbox" value="18">18
                                <input type="checkbox" value="19">19
                                <input type="checkbox" value="20">20
                            </div>
                            <div class="imp minList">
                                <input type="checkbox" value="21">21
                                <input type="checkbox" value="22">22
                                <input type="checkbox" value="23">23
                                <input type="checkbox" value="24">24
                                <input type="checkbox" value="25">25
                                <input type="checkbox" value="26">26
                                <input type="checkbox" value="27">27
                                <input type="checkbox" value="28">28
                                <input type="checkbox" value="29">29
                                <input type="checkbox" value="30">30
                            </div>
                            <div class="imp minList">
                                <input type="checkbox" value="31">31
                                <input type="checkbox" value="32">32
                                <input type="checkbox" value="33">33
                                <input type="checkbox" value="34">34
                                <input type="checkbox" value="35">35
                                <input type="checkbox" value="36">36
                                <input type="checkbox" value="37">37
                                <input type="checkbox" value="38">38
                                <input type="checkbox" value="39">39
                                <input type="checkbox" value="40">40
                            </div>
                            <div class="imp minList">
                                <input type="checkbox" value="41">41
                                <input type="checkbox" value="42">42
                                <input type="checkbox" value="43">43
                                <input type="checkbox" value="44">44
                                <input type="checkbox" value="45">45
                                <input type="checkbox" value="46">46
                                <input type="checkbox" value="47">47
                                <input type="checkbox" value="48">48
                                <input type="checkbox" value="49">49
                                <input type="checkbox" value="50">50
                            </div>
                            <div class="imp minList">
                                <input type="checkbox" value="51">51
                                <input type="checkbox" value="52">52
                                <input type="checkbox" value="53">53
                                <input type="checkbox" value="54">54
                                <input type="checkbox" value="55">55
                                <input type="checkbox" value="56">56
                                <input type="checkbox" value="57">57
                                <input type="checkbox" value="58">58
                                <input type="checkbox" value="59">59
                            </div>
                        </div>
                        <div class="tab-pane" id="hour">
                            <div class="line">
                                <input type="radio" checked="checked" name="hour"
                                       onclick="everyTime(this)"> 小时 允许的通配符[, - * /]
                            </div>
                            <div class="line">
                                <input type="radio" name="hour" onclick="cycle(this)">
                                周期从 <input class="numberspinner form-control"
                                           style="width:60px;" data-options="min:0,max:23" value="0">
                                - <input class="numberspinner form-control" style="width:60px;"
                                         data-options="min:2,max:23" value="2"> 小时
                            </div>
                            <div class="line">
                                <input type="radio" name="hour" onclick="startOn(this)">
                                从 <input class="numberspinner form-control" style="width:60px;"
                                         data-options="min:0,max:23" value="0"> 小时开始,每
                                <input class="numberspinner form-control" style="width:60px;"
                                       data-options="min:1,max:23" value="1"> 小时执行一次
                            </div>
                            <div class="line">
                                <input type="radio" name="hour" id="hour_appoint"> 指定
                            </div>
                            <div class="imp hourList">
                                AM:
                                <input type="checkbox" value="0">00
                                <input type="checkbox" value="1">01
                                <input type="checkbox" value="2">02
                                <input type="checkbox" value="3">03
                                <input type="checkbox" value="4">04
                                <input type="checkbox" value="5">05
                                <input type="checkbox" value="6">06
                                <input type="checkbox" value="7">07
                                <input type="checkbox" value="8">08
                                <input type="checkbox" value="9">09
                                <input type="checkbox" value="10">10
                                <input type="checkbox" value="11">11
                            </div>
                            <div class="imp hourList">
                                PM:
                                <input type="checkbox" value="12">12
                                <input type="checkbox" value="13">13
                                <input type="checkbox" value="14">14
                                <input type="checkbox" value="15">15
                                <input type="checkbox" value="16">16
                                <input type="checkbox" value="17">17
                                <input type="checkbox" value="18">18
                                <input type="checkbox" value="19">19
                                <input type="checkbox" value="20">20
                                <input type="checkbox" value="21">21
                                <input type="checkbox" value="22">22
                                <input type="checkbox" value="23">23
                            </div>
                        </div>
                        <div class="tab-pane" id="day">
                            <div class="line">
                                <input type="radio" checked="checked" name="day"
                                       onclick="everyTime(this)"> 日 允许的通配符[, - * / L W]
                            </div>
                            <div class="line">
                                <input type="radio" name="day" onclick="unAppoint(this)">
                                不指定
                            </div>
                            <div class="line">
                                <input type="radio" name="day" onclick="cycle(this)">
                                周期从
                                <input class="numberspinner form-control" style="width:60px;"
                                       data-options="min:1,max:31" value="1">
                                -
                                <input class="numberspinner form-control" style="width:60px;"
                                       data-options="min:2,max:31" value="2"> 日
                            </div>
                            <div class="line">
                                <input type="radio" name="day" onclick="startOn(this)">
                                从
                                <input class="numberspinner form-control" style="width:60px;"
                                       data-options="min:1,max:31" value="1">
                                日开始,每
                                <input class="numberspinner form-control" style="width:60px;"
                                       data-options="min:1,max:31" value="1"> 天执行一次
                            </div>
                            <div class="line">
                                <input type="radio" name="day" onclick="workDay(this)">
                                每月
                                <input class="numberspinner form-control" style="width:60px;"
                                       data-options="min:1,max:31" value="1"> 号最近的那个工作日
                            </div>
                            <div class="line">
                                <input type="radio" name="day" onclick="lastDay(this)">
                                本月最后一天
                            </div>
                            <div class="line">
                                <input type="radio" name="day" id="day_appoint"> 指定
                            </div>
                            <div class="imp dayList">
                                <input type="checkbox" value="1">1
                                <input type="checkbox" value="2">2
                                <input type="checkbox" value="3">3
                                <input type="checkbox" value="4">4
                                <input type="checkbox" value="5">5
                                <input type="checkbox" value="6">6
                                <input type="checkbox" value="7">7
                                <input type="checkbox" value="8">8
                                <input type="checkbox" value="9">9
                                <input type="checkbox" value="10">10
                                <input type="checkbox" value="11">11
                                <input type="checkbox" value="12">12
                                <input type="checkbox" value="13">13
                                <input type="checkbox" value="14">14
                                <input type="checkbox" value="15">15
                                <input type="checkbox" value="16">16
                            </div>
                            <div class="imp dayList">
                                <input type="checkbox" value="17">17
                                <input type="checkbox" value="18">18
                                <input type="checkbox" value="19">19
                                <input type="checkbox" value="20">20
                                <input type="checkbox" value="21">21
                                <input type="checkbox" value="22">22
                                <input type="checkbox" value="23">23
                                <input type="checkbox" value="24">24
                                <input type="checkbox" value="25">25
                                <input type="checkbox" value="26">26
                                <input type="checkbox" value="27">27
                                <input type="checkbox" value="28">28
                                <input type="checkbox" value="29">29
                                <input type="checkbox" value="30">30
                                <input type="checkbox" value="31">31
                            </div>
                        </div>
                        <div class="tab-pane" id="month">
                            <div class="line">
                                <input type="radio" checked="checked" name="mouth"
                                       onclick="everyTime(this)"> 月 允许的通配符[, - * /]
                            </div>
                            <div class="line">
                                <input type="radio" name="mouth" onclick="unAppoint(this)">
                                不指定
                            </div>
                            <div class="line">
                                <input type="radio" name="mouth" onclick="cycle(this)">
                                周期从
                                <input class="numberspinner form-control" style="width:60px;"
                                       data-options="min:1,max:12" value="1">
                                -
                                <input class="numberspinner form-control" style="width:60px;"
                                       data-options="min:2,max:12" value="2"> 月
                            </div>
                            <div class="line">
                                <input type="radio" name="mouth" onclick="startOn(this)">
                                从
                                <input class="numberspinner form-control" style="width:60px;"
                                       data-options="min:1,max:12" value="1"> 日开始,每
                                <input class="numberspinner form-control" style="width:60px;"
                                       data-options="min:1,max:12" value="1"> 月执行一次
                            </div>
                            <div class="line">
                                <input type="radio" name="mouth" id="mouth_appoint"> 指定
                            </div>
                            <div class="imp mouthList">
                                <input type="checkbox" value="1">1
                                <input type="checkbox" value="2">2
                                <input type="checkbox" value="3">3
                                <input type="checkbox" value="4">4
                                <input type="checkbox" value="5">5
                                <input type="checkbox" value="6">6
                                <input type="checkbox" value="7">7
                                <input type="checkbox" value="8">8
                                <input type="checkbox" value="9">9
                                <input type="checkbox" value="10">10
                                <input type="checkbox" value="11">11
                                <input type="checkbox" value="12">12
                            </div>
                        </div>
                        <div class="tab-pane" id="week">
                            <div class="line">
                                <input type="radio" checked="checked" name="week"
                                       onclick="everyTime(this)"> 周 允许的通配符[, - * / L #]
                            </div>
                            <div class="line">
                                <input type="radio" name="week" onclick="unAppoint(this)">
                                不指定
                            </div>
                            <div class="line">
                                <input type="radio" name="week" onclick="startOn(this)">
                                周期 从星期
                                <input class="numberspinner form-control" style="width:60px;" data-options="min:1,max:7"
                                       value="1">
                                -
                                <input class="numberspinner form-control" style="width:60px;" data-options="min:2,max:7"
                                       value="2">
                            </div>
                            <div class="line">
                                <input type="radio" name="week" onclick="weekOfDay(this)">
                                第
                                <input class="numberspinner form-control" style="width:60px;" data-options="min:1,max:4"
                                       value="1">
                                周 的星期
                                <input class="numberspinner form-control" style="width:60px;" data-options="min:1,max:7"
                                       value="1">
                            </div>
                            <div class="line">
                                <input type="radio" name="week" onclick="lastWeek(this)">
                                本月最后一个星期
                                <input class="numberspinner form-control" style="width:60px;" data-options="min:1,max:7"
                                       value="1">
                            </div>
                            <div class="line">
                                <input type="radio" name="week" id="week_appoint"> 指定
                            </div>
                            <div class="imp weekList">
                                <input type="checkbox" value="1">1
                                <input type="checkbox" value="2">2
                                <input type="checkbox" value="3">3
                                <input type="checkbox" value="4">4
                                <input type="checkbox" value="5">5
                                <input type="checkbox" value="6">6
                                <input type="checkbox" value="7">7
                            </div>
                        </div>

                        <div class="tab-pane" id="year">
                            <div class="line">
                                <input type="radio" checked="checked" name="year"
                                       onclick="unAppoint(this)"> 不指定 允许的通配符[, - * /] 非必填
                            </div>
                            <div class="line">
                                <input type="radio" name="year" onclick="everyTime(this)">
                                每年
                            </div>
                            <div class="line">
                                <input type="radio" name="year" onclick="cycle(this)">
                                周期从
                                <input class="numberspinner form-control" style="width:90px;"
                                       data-options="min:2016,max:3000" value="2017"> -
                                <input class="numberspinner form-control" style="width:90px;"
                                       data-options="min:2020,max:3000" value="2020">
                            </div>
                        </div>
                    </div>
                    <!-- /.tab-content -->
                </div>
                <!-- /.nav-tabs-custom -->
            </div>
            <!-- /.col -->
        </div>
        <div class="box">
            <div class="box-body">
                <table style="height: 100px;;width: 100%">
                    <tbody>
                    <tr>
                        <td></td>
                        <td>秒</td>
                        <td>分钟</td>
                        <td>小时</td>
                        <td>日</td>
                        <td>月</td>
                        <td>星期</td>
                        <td>年</td>
                    </tr>
                    <tr>
                        <td>表达式字段:</td>
                        <td>
                            <input type="text" name="v_second" class="col form-control" style="width:50px;background-color:#fff"
                                   value="*" readonly="readonly"/>
                        </td>
                        <td>
                            <input type="text" name="v_min" class="col form-control" style="width:50px;background-color:#fff"
                                   value="*" readonly="readonly"/>
                        </td>
                        <td>
                            <input type="text" name="v_hour" class="col form-control" style="width:50px;background-color:#fff"
                                   value="*" readonly="readonly"/>
                        </td>
                        <td>
                            <input type="text" name="v_day" class="col form-control" style="width:50px;background-color:#fff"
                                   value="*" readonly="readonly"/></td>
                        <td>
                            <input type="text" name="v_mouth" class="col form-control" style="width:50px;background-color:#fff"
                                   value="*" readonly="readonly"/>
                        </td>
                        <td>
                            <input type="text" name="v_week" class="col form-control" style="width:50px;background-color:#fff"
                                   value="?" readonly="readonly"/>
                        </td>
                        <td>
                            <input type="text" name="v_year" class="col form-control" style="width:50px;background-color:#fff"
                                   readonly="readonly"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Cron表达式:</td>
                        <td colspan="7">
                            <input type="text" name="cron" class="form-control" style="width:200px;background-color:#fff"
                                   value="* * * * * ? " id="cron" readonly="readonly"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </section>
</div>
<!-- ./wrapper -->
<#include "/common/footer.ftl">
<script src="${basePath}/dist/js/cron.js"></script>
</body>
</html>
