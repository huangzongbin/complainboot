<#include "../mapper.ftl">
package ${genTable.genScheme.packageName}.mapper;

import com.krt.common.base.BaseMapper;
import ${genTable.genScheme.packageName}.entity.${genTable.className};
<#if !genTable.cgenTables??>
import java.util.List;
import org.apache.ibatis.annotations.Param;
</#if>
import java.util.Map;
import java.util.List;

/**
 * @author ${genTable.genScheme.coder}
 * @version 1.0
 * @Description: ${genTable.comment}映射层
 * @date ${.now?string("yyyy年MM月dd日")}
 */
public interface ${genTable.className}Mapper extends BaseMapper<${genTable.className}>{

<#if !genTable.cgenTables??>
    /**
     * 根据${genTable.parentTableFkTf}删除${genTable.comment}
     *
     * @param ${genTable.parentTableFkTf}
     */
    void deleteBy${genTable.parentTableFkTf?cap_first}(@Param("${genTable.parentTableFkTf}") Integer ${genTable.parentTableFkTf});

    /**
     * 根据${genTable.parentTableFkTf}查询
     *
     * @param ${genTable.parentTableFkTf}
     * @return
     */
    List selectBy${genTable.parentTableFkTf?cap_first}(@Param("${genTable.parentTableFkTf}") Integer ${genTable.parentTableFkTf});
</#if>

    <@excelOut genTable></@excelOut>
}
