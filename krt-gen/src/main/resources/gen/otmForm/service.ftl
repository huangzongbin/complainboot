<#include "../service.ftl">
package ${genTable.genScheme.packageName}.service;

import ${genTable.genScheme.packageName}.entity.${genTable.className};
import com.krt.common.base.BaseService;
import java.util.Map;
import java.util.List;

/**
 * @author ${genTable.genScheme.coder}
 * @version 1.0
 * @Description: ${genTable.comment}服务接口层
 * @date ${.now?string("yyyy年MM月dd日")}
 */
public interface ${genTable.className}Service extends BaseService<${genTable.className}>{

<#if !genTable.cgenTables??>
    /**
     * 根据${genTable.parentTableFkTf}删除${genTable.comment}
     *
     * @param ${genTable.parentTableFkTf}
     */
    void deleteBy${genTable.parentTableFkTf?cap_first}(Integer ${genTable.parentTableFkTf});

    /**
     * 根据${genTable.parentTableFkTf}查询
     *
     * @param ${genTable.parentTableFkTf}
     * @return
     */
    List selectBy${genTable.parentTableFkTf?cap_first}(Integer ${genTable.parentTableFkTf});
</#if>

    <@excelOut genTable></@excelOut>
}
