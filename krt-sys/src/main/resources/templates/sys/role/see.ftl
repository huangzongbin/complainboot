<#include "/common/layoutForm.ftl">
<@header title="查看角色"></@header>
<@body>
    <div class="wrapper">
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <form role="form" class="form-horizontal krt-form" id="krtForm">
                        <div class="box-body">
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="code" class="control-label col-sm-4">
                                            <span class="form-required">*</span>角色名称：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control" id="name" name="name" value="${(role.name)!}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="name" class="control-label col-sm-4">
                                            <span class="form-required">*</span>角色编码：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control" id="code" name="code" value="${(role.code)!}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="sort" class="control-label col-sm-4">
                                            <span class="form-required">*</span>排序：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control" id="sort" name="sort" value="${(role.sort)!}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="remark" class="control-label col-sm-4">备注：</label>
                                        <div class="col-sm-5">
                                            <textarea rows="4" name="remark" class="form-control">${(role.remark)!}</textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- 参数 -->
                        <input type="hidden" id="id" name="id" value="${(role.id)!}">
                    </form>
                </div>
            </div>
        </section>
    </div>
</@body>

<@footer>
</@footer>
