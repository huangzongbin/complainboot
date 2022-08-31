<#include "validate.ftl">
<#--
组件
column 字段属性
genTable 表属性
doType 操作类型 insert：添加 update:修改
index 子表单索引 是否是子表单 是：!='-1' 不是 ='-1'
-->
<#macro component column genTable doType index>
<#-- 文本组件 -->
    <#if column.showType=='text' && doType=='insert'>
    <input type="text" id="${column.javaField!}" name="${column.javaField!}" class="form-control"<@validate (column.validate!"")/>>
    </#if>
    <#if column.showType=='text' && doType=='update'>
    <input type="text" id="${column.javaField!}" name="${column.javaField!}" value="${"$"}{${genTable.className?uncap_first}.${column.javaField!}!}" class="form-control"<@validate (column.validate!"")/>>
    </#if>
    <#if column.showType=='text' && doType=='query'>
    <input type="text" id="${column.javaField!}" name="${column.javaField!}" placeholder="${column.comment!}" class="form-control">
    </#if>
<#-- 多行文本组件 -->
    <#if column.showType=='textarea' && doType=='insert'>
    <textarea rows="2" name="${column.javaField!}" class="form-control"<@validate (column.validate!"")/>></textarea>
    </#if>
    <#if column.showType=='textarea' && doType=='update'>
    <textarea rows="2" name="${column.javaField!}" class="form-control"<@validate (column.validate!"")/>>${"$"}{${genTable.className?uncap_first}.${column.javaField!}!}</textarea>
    </#if>
    <#if column.showType=='textarea' && doType=='query'>
    <input type="text" id="${column.javaField!}" name="${column.javaField!}" placeholder="${column.comment!}" class="form-control">
    </#if>
<#-- 单选框组件 -->
    <#if column.showType=='radio' && doType=='insert'>
        <#if column.dicType ??>
        <${"@"}dic type="${column.dicType}" ; dicList>
            <${"#"}list dicList as item>
                <input type="radio" name="${column.javaField!}" class="icheck" value="${"$"}{item.code}"<@validate (column.validate!"")/>> ${"$"}{item.name}
            </${"#"}list>
        </${"@"}dic>
        </#if>
    </#if>
    <#if column.showType=='radio' && doType=='update'>
        <#if column.dicType ??>
        <${"@"}dic type="${column.dicType}" ; dicList>
            <${"#"}list dicList as item>
                <input type="radio" name="${column.javaField!}" class="icheck" value="${"$"}{item.code}"<@validate (column.validate!"")/> ${"$"}{((${genTable.className?uncap_first}.${column.javaField!}==item.code)${"?"}string("checked",""))!}> ${"$"}{item.name}
            </${"#"}list>
        </${"@"}dic>
        </#if>
    </#if>
    <#if column.showType=='radio' && doType=='query'>
        <#if column.dicType ??>
        <${"@"}dic type="${column.dicType}" ; dicList>
            <${"#"}list dicList as item>
                <input type="radio" name="${column.javaField!}" class="icheck" value="${"$"}{item.code}"> ${"$"}{item.name}
            </${"#"}list>
        </${"@"}dic>
        </#if>
    </#if>
<#-- 复选框组件 -->
    <#if column.showType=='checkbox' && doType=='insert'>
        <#if column.dicType ??>
        <${"@"}dic type="${column.dicType}" ; dicList>
            <${"#"}list dicList as item>
                <input type="checkbox" name="${column.javaField!}" class="icheck" value="${"$"}{item.code}"<@validate (column.validate!"")/>> ${"$"}{item.name}
            </${"#"}list>
        </${"@"}dic>
        </#if>
    </#if>
    <#if column.showType=='checkbox' && doType=='update'>
        <#if column.dicType ??>
        <${"@"}dic type="${column.dicType}" ; dicList>
            <${"#"}list dicList as item>
                <input type="checkbox" name="${column.javaField!}" class="icheck" value="${"$"}{item.code}"<@validate (column.validate!"")/>
                <${"#"}if (${genTable.className?uncap_first}.${column.javaField!} ?contains ( item.code))!false>checked</${"#"}if>
                > ${"$"}{item.name}
            </${"#"}list>
        </${"@"}dic>
        </#if>
    </#if>
    <#if column.showType=='checkbox' && doType=='query'>
        <#if column.dicType ??>
        <${"@"}dic type="${column.dicType}" ; dicList>
            <${"#"}list dicList as item>
                <input type="checkbox" name="${column.javaField!}" class="icheck" value="${"$"}{item.code}"> ${"$"}{item.name}
            </${"#"}list>
        </${"@"}dic>
        </#if>
    </#if>
