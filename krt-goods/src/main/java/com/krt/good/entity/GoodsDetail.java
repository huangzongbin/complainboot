package com.krt.good.entity;

import cn.hutool.db.DaoTemplate;
import com.krt.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息实体类
 *
 * @author hzb
 * @version 1.0
 * @date 2022年04月12日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("goods_detail")
@ApiModel(value ="商品详情" )
public class GoodsDetail extends BaseEntity {

    /**
     * goods 表的id
     */
    @ApiModelProperty(hidden = true)
    private Integer pid;

    /**
     * 商家
     */
    private String store;

    @ApiModelProperty()
    private String storeName;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品编号
     */
    private String code;

    /**
     * 数量
     */
    private BigDecimal amount;

    /**
     * 1:表示样衣 2 表示商品货号
     */
    private String type;

    /**
     * 增减类型： 1表示发货  2表示退货
     */
    private String creaseType;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 总价
     */
    private BigDecimal priceTotal;

    /**
     * 备注
     */
    private String remark;

    /**
     * 保存时间
     */
    @ApiModelProperty(name = "格式：yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sendTime;


    /**
     * 图片路径
     */
    private String photoUrl;

}