<#--
通用的树选择页
 @author 殷帅
 @version 1.0
 @date 2018-06-21 15:50:00

 参数说明
 url 获取json参数 必填 encodeURL编码
 checkbox 是否多选 必填
 sId 匹配属性  可选
 sValue 匹配值 可选
 -->
<#include "/common/layout.ftl">
<@header>
<link rel="stylesheet" href="${basePath}/plugin/ztree/zTreeStyle/zTreeStyle.css">
</@header>
<@body>
<div class="treeShowHideButton" onclick="search();">
    <label id="btnShow" title="显示搜索" style="display:none;font-weight:700;">︾</label>
    <label id="btnHide" title="隐藏搜索" style="font-weight:700;">︽</label>
</div>
<div class="treeSearchInput" id="search">
    <label for="keyword">关键字：</label><input type="text" class="empty" id="keyword" maxlength="50">
    <button class="btn" id="btn" onclick="searchNode()"> 搜索</button>
</div>
<div class="treeExpandCollapse">
    <a href="javascript:" id="btnExpand">展开</a> /
    <a href="javascript:" id="btnCollapse">折叠</a>
</div>
<input type="hidden" id="sValue" value="${sValue!}"/>
<div id="ztree" class="ztree"></div>
</@body>
<@footer>
<!-- ztree -->
<script type="text/javascript" src="${basePath}/plugin/ztree/browser.js"></script>
<script type="text/javascript" src="${basePath}/plugin/ztree/jquery.ztree.all-3.5.js"></script>
<!-- 框架js -->
<script type="text/javascript" src="${basePath}/dist/js/krt.min.js"></script>
<script language="javascript" type="text/javascript">
    var tree;
    var chkboxType = { Y: "ps", N : "ps"};
    <#if chkboxType??>
    var yn = "${chkboxType}".split("-");
    chkboxType = { Y: yn[0], N : yn[1]};
    </#if>
    var setting = {
        view: {
            selectedMulti: false,
            dblClickExpand: false
        },
        <#if checkbox=='true'>
        check: {
            enable: true,
                chkStyle: "checkbox",
                chkboxType:chkboxType,
                autoCheckTrigger: true,
                radioType: "all"
        },
        </#if>
        data: {
            simpleData: {
                enable: true,
                pIdKey: "pid",
            }
        }

    };

    //初始化树
    function initTree() {
        krt.ajax({
            type: "POST",
            url: "${url!}",
            async: false,
            success: function (rb) {
                if (rb.code == 200) {
                    $.fn.zTree.init($("#ztree"), setting, rb.data);
                } else {
                    krt.layer.msg(rb.msg);
                }
            },
        });
    }

   //默认选择节点
    function selectCheckNode() {
        <#-- 多选 -->
        <#if checkbox=='true'>
            var sValueVal = $("#sValue").val();
            if (sValueVal != '') {
                var sValueArr = sValueVal.split(",");
                for (var i = 0; i < sValueArr.length; i++) {
                    var sValue = sValueArr[i];
                    var node = tree.getNodeByParam("${sId!}", sValue);
                    if (node != null) {
                        tree.checkNode(node);
                        //选中展开
                        tree.selectNode(node);
                    }
                }
            }
        </#if>
        <#-- 单选 -->
        <#if checkbox=='false'>
            var sValue = $("#sValue").val();
            if (sValue != '') {
                var node = tree.getNodeByParam("${sId!}", sValue);
                if (node != null) {
                    tree.selectNode(node)
                }
            }
        </#if>
    }

    //初始化
    initTree();
    tree = $.fn.zTree.getZTreeObj("ztree");

    //是否展开
    if('${(expandAll)!}'== 'true'){
        tree.expandAll(true);
    }

    selectCheckNode();

    //展开
    $('#btnExpand').click(function () {
        tree.expandAll(true);
    });

    //折叠
    $('#btnCollapse').click(function () {
        tree.expandAll(false);
    });

    /** ***********************************搜索***************************************** */

    var lastValue = "", nodeList = [], key = $("#keyword");
    key.bind("focus", focusKey).bind("blur", blurKey).bind("change cut input propertychange", searchNode);
    key.bind("keydown", function (e) {
        if (e.which == 13) {
            searchNode();
        }
    });

    function focusKey(e) {
        if (key.hasClass("empty")) {
            key.removeClass("empty");
        }
    }

    function blurKey(e) {
        if (key.get(0).value === "") {
            key.addClass("empty");
        }
        searchNode(e);
    }

    function searchNode() {
        var value = $.trim(key.get(0).value);
        var keyType = "name";
        if (lastValue === value) {
            return;
        }
        lastValue = value;
        var nodes = tree.getNodes();
        if (value == "") {
            showAllNode(nodes);
            return;
        }
        hideAllNode(nodes);
        nodeList = tree.getNodesByParamFuzzy(keyType, value);
        updateNodes(nodeList);
    }

    function hideAllNode(nodes) {
        nodes = tree.transformToArray(nodes);
        for (var i = nodes.length - 1; i >= 0; i--) {
            tree.hideNode(nodes[i]);
        }
    }

    function showAllNode(nodes) {
        nodes = tree.transformToArray(nodes);
        for (var i = nodes.length - 1; i >= 0; i--) {
            if (nodes[i].getParentNode() != null) {
                tree.expandNode(nodes[i], false, false, false, false);
            } else {
                tree.expandNode(nodes[i], true, true, false, false);
            }
            tree.showNode(nodes[i]);
            showAllNode(nodes[i].children);
        }
    }

    function updateNodes(nodeList) {
        tree.showNodes(nodeList);
        for (var i = 0, l = nodeList.length; i < l; i++) {
            var treeNode = nodeList[i];
            showChildren(treeNode);
            showParent(treeNode)
        }
    }

    function showChildren(treeNode) {
        if (treeNode.isParent) {
            for (var idx in treeNode.children) {
                var node = treeNode.children[idx];
                tree.showNode(node);
                showChildren(node);
            }
        }
    }
    function showParent(treeNode) {
        var parentNode;
        while ((parentNode = treeNode.getParentNode()) != null) {
            tree.showNode(parentNode);
            tree.expandNode(parentNode, true, false, false);
            treeNode = parentNode;
        }
    }

    function search($this) {
        $('#search').slideToggle(200);
        $('#btnShow').toggle();
        $('#btnHide').toggle();
        $('#keyword').focus();
    }

</script>
</@footer>
