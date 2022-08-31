<#include "../mapperXml.ftl">
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${genTable.genScheme.packageName}.mapper.${genTable.className}Mapper">

    <sql id="Base_Column_List">
        <#list genTable.genTableColumns as column>${column.name}<#if (column_has_next)>,</#if></#list>
    </sql>

    <!-- 添加${genTable.comment} -->
    <insert id="insert" parameterType="${genTable.genScheme.packageName}.entity.${genTable.className}" useGeneratedKeys="true" keyProperty="id">
        insert into ${genTable.name}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list genTable.genTableColumns as column>
            <if test="${column.javaField} != null"> ${column.name},</if>
        </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
        <#list genTable.genTableColumns as column>
            <if test="${column.javaField} != null"> ${"#"}{${column.javaField},jdbcType=${column.jdbcType?upper_case}},</if>
        </#list>
        </trim>
    </insert>

    <!-- 批量${genTable.comment} -->
    <insert id="insertBatch" parameterType="java.util.List" useGeneratedKeys="true" keyProperty="id">
        insert into ${genTable.name}
        (<#list genTable.genTableColumns as column>${column.name}<#if (column_has_next)>,</#if></#list>)
        values
        <foreach collection="list" item="item" index="index" separator=",">
            (<#list genTable.genTableColumns as column>${"#"}{item.${column.javaField}}<#if (column_has_next)>,</#if></#list>)
        </foreach>
    </insert>

    <!-- 删除${genTable.comment} -->
    <delete id="delete" parameterType="java.lang.Integer">
        delete from ${genTable.name} where id = ${"#"}{id,jdbcType=INTEGER}
    </delete>

    <!-- 批量删除${genTable.comment} -->
    <delete id="deleteByIds" parameterType="java.lang.Integer">
        delete from ${genTable.name} where id in
        <foreach item="id" collection="array" open="(" separator="," close=")">
        ${"#"}{id}
        </foreach>
    </delete>

    <!-- 修改${genTable.comment} -->
    <update id="update" parameterType="${genTable.genScheme.packageName}.entity.${genTable.className}">
        update ${genTable.name}
        <set>
        <#list genTable.genTableColumns as column>
            <if test="${column.javaField} != null"> ${column.name} = ${"#"}{${column.javaField},jdbcType=${column.jdbcType?upper_case}},</if>
        </#list>
        </set>
        where id = ${"#"}{id,jdbcType=INTEGER}
    </update>

    <!-- 根据ID查询${genTable.comment} -->
    <select id="selectById" resultType="java.util.Map">
        select
        <include refid="Base_Column_List"/>
        from ${genTable.name}
        where id = ${"#"}{id,jdbcType=INTEGER}
    </select>

    <!-- 根据ID查询${genTable.comment}实体 -->
    <select id="selectEntityById" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from ${genTable.name}
        where id = ${"#"}{id,jdbcType=INTEGER}
    </select>

    <!-- 查询${genTable.comment}列表 -->
    <select id="selectAll" resultType="java.util.Map">
        select
        <include refid="Base_Column_List"/>
        from ${genTable.name}
    </select>

    <!-- 带参数查询${genTable.comment}列表 -->
    <select id="selectList" resultType="java.util.Map">
        select
        <include refid="Base_Column_List"/>
        from ${genTable.name} where 1=1
        <@mapperQuery genTable></@mapperQuery>
    </select>

<#if !genTable.cgenTables??>
    <!-- 根据${genTable.parentTableFkTf}删除${genTable.comment} -->
    <delete id="deleteBy${genTable.parentTableFkTf?cap_first}">
        delete from ${genTable.name} where ${genTable.parentTableFk} = ${"#"}{${genTable.parentTableFkTf}}
    </delete>

    <!-- 根据${genTable.parentTableFkTf}查询 -->
    <select id="selectBy${genTable.parentTableFkTf?cap_first}" resultType="java.util.Map">
        select
        <include refid="Base_Column_List"/>
        from ${genTable.name} where ${genTable.parentTableFk} = ${"#"}{${genTable.parentTableFkTf}}
    </select>
</#if>

    <@excelOut genTable></@excelOut>
</mapper>

