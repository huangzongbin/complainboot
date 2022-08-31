package com.krt.good.vo;

import com.krt.good.entity.GoodsDetail;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
@Data
public class GoodsVO implements Serializable {

    /**
     * 商品名称
     */
    private String name;


    /**
     * 商品名称
     */
    private String store;
    /**
     * 商品名称
     */
    private String storeName;


    /**
     * 商品编号
     */
    private String code;

    /**
     * 数量
     */
    private BigDecimal amount;

    /**
     * 总价格
     */
    private BigDecimal totalPrice;

    /**
     * 图片
     */
    private String photoUrl;

    /**
     * 1:表示样衣 2 表示商品货号
     */
    private String type;

    private List<GoodsDetail> details;
}
