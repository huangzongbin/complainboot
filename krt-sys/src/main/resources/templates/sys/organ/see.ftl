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
                                        <label for="pname" class="control-label col-sm-4">上级机构：</label>
                                        <div class="col-sm-8">
                                            <!-- 参数 -->
                                            <input type="hidden" name="pid" id="pid" value="${(pOrgan.id)!}" class="form-control">
                                            <input type="text" name="pname" value="${(pOrgan.name)!}" id="pname" readonly class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="name" class="control-label col-sm-4">
                                            <span class="form-required">*</span>机构名称：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" name="name" id="name" value="${(organ.name)!}" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="code" class="control-label col-sm-4">
                                            <span class="form-required">*</span>机构代码：
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" name="code" id="code" value="${(organ.code)!}" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="type" class="control-label col-sm-4">
                                            <span class="form-required">*</span>机构类别：
                                        </label>
                                        <div class="col-sm-8">
                                            <select name="type" id="type" class="form-control select2" required style="width: 100">
                                                <option value="">==请选择==</option>
                                                <@dic type="organ_type";typeList>
                                                    <#list typeList as type>
                                                        <option value="${type.code}" ${(organ.type==type.code)?string("selected","")}>${type.name}</option>
                                                    </#list>
                                                </@dic>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="grade" class="control-label col-sm-4">
                                            <span class="form-required">*</span>机构类型：
                                        </label>
                                        <div class="col-sm-8">
                                            <select name="grade" id="grade" class="form-control select2" required style="width: 100">
                                                <option value="">==请选择==</option>
                                                <@dic type="organ_grade";gradeList>
                                                    <#list gradeList as grade>
                                                        <option value="${grade.code}" ${(organ.grade==grade.code)?string("selected","")}>${grade.name}</option>
                                                    </#list>
                                                </@dic>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="primaryMan" class="control-label col-sm-4">主要负责人：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="primaryMan" id="primaryMan" value="${(organ.primaryMan)!}" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="viceMan" class="control-label col-sm-4">副负责人：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="viceMan" id="viceMan" value="${(organ.viceMan)!}" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="linkMan" class="control-label col-sm-4">联系人：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="linkMan" id="linkMan" value="${(organ.linkMan)!}" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="zipcode" class="control-label col-sm-4">邮编：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="zipcode" id="zipcode" value="${(organ.zipcode)!}" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="phone" class="control-label col-sm-4">手机号码：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="phone" id="phone" value="${(organ.phone)!}" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="email" class="control-label col-sm-4">邮箱：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="email" id="email" value="${(organ.email)!}" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="regionName" class="control-label col-sm-4">区域信息：</label>
                                        <div class="col-sm-8">
                                            <input type="text" name="regionName" id="regionName" value="${(organ.regionName)!}" readonly class="form-control">
                                        </div>
                                        <!-- 参数 -->
                                        <input type="hidden" name="regionId" id="regionId" value="${(organ.regionId)!}" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="address" class="control-label col-sm-2">联系地址：</label>
                                        <div class="col-sm-10">
                                            <input type="text" name="address" id="address" value="${(organ.address)!}" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="remark" class="control-label col-sm-2">备注：</label>
                                        <div class="col-sm-10">
                                            <textarea type="text" name="remark" id="remark" rows="4" class="form-control">${(organ.remark)!}</textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- 参数 -->
                        <input type="hidden" name="id" id="id" value="${organ.id}">
                    </form>
                </div>
            </div>
        </section>
    </div>
</@body>
<@footer>
</@footer>

