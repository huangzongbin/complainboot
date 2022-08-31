package com.krt.sys.mapper;

import com.krt.sys.entity.Number;
import com.krt.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 流水号映射层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年05月17日
 */
@Mapper
public interface NumberMapper extends BaseMapper<Number> {

    /**
     * 根据code查询流水号加悲观锁
     *
     * @param code 规则编码
     * @return 流水号
     */
    Number selectByCodeForUpdate(@Param("code") String code);

    /**
     * 查询当前日期流水号码
     *
     * @param code        规则编码
     * @param dateFormat  日期格式化
     * @param currentDate 当前日期
     * @return 当前日期流水号码
     */
    Integer selectNum(@Param("code") String code, @Param("dateFormat") String dateFormat, @Param("currentDate") String currentDate);

    /**
     * 根据code查询流水号
     *
     * @param code 规则编码
     * @return 流水号码
     */
    Integer selectNumByCode(@Param("code") String code);

    /**
     * 查询当前时间
     *
     * @param dateFormat 数据库日期格式化
     * @return 当前时间
     */
    String selectCurrentDate(@Param("dateFormat") String dateFormat);
}
