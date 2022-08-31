<#include "/common/layoutForm.ftl">
<@header>
    <style>
        .tab-footer {
            /*left: 0;*/
            /*bottom: 0;*/
            /*position: fixed;*/
            width: 100%;
            text-align: center;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
            border-bottom-right-radius: 3px;
            border-bottom-left-radius: 3px;
            border-top: 1px solid #f4f4f4;
            padding: 10px;
            background-color: #ffffff;
        }
    </style>
</@header>
<@body class="body-bg-default">
    <div class="wrapper">
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <form action="${basePath}/gen/genTable/insert" method="post" id="krtForm" class="form-horizontal">
                        <div class="nav-tabs-custom">
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="#tab_1-1" data-toggle="tab">基本信息</a></li>
                                <li><a href="#tab_2-2" data-toggle="tab">字段信息</a></li>
                                <li><a href="#tab_3-2" data-toggle="tab">生成信息</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active" id="tab_1-1">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label for="code" class="control-label col-sm-4">
                                                    <span class="form-required">*</span>表名：
                                                </label>
                                                <div class="col-sm-5">
                                                    <input type="text" name="genTable.name" id="name" value="${(gTable.name)!}" readonly required class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label for="code" class="control-label col-sm-4">
                                                    <span class="form-required">*</span>注释：
                                                </label>
                                                <div class="col-sm-5">
                                                    <input type="text" name="genTable.comment" id="comment" value="${(gTable.comment)!}" required class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label for="code" class="control-label col-sm-4">
                                                    <span class="form-required">*</span>类名：
                                                </label>
                                                <div class="col-sm-5">
                                                    <input type="text" name="genTable.className" id="className" value="${(className)!}" required class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label for="code" class="control-label col-sm-4">
                                                    父表名：
                                                </label>
                                                <div class="col-sm-5">
                                                    <select name="genTable.parentTable" id="genTable.parentTable" class="select2 form-control" style="width: 100%">
                                                        <option value="">请选择</option>
                                                        <#list dbTableList as table>
                                                            <option value="${table.name}">${table.name}</option>
                                                        </#list>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label for="code" class="control-label col-sm-4">
                                                    父表关联字段：
                                                </label>
                                                <div class="col-sm-5">
                                                    <select name="genTable.parentTableFk" id="genTable.parentTableFk" class="select2 form-control" style="width: 100%">
                                                        <option value="">请选择</option>
                                                        <#list tableColumnList as column>
                                                            <option value="${column.name}">${column.name}</option>
                                                        </#list>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- /.tab-pane -->
                                <div class="tab-pane table-responsive" id="tab_2-2">
                                    <table class="table table-bordered table-krt" style="text-align:center"
                                           id="field_list_table">
                                        <thead id="gridThead">
                                        <tr>
                                            <td rowspan="2" class="active">#</td>
                                            <td colspan="5" class="active">字段</td>
                                            <td colspan="5" class="active">列表</td>
                                            <td colspan="7" class="active">表单</td>
                                        </tr>
                                        <tr>
                                            <td class="active" style="text-align: left">列名</td>
                                            <td class="active" style="text-align: left">说明</td>
                                            <td class="active" style="text-align: left">字段类型</td>
                                            <td class="active" style="text-align: left">属性类型</td>
                                            <td class="active" style="text-align: left">属性名称</td>
                                            <td class="active">导入</td>
                                            <td class="active">导出</td>
                                            <td class="active">列表</td>
                                            <td class="active">查询</td>
                                            <td class="active">匹配方式</td>
                                            <td class="active">插入</td>
                                            <td class="active">更新</td>
                                            <td class="active">控件类型</td>
                                            <td class="active">栅格</td>
                                            <td class="active">字典类型</td>
                                            <td class="active">数据绑定</td>
                                            <td class="active">字段验证</td>
                                        </tr>
                                        </thead>
                                        <tbody id="gridtbody">
                                        <#list tableColumnList as column>
                                            <tr>
                                                <td style="text-align: left">${column_index+1}</td>
                                                <td style="text-align: left">
                                                    ${column.name!}
                                                    <input type="hidden" name="genTableColumns[${column_index}].name" value="${column.name!}" class="form-control">
                                                </td>
                                                <td style="text-align: left">
                                                    <input type="text" name="genTableColumns[${column_index}].comment" value="${column.comment!}" class="form-control" required>
                                                </td>
                                                <td style="text-align: left">
                                                    ${column.jdbcType!}
                                                    <input type="hidden" name="genTableColumns[${column_index}].jdbcType" value="${column.jdbcType!}" class="form-control">
                                                </td>
                                                <td style="text-align: left">
                                                    ${column.javaType!}
                                                    <input type="hidden" name="genTableColumns[${column_index}].javaType" value="${column.javaType!}" class="form-control">
                                                </td>
                                                <td style="text-align: left">
                                                    ${column.javaField!}
                                                    <input type="hidden" name="genTableColumns[${column_index}].javaField" value="${column.javaField!}" class="form-control">
                                                </td>
                                                <td>
                                                    <input type="checkbox" name="genTableColumns[${column_index}].excelIn" class="icheck" value="0" <#if (column.excelIn)??>${(column.excelIn=='0')?string('checked','')}</#if>>
                                                </td>
                                                <td>
                                                    <input type="checkbox" name="genTableColumns[${column_index}].excelOut" class="icheck" value="0" <#if (column.excelOut)??>${(column.excelOut=='0')?string('checked','')}</#if>>
                                                </td>
                                                <td>
                                                    <input type="checkbox" name="genTableColumns[${column_index}].isList" class="icheck" value="0" <#if (column.isList)??>${(column.isList=='0')?string('checked','')}</#if>>
                                                </td>
                                                <td>
                                                    <input type="checkbox" name="genTableColumns[${column_index}].isQuery" class="icheck" value="0" <#if (column.isQuery)??>${(column.isQuery=='0')?string('checked','')}</#if>>
                                                </td>
                                                <td>
                                                    <select name="genTableColumns[${column_index}].queryType"
                                                            class="select2 form-control" style="width: 80px;">
                                                        <option value="">请选择</option>
                                                        <option value="1">=</option>
                                                        <option value="2">!=</option>
                                                        <option value="3">&gt;</option>
                                                        <option value="4">&gt;=</option>
                                                        <option value="5">&lt;</option>
                                                        <option value="6">&lt;=</option>
                                                        <option value="7">Like</option>
                                                        <option value="8">左Like</option>
                                                        <option value="9">右Like</option>
                                                    </select>
                                                </td>
                                                <td><input type="checkbox" name="genTableColumns[${column_index}].isInsert" class="icheck" value="0"></td>
                                                <td><input type="checkbox" name="genTableColumns[${column_index}].isEdit" class="icheck" value="0"></td>
                                                <td>
                                                    <select name="genTableColumns[${column_index}].showType" class="select2 form-control" style="width: 100px;">
                                                        <option value="">请选择</option>
                                                        <option value="text">文本框</option>
                                                        <option value="hidden">隐藏域</option>
                                                        <option value="textarea">多行文本框</option>
                                                        <option value="select">下拉框</option>
                                                        <option value="radio">单选框</option>
                                                        <option value="checkbox">复选框</option>
                                                        <option value="date">日期框</option>
                                                        <option value="file">文件上传</option>
                                                        <option value="img">图片上传</option>
                                                        <option value="avatar">头像上传</option>
                                                        <option value="editor">编辑器</option>
                                                        <option value="ptree">父级树</option>
                                                        <option value="rTree">单选树</option>
                                                        <option value="cTree">多选树</option>
                                                        <option value="rList">单选列表</option>
                                                        <option value="cList">多选列表</option>
                                                    </select>
                                                </td>
                                                <td>
                                                    <select name="genTableColumns[${column_index}].grid"
                                                            class="select2 form-control" style="width: 100px;">
                                                        <option role="option" value="6/4/8">6/4/8 两列</option>
                                                        <option role="option" value="12/2/10">12/2/10 一列</option>
                                                        <option role="option" value="4/4/8">4/4/8 三列</option>
                                                        <option role="option" value="3/4/8">3/4/8 四列</option>
                                                    </select>
                                                </td>
                                                <td>
                                                    <select name="genTableColumns[${column_index}].dicType"
                                                            class="select2 form-control" style="width: 100px;">
                                                        <option value="">请选择</option>
                                                        <#list dicTypeList as dicType>
                                                            <option value="${dicType.code}">${dicType.name}</option>
                                                        </#list>
                                                    </select>
                                                </td>
                                                <td>
                                                    <select name="genTableColumns[${column_index}].dataTable"
                                                            class="select2 form-control" style="width: 100px;">
                                                        <option value="">请选择</option>
                                                        <#list  dbTableList as table>
                                                            <option value="${table.name}">${table.name}</option>
                                                        </#list>
                                                    </select>
                                                </td>
                                                <td>
                                                    <select name="genTableColumns[${column_index}].validate"
                                                            class="select2 form-control" style="width: 100px;"
                                                            multiple="multiple">
                                                        <option value="">请选择</option>
                                                        <option value="required">必填</option>
                                                        <option value="isUserName">用户名</option>
                                                        <option value="isMobile">手机号码</option>
                                                        <option value="isTel">电话号码</option>
                                                        <option value="isPhone">手机号码||电话号码</option>
                                                        <option value="isStringAndNum">字母数字</option>
                                                        <option value="chcharacter">汉字</option>
                                                        <option value="isZipCode">邮政编码</option>
                                                        <option value="rangelength">长度限制</option>
                                                    </select>
                                                </td>
                                                <input type="hidden" name="genTableColumns[${column_index}].sort"
                                                       value="${column.sort}" readonly style="width: 50px"
                                                       class="order form-control">
                                            </tr>
                                        </#list>
                                        </tbody>
                                    </table>
                                </div>
                                <!-- /.tab-pane -->
                                <div class="tab-pane" id="tab_3-2">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label for="code" class="control-label col-sm-4">
                                                    <span class="form-required">*</span>生成模板：
                                                </label>
                                                <div class="col-sm-5">
                                                    <select name="genScheme.template" required class="form-control select2" style="width: 100%">
                                                        <option value="">请选择</option>
                                                        <option value="common">单表</option>
                                                        <option value="otmForm">主从表</option>
                                                        <option value="tree">树机构</option>
                                                        <option value="treeList">左树右列表</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label for="code" class="control-label col-sm-4">
                                                    <span class="form-required">*</span>表单风格：
                                                </label>
                                                <div class="col-sm-5">
                                                    <select name="genScheme.formType" required class="form-control select2" style="width: 100%">
                                                        <option value="">请选择</option>
                                                        <option value="dialog">弹窗</option>
                                                        <option value="tab">Tab</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label for="code" class="control-label col-sm-4">
                                                    <span class="form-required">*</span>生成结构：
                                                </label>
                                                <div class="col-sm-5">
                                                    <input type="checkbox" name="genScheme.fileType" class="icheck" value="entity" checked required> Entity
                                                    <input type="checkbox" name="genScheme.fileType" class="icheck" value="mapper" checked required> Mapper
                                                    <input type="checkbox" name="genScheme.fileType" class="icheck" value="mapperXml" checked required> MapperXml
                                                    <input type="checkbox" name="genScheme.fileType" class="icheck" value="service" checked required> Service && ServiceImpl
                                                    <input type="checkbox" name="genScheme.fileType" class="icheck" value="controller" checked required> Controller
                                                    <input type="checkbox" name="genScheme.fileType" class="icheck" value="ftl" checked required> FTL
                                                    <input type="checkbox" name="genScheme.fileType" class="icheck" value="permission" checked required> Permission
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label for="code" class="control-label col-sm-4">
                                                    <span class="form-required">*</span>包名：
                                                </label>
                                                <div class="col-sm-5">
                                                    <input type="text" name="genScheme.packageName" id="packageName" required class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label for="code" class="control-label col-sm-4">
                                                    权限模块名：
                                                </label>
                                                <div class="col-sm-5">
                                                    <input type="text" name="genScheme.permissionName" id="permissionName" required class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label for="code" class="control-label col-sm-4">
                                                    程序员：
                                                </label>
                                                <div class="col-sm-5">
                                                    <input type="text" name="genScheme.coder" id="coder" required class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
                                                <label for="code" class="control-label col-sm-4">
                                                    代码生成路径：
                                                </label>
                                                <div class="col-sm-5">
                                                    <input type="text" name="genScheme.codePath" id="codePath" required class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-footer">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group" style="margin-bottom:0px;">
                                            <div style="text-align: center">
                                                <button type="button" id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> 提交</button>
                                                <button type="button" id="closeIframeBtn" class="btn btn-danger"><i class="fa fa-close"></i> 关闭</button>
                                                <button type="reset" id="backBtn" class="btn btn-default"><i class="fa fa-refresh"></i> 上一步</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </div>
