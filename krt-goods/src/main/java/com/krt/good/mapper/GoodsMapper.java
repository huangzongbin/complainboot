package com.krt.good.mapper;

import com.krt.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.krt.good.entity.Goods;

/**
 * 商品信息映射层
 *
 * @author hzb
 * @version 1.0
 * @date 2022年04月09日
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods>{


}
