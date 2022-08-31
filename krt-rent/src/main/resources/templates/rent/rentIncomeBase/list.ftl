<#include "/common/layoutList.ftl">
<@header>
    <style>
        .table > tbody > tr > td {
            text-align: center;
        }

        .table > thead:first-child > tr:first-child > th {
            text-align: center;
        }

    </style>
</@header>
<@body class="body-bg-default">
<div class="wrapper">
    <section class="content">
        <!-- 列表数据区 -->
        <div class="box-search">
            <div class="row row-search">
                <div class="col-sm-12">
                    <form class="form-inline" action="">
                        <div class="form-group" style="margin:5px">
                            <input type="hidden" value="${houseType!}" id="houseType">
                        </div>
                        <#--<div class="form-group">
                            <div class="text-center">
                                <button type="button" id="searchBtn" class="btn btn-primary btn-sm">
                                    <i class="fa fa-search fa-btn"></i>搜索
                                </button>
                            </div>
                        </div>-->
                    </form>
                </div>
            </div>
        </div>
        <div class="box">
            <div class="box-body">
                <!-- 工具按钮区 -->
                <div class="table-toolbar" id="table-toolbar">
                    <@shiro.hasPermission name="rentIncomeBase:rentIncomeBase:excelIn">
                        <button title="导入" type="button" id="excelIn" data-placement="left" data-toggle="tooltip" class="btn bg-orange btn-sm">
                            <i class="fa fa-chevron-up"></i> 导入
                        </button>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="rentIncomeBase:rentIncomeBase:excelIn">
                        <a style="margin-left: 8px;" href="${basePath}/excel/租金详细情况模板.xlsx" title="模板下载" id="excelOut" data-placement="left" data-toggle="tooltip" class="btn bg-purple btn-sm">
                            <i class="fa fa-chevron-down"></i>  模板下载
                        </a>
                    </@shiro.hasPermission>
                </div>
                <table id="datatable" class="table table-bordered table-hover"></table>
            </div>
        </div>
    </section>
</div>
</@body>
<@footer>
    <script type="text/javascript">
        var datatable;
        $(function () {

            //初始化datatable
            datatable = $('#datatable').DataTable({

                ajax: {
                    url: "${basePath}/rent/rentIncomeBase/list",
                    type: "post",
                    data: function (d) {
                        d.houseType = $("#houseType").val();
                        d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                        d.orderType = d.order[0].dir;
                    }
                },
                columns: [
                    {title: 'id', data: "id", visible: false},
                    {title: 'month', data: "month", visible: false},
                    {title: 'status', data: "status", visible: false},
                    {
                        "title": "序号",
                        "data": "id",
                        "render": function (data, type, row, hang) {
                            return (datatable.context[0]._iDisplayStart + (hang.row + 1));
                        }
                    },
                    {
                        title: "租金缴纳月份", data: "year",
                        "render": function (data, type, row) {
                            if (data && row.month) {
                                return data + "年" + row.month + "月";
                            } else {
                                return "";
                            }
                        }
                    },
                    {title: "导入时间", data: "insertTime",
                        "render": function (data, type, row) {
                            if (row.updateTime!=null && row.updateTime!='' ) {
                                return row.updateTime;
                            } else {
                                return data;
                            }
                        }
                    },
                    {title: '缴费状态', data: "payStatus",
                        "render": function (data) {
                           if (data==1){
                               return "待缴费";
                           } else if(data==2){
                               return "缴费中";
                           } else if(data==3){
                               return "结束缴费";
                           }
                        }
                    },
                    {title: "导入数量", data: "number"},
                    {
                        title: "操作", data: "id", orderable: false,
                        "render": function (data, type, row) {
                            return  (row.status == 0) ? ' <@shiro.hasPermission name="rentIncomeBase:rentIncomeBase:see">'
                                + '<button class="btn btn-xs btn-info seeBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-eye fa-btn"></i>查看详情'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                +' <@shiro.hasPermission name="rentIncomeBase:rentIncomeBase:pay">'
                                + '<button class="btn btn-xs btn-warning pay"  rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>开始缴费'
                                + '</button>'
                                + '</@shiro.hasPermission>': ' <@shiro.hasPermission name="rentIncomeBase:rentIncomeBase:see">'
                                + '<button class="btn btn-xs btn-info seeBtn" rid="' + row.id + '">'
                                + '<i class="fa fa-eye fa-btn"></i>查看详情'
                                + '</button>'
                                + '</@shiro.hasPermission>'
                                +' <@shiro.hasPermission name="rentIncomeBase:rentIncomeBase:pay">'
                                + '<button class="btn btn-xs btn-info payStop"  rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>结束缴费'
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

            //查看
            $(document).on("click", ".seeBtn", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialogView("租金缴纳详细情况", "${basePath}/rent/rentIncomeBase/detailsList?baseId=" + id, "1700px", "900px");
            });

          //开始缴费
            $(document).on("click", ".pay", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialogView("开始缴费","${basePath}/rent/rentIncomeBase/whetherPay?id="+id+"&msgType=msg01&status=1&payStatus=2","800px","500px");
                <#--krt.ajax({-->
                <#--    type: "POST",-->
                <#--    url: "${basePath}/rent/rentIncomeBase/whetherPay",-->
                <#--    data: {"id": id , "status": 1,"payStatus" : 2},-->
                <#--    success: function () {-->
                <#--        krt.layer.msg("开始缴费");-->
                <#--        krt.table.reloadTable();-->
                <#--    }-->
                <#--});-->

            });
            //结束缴费
            $(document).on("click", ".payStop", function () {
                var id = $(this).attr("rid");
                top.krt.layer.openDialogView("结束缴费","${basePath}/rent/rentIncomeBase/whetherPay?id="+id+"&msgType=msg02&status=0&payStatus=3","800px","500px");
                <#--krt.ajax({-->
                <#--    type: "POST",-->
                <#--    url: "${basePath}/rent/rentIncomeBase/whetherPay",-->
                <#--    data: {"id": id , "status": 0 ,"payStatus": 3},-->
                <#--    success: function () {-->
                <#--        krt.layer.msg("结束缴费");-->
                <#--        krt.table.reloadTable();-->
                <#--    }-->
                <#--});-->

            });
        //导入按钮
        $("#excelIn").on("click",function () {
            var houseType = '${houseType!}'
            top.krt.layer.openDialogView("租金缴纳数据导入","${basePath}/rent/rentIncomeBase/excelInFtl?houseType="+houseType,"800px","500px");
        });

    });
    </script>
</@footer>
