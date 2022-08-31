package com.krt.common.base;

import java.util.List;
import java.util.Map;

/**
 * 公共抽取的映射层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月16日
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    /**
     * 自定义分页(PageHelper 模式)
     *
     * @param para
     * @return {@link List}
     */
    List selectPageList(Map para);

    /**
     * excel导出
     *
     * @param para
     * @return {@link List}
     */
    List selectExcelList(Map para);
}
