package com.krt.common.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldFill.UPDATE;


/**
 * 实体基类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月16日
 */
@Data
public class BaseEntity implements Serializable {

    /**
     * 主键id
     */
    @TableId
    private Integer id;

    /**
     * 录入人
     */
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    @TableField(fill = INSERT)
    private Integer inserter;

    /**
     * 录入时间
     */
    @ApiModelProperty(hidden = true)
    @TableField(fill = INSERT)
    private Date insertTime;

    /**
     * 更新人
     */
    @ApiModelProperty(hidden = true)
    @JSONField(serialize = false)
    @TableField(fill = UPDATE)
    private Integer updater;

    /**
     * 更新时间
     */
    @ApiModelProperty(hidden = true)
    @TableField(fill = INSERT_UPDATE)
    private Date updateTime;

    /**
     * 删除标记（1 表示删除，0 表示未删除；）
     */
//    @ApiModelProperty(hidden = true)
//    @TableField(value = "del_flag")
//    protected Boolean isDeleted;

}
