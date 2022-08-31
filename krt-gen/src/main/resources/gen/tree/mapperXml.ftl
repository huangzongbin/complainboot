<#include "../macro.ftl">
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${genTable.genScheme.packageName}.mapper.${genTable.className}Mapper">

    <sql id="Base_Column_List">
        <#list genTable.genTableColumns as column>${column.name}<#if (column_has_next)>,</#if></#list>
    </sql>

    <!-- 根据pid查询${genTable.comment}树列表 -->
    <select id="selectByPid" resultType="${genTable.genScheme.packageName}.entity.${genTable.className}">
        select
            <#list genTable.genTableColumns as column>${column.name},</#list>
            (select CASE WHEN count(*)>0 THEN 'true' ELSE 'false' END from ${genTable.name} s where s.pid = ${genTable.name}.id) hasChild
        from ${genTable.name} where pid = ${"#"}{pid}
    </select>

    <@excelOut genTable></@excelOut>
</mapper>

