<#include "/common/layoutForm.ftl">
<@header></@header>
<@body>
    <div class="wrapper">
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <form role="form" class="form-horizontal krt-form krt-width-form" id="krtForm">
                        <div class="box-body">
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="code" class="control-label col-sm-4">上级菜单：</label>
                                        <div class="col-sm-8">
                                            <!-- 参数 -->
                                            <input type="hidden" name="pid" id="pid" value="${(pRes.id)!}" class="form-control">
                                            <input type="text" name="pname" value="${(pRes.name)!}" id="pname" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="code" class="control-label col-sm-4">
                                            <span class="form-required">*</span>菜单名称
                                            ：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="name" id="name" value="${(res.name)!}" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="url" class="control-label col-sm-4">链接：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="url" id="url" value="${(res.url)!}" class="form-control">
                                            <span class="help-inline">点击菜单跳转的页面,按钮为空</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="permission" class="control-label col-sm-4">权限标志：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="permission" id="permission" value="${(res.permission)!}" class="form-control">
                                            <span class="help-inline">控制器中定义的权限标识 <br/>如：@RequiresPermissions("权限标识")</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="icon" class="control-label col-sm-4">图标：</label>
                                        <div class="col-sm-8">
                                            <#if res.icon??>
                                                <i id="iconIcon" class="fa ${(res.icon)!}"></i>
                                                <span id="iconIconLabel">${(res.icon)!}</span>
                                            <#else>
                                                <i id="iconIcon" class="icon- hide"></i>
                                                <span id="iconIconLabel">无</span>
                                            </#if>
                                            <input id="icon" type="hidden" value="${res.icon!}" name="icon">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="type" class="control-label col-sm-4">类别：</label>
                                        <div class="col-sm-8">
                                            <input type="radio" name="type" value="url" class="icheck" ${(res.type=='url')?string('checked','')}> 菜单
                                            <input type="radio" name="type" value="button" class="icheck" ${(res.type=='button')?string('checked','')}> 按钮
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="target" class="control-label col-sm-4">目标：</label>
                                        <div class="col-sm-8">
                                            <select name="target" id="target" class="form-control select2" style="width: 100%">>
                                                <option value="">iframe</option>
                                                <option value="_blank" ${((res.target=='_blank')?string('selected',''))!}>新tab页</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="sort" class="control-label col-sm-4">排序：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="sort" id="sort" value="${(res.sort)!}" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="remark" class="control-label col-sm-2">备注：</label>
                                        <div class="col-sm-10">
                                            <textarea rows="3" id="remark" name="remark" class="form-control">${(res.remark)!}</textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- 参数 -->
                            <input type="hidden" name="id" id="id" value="${(res.id)!}">
                    </form>
                </div>
            </div>
        </section>
    </div>
</@body>
<@footer></@footer>
