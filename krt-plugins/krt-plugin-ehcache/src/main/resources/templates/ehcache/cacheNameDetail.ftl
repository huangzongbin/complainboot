<#include "/common/layoutList.ftl">
<@header></@header>
<@body class="body-bg-default">
<div class="wrapper">
    <section class="content">
        <div class="box">
            <div class="box-body">
                <div class="table-button">
                    <div class="table-toolbar" id="table-toolbar">
                        <button type="button" class="btn btn-primary btn-sm" onclick="window.history.back();"><i class="fa fa-mail-reply fa-btn"></i>返回</button>
                    </div>
                </div>
                <div class="table-responsive">
                    <table id="datatable" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>缓存对象名称</th>
                            <th>命中次数</th>
                            <th>大小</th>
                            <th>最后创建/更新时间</th>
                            <th>最后访问时间</th>
                            <th>过期时间</th>
                            <th>timeToIdle(秒)</th>
                            <th>timeToLive(秒)</th>
                            <th>version</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#list caches as cache>
                            <tr>
                                <td>${cache_index+1}</td>
                                <td>${cache.key!}</td>
                                <td>${cache.hitCount!}</td>
                                <td>${cache.size!}</td>
                                <td>${cache.latestOfCreationAndUpdateTime!}</td>
                                <td>${cache.lastAccessTime!}</td>
                                <td>${cache.expirationTime!}</td>
                                <td>${cache.timeToIdle!}</td>
                                <td>${cache.timeToLive!}</td>
                                <td>${cache.version!}</td>
                                <td>
                                    <@shiro.hasPermission name="ehcache:cacheNameDetail">
                                        <button class="btn btn-xs btn-info seeBtn" cacheValue="${(cache.objectValue)!""}"><i class="fa fa-eye fa-btn"></i>查看Value</button>
                                    </@shiro.hasPermission>
                                    <@shiro.hasPermission name="ehcache:delete">
                                        <button class="btn btn-xs btn-danger deleteBtn" cacheName="${cacheName!}" key="${cache.key!}"><i class="fa fa-trash fa-btn"></i>删除</button>
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
        $(".seeBtn").click(function () {
            var cacheValue = $(this).attr("cacheValue");
            krt.layer.alert(cacheValue);
        })

        //删除
        $(".deleteBtn").click(function () {
            var cacheName = $(this).attr("cacheName");
            var key = $(this).attr("key");
            krt.layer.confirm("你确定删除缓存吗？", function () {
                $.ajax({
                    type: "POST",
                    url: "${basePath}/ehcache/delete",
                    data: {"cacheName": cacheName, "key": key},
                    success: function (msg) {
                        krt.layer.msg(msg.data);
                        if (msg.code == 200) {
                            window.location.href = window.location.href;
                        }
                    }
                });
            });
        });
    });
</script>
</@footer>

