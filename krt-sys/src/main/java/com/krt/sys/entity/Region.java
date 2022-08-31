package com.krt.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.krt.common.base.BaseEntity;
import com.krt.common.validator.group.InsertGroup;
import com.krt.common.validator.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 区域实体类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年03月27日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_region")
public class Region extends BaseEntity {

    /**
     * 地区编码
     */
    @NotBlank(message = "地区编码不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    private String code;

    /**
     * 地区名称
     */
    @NotBlank(message = "地区名称不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 区域类型编码
     */
    @NotBlank(message = "区域类型不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    private String type;

    /**
     * 父id
     */
    private Integer pid;

    /**
     * 是否包含子区域 'true' 'false'
     */
    @TableField(exist = false)
    private String hasChild;
}
