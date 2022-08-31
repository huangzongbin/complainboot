<#include "../macro.ftl">
package ${genTable.genScheme.packageName}.service;

import ${genTable.genScheme.packageName}.entity.${genTable.className};
import com.krt.common.base.IBaseService;
import java.util.List;

/**
 * ${genTable.comment}接口层
 *
 * @author ${genTable.genScheme.coder}
 * @version 1.0
 * @date ${.now?string("yyyy年MM月dd日")}
 */
public interface I${genTable.className}Service extends IBaseService<${genTable.className}>{

    /**
     * 根据pid查询${genTable.comment}
     *
     * @param pid ${genTable.comment}pid
     * @return {@link List<${genTable.className}>}
     */
    List<${genTable.className}> selectByPid(Integer pid);

}
