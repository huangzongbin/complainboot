<#include "../serviceImpl.ftl">
package ${genTable.genScheme.packageName}.service.impl;

import org.springframework.stereotype.Service;
import ${genTable.genScheme.packageName}.entity.${genTable.className};
import ${genTable.genScheme.packageName}.mapper.${genTable.className}Mapper;
import ${genTable.genScheme.packageName}.service.${genTable.className}Service;
import com.krt.common.base.BaseServiceImpl;
import java.util.Map;
import java.util.List;
<#if genTable.cgenTables?exists>
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
 <#list genTable.cgenTables as cgenTables>
 import ${cgenTables.genScheme.packageName}.service.${cgenTables.className}Service;
 import ${cgenTables.genScheme.packageName}.entity.${cgenTables.className};
 </#list>
</#if>

/**
 * @author ${genTable.genScheme.coder}
 * @version 1.0
 * @Description: ${genTable.comment}服务接口实现层
 * @date ${.now?string("yyyy年MM月dd日")}
 */
@Service
public class ${genTable.className}ServiceImpl extends BaseServiceImpl<${genTable.className}Mapper,${genTable.className}> implements ${genTable.className}Service {

<#if genTable.cgenTables?exists>
 <#list genTable.cgenTables as cgenTables>

    @Autowired
    private ${cgenTables.className}Service ${cgenTables.className?uncap_first}Service;
 </#list>

    /**
     * 添加${genTable.comment}
     *
     * @param ${genTable.className?uncap_first}
     * @return
     */
    @Override
    public Integer insert(${genTable.className} ${genTable.className?uncap_first}) {
    Integer result = super.insert(${genTable.className?uncap_first});
 <#list genTable.cgenTables as cgenTables>
        List<${cgenTables.className}> ${cgenTables.className?uncap_first}List = new ArrayList<>();
        if(${genTable.className?uncap_first}.get${cgenTables.className}()!=null){
            for (${cgenTables.className} ${cgenTables.className?uncap_first} : ${genTable.className?uncap_first}.get${cgenTables.className}()) {
                if(${cgenTables.className?uncap_first}.getId()!=null) {
                    ${cgenTables.className?uncap_first}.setId(null);
                    ${cgenTables.className?uncap_first}.set${cgenTables.parentTableFkTf?cap_first}(result);
                    ${cgenTables.className?uncap_first}List.add(${cgenTables.className?uncap_first});
                }
            }
        }
        if (!${cgenTables.className?uncap_first}List.isEmpty()) {
            ${cgenTables.className?uncap_first}Service.insertBatch(${cgenTables.className?uncap_first}List);
        }
 </#list>
        return result;
    }

    /**
     * 删除${genTable.comment}
     * @param id
     */
    @Override
    public void delete(Integer id) {
        //删除主库
        super.delete(id);
        //删除子表
 <#list genTable.cgenTables as cgenTables>
        ${cgenTables.className?uncap_first}Service.deleteBy${cgenTables.parentTableFkTf?cap_first}(id);
 </#list>
    }

    /**
    * 修改${genTable.comment}
    * @param ${genTable.className?uncap_first}
    */
    @Override
    public void update(${genTable.className} ${genTable.className?uncap_first}){
        super.update(${genTable.className?uncap_first});
        //删除子库
 <#list genTable.cgenTables as cgenTables>
        ${cgenTables.className?uncap_first}Service.deleteBy${cgenTables.parentTableFkTf?cap_first}(${genTable.className?uncap_first}.getId());
        List<${cgenTables.className}> ${cgenTables.className?uncap_first}List = new ArrayList<>();
        if(${genTable.className?uncap_first}.get${cgenTables.className}()!=null){
            for (${cgenTables.className} ${cgenTables.className?uncap_first} : ${genTable.className?uncap_first}.get${cgenTables.className}()) {
                if(${cgenTables.className?uncap_first}.getId()!=null) {
                    ${cgenTables.className?uncap_first}.setId(null);
                    ${cgenTables.className?uncap_first}.set${cgenTables.parentTableFkTf?cap_first}(${genTable.className?uncap_first}.getId());
                    ${cgenTables.className?uncap_first}List.add(${cgenTables.className?uncap_first});
                }
            }
        }
        if (!${cgenTables.className?uncap_first}List.isEmpty()) {
                ${cgenTables.className?uncap_first}Service.insertBatch(${cgenTables.className?uncap_first}List);
        }
 </#list>
    }

    /**
     * 批量删除
     *
     * @param idsStr
     */
    @Override
    public void deleteByIds(String idsStr) {
        String[] arr = idsStr.split(",");
        for(String id:arr){
            delete(Integer.valueOf(id.toString()));
        }
    }
<#else>
    /**
     * 根据${genTable.parentTableFkTf}删除${genTable.comment}
     *
     * @param ${genTable.parentTableFkTf}
     */
    @Override
    public void deleteBy${genTable.parentTableFkTf?cap_first}(Integer ${genTable.parentTableFkTf}){
       baseMapper.deleteBy${genTable.parentTableFkTf?cap_first}(${genTable.parentTableFkTf});
    }

    /**
     * 根据${genTable.parentTableFkTf}查询
     *
     * @param ${genTable.parentTableFkTf}
     * @return
     */
    @Override
    public List selectBy${genTable.parentTableFkTf?cap_first}(Integer ${genTable.parentTableFkTf}){
        return baseMapper.selectBy${genTable.parentTableFkTf?cap_first}(${genTable.parentTableFkTf});
    }
</#if>

    <@excelOut genTable></@excelOut>
}