<#-- 下拉框框组件 -->
    <#if column.showType=='select' && doType=='insert'>
    <select class="form-control select2" style="width: 100%" id="${column.javaField!}" name="${column.javaField!}"<@validate (column.validate!"")/>>
        <option value="">==请选择==</option>
        <#if column.dicType ??>
            <${"@"}dic type="${column.dicType}" ; dicList>
                <${"#"}list dicList as item>
                    <option value="${"$"}{item.code}">${"$"}{item.name}</option>
                </${"#"}list>
            </${"@"}dic>
        </#if>
    </select>
    </#if>
    <#if column.showType=='select' && doType=='update'>
    <select class="form-control select2" style="width: 100%" id="${column.javaField!}" name="${column.javaField!}"<@validate (column.validate!"")/>>
        <option value="">==请选择==</option>
        <#if column.dicType ??>
            <${"@"}dic type="${column.dicType}" ; dicList>
                <${"#"}list dicList as item>
                    <option value="${"$"}{item.code}" ${"$"}{((${genTable.className?uncap_first}.${column.javaField!}==item.code)${"?"}string("selected",""))!}>${"$"}{item.name}</option>
                </${"#"}list>
            </${"@"}dic>
        </#if>
    </select>
    </#if>
    <#if column.showType=='select' && doType=='query'>
    <div class="form-group">
        <select class="form-control select2" style="width: 100%" id="${column.javaField!}" name="${column.javaField!}">
            <option value="">==请选择==</option>
            <#if column.dicType ??>
                <${"@"}dic type="${column.dicType}" ; dicList>
                    <${"#"}list dicList as item>
                        <option value="${"$"}{item.code}">${"$"}{item.name}</option>
                    </${"#"}list>
                </${"@"}dic>
            </#if>
        </select>
    </div>
    </#if>
<#-- 编辑器组件 -->
    <#if column.showType=='editor' && doType=='insert'>
    <${"@"}krt.editor name="${column.javaField!}" validate='<@validate (column.validate!"")/>'></${"@"}krt.editor>
    </#if>
    <#if column.showType=='editor' && doType=='update'>
    <${"@"}krt.editor  name="${column.javaField!}" value="${"$"}{${genTable.className?uncap_first}.${column.javaField!}!}" validate='<@validate (column.validate!"")/>'></${"@"}krt.editor>
    </#if>
    <#if column.showType=='editor' && doType=='query'>
    <input type="text" id="${column.javaField!}" name="${column.javaField!}" class="form-control">
    </#if>
<#-- 日期组件 -->
    <#if column.showType=='date' && doType=='insert'>
    <div class="input-group input-group-addon-right-radius">
        <input type="text" class="form-control pull-right" name="${column.javaField!}" id="${column.javaField!}" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"<@validate (column.validate!"")/>>
        <div class="input-group-addon">
            <i class="fa fa-calendar"></i>
        </div>
    </div>
    </#if>
    <#if column.showType=='date' && doType=='update'>
    <div class="input-group input-group-addon-right-radius">
        <input type="text" class="form-control pull-right" name="${column.javaField!}" id="${column.javaField!}" value="${"$"}{(${genTable.className?uncap_first}.${column.javaField!}?string("yyyy-MM-dd HH:mm:ss"))!}" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"<@validate (column.validate!"")/>>
        <div class="input-group-addon">
            <i class="fa fa-calendar"></i>
        </div>
    </div>
    </#if>
    <#if column.showType=='date' && doType=='query'>
    <div class="input-group input-group-addon-right-radius">
        <input type="text" class="form-control pull-right" name="${column.javaField!}" id="${column.javaField!}" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
        <div class="input-group-addon">
            <i class="fa fa-calendar"></i>
        </div>
    </div>
    </#if>
