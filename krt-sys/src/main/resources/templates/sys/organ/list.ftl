<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <div class="row row-space15">
                <div class="col-sm-2">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">组织机构树</h3>
                            <div class="box-tools">
                                <button class="btn btn-box-tool" id="expandTreeBtn" title="展开">
                                    <i class="fa  fa-chevron-down"></i>
                                </button>
                                <button class="btn btn-box-tool" id="collapseTreeBtn" title="折叠" style="display:none;">
                                    <i class="fa  fa-chevron-up"></i>
                                </button>
                                <button class="btn btn-box-tool" id="refTreeBtn" title="刷新">
                                    <i class="fa fa-refresh"></i>
                                </button>
                            </div>
                        </div>
                        <div class="box-body">
                            <div id="ztree" class="ztree"></div>
                            <!-- 参数 -->
                            <input type="hidden" id="pid" value="">
                        </div>
                        <!-- /.box-body -->
                    </div>
                </div>
                <div class="col-sm-10">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">组织机构管理</h3>
                        </div>
                        <div class="box-body">
                            <div class="table-toolbar" id="table-toolbar">
                                <@shiro.hasPermission name="sys:organ:insert">
                                    <button id="insertBtn" data-placement="left" class="btn btn-success btn-sm">
                                        <i class="fa fa-plus"></i> 添加
                                    </button>
                                </@shiro.hasPermission>
                                <button id="expandTableBtn" data-placement="left" class="btn bg-orange btn-sm">
                                    <i class="fa fa-chevron-down"></i> 全部展开
                                </button>
                                <button id="collapseTableBtn" data-placement="left" class="btn bg-purple btn-sm"
                                        style="display:none;">
                                    <i class="fa fa-chevron-up"></i> 全部收缩
                                </button>
                            </div>
                            <div class="table-responsive" id="table-body">
                                <table id="tree-table" class="table table-bordered table-hover"></table>
                            </div>
                        </div>
                        <!-- /.box-body -->
                    </div>
                </div>
            </div>
        </section>
    </div>
