/*! list.js
 * ===============================================
 * 列表页共用js
 * ===============================================
 *
 * @Author  殷帅
 * @version 1.0.0
 */
/**
 * icheck渲染
 */
$(".icheck").iCheck({
    checkboxClass: 'icheckbox_minimal-blue',
    radioClass: 'iradio_minimal-blue'
});

/**
 * select2渲染
 */
$(".select2").select2({
    language: "zh-CN",
});

/**
 * 全选
 */
function fnCheckAll() {
    var checkAll = $("#checkAll");
    var ichecks = $(".check");
    checkAll.iCheck('uncheck');
    checkAll.iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    });
    ichecks.unbind();
    ichecks.iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    });
    checkAll.on('ifChecked', function (event) {
        ichecks.iCheck('check');
    }).on('ifUnchecked', function (event) {
        ichecks.iCheck('uncheck');
    });
}

/**
 * dataTable全局配置
 */
$.extend($.fn.dataTable.defaults, {
    dom: 'rt<"dataTables_page"<"col-sm-6 col-xs-12"il><"col-sm-6 col-xs-12"p>>',
    lengthChange: true,//选择lenth
    autoWidth: true,//自动宽度
    searching: false,//搜索
    processing: false,//loding
    serverSide: true,//服务器模式
    ordering: true,//排序
    order: [[0, 'desc']], //默认第一行倒序
    aLengthMenu: [10, 25, 50, 100], //可以切换的每页显示条数
    pageLength: 10,//初始化length
    deferRender: true,//延迟加载
    scrollX: "100%",
    scrollXInner: "100%",
    oLanguage: {
        sProcessing: "<img src='" + krt.util.getBasePath() + "/dist/img/loading.gif'/>",
        sLengthMenu: "每页显示 _MENU_ 条结果",
        sZeroRecords: "没有匹配结果",
        sInfo: "显示第 _START_ 至 _END_ 条结果，共 _TOTAL_ 条，",
        sInfoEmpty: "共0条记录",
        sThousands: "",
        sInfoFiltered: "",
        sInfoPostFix: "",
        sSearch: "搜索:",
        sUrl: "",
        sEmptyTable: "表中数据为空",
        sLoadingRecords: "加载中...",
        oPaginate: {
            sFirst: "首页",
            sPrevious: "上一页",
            sNext: "下一页",
            sLast: "末页"
        },
        oAria: {
            sSortAscending: "以升序排列此列",
            sSortDescending: "以降序排列此列"
        }
    },
    fnDrawCallback: function () {
        var checkAll = $("#checkAll");
        if (checkAll.length > 0) {
            fnCheckAll();//复选框渲染
        }
    }
});

/**
 * 获取datatable全选id
 * @returns {string}
 */
function getIds() {
    var ids = new Array();
    $(".check").each(function () {
        if ($(this).prop("checked")) {
            ids.push($(this).val())
        }
    });
    return ids.join(",");
}


/**
 * 选择页面多选
 */
function cCheck() {
    //初始化全选
    var checkAll = $("#checkAll");
    $(".checkAll").unbind();
    checkAll.iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    });
    checkAll.iCheck('uncheck');
    //初始化子集
    $(".cCheck").unbind();
    $(".cCheck").iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    });
    //绑定全选事件
    checkAll.on('ifChecked', function (event) {
        $(".cCheck").iCheck('check');
    }).on('ifUnchecked', function (event) {
        $(".cCheck").iCheck('uncheck');
    });
    //参数定义
    var sName = new Array();
    var sId = new Array();
    //子集点击事件
    $('.cCheck').on('ifChecked', function (event) {
        var idVal = $("#sId").val();
        var nameVal = $("#sName").val();
        if (idVal != '') {
            sName = nameVal.split(",");
            sId = idVal.split(",");
        }
        sName.push($(this).attr("rname"));
        sId.push($(this).attr("rid"));
        $("#sName").val(sName.join(","));
        $("#sId").val(sId.join(","));
    });
    //子集取消选中事件
    $('.cCheck').on('ifUnchecked', function (event) {
        var idVal = $("#sId").val();
        var nameVal = $("#sName").val();
        if (idVal != '') {
            sName = nameVal.split(",");
            sId = idVal.split(",");
        }
        var index = sId.indexOf($(this).attr("rid"));
        if (index > -1) {
            sId.splice(index, 1);
            sName.splice(index, 1);
            $("#sName").val(sName.join(","));
            $("#sId").val(sId.join(","));
        }
    });
}

/**
 * 选择页面单选
 */
function rCheck() {
    //单选
    $(".rCheck").iCheck({
        checkboxClass: 'icheckbox_minimal-blue',
        radioClass: 'iradio_minimal-blue'
    });
    $('.rCheck').on('ifChecked', function (event) {
        $("#sId").val($(this).attr("rid"));
        $("#sName").val($(this).attr("rname"));
    });
}
