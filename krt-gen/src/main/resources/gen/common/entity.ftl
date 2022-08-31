package ${genTable.genScheme.packageName}.entity;

import com.krt.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
<#assign HasDate = true>
<#assign HasBigDecimal = true>
<#list genTable.genTableColumns as column>
    <#if column.javaType=='Date' && HasDate && column.name != 'insert_time' && column.name != 'update_time'>
    import java.util.Date;
    import org.springframework.format.annotation.DateTimeFormat;
        <#assign HasDate = false>
    </#if>
    <#if column.javaType=='BigDecimal' && HasBigDecimal>
    import java.math.BigDecimal;
        <#assign HasBigDecimal = false>
    </#if>
</#list>
<#if genTable.excelIn=='0'>
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
</#if>

/**
 * ${genTable.comment}实体类
 *
 * @author ${genTable.genScheme.coder}
 * @version 1.0
 * @date ${.now?string("yyyy年MM月dd日")}
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("${genTable.name}")
<#if genTable.excelIn=='0'>
@ExcelTarget("${genTable.className}")
</#if>
public class ${genTable.className} extends BaseEntity {

<#list genTable.genTableColumns as column>
<#if genTable.excelIn=='0'>
    <#if column.javaType=='Date' && column.name != 'insert_time' && column.name != 'update_time'>
    /**
     * ${column.comment}
     */
    <#if column.excelIn=='0'>@Excel(name="${column.comment}",format = "yyyy-MM-dd HH:mm:ss")</#if><#if column.excelIn=='1'>@ExcelIgnore</#if>
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ${column.javaType} ${column.javaField};

    </#if>
    <#if column.javaType!='Date' && column.name != 'id' && column.name != 'inserter' && column.name != 'updater'>
    /**
     * ${column.comment}
     */
    <#if column.excelIn=='0'>@Excel(name="${column.comment}")</#if><#if column.excelIn=='1'>@ExcelIgnore</#if>
    private ${column.javaType} ${column.javaField};

    </#if>
</#if>
<#if genTable.excelIn=='1'>
    <#if column.javaType=='Date' && column.name != 'insert_time' && column.name != 'update_time'>
    /**
     * ${column.comment}
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ${column.javaType} ${column.javaField};

    </#if>
    <#if column.javaType!='Date' && column.name != 'id' && column.name != 'inserter' && column.name != 'updater'>
    /**
     * ${column.comment}
     */
    private ${column.javaType} ${column.javaField};

    </#if>
</#if>
</#list>
}