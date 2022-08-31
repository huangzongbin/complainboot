<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
<div class="wrapper">
    <section class="content">
        <!-- 搜索条件区 -->
        <div class="box-search">
            <div class="row row-search">
                <div class="col-sm-12">
                    <form class="form-inline" action="">
                        <div class="form-group" style="margin:5px">
                            <label for="test01" class="control-label" style="padding:0 5px">小区名称</label>
                            <input type="text" class="form-control" placeholder="小区名称" id="test01">
                            <label for="test02" class="control-label" style="padding:0 5px"> 姓名</label>
                            <input type="text" class="form-control" placeholder="姓名" id="test02">
                            <label for="test03" class="control-label" style="padding:0 5px"> 联系电话</label>
                            <input type="text" class="form-control" placeholder="联系电话" id="test03">
                            <label for="test04" class="control-label" style="padding:0 5px"> 身份证号码</label>
                            <input type="text" class="form-control" placeholder="身份证号码" id="test04">
                            <input type="hidden" value="${houseType!}" id="houseType">
                        </div>
                        <div class="form-group">
                            <div class="text-center">
                                <button type="button" id="searchBtn" class="btn btn-primary btn-sm">
                                    <i class="fa fa-search fa-btn"></i>搜索
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- 列表数据区 -->
        <div class="box">
            <div class="box-body">
                <!-- 工具按钮区 -->
                <div class="table-toolbar" id="table-toolbar">
                    <@shiro.hasPermission name="rent:house:insert">
                        <button title="添加" type="button" id="insertBtn" data-placement="left" data-toggle="tooltip" class="btn btn-success btn-sm">
                            <i class="fa fa-plus"></i> 添加
                        </button>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="rent:house:delete">
                        <button class="btn btn-sm btn-danger" id="deleteBatchBtn">
                            <i class="fa fa-trash fa-btn"></i>批量删除
                        </button>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="rent:house:excelIn">
                        <button title="导入" type="button" id="excelIn" data-placement="left" data-toggle="tooltip" class="btn bg-orange btn-sm">
                            <i class="fa fa-chevron-up"></i> 导入
                        </button>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="rent:house:excelIn">
                        <@krt.excelIn btnText='导入检查身份证' id="excelInBtn2" url="${basePath}/rent/rentHouse/checkIdcard" >
                        </@krt.excelIn>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="rent:house:excelIn">
                        <a style="margin-left: 8px;" href="${basePath}/excel/住户信息导入模板.xlsx" title="模板下载" id="excelOut" data-placement="left" data-toggle="tooltip" class="btn bg-purple btn-sm">
                            <i class="fa fa-chevron-down"></i>  模板下载
                        </a>
                    </@shiro.hasPermission>
                </div>
                <table id="datatable" class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th><input type="checkbox" id="checkAll" class="icheck"></th>
                        <th>小区名称</th>
                        <th>地址</th>
                        <th>姓名</th>
                        <th>联系电话</th>
                        <th>身份证号码</th>
                        <th>银行卡账号</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </section>
</div>
</@body>
<@footer>
<script src="${basePath}/layui/layui-v2.2.6/layui/layui.js"></script>
<link rel="stylesheet" href="${basePath}/layui/layui-v2.2.6/layui/css/layui.css">
<script type="text/javascript">
    layui.use('upload', function(){
        var upload = layui.upload;
        //执行实例
        var layerload;
        var uploadInst = upload.render({
            elem: '#excelIn' //绑定元素
            ,url: '${basePath}/rent/rentHouse/excelIn?houseType='+'${houseType!}'
            ,accept: 'file' //允许上传的文件类型
            , exts: 'xlsx|xls|'        //可传输文件的后缀
            ,before : function () {
                layerload=layer.load();
            }
            ,done: function(res){
                layer.close(layerload);
                if(res.code==500){
                    top.layer.msg("导入失败，请联系管理员");
                    return null;
                }else{
                    top.layer.msg("正在统计，请稍候");
                }
                datatable.ajax.reload();
                //上传完毕回调
                var html ='';
                var fileExistCards = res.data.fileExistCards;
                var erroeIdcardList = res.data.erroeIdcardList;
                var nullCards = res.data.nullCards;
                for ( var i = 0; i <fileExistCards.length; i++){
                    html += "<li style=\"list-style-position:inside;list-style-type:decimal;height:auto;font-size:14px" +
                            ";margin-bottom:5px;padding:5px 7px;border:1px solid #d4e3f9;border-radius:5px;box-shadow: 1px 1px 3px #d4e3f9;\"" +
                            ">文件中身份证号为：" + fileExistCards[i] + " 存在多个数据</li>";
                }
                for ( var i = 0; i <erroeIdcardList.length; i++){
                    html += "<li style=\"list-style-position:inside;list-style-type:decimal;height:auto;font-size:14px" +
                            ";margin-bottom:5px;padding:5px 7px;border:1px solid #d4e3f9;border-radius:5px;box-shadow: 1px 1px 3px #d4e3f9;\"" +
                            ">身份证号为：" + erroeIdcardList[i] + " 身份证错误请检查后导入</li>";
                }
                for ( var i = 0; i <nullCards.length; i++){
                    html += "<li style=\"list-style-position:inside;list-style-type:decimal;height:auto;font-size:14px" +
                            ";margin-bottom:5px;padding:5px 7px;border:1px solid #d4e3f9;border-radius:5px;box-shadow: 1px 1px 3px #d4e3f9;\"" +
                            ">小区名称为：" + nullCards[i] + " 数据未填写身份证号</li>";
                }
                if(html!=''){
                    var str = '其他数据已全部导入成功';
                    if(res.data.number==0){
                        str = '未有数据成功导入系统';
                    }
                    html += "<li style=\"list-style-position:inside;list-style-type:decimal;height:auto;font-size:14px" +
                            ";margin-bottom:5px;padding:5px 7px;border:1px solid #d4e3f9;border-radius:5px;box-shadow: 1px 1px 3px #d4e3f9;\"" +
                                ">"+str+"</li>";
                    html = "<ul style=\"padding-left: 0;\">" + html + "</ul>";
                    top.layer.alert(html,{
                        title:"导入结果",
                        btn:0,
                        shade:0
                    });
                }else{
                    top.layer.msg("导入成功，无问题数据");
                }
            }
            ,error: function(){
                //请求异常回调
                layer.close(layerload);
                top.layer.msg("导入异常");
            }
        });
    });
    $("#excelOutBtn").click(function () {
        window.location.href = "${basePath}customform/customImportGongs/excelOut";
    })
