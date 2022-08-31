package ${genTable.genScheme.packageName}.mapper;

import com.krt.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ${genTable.genScheme.packageName}.entity.${genTable.className};
import java.util.List;

/**
 * ${genTable.comment}映射层
 *
 * @author ${genTable.genScheme.coder}
 * @version 1.0
 * @date ${.now?string("yyyy年MM月dd日")}
 */
@Mapper
public interface ${genTable.className}Mapper extends BaseMapper<${genTable.className}>{

    /**
     * 根据pid查询${genTable.comment}
     *
     * @param pid ${genTable.comment}pid
     * @return {@link List<${genTable.className}>}
     */
    List<${genTable.className}> selectByPid(Integer pid);

}
