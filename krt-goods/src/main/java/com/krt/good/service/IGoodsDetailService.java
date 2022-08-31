package com.krt.good.service;

import com.krt.common.base.IBaseService;
import com.krt.good.entity.GoodsDetail;


/**
 * 商品信息服务接口层
 *
 * @author hzb
 * @version 1.0
 * @date 2022年04月12日
 */
public interface IGoodsDetailService extends IBaseService<GoodsDetail>{

    /**
     * 保存接口
     * @param saveBody
     */
    void insertEntry(GoodsDetail saveBody);

    /**
     * 详情保存
     * @param saveBody
     */
    void insertDetail(GoodsDetail saveBody);

    /**
     * 修改详情
     * @param updateDetail
     */
    void updateEntry(GoodsDetail updateDetail);

    /**
     * 删除实体
     * @param goodsDetail
     */
    void deleteEntry(GoodsDetail goodsDetail);
}
