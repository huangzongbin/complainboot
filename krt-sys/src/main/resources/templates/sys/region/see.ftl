<#include "/common/layoutForm.ftl">
<@header></@header>
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
                                        <label for="pname" class="control-label col-sm-4">上级区域：</label>
                                        <div class="col-sm-5">
                                            <!-- 参数 -->
                                            <input type="hidden" name="pid" id="pid" value="${(pRegion.id)!}" class="form-control">
                                            <input type="text" name="pname" value="${(pRegion.name)!}" id="pname" readonly class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="type" class="control-label col-sm-4">
                                            <span class="form-required">*</span>区域类型：
                                        </label>
                                        <div class="col-sm-5">
                                            <select class="form-control select2" name="type" required style="width: 100%">>
                                                <option value="">==请选择==</option>
                                                <@dic type="region_type";typeList>
                                                    <#list typeList as type>
                                                        <option value="${type.code}" ${(type.code==region.type)?string("selected","")}>${type.name}</option>
                                                    </#list>
                                                </@dic>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="name" class="control-label col-sm-4">
                                            <span class="form-required">*</span>区域名称：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" name="name" id="name" value="${(region.name)!}" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label for="code" class="control-label col-sm-4">
                                            <span class="form-required">*</span>区域编码：
                                        </label>
                                        <div class="col-sm-5">
                                            <input type="text" name="code" id="code" value="${(region.code)!}" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- 参数 -->
                        <input type="hidden" id="id" name="id" value="${(region.id)!}">
                    </form>
                </div>
            </div>
        </section>
    </div>
</@body>
<@footer>
</@footer>

