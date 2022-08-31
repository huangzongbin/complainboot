package com.krt.good.entity;

import com.krt.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
    import java.math.BigDecimal;

/**
 * 商品信息实体类
 *
 * @author hzb
 * @version 1.0
 * @date 2022年04月09日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("goods")
public class Goods extends BaseEntity {

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
     * 数量
     */
    private BigDecimal totalPrice;


    /**
     * 1:表示样衣 2 表示商品货号
     */
    private String type;

    /**
     * 图片路径
     */
    private String photoUrl;

    /**
     * 备注
     */
    private String remark;

}