</@body>
<@footer>
    <script type="text/javascript" src="${basePath}/plugin/jquery-dragsort/jquery.dragsort.js"></script>
    <script type="text/javascript">
        var validateForm;
        $(function () {
            //表单验证
            validateForm = $("#krtForm").validate({
                ignore: "",
            });
            //立即验证
            validateForm.form();

            //上一步
            $("#backBtn").click(function () {
                window.location.href = "${basePath}/gen/genTable/selectDbTable";
            })

            //提交
            $("#submitBtn").click(function () {
                if (!validateForm.form()) {
                    krt.layer.msg("请检查表单填写是否完整");
                    return false;
                }
                krt.ajax({
                    type: "POST",
                    url: "${basePath}/gen/genTable/doInsert",
                    data: $('#krtForm').serialize(),// 要提交的表单
                    validateForm: validateForm,
                    success: function (rb) {
                        krt.layer.msg(rb.msg);
                        if (rb.code == 200) {
                            top.krt.tab.closeThisTabs(function (contentWindow) {
                                contentWindow.krt.table.reloadTable();
                            })
                        }
                    }
                });
            });

            //拖拽排序
            $("#gridtbody").dragsort({
                itemSelector: "tr",
                dragSelector: "tr",
                dragBetween: false,
                dragSelectorExclude: "input,select,span",
                dragEnd: function () {
                    $(".order").each(function (index) {
                        $(this).val(index + 1);
                    });
                },
            });
        })
    </script>
</@footer>