<#-- 头像上传 -->
    <#if column.showType=='avatar' && doType=='insert'>
    <input type="hidden" name="${column.javaField!}" id="${column.javaField!}"/>
    <div id='uploader-${column.javaField!}' class="uploader photo-center"></div>
    <script type="text/javascript">
        $(function(){
            $('#uploader-${column.javaField!}').krtUploader({
                type: 'photo',
                chunk_size:"0",
                url: '${"$"}{basePath}/upload/fileUpload?dir=image',
                resultCallBack:function(data){
                    $('#uploader-${column.javaField!}').find('.img-photo').find('img').prop('src',data);
                    $("#${column.javaField!}").val(data);
                }
            });
        })
    </script>
    </#if>
    <#if column.showType=='avatar' && doType=='update'>
    <input type="hidden" name="${column.javaField!}" id="${column.javaField!}" value="${"$"}{${genTable.className?uncap_first}.${column.javaField!}!}"<@validate (column.validate!"")/>/>
    <div id='uploader-${column.javaField!}' class="uploader photo-center"></div>
    <script type="text/javascript">
        $(function () {
            $('#uploader-${column.javaField!}').krtUploader({
                type: 'photo',
                chunk_size:"0",
                url: '${"$"}{basePath}/upload/fileUpload?dir=image',
                resultCallBack: function (data) {
                    $('#uploader-${column.javaField!}').find('.img-photo').find('img').prop('src',data);
                    $("#${column.javaField!}").val(data);
                }
            });
            $('#uploader-${column.javaField!}').find('.img-photo').find('img').prop('src', $("#${column.javaField!}").val());
        })
    </script>
    </#if>
<#-- 图片组件 -->
    <#if column.showType=='img' && doType=='insert'>
    <div id='uploader-${column.javaField!}' class="uploader"></div>
    <input type="hidden" id="${column.javaField!}"  name="${column.javaField!}"<@validate (column.validate!"")/>>
    <script type="text/javascript">
        $(function () {
            $('#uploader-${column.javaField!}').krtUploader({
                type: 'grid-list',
                url: '${"$"}{basePath}/upload/fileUpload?dir=image',
                autoUpload: true,//自动上传
                chunk_size: "0",
                limitFilesCount: 1, //限定数量
                filters: { //限定格式
                    mime_types: [
                        {title: '图片', extensions: 'gif,jpg,jpeg,png,bmp'}
                    ]
                },
                deleteActionOnDone: function (file, doRemoveFile) {
                    doRemoveFile();
                },
                deleteConfirm: '是否删除图片',
                resultAllCallBack: function (data) {
                    $("#${column.javaField!}").val(data);
                },
            });
        });
    </script>
    </#if>
    <#if column.showType=='img' && doType=='update'>
    <div id='uploader-${column.javaField!}' class="uploader"></div>
    <input type="hidden" id="${column.javaField!}"  name="${column.javaField!}" value="${"$"}{${genTable.className?uncap_first}.${column.javaField!}!}"<@validate (column.validate!"")/>>
    <script type="text/javascript">
        $(function () {
            $('#uploader-${column.javaField!}').krtUploader({
                type: 'grid-list',
                url: '${"$"}{basePath}/upload/fileUpload?dir=image',
                autoUpload: true,//自动上传
                chunk_size: "0",
                limitFilesCount: 1, //限定数量
                filters: { //限定格式
                    mime_types: [
                        {title: '图片', extensions: 'gif,jpg,jpeg,png,bmp'}
                    ]
                },
                backFiles:$("#${column.javaField!}").val(),
                deleteActionOnDone: function (file, doRemoveFile) {
                    doRemoveFile();
                },
                deleteConfirm: '是否删除图片',
                resultAllCallBack: function (data) {
                    $("#${column.javaField!}").val(data);
                },
            });
        });
    </script>
    </#if>
