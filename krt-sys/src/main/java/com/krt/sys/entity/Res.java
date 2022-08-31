package com.krt.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.krt.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 资源实体类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年10月16日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_res")
public class Res extends BaseEntity {

    /**
     * 资源名称
     */
    @NotBlank(message = "资源名称")
    private String name;

    /**
     * 连接
     */
    private String url;

    /**
     * 父id
     */
    private Integer pid;

    /**
     * 图表
     */
    private String icon;

    /**
     * 权限
     */
    private String permission;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer sort;

    /**
     * 类别 0：菜单 1按钮
     */
    @NotBlank(message = "类型不能为空")
    private String type;

    /**
     * 打开目标
     */
    private String target;

    /**
     * 备注
     */
    private String remark;

    /**
     * 子模块
     */
    @TableField(exist = false)
    private List<Res> child;

    /**
     * 是否包含子资源 'true' 'false'
     */
    @TableField(exist = false)
    private String hasChild;

}