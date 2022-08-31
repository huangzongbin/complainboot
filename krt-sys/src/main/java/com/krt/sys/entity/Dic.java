package com.krt.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.krt.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 字典表实体类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年04月11日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dic")
public class Dic extends BaseEntity {

    /**
     * 类别
     */
    @NotBlank(message = "类别不能为空")
    private String type;

    /**
     * 父id
     */
    @NotNull(message = "父id不能为空")
    private Integer pid;

    /**
     * 字典代码
     */
    @NotBlank(message = "字典代码不能为空")
    private String code;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer sort;

}