<#-- 文件上传 -->
    <#if column.showType=='file' && doType=='insert'>
    <div id='uploader-${column.javaField!}' class="uploader"></div>
    <input type="hidden" id="${column.javaField!}"  name="${column.javaField!}"<@validate (column.validate!"")/>>
    <script type="text/javascript">
        $(function () {
            $('#uploader-${column.javaField!}').krtUploader({
                type: 'lg-list',
                url: '${"$"}{basePath}/upload/fileUpload?dir=file',
                autoUpload: true,//自动上传
                chunk_size: "0",
                limitFilesCount: 1, //限定数量
                filters: { //限定格式
                    mime_types: [
                        {title: '文件', extensions: 'doc,docx,xls,xlsx,ppt,zip,rar,pdf'}
                    ]
                },
                deleteActionOnDone: function (file, doRemoveFile) {
                    doRemoveFile();
                },
                deleteConfirm: '是否删除文件',
                resultAllCallBack: function (data) {
                    $("#${column.javaField!}").val(data);
                },
            });
        });
    </script>
    </#if>
    <#if column.showType=='file' && doType=='update'>
    <div id='uploader-${column.javaField!}' class="uploader"></div>
    <input type="hidden" id="${column.javaField!}"  name="${column.javaField!}" value="${"$"}{${genTable.className?uncap_first}.${column.javaField!}!}"<@validate (column.validate!"")/>>
    <script type="text/javascript">
        $(function () {
            $('#uploader-${column.javaField!}').krtUploader({
                type: 'lg-list',
                url: '${"$"}{basePath}/upload/fileUpload?dir=file',
                autoUpload: true,//自动上传
                chunk_size: "0",
                limitFilesCount: 1, //限定数量
                filters: { //限定格式
                    mime_types: [
                        {title: '文件', extensions: 'doc,docx,xls,xlsx,ppt,zip,rar,pdf'}
                    ]
                },
                backFiles:$("#${column.javaField!}").val(),
                deleteActionOnDone: function (file, doRemoveFile) {
                    doRemoveFile();
                },
                deleteConfirm: '是否删除文件',
                resultAllCallBack: function (data) {
                    $("#${column.javaField!}").val(data);
                },
            });
        });
    </script>
    </#if>
<#-- 父级树 -->
    <#if column.showType=='ptree' && column.name=='pid' && doType=='insert'>
    <!-- 参数 -->
    <input type="hidden" name="pid" id="pid" value="${"$"}{(p${genTable.className}.id)!}" class="form-control">
    <div class="input-group">
        <input type="text" name="pname" readonly id="pname" value="${"$"}{(p${genTable.className}.name)!}" class="form-control">
        <div class="input-group-btn">
            <button class="btn btn-default" id="pTreeBtn" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>
    </div>
    <script type="text/javascript">
        $("#pname,#pTreeBtn").click(function () {
            var url = "${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/${genTable.className?uncap_first}Tree";
            krt.layer.open({
                type: 2,
                area: ['310px', '450px'],
                title: "选择上级${genTable.comment}",
                maxmin: false, //开启最大化最小化按钮
                content: "${"$"}{basePath}/common/treeSelect?url="+encodeURI(url)+"&sValue="+$("#pid").val(),
                btn: ['确定','取消', '关闭'],
                yes: function (index, layero) {
                    var tree = layero.find("iframe")[0].contentWindow.tree;
                    var nodes = tree.getSelectedNodes();
                    if (nodes == null || nodes == '') {
                        krt.layer.msg("请选择${genTable.comment}");
                    } else {
                        $("#pname").val(nodes[0].name);
                        $("#pid").val(nodes[0].id);
                        krt.layer.close(index);
                    }
                },
                btn2: function () {
                    $("#pname").val("");
                    $("#pid").val("");
                },
                cancel: function (index) {
                    krt.layer.close(index);
                }
            });
        });
    </script>
    </#if>
    <#if column.showType=='ptree' && column.name=='pid' && doType=='update'>
    <!-- 参数 -->
    <input type="hidden" name="pid" id="pid" value="${"$"}{(p${genTable.className}.id)!}" class="form-control">
    <div class="input-group">
        <input type="text" name="pname" readonly value="${"$"}{(p${genTable.className}.name)!}" id="pname" class="form-control">
        <div class="input-group-btn">
            <button class="btn btn-default" id="pTreeBtn" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>
    </div>
    <script type="text/javascript">
        $("#pname,#pTreeBtn").click(function () {
            var url = "${"$"}{basePath}/${namespace}/${genTable.className?uncap_first}/${genTable.className?uncap_first}Tree";
            krt.layer.open({
                type: 2,
                area: ['310px', '450px'],
                title: "选择上级${genTable.comment}",
                maxmin: false, //开启最大化最小化按钮
                content: "${"$"}{basePath}/common/treeSelect?url="+encodeURI(url)+"&sValue="+$("#pid").val(),
                btn: ['确定','取消', '关闭'],
                yes: function (index, layero) {
                    var tree = layero.find("iframe")[0].contentWindow.tree;
                    var nodes = tree.getSelectedNodes();
                    if (nodes == null || nodes == '') {
                        krt.layer.msg("请选择${genTable.comment}");
                    } else {
                        $("#pname").val(nodes[0].name);
                        $("#pid").val(nodes[0].id);
                        krt.layer.close(index);
                    }
                },
                btn2: function () {
                    $("#pname").val("");
                    $("#pid").val("");
                },
                cancel: function (index) {
                    krt.layer.close(index);
                }
            });
        });
    </script>
    </#if>
    <#if column.showType=='ptree' && column.name=='pid' && doType=='query'>
    <input type="text" id="${column.javaField!}" name="${column.javaField!}" class="form-control">
    </#if>
