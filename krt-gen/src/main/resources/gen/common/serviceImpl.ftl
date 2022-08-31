package ${genTable.genScheme.packageName}.service.impl;

import org.springframework.stereotype.Service;
import ${genTable.genScheme.packageName}.entity.${genTable.className};
import ${genTable.genScheme.packageName}.mapper.${genTable.className}Mapper;
import ${genTable.genScheme.packageName}.service.I${genTable.className}Service;
import com.krt.common.base.BaseServiceImpl;


/**
 * ${genTable.comment}服务接口实现层
 *
 * @author ${genTable.genScheme.coder}
 * @version 1.0
 * @date ${.now?string("yyyy年MM月dd日")}
 */
@Service
public class ${genTable.className}ServiceImpl extends BaseServiceImpl<${genTable.className}Mapper, ${genTable.className}> implements I${genTable.className}Service {

}