</script>
<script type="text/javascript">
    var datatable;
    $(function () {
        datatable = $('#datatable').DataTable({
            "dom": 'rt<"dataTables_page"<"col-sm-6 col-xs-12"il><"col-sm-6 col-xs-12"p>>',
            "lengthChange": true,//选择lenth
            "autoWidth": false,//自动宽度
            "searching": false,//搜索
            "processing": false,//loding
            "serverSide": true,//服务器模式
            "ordering": false,//排序
            "pageLength": 10,//初始化lenth
            "deferRender": true,
            <#--"language": {-->
                <#--"url": "${basePath}/static/assets/libs/datatables/language/zh_cn.json"-->
            <#--},-->
            "ajax": {
                "url": "${basePath}/rent/rentHouse/list",
                "type": "post",
                "data": function (d) {
                    d.houseName = $("#test01").val();
                    d.name = $("#test02").val();
                    d.phone = $("#test03").val();
                    d.idCard = $("#test04").val();
                    d.houseType = $("#houseType").val();
                }
            },
            "columns": [
                {
                    "data": "id",
                    width: "10",
                    render: function (data, type, row, hang) {
                        return '<input type="checkbox" class="icheck check" value="' + data + '">';
                    }
                },
                {"data": "houseName"},
                {"data": "address"},
                {"data": "name"},
                {"data": "phone"},
                {"data": "idCard"},
                {"data": "bankCard"},
                {
                    "data": "id",
                    "render": function (data, type, row) {
                        return '<@shiro.hasPermission name="rent:house:list">'
                                + '<button class="btn btn-xs btn-info seeBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-eye fa-btn"></i>查看'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="rent:house:update">'
                                    + '<button class="btn btn-xs btn-warning updateBtn" rid="' + row.id + '">'
                                    + '<i class="fa fa-edit fa-btn"></i>修改'
                                    + '</button>'
                                    + '</@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="rent:house:delete">'
                                    + '<button class="btn btn-xs btn-danger deleteBtn" rid="' + row.id + '">'
                                    + '<i class="fa fa-trash fa-btn"></i>删除'
                                    + '</button>'
                                    + '</@shiro.hasPermission>';
                    }
                }
            ]
        });

        //搜索
        $("#searchBtn").on('click', function () {
            datatable.ajax.reload();
        });

        //新增
        $("#insertBtn").click(function () {
            top.krt.layer.openDialog("新增住户","${basePath}/rent/rentHouse/insert?houseType="+${houseType!}, "800px", "380px");
        });

        //修改
        $(document).on("click", ".updateBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialog("修改住户","${basePath}/rent/rentHouse/update?id=" + id, "800px", "380px");
        });

        //查看
        $(document).on("click", ".seeBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialogView("查看住户", "${basePath}/rent/rentHouse/see?id=" + id, "800px", "380px");
        });

        //删除
        $(document).on("click", ".deleteBtn", function () {
            var id = $(this).attr("rid");
            krt.layer.confirm("你确定删除吗？", function () {
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/rent/rentHouse/delete",
                    data: {"id": id},
                    success: function (rb) {
                        krt.layer.msg(rb.msg);
                        if (rb.code == 200) {
                            krt.table.reloadTable();
                        }
                    }
                });
            });
        });

        //批量删除
        $("#deleteBatchBtn").click(function () {
            var ids = getIds();
            if (ids == "") {
                krt.layer.error("请选择要删除的数据!");
                return false;
            } else {
                krt.layer.confirm("你确定删除吗？", function () {
                    krt.ajax({
                        type: "POST",
                        url: "${basePath}/rent/rentHouse/deleteByIds",
                        data: {"ids": ids},
                        success: function (rb) {
                            krt.layer.msg(rb.msg);
                            if (rb.code == 200) {
                                krt.table.reloadTable(ids);
                            }
                        }
                    });
                });
            }
        });
    });
</script>
</@footer>
