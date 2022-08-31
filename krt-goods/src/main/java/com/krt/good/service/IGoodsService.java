package com.krt.good.service;

import com.krt.good.dto.GoodsDetailDTO;
import com.krt.good.entity.Goods;
import com.krt.common.base.IBaseService;
import com.krt.good.entity.GoodsDetail;


/**
 * 商品信息服务接口层
 *
 * @author hzb
 * @version 1.0
 * @date 2022年04月09日
 */
public interface IGoodsService extends IBaseService<Goods>{


    /**
     * 刪除整个对象
     * @param goods
     */
    void deleteEntry(Goods goods);


}