<#-- 单选树 -->
    <#if column.showType=='rTree' && doType=='insert'>
    <div class="input-group">
        <input type="text" name="${column.javaField!}"  readonly id="${column.javaField!}" class="form-control">
        <div class="input-group-btn">
            <button class="btn btn-default" id="${column.javaField?replace('Name', '')}Btn" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>
    </div>
    <script type="text/javascript">
    $("#${column.javaField!},#${column.javaField?replace('Name', '')}Btn").click(function () {
        var url = "${"$"}{basePath}/${namespace}/${column.genTable.className?uncap_first}/${column.genTable.className?uncap_first}Tree";
        krt.layer.open({
            type: 2,
            area: ['310px', '450px'],
            title: "选择上级${column.genTable.comment}",
            maxmin: false,
            content: "${"$"}{basePath}/common/treeSelect?url="+encodeURI(url)+"&sValue="+$("#${column.javaField?replace('Name', 'Id')}").val(),
            btn: ['确定','取消', '关闭'],
            yes: function (index, layero) {
                var tree = layero.find("iframe")[0].contentWindow.tree;
                var nodes = tree.getSelectedNodes();
                if (nodes == null || nodes == '') {
                    krt.layer.msg("请选择${column.genTable.comment}");
                } else {
                    $("#${column.javaField?replace('Name', 'Id')}").val(nodes[0].id);
                    $("#${column.javaField!}").val(nodes[0].name);
                    krt.layer.close(index);
                }
            },
            btn2: function () {
                $("#${column.javaField?replace('Name', 'Id')}").val("");
                $("#${column.javaField!}").val("");
            },
            cancel: function (index) {
                krt.layer.close(index);
            }
        });
    });
    </script>
    </#if>
    <#if column.showType=='rTree'  && doType=='update'>
    <div class="input-group">
        <input type="text" name="${column.javaField!}" id="${column.javaField!}" readonly value="${"$"}{${genTable.className?uncap_first}.${column.javaField!}!}" class="form-control">
        <div class="input-group-btn">
            <button class="btn btn-default" id="${column.javaField?replace('Name', '')}Btn" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>
    </div>
    <script type="text/javascript">
        $("#${column.javaField!},#${column.javaField?replace('Name', '')}Btn").click(function () {
            var url = "${"$"}{basePath}/${namespace}/${column.genTable.className?uncap_first}/${column.genTable.className?uncap_first}Tree";
            krt.layer.open({
                type: 2,
                area: ['310px', '450px'],
                title: "选择上级${column.genTable.comment}",
                maxmin: false,
                content: "${"$"}{basePath}/common/treeSelect?url="+encodeURI(url)+"&sValue="+$("#${column.javaField?replace('Name', 'Id')}").val(),
                btn: ['确定','取消', '关闭'],
                yes: function (index, layero) {
                    var tree = layero.find("iframe")[0].contentWindow.tree;
                    var nodes = tree.getSelectedNodes();
                    if (nodes == null || nodes == '') {
                        krt.layer.msg("请选择${column.genTable.comment}");
                    } else {
                        $("#${column.javaField?replace('Name', 'Id')}").val(nodes[0].id);
                        $("#${column.javaField!}").val(nodes[0].name);
                        krt.layer.close(index);
                    }
                },
                btn2: function () {
                    $("#${column.javaField?replace('Name', 'Id')}").val("");
                    $("#${column.javaField!}").val("");
                },
                cancel: function (index) {
                    krt.layer.close(index);
                }
            });
        });
    </script>
    </#if>
