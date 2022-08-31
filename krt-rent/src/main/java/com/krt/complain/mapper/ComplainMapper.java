package com.krt.complain.mapper;

import com.krt.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.krt.complain.entity.Complain;

/**
 * 投诉信息映射层
 *
 * @author 转移
 * @version 1.0
 * @date 2019年06月10日
 */
@Mapper
public interface ComplainMapper extends BaseMapper<Complain>{


}
