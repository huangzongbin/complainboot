<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
<div class="wrapper">
    <section class="content">
        <div class="box">
            <div class="box-body">
                <div class="table-button">
                    <div class="table-toolbar" id="table-toolbar">
                        <@shiro.hasPermission name="cache:deleteAll">
                            <button type="button" class="btn btn-sm btn-danger" id="deleteCache">
                                <i class="fa fa-trash fa-btn"></i>清空缓存
                            </button>
                        </@shiro.hasPermission>
                    </div>
                </div>
                <div class="table-responsive">
                    <table id="datatable" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th style="text-align: center">序号</th>
                            <th>缓存名称</th>
                            <th>读取总命中率</th>
                            <th>读取命中次数</th>
                            <th>读取失效次数</th>
                            <th>内存中对象数</th>
                            <th>硬盘中对象数</th>
                            <th>内存中占用</th>
                            <th>硬盘中占用</th>
                            <th>详情</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#list list as cache>
                            <tr>
                                <td style="text-align: center">${cache_index+1}</td>
                                <td>${cache.cacheName}</td>
                                <td>${cache.cacheHitPercent}</td>
                                <td>${cache.cacheHitCount}</td>
                                <td>${cache.cacheMissCount}</td>
                                <td>${cache.localHeapCount}</td>
                                <td>${cache.localDiskCount}</td>
                                <td>${cache.localHeapSize}</td>
                                <td>${cache.localDiskSize}</td>
                                <td>
                                    <@shiro.hasPermission name="ehcache:cacheNameDetail">
                                        <button class="btn btn-xs btn-info seeBtn" cacheName="${cache.cacheName}"><i class="fa fa-eye fa-btn"></i>查看</button>
                                    </@shiro.hasPermission>
                                    <@shiro.hasPermission name="ehcache:delete">
                                        <button class="btn btn-xs btn-danger deleteBtn" cacheName="${cache.cacheName}"><i class="fa fa-trash fa-btn"></i>删除</button>
                                    </@shiro.hasPermission>

                                </td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
</div>
</@body>
<@footer>
<script type="text/javascript">
    $(function () {
        //查看
        $(document).on("click", ".seeBtn", function () {
            var cacheName = $(this).attr("cacheName");
            window.location.href = "${basePath}/ehcache/cacheNameDetail?cacheName=" + cacheName;
        });
        //删除
        $(document).on("click", ".deleteBtn", function () {
            var cacheName = $(this).attr("cacheName");
            krt.layer.confirm("你确定删除缓存吗？", function () {
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/ehcache/delete",
                    data: {"cacheName": cacheName},
                    success: function (rb) {
                        krt.layer.msg(rb.msg);
                        if (rb.code == 200) {
                            window.location.href = window.location.href;
                        }
                    }
                });
            });
        });
        //清空系统缓存
        $("#deleteCache").click(function () {
            krt.layer.confirm("你确定清空系统缓存吗？", function () {
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/ehcache/deleteAll",
                    success: function (rb) {
                        top.layer.msg(rb.msg);
                    }
                });
            });
        });
    });
</script>
</@footer>