</@body>
<@footer>
    <script>
        /******************************** ztree事件 ********************************/

            //ztree设置
        var tree;
        var setting = {
            view: {
                selectedMulti: false,
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true,
                    pIdKey: "pid",
                }
            },
            callback: {
                onClick: zTreeOnClick
            }
        };

        //ztree点击事件
        function zTreeOnClick(event, treeId, treeNode) {
            var pid = treeNode.id;
            $("#pid").val(pid);
            initTable(pid);
        }

        //刷新树
        $("#refTreeBtn").click(function () {
            initTree();
        });

        //展开tree
        $("#expandTreeBtn").click(function () {
            tree.expandAll(true);
            $(this).hide();
            $('#collapseTreeBtn').show();
        });

        //折叠tree
        $("#collapseTreeBtn").click(function () {
            tree.expandAll(false);
            $(this).hide();
            $('#expandTreeBtn').show();
        });

        //加载树数据
        function initTree() {
            krt.ajax({
                type: "POST",
                url: "${basePath}/sys/organ/organTree",
                async: false,
                success: function (rb) {
                    if (rb.code == 200) {
                        $.fn.zTree.init($("#ztree"), setting, rb.data);
                    } else {
                        krt.layer.msg(rb.msg);
                    }
                }
            });
            tree = $.fn.zTree.getZTreeObj("ztree");
            $("#expandTreeBtn").trigger("click");
        }

        //初始化tree
        initTree();


        /******************************** treeTable事件 ********************************/

        //初始化treeTable
        function initTreeTable() {
            $("#tree-table").treetable({
                expandable: true,
                onNodeExpand: nodeExpand,
                onNodeCollapse: nodeCollapse
            });
        }

        //刷新table
        function reloadTable() {
            var pid = $("#pid").val();
            initTable(pid);
        }

        //展开事件
        function nodeExpand() {
            getNodeViaAjax(this.id);
        }

        //收缩事件
        function nodeCollapse() {

        }

        //初始化父表
        function initTable(pid) {
            $("#pid").val(pid);
            krt.ajax({
                type: "POST",
                url: "${basePath}/sys/organ/list",
                data: {"pid": pid},
                success: function (rb) {
                    if (rb.code == 200) {
                        var tbody = '';
                        var resList = rb.data;
                        for (var i = 0; i < resList.length; i++) {
                            var node = resList[i];
                            var hasChild = "";
                            if (node.hasChild == 'true') {
                                hasChild = 'data-tt-branch="true"';
                            }
                            var tr = '<tr data-tt-id="' + node.id + '" ' + hasChild + '>'
                                + "<td>" + krt.util.isNull(node.name) + "</td>"
                                + "<td>" + krt.util.isNull(node.code) + "</td>"
                                + "<td>" + krt.util.getDic('organ_type', node.type) + "</td>"
                                + "<td>" + krt.util.getDic('organ_grade', node.grade) + "</td>"
                                + "<td>" + krt.util.isNull(node.remark) + "</td>"
                                + "<td>"
                                + '<@shiro.hasPermission name="sys:organ:see"><button class="btn btn-xs btn-info seeBtn" rid="' + node.id + '" pid="' + node.pid + '"><i class="fa fa-eye fa-btn"></i>查看</button></@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="sys:organ:update"><button class="btn btn-xs btn-warning updateBtn" rid="' + node.id + '" pid="' + node.pid + '"><i class="fa fa-edit fa-btn"></i>修改</button></@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="sys:organ:delete"><button class="btn btn-xs btn-danger deleteBtn" rid="' + node.id + '"><i class="fa fa-trash fa-btn"></i>删除</button></@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="sys:organ:insert"><button class="btn btn-xs btn-success addBtn" rid="' + node.id + '"><i class="fa fa-plus fa-btn"></i>添加下级机构</button></@shiro.hasPermission>'
                                + "</td>"
                                + "</tr>";
                            tbody = tbody + tr;
                        }
                        $("#tree-table").remove();
                        var html = '<table id="tree-table" class="table table-bordered table-hover"><thead><tr><th>机构名称</th><th>机构代码</th><th>机构类别</th><th>结构类型</th><th>备注</th><th>操作</th></tr></thead><tbody id="tbody">' + tbody + '</tbody></table>';
                        $("#table-body").append(html);
                        initTreeTable();
                        //展开第一级
                        $('#tree-table').treetable('expandAll');
                    } else {
                        krt.layer.error(rb.msg);
                    }
                }
            });
        }

        //加载子节点
        function getNodeViaAjax(pid) {
            krt.ajax({
                type: 'POST',
                url: "${basePath}/sys/organ/list",
                data: {"pid": pid},
                success: function (rb) {
                    if (rb.code == 200) {
                        var childNodes = rb.data;
                        if (childNodes) {
                            var parentNode = $("#tree-table").treetable("node", pid);
                            for (var i = 0; i < childNodes.length; i++) {
                                var node = childNodes[i];
                                var nodeToAdd = $("#tree-table").treetable("node", node.id);
                                if (!nodeToAdd) {
                                    var hasChild = "";
                                    if (node.hasChild == 'true') {
                                        hasChild = 'data-tt-branch="true"';
                                    }
                                    var tr = '<tr data-tt-id="' + node.id + '" ' + hasChild + ' data-tt-parent-id="' + pid + '">'
                                        + "<td>" + krt.util.isNull(node.name) + "</td>"
                                        + "<td>" + krt.util.isNull(node.code) + "</td>"
                                        + "<td>" + krt.util.getDic('organ_type', node.type) + "</td>"
                                        + "<td>" + krt.util.getDic('organ_grade', node.grade) + "</td>"
                                        + "<td>" + krt.util.isNull(node.remark) + "</td>"
                                        + "<td>"
                                        + '<@shiro.hasPermission name="sys:organ:see"><button class="btn btn-xs btn-info seeBtn" rid="' + node.id + '" pid="' + node.pid + '"><i class="fa fa-eye fa-btn"></i>查看</button></@shiro.hasPermission>'
                                        + '<@shiro.hasPermission name="sys:organ:update"><button class="btn btn-xs btn-warning updateBtn" rid="' + node.id + '" pid="' + node.pid + '"><i class="fa fa-edit fa-btn"></i>修改</button></@shiro.hasPermission>'
                                        + '<@shiro.hasPermission name="sys:organ:delete"><button class="btn btn-xs btn-danger deleteBtn" rid="' + node.id + '"><i class="fa fa-trash fa-btn"></i>删除</button></@shiro.hasPermission>'
                                        + '<@shiro.hasPermission name="sys:organ:insert"><button class="btn btn-xs btn-success addBtn" rid="' + node.id + '"><i class="fa fa-plus fa-btn"></i>添加下级机构</button></@shiro.hasPermission>'
                                        + "</td>"
                                        + "</tr>";
                                    $("#tree-table").treetable("loadBranch", parentNode, tr);
                                }
                            }
                        }
                    } else {
                        krt.layer.error(rb.msg);
                    }
                }
            });
        }

        //加载默认
        initTable(0);

        //收缩
        $("#collapseTableBtn").click(function () {
            $('#tree-table').treetable('collapseAll');
            $(this).hide();
            $('#expandTableBtn').show();
        });

        //展开
        $("#expandTableBtn").click(function () {
            $('#tree-table').treetable('expandAll');
            $(this).hide();
            $('#collapseTableBtn').show();
        })

        //新增
        $("#insertBtn").click(function () {
            var pid = $("#pid").val();
            top.krt.layer.openDialog("新增组织机构", "${basePath}/sys/organ/insert?pid=" + pid, "1000px", "600px");
        });

        //修改
        $(document).on("click", ".updateBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialog("修改组织机构", "${basePath}/sys/organ/update?id=" + id, "1000px", "600px");
        });

        //查看
        $(document).on("click", ".seeBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialog("查看组织机构", "${basePath}/sys/organ/see?id=" + id, "1000px", "600px");
        });

        //添加子组织机构
        $(document).on("click", ".addBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialog("新增组织机构", "${basePath}/sys/organ/insert?pid=" + id, "1000px", "600px");
        });

        //删除
        $(document).on("click", ".deleteBtn", function () {
            var id = $(this).attr("rid");
            krt.layer.confirm("你确定删除此机构及下级机构吗？", function () {
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/sys/organ/delete",
                    data: {"id": id},
                    success: function (rb) {
                        krt.layer.msg(rb.msg);
                        if (rb.code == 200) {
                            var pid = $("#pid").val();
                            initTable(pid);
                            initTree();
                        }
                    }
                });
            });
        });
    </script>
</@footer>
