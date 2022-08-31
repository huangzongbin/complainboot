package com.krt.sys.mapper;


import com.krt.common.base.BaseMapper;
import com.krt.sys.entity.Log;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日志管理映射层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月19日
 */
@Mapper
public interface LogMapper extends BaseMapper<Log> {


}