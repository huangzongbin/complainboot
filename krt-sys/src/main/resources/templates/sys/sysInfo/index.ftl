<#include "/common/layoutForm.ftl">
<@header></@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">服务器监控</h3>
                        </div>
                        <div class="box-body">
                            <div class="row row-space15">
                                <div class="col-sm-12 col-md-4">
                                    <div class="box-without-boder">
                                        <div class="box-header">
                                            <i class="fa fa-pie-chart"></i>&nbsp;CPU
                                        </div>
                                        <div class="box-body">
                                            <div id="cpu"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-12 col-md-4">
                                    <div class="box-without-boder">
                                        <div class="box-header">
                                            <i class="fa fa-pie-chart"></i>&nbsp;总内存
                                        </div>
                                        <div class="box-body">
                                            <div id="ram"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-12 col-md-4">
                                    <div class="box-without-boder">
                                        <div class="box-header">
                                            <i class="fa fa-pie-chart"></i>&nbsp;JVM
                                        </div>
                                        <div class="box-body">
                                            <div id="jvm"></div>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-sm-12">
                                    <div class="box-without-boder">
                                        <div class="box-header">
                                            <i class="fa fa-dashboard"></i>&nbsp;堆/非堆
                                        </div>
                                        <div class="box-body">
                                            <table class="table table-bordered table-hover">
                                                <thead>
                                                <tr>
                                                    <th>属性</th>
                                                    <th>初始大小</th>
                                                    <th>最大内存</th>
                                                    <th>已用内存</th>
                                                    <th>可用内存</th>
                                                </tr>
                                                </thead>
                                                <tbody id="jvmHeap"></tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-sm-12">
                                    <div class="box-without-boder">
                                        <div class="box-header">
                                            <i class="fa fa-file"></i>&nbsp;磁盘状态
                                        </div>
                                        <div class="box-body">
                                            <table class="table table-bordered table-hover">
                                                <thead>
                                                <tr>
                                                    <th>#</th>
                                                    <th>盘符路径</th>
                                                    <th>文件系统</th>
                                                    <th>盘符类型</th>
                                                    <th>总大小</th>
                                                    <th>可用大小</th>
                                                    <th>已用百分比</th>
                                                </tr>
                                                </thead>
                                                <tbody id="disk"></tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </section>
    </div>
</@body>
<@footer>
    <script type="text/javascript" src="${basePath}/plugin/svgDoughnutChart/jquery.svgDoughnutChart.min.js"></script>
    <script type="text/javascript">
        function cpuMemory() {
            $.get("${basePath}/sys/sysInfo/cpuMemory", function (rb) {
                var data = rb.data;
                $('#cpu').doughnutChart({
                    positiveColor: 'orange',
                    backgroundColor: 'white',
                    percentage: data.cpu.used.replace('%', ''),
                    innerText: data.cpu.used,
                    Title: data.cpu.kernel + ' CPU' + data.cpu.power,
                    positiveText: data.cpu.used + '(U' + data.cpu.user + ",S" + data.cpu.system + ')',
                    negativeText: data.cpu.idle
                });
                $('#ram').doughnutChart({
                    positiveColor: 'orange',
                    backgroundColor: 'white',
                    percentage: data.system.usedPercentage.replace('%', ''),
                    innerText: data.system.usedPercentage,
                    Title: data.system.total,
                    positiveText: data.system.usedPercentage + ' ' + data.system.used,
                    negativeText: data.system.availablePercentage + ' ' + data.system.available
                });
                $('#jvm').doughnutChart({
                    positiveColor: 'orange',
                    backgroundColor: 'white',
                    percentage: data.jvm.usedPercentage.replace('%', ''),
                    innerText: data.jvm.usedPercentage,
                    Title: data.jvm.total,
                    positiveText: data.jvm.usedPercentage + ' ' + data.jvm.used,
                    negativeText: data.jvm.availablePercentage + ' ' + data.jvm.available
                });
            })
        }

        // 2 秒刷新一次
        cpuMemory();
        setInterval(function () {
            cpuMemory()
        }, 3000);

        $.get("${basePath}/sys/sysInfo/jvmHeapDisk", function (rb) {
            var data = rb.data;
            var jh = data.heap, jn = data.nonHeap;
            $('#jvmHeap').html('<tr><td>堆</td><td>' + jh.init + '</td><td>' + jh.max + '</td><td>' + jh.used + '</td><td>' + jh.committed
                + '</td></tr><tr><td>非堆</td><td>' + jn.init + '</td><td>' + jn.max + '</td><td>' + jn.used + '</td><td>' + jn.committed + '</td></tr>');
            var text = '', dl = data.diskList;
            for (var i = 0; i < dl.length; i++) {
                var d = dl[i];
                text += '<tr><td>' + i + '</td><td>' + d.name + '</td><td>' + d.mount + '</td><td>' + d.type + '</td><td>' + d.total;
                text += '</td><td>' + d.usable + '</td><td>' + d.percentage + '</td></tr>';
            }
            $('#disk').html(text);
        });
    </script>
</@footer>
