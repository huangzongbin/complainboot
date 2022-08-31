<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <div class="row row-space15">
                <div class="col-sm-2">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">项目资源树</h3>
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
                            <input type="hidden" id="pid" value="0">
                        </div>
                        <!-- /.box-body -->
                    </div>
                </div>
                <div class="col-sm-10">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">资源管理</h3>
                        </div>
                        <div class="box-body">
                            <div class="table-toolbar" id="table-toolbar">
                                <@shiro.hasPermission name="sys:res:insert">
                                    <button id="insertBtn" data-placement="left" data-toggle="tooltip"
                                            class="btn btn-success btn-sm">
                                        <i class="fa fa-plus"></i> 添加
                                    </button>
                                </@shiro.hasPermission>
                                <button id="expandTableBtn" data-placement="left" data-toggle="tooltip"
                                        class="btn bg-orange btn-sm">
                                    <i class="fa fa-chevron-down"></i> 全部展开
                                </button>
                                <button id="collapseTableBtn" data-placement="left" data-toggle="tooltip"
                                        class="btn bg-purple btn-sm" style="display:none;">
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
        <!-- /.content -->
    </div>
</@body>
<@footer>
    <script>
        /******************************** ztree事件 ********************************/

            //ztree设置
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

        //加载树数据
        function initTree() {
            krt.ajax({
                type: "POST",
                url: "${basePath}/sys/res/resTree",
                async: false,
                success: function (rb) {
                    if (rb.code == 200) {
                        $.fn.zTree.init($("#ztree"), setting, rb.data);
                    } else {
                        krt.layer.msg(rb.msg);
                    }
                }
            });
        }

        //初始化tree
        initTree();
        var tree = $.fn.zTree.getZTreeObj("ztree");

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
            krt.ajax({
                type: "POST",
                url: "${basePath}/sys/res/list",
                data: {"pid": pid},
                success: function (rb) {
                    if (rb.code == 200) {
                        var resList = rb.data;
                        var tbody = '';
                        for (var i = 0; i < resList.length; i++) {
                            var node = resList[i];
                            var hasChild = "";
                            if (node.hasChild == 'true') {
                                hasChild = 'data-tt-branch="true"';
                            }
                            var type = node.type;
                            if (type == 'url') {
                                type = '<span class="badge bg-red">菜单</span>';
                            } else {
                                type = '<span class="badge bg-light-blue">按钮</span>';
                            }
                            var tr = '<tr data-tt-id="' + node.id + '" ' + hasChild + '>'
                                + "<td> <i class='fa " + node.icon + "'></i> " + node.name + "</td>"
                                + "<td>" + node.url + "</td>"
                                + "<td>" + type + "</td>"
                                + "<td>" + node.permission + "</td>"
                                + "<td>" + node.sort + "</td>"
                                + "<td>"
                                + '<@shiro.hasPermission name="sys:res:see"><button class="btn btn-xs btn-info seeBtn" rid="' + node.id + '" pid="' + node.pid + '"><i class="fa fa-eye fa-btn"></i>查看</button></@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="sys:res:update"><button class="btn btn-xs btn-warning updateBtn" rid="' + node.id + '" pid="' + node.pid + '"><i class="fa fa-edit fa-btn"></i>修改</button></@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="sys:res:delete"><button class="btn btn-xs btn-danger deleteBtn" rid="' + node.id + '"><i class="fa fa-trash fa-btn"></i>删除</button></@shiro.hasPermission>'
                                + '<@shiro.hasPermission name="sys:res:insert"><button class="btn btn-xs btn-success addBtn" rid="' + node.id + '"><i class="fa fa-plus fa-btn"></i>添加子资源</button></@shiro.hasPermission>'
                                + "</td>"
                                + "</tr>";
                            tbody = tbody + tr;
                        }
                        $("#tree-table").remove();
                        var html = '<table id="tree-table" class="table table-bordered table-hover table-krt"><thead><tr><th>名称</th><th>连接</th><th>类型</th><th>权限标志</th><th>排序</th><th>操作</th></tr></thead><tbody id="tbody">' + tbody + '</tbody></table>';
                        $("#table-body").append(html);
                        initTreeTable();
                    } else {
                        krt.layer.msg(rb.msg);
                    }
                }
            });
        }

        //加载子节点
        function getNodeViaAjax(pid) {
            krt.ajax({
                type: 'POST',
                url: "${basePath}/sys/res/list",
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
                                    var type = node.type;
                                    if (type == 'url') {
                                        type = '<span class="badge bg-red">菜单</span>';
                                    } else {
                                        type = '<span class="badge bg-light-blue">按钮</span>';
                                    }
                                    var tr = '<tr data-tt-id="' + node.id + '" ' + hasChild + ' data-tt-parent-id="' + pid + '">'
                                        + "<td> <i class='fa " + node.icon + "'></i> " + node.name + "</td>"
                                        + "<td>" + node.url + "</td>"
                                        + "<td>" + type + "</td>"
                                        + "<td>" + node.permission + "</td>"
                                        + "<td>" + node.sort + "</td>"
                                        + "<td>"
                                        + '<@shiro.hasPermission name="sys:res:see"><button class="btn btn-xs btn-info seeBtn" rid="' + node.id + '" pid="' + node.pid + '"><i class="fa fa-eye fa-btn"></i>查看</button></@shiro.hasPermission>'
                                        + '<@shiro.hasPermission name="sys:res:update"><button class="btn btn-xs btn-warning updateBtn" rid="' + node.id + '" pid="' + node.pid + '"><i class="fa fa-edit fa-btn"></i>修改</button></@shiro.hasPermission>'
                                        + '<@shiro.hasPermission name="sys:res:delete"><button class="btn btn-xs btn-danger deleteBtn" rid="' + node.id + '"><i class="fa fa-trash fa-btn"></i>删除</button></@shiro.hasPermission>'
                                        + '<@shiro.hasPermission name="sys:res:insert"><button class="btn btn-xs btn-success addBtn" rid="' + node.id + '"><i class="fa fa-plus fa-btn"></i>添加子资源</button></@shiro.hasPermission>'
                                        + "</td>"
                                        + "</tr>";
                                    $("#tree-table").treetable("loadBranch", parentNode, tr);
                                }
                            }
                        }
                    } else {
                        krt.layer.msg(rb.msg);
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
            top.krt.layer.openDialog("新增资源", "${basePath}/sys/res/insert?pid=" + pid, "1000px", "480px");
        });

        //修改
        $(document).on("click", ".updateBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialog("修改资源", "${basePath}/sys/res/update?id=" + id, "1000px", "480px");
        });

        //查看
        $(document).on("click", ".seeBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialog("查看资源", "${basePath}/sys/res/see?id=" + id, "1000px", "480px");
        });

        //添加子资源
        $(document).on("click", ".addBtn", function () {
            var id = $(this).attr("rid");
            top.krt.layer.openDialog("新增资源", "${basePath}/sys/res/insert?pid=" + id, "1000px", "480px");
        });

        //删除
        $(document).on("click", ".deleteBtn", function () {
            var id = $(this).attr("rid");
            krt.layer.confirm("你确定删除此模块及下级模块吗？", function () {
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/sys/res/delete",
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