<#-- 多选树 -->
    <#if column.showType=='cTree' && doType=='insert'>
    <div class="input-group">
        <input type="text" name="${column.javaField!}" readonly id="${column.javaField!}" class="form-control">
        <div class="input-group-btn">
            <button class="btn btn-default" id="${column.javaField?replace('Name', '')}Btn" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>
    </div>
    <script type="text/javascript">
        $("#${column.javaField!},#${column.javaField?replace('Name', '')}Btn").click(function () {
            var url = "${"$"}{basePath}/${namespace}/${column.genTable.className?uncap_first}/${column.genTable.className?uncap_first}Tree";
            krt.layer.open({
                type: 2,
                area: ['310px', '450px'],
                title: "选择上级${column.genTable.comment}",
                maxmin: false,
                content: "${"$"}{basePath}/common/treeSelect?url="+encodeURI(url)+"&checkbox=true&sValue="+$("#${column.javaField?replace('Name', 'Id')}").val(),
                btn: ['确定','取消', '关闭'],
                yes: function (index, layero) {
                    var tree = layero.find("iframe")[0].contentWindow.tree;
                    var nodes  = tree.getCheckedNodes(true);
                    if (nodes == null || nodes == '') {
                        krt.layer.msg("请选择上级${column.genTable.comment}");
                    } else {
                        var nodesIds = new Array();
                        var nodeNames = new Array();
                        for (var i = 0; i < nodes.length; i++) {
                            nodesIds[i] = nodes[i].id;
                            nodeNames[i] = nodes[i].name;
                        }
                        var ${column.javaField?replace('Name', 'Id')} = nodesIds.join(",");
                        var ${column.javaField!} = nodeNames.join(",");
                        $("#${column.javaField?replace('Name', 'Id')}").val(${column.javaField?replace('Name', 'Id')});
                        $("#${column.javaField!}").val(${column.javaField!});
                        krt.layer.close(index);
                    }
                },
                btn2: function () {
                    $("#${column.javaField?replace('Name', 'Id')}").val("");
                    $("#${column.javaField!}").val("");
                },
                cancel: function (index) {
                    krt.layer.close(index);
                }
            });
        });
    </script>
    </#if>
    <#if column.showType=='cTree'  && doType=='update'>
    <div class="input-group">
        <input type="text" name="${column.javaField!}" readonly value="${"$"}{${genTable.className?uncap_first}.${column.javaField!}!}" id="${column.javaField!}" class="form-control">
        <div class="input-group-btn">
            <button class="btn btn-default" id="${column.javaField?replace('Name', '')}Btn" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>
    </div>
    <script type="text/javascript">
        $("#${column.javaField!},#${column.javaField?replace('Name', '')}Btn").click(function () {
            var url = "${"$"}{basePath}/${namespace}/${column.genTable.className?uncap_first}/${column.genTable.className?uncap_first}Tree";
            krt.layer.open({
                type: 2,
                area: ['310px', '450px'],
                title: "选择上级${column.genTable.comment}",
                maxmin: false,
                content: "${"$"}{basePath}/common/treeSelect?url="+encodeURI(url)+"&checkbox=true&sValue="+$("#${column.javaField?replace('Name', 'Id')}").val(),
                btn: ['确定','取消', '关闭'],
                yes: function (index, layero) {
                    var tree = layero.find("iframe")[0].contentWindow.tree;
                    var nodes  = tree.getCheckedNodes(true);
                    if (nodes == null || nodes == '') {
                        krt.layer.msg("请选择上级${column.genTable.comment}");
                    } else {
                        var nodesIds = new Array();
                        var nodeNames = new Array();
                        for (var i = 0; i < nodes.length; i++) {
                            nodesIds[i] = nodes[i].id;
                            nodeNames[i] = nodes[i].name;
                        }
                        var ${column.javaField?replace('Name', 'Id')} = nodesIds.join(",");
                        var ${column.javaField!} = nodeNames.join(",");
                        $("#${column.javaField?replace('Name', 'Id')}").val(${column.javaField?replace('Name', 'Id')});
                        $("#${column.javaField!}").val(${column.javaField!});
                        krt.layer.close(index);
                    }
                },
                btn2: function () {
                    $("#${column.javaField?replace('Name', 'Id')}").val("");
                    $("#${column.javaField!}").val("");
                },
                cancel: function (index) {
                    krt.layer.close(index);
                }
            });
        });
    </script>
    </#if>
