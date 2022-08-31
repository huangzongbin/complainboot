<#include "../macro.ftl">
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${genTable.genScheme.packageName}.mapper.${genTable.className}Mapper">

    <sql id="Base_Column_List">
        <#list genTable.genTableColumns as column>${column.name}<#if (column_has_next)>,</#if></#list>
    </sql>

    <!-- 带参数查询${genTable.comment}列表 -->
    <select id="selectPageList" resultType="${genTable.genScheme.packageName}.entity.${genTable.className}">
        select
            <include refid="Base_Column_List"/>
        from ${genTable.name} where 1=1
        <@mapperQuery genTable></@mapperQuery>
    </select>

    <@excelOut genTable></@excelOut>
</mapper>

