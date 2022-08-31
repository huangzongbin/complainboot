<#include "../macro.ftl">
package ${genTable.genScheme.packageName}.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ${genTable.genScheme.packageName}.entity.${genTable.className};
import ${genTable.genScheme.packageName}.mapper.${genTable.className}Mapper;
import ${genTable.genScheme.packageName}.service.I${genTable.className}Service;
import com.krt.common.base.BaseServiceImpl;
import java.io.Serializable;
import java.util.List;

/**
 * ${genTable.comment}实现层
 *
 * @author ${genTable.genScheme.coder}
 * @version 1.0
 * @date ${.now?string("yyyy年MM月dd日")}
 */
@Service
public class ${genTable.className}ServiceImpl extends BaseServiceImpl<${genTable.className}Mapper, ${genTable.className}> implements I${genTable.className}Service {

    /**
     * 根据pid查询${genTable.comment}
     *
     * @param pid ${genTable.comment}pid
     * @return {@link List<${genTable.className}>}
     */
    @Override
    public List<${genTable.className}> selectByPid(Integer pid) {
         return baseMapper.selectByPid(pid);
    }

    /**
     * 递归删除${genTable.comment}
     *
     * @param id ${genTable.comment}id
     * @return {@link boolean}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Serializable id) {
        boolean result = super.deleteById(id);
        List<${genTable.className}> list = selectList(new LambdaQueryWrapper<${genTable.className}>().eq(${genTable.className}::getPid, id));
        for (${genTable.className} entity : list) {
            deleteById(entity.getId());
        }
        return result;
    }
}
