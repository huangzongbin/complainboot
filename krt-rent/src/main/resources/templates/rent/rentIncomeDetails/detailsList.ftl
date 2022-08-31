<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
<div class="wrapper">
    <section class="content">
        <div class="box-search">
            <div class="row row-search">
                <div class="col-sm-12">
                    <form class="form-inline" action="">
                        <div class="form-group" style="margin:5px">
                            <label for="test01" class="control-label" style="padding:0 5px">地址</label>
                            <input type="text" class="form-control" placeholder="地址" id="test01">
                            <label for="test02" class="control-label" style="padding:0 5px"> 姓名</label>
                            <input type="text" class="form-control" placeholder="姓名" id="test02">
                            <label for="test03" class="control-label" style="padding:0 5px"> 联系电话</label>
                            <input type="text" class="form-control" placeholder="联系电话" id="test03">
                            <label for="test04" class="control-label" style="padding:0 5px"> 身份证号码</label>
                            <input type="text" class="form-control" placeholder="身份证号码" id="test04">
                            <label for="status" class="control-label" style="padding:0 5px" > 缴纳状态</label>
                            <select class="form-control select2" style="padding:1 px" id="status"
                                    name="status" >
                                <option value="">请选择</option>
                                <option value= 0 >
                                    待缴纳
                                </option>
                                <option value= 1 >
                                    已缴纳
                                </option>
                                <option value= 2 >
                                    已划扣
                                </option>
                            </select>
                        </div>
                        <div class="form-group">
                            <div class="text-center">
                                <button type="button" id="searchBtn" class="btn btn-primary btn-sm">
                                    <i class="fa fa-search fa-btn"></i>搜索
                                </button>
                                <@shiro.hasPermission name="rentIncomeDetails:rentIncomeDetails:excelOut">
                                    <button type="button" id="downloadBtn" class="btn btn-primary btn-sm">
                                        <i class="fa fa-cloud-download fa-btn"></i>
                                        导出
                                    </button>
                                </@shiro.hasPermission>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- 列表数据区 -->
        <div class="box">
            <div>
                <input type="hidden" name="baseId" value="${baseId!}" id="baseId">
            </div>
            <div class="box-body">
                <table id="datatable" class="table table-bordered table-hover"></table>
            </div>

        </div>
    </section>
</div>
</@body>
<@footer>
    <style type="text/css">
        #datatable th, #datatable td { white-space: nowrap; }
    </style>
<script type="text/javascript">
    var datatable;
    $(function () {
        //初始化datatable
        datatable = $('#datatable').DataTable({
            ajax: {
                url: "${basePath}/rent/rentIncomeBase/detailsList",
                type: "post",
                data: function (d) {
                    d.address = $("#test01").val();
                    d.name = $("#test02").val();
                    d.phone = $("#test03").val();
                    d.idCard = $("#test04").val();
                    d.baseId = $("#baseId").val();
                    d.status = $("#status").val();
                    d.orderName = krt.util.camel2Underline(d.columns[d.order[0].column].data);
                    d.orderType = d.order[0].dir;
                }
            },
            columns: [
    {title: 'id', data: "id",visible:false},
        {
                    "title": "序号",
                    "data": "id",
                    "render": function (data, type, row, hang) {
                        return (datatable.context[0]._iDisplayStart + (hang.row + 1));
                    }
                },
                {title: "地址",data: "address"},
                {title: "应缴租金",data: "monthRental"},
                {title: "租金",data: "rental"},
                {title: "历欠租金",data: "oweRental"},
                {title: "物业费",data: "property"},
                {title: "历欠物业费",data: "oweProperty"},
                {title: "滞纳金",data: "overdue"},
                {title: "小计",data: "totalRental"},
                {title: "月租金",data: "advanceRental"},
                {title: "月物业费",data: "advanceProperty"},
                {title: "预缴月单价",data: "advancePrice"},
                {title: "预缴月份",data: "advanceMonths",
                    render:function (data, type, row){
                        if(row.advanceMonths>0){
                            return row.advanceStartMonth+"--"+row.advanceEndMonth;
                        }else{
                            return "";
                        }}
                },
                {title: "预缴总金额",data: "advanceAmount"},
                {title: "是否预缴",data: "advanceMonths",
                    render:function (data){
                        if(data>0){
                            return "<span style='color:red'>是</span>"
                        }else{
                            return "否"
                        }
                    }
                },
                {title: "合计租金",data: "totalRentalAll"},
                {title: "姓名",data: "name"},
                {title: "联系电话",data: "phone"},
                {title: "身份证号码",data: "idCard"},
                {title: "银行卡账号",data: "bankCard"},
                {title: "缴纳状态",data: "status",
                   /* render: function (data) {
                        return krt.util.getDic('pay_operation',data);
                    }*/
                    render: function (data) {
                        if (data == 0) {
                            return "待缴费";
                        } else if (data == 1) {
                            return "已缴费";
                        } else if (data == 2) {
                            return "已划扣";
                        }
                            return "";
                    }

                },
                {title: "缴纳时间",data: "paymentTiime",
                    "render": function (data, type, row) {
                            return (row.status == 0) ? "" : data;
                    }

                },
                {title: "备注",data: "remark"},
                {title: "收款人",data: "payee"},
                {
                    title: "操作", data: "id", orderable: false,
                    "render": function (data, type, row) {
                            /*return (row.status == 0) ? '<@shiro.hasPermission name="rentIncomeDetails:rentIncomeDetails:payment">'
                                + '<button class="btn btn-xs btn-warning payment" rid="' + row.id + '">'
                                + '<i class="fa fa-edit fa-btn"></i>已缴纳'
                                + '</button>'
                                + '</@shiro.hasPermission>' : "";*/
                        return "";
                    }
                }
            ]
        });
        //搜索
        $("#searchBtn").on('click', function () {
            datatable.ajax.reload();
        });


        //已缴纳
        $(document).on("click", ".payment", function () {
            var id = $(this).attr("rid");
            var baseId =$("#baseId").val();
            top.krt.layer.openDialog("租金缴纳","${basePath}/rent/rentIncomeBase/payment?id=" + id +"&baseId="+ baseId ,"400px", "510px");
            var index = krt.layer.getFrameIndex(); //获取窗口索引
            krt.layer.close(index);
        });

        //导出
        $("#downloadBtn").on('click', function () {
            var address = $("#test01").val();
            var name = $("#test02").val();
            var phone = $("#test03").val();
            var idCard = $("#test04").val();
            var baseId = $("#baseId").val();
            var status = $("#status").val();
            window.location.href = "${basePath}/rent/rentIncomeDetails/excelOut?baseId="+ baseId+"&address="+address+"&name="+name+"&phone="+phone+"&idCard="+idCard+"&status="+status;
        });

    });
</script>
</@footer>