<#-- 单选List -->
    <#if column.showType=='rList' && doType=='insert'>
    <div class="input-group">
        <input type="text" name="${column.javaField!}" id="${column.javaField!}" class="form-control" readonly<@validate (column.validate!"")/>>
        <div class="input-group-btn">
            <button class="btn btn-default" id="${column.javaField?replace('Name', '')}Btn" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $("#${column.javaField!},#${column.javaField?replace('Name', '')}Btn").click(function () {
                var url = "${namespace}/${genTable.className?uncap_first}/${column.genTable.className?uncap_first}List";
                var id = $("#${column.javaField?replace('Name', 'Id')}").val();
                var name = $("#${column.javaField!}").val();
                krt.layer.open({
                    type: 2,
                    area: ['1000px', '600px'],
                    title: "选择${column.genTable.comment}",
                    maxmin: true, //开启最大化最小化按钮
                    content: "${"$"}{basePath}/common/listSelect?url="+encodeURI(url)+"&sId=" + id + "&sName=" + name,
                    btn: ['确定','取消', '关闭'],
                    yes: function (index, layero) {
                        var sId = layero.find("iframe")[0].contentWindow.$("#sId").val();
                        var sName = layero.find("iframe")[0].contentWindow.$("#sName").val();
                        if (sId == null || sId == '') {
                            krt.layer.msg("请选择${column.genTable.comment}");
                        } else {
                            $("#${column.javaField!}").val(sName);
                            $("#${column.javaField?replace('Name', 'Id')}").val(sId);
                            krt.layer.close(index);
                        }
                    },
                    btn2: function () {
                        $("#${column.javaField?replace('Name', 'Id')}").val("");
                        $("#${column.javaField!}").val("");
                    },
                    cancel: function (index) {
                        krt.layer.close(index);
                    }
                });
            });
        })
    </script>
    </#if>
    <#if column.showType=='rList'  && doType=='update'>
    <div class="input-group">
        <input type="text" name="${column.javaField!}" value="${"$"}{${genTable.className?uncap_first}.${column.javaField!}!}" id="${column.javaField!}" class="form-control" readonly<@validate (column.validate!"")/>>
        <div class="input-group-btn">
            <button class="btn btn-default" id="${column.javaField?replace('Name', '')}Btn" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $("#${column.javaField!},#${column.javaField?replace('Name', '')}Btn").click(function () {
                var url = "${namespace}/${genTable.className?uncap_first}/${column.genTable.className?uncap_first}List";
                var id = $("#${column.javaField?replace('Name', 'Id')}").val();
                var name = $("#${column.javaField!}").val();
                krt.layer.open({
                    type: 2,
                    area: ['1000px', '600px'],
                    title: "选择${column.genTable.comment}",
                    maxmin: true, //开启最大化最小化按钮
                    content: "${"$"}{basePath}/common/listSelect?url="+encodeURI(url)+"&sId=" + id + "&sName=" + name,
                    btn: ['确定','取消', '关闭'],
                    yes: function (index, layero) {
                        var sId = layero.find("iframe")[0].contentWindow.$("#sId").val();
                        var sName = layero.find("iframe")[0].contentWindow.$("#sName").val();
                        if (sId == null || sId == '') {
                            krt.layer.msg("请选择${column.genTable.comment}");
                        } else {
                            $("#${column.javaField!}").val(sName);
                            $("#${column.javaField?replace('Name', 'Id')}").val(sId);
                            krt.layer.close(index);
                        }
                    },
                    btn2: function () {
                        $("#${column.javaField?replace('Name', 'Id')}").val("");
                        $("#${column.javaField!}").val("");
                    },
                    cancel: function (index) {
                        krt.layer.close(index);
                    }
                });
            });
        })
    </script>
    </#if>
