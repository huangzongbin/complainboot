package com.krt.quartz.mapper;


import com.krt.common.base.BaseMapper;
import com.krt.quartz.entity.Quartz;
import org.apache.ibatis.annotations.Mapper;

/**
 * 任务调度映射层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月22日
 */
@Mapper
public interface QuartzMapper extends BaseMapper<Quartz> {


}