<#-- 多选List -->
    <#if column.showType=='cList' && doType=='insert'>
    <div class="input-group">
        <input type="text" name="${column.javaField!}" id="${column.javaField!}" class="form-control" readonly<@validate (column.validate!"")/>>
        <div class="input-group-btn">
            <button class="btn btn-default" id="${column.javaField?replace('Name', '')}Btn" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $("#${column.javaField!},#${column.javaField?replace('Name', '')}Btn").click(function () {
                var url = "${namespace}/${genTable.className?uncap_first}/${column.genTable.className?uncap_first}Lists";
                var id = $("#${column.javaField?replace('Name', 'Id')}").val();
                var name = $("#${column.javaField!}").val();
                krt.layer.open({
                    type: 2,
                    area: ['1000px', '600px'],
                    title: "选择${column.genTable.comment}",
                    maxmin: true, //开启最大化最小化按钮
                    content: "${"$"}{basePath}/common/listSelect?url="+encodeURI(url)+"&sId=" + id + "&sName=" + name,
                    btn: ['确定','取消', '关闭'],
                    yes: function (index, layero) {
                        var sId = layero.find("iframe")[0].contentWindow.$("#sId").val();
                        var sName = layero.find("iframe")[0].contentWindow.$("#sName").val();
                        if (sId == null || sId == '') {
                            krt.layer.msg("请选择${column.genTable.comment}");
                        } else {
                            $("#${column.javaField!}").val(sName);
                            $("#${column.javaField?replace('Name', 'Id')}").val(sId);
                            krt.layer.close(index);
                        }
                    },
                    btn2: function () {
                        $("#${column.javaField?replace('Name', 'Id')}").val("");
                        $("#${column.javaField!}").val("");
                    },
                    cancel: function (index) {
                        krt.layer.close(index);
                    }
                });
            });
        })
    </script>
    </#if>
    <#if column.showType=='cList'  && doType=='update'>
    <div class="input-group">
        <input type="text" name="${column.javaField!}" value="${"$"}{${genTable.className?uncap_first}.${column.javaField!}!}" id="${column.javaField!}" class="form-control" readonly<@validate (column.validate!"")/>>
        <div class="input-group-btn">
            <button class="btn btn-default" id="${column.javaField?replace('Name', '')}Btn" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>
    </div>
    <script type="text/javascript">
        $(function () {
            $("#${column.javaField!},#${column.javaField?replace('Name', '')}Btn").click(function () {
                var url = "${namespace}/${genTable.className?uncap_first}/${column.genTable.className?uncap_first}Lists";
                var id = $("#${column.javaField?replace('Name', 'Id')}").val();
                var name = $("#${column.javaField!}").val();
                krt.layer.open({
                    type: 2,
                    area: ['1000px', '600px'],
                    title: "选择${column.genTable.comment}",
                    maxmin: true, //开启最大化最小化按钮
                    content: "${"$"}{basePath}/common/listSelect?url="+encodeURI(url)+"&sId=" + id + "&sName=" + name,
                    btn: ['确定','取消', '关闭'],
                    yes: function (index, layero) {
                        var sId = layero.find("iframe")[0].contentWindow.$("#sId").val();
                        var sName = layero.find("iframe")[0].contentWindow.$("#sName").val();
                        if (sId == null || sId == '') {
                            krt.layer.msg("请选择${column.genTable.comment}");
                        } else {
                            $("#${column.javaField!}").val(sName);
                            $("#${column.javaField?replace('Name', 'Id')}").val(sId);
                            krt.layer.close(index);
                        }
                    },
                    btn2: function () {
                        $("#${column.javaField?replace('Name', 'Id')}").val("");
                        $("#${column.javaField!}").val("");
                    },
                    cancel: function (index) {
                        krt.layer.close(index);
                    }
                });
            });
        })
    </script>
    </#if>
</#macro>
