package com.krt.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.krt.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 组织机构实体类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年10月16日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_organ")
public class Organ extends BaseEntity {

    /**
     * 父级id
     */
    @NotNull(message = "父级id不能为空")
    private Integer pid;

    /**
     * 机构名称
     */
    @NotBlank(message = "机构名称不能为空")
    private String name;

    /**
     * 机构代码
     */
    @NotBlank(message = "机构代码不能为空")
    private String code;

    /**
     * 机构类别编码
     */
    private String type;

    /**
     * 结构类型编码
     */
    private String grade;

    /**
     * 结构类型
     */
    @TableField(exist = false)
    private String gradeName;

    /**
     * 主要负责人
     */
    private String primaryMan;

    /**
     * 副负责人
     */
    private String viceMan;

    /**
     * 联系人
     */
    private String linkMan;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 邮编
     */
    private String zipcode;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 区域id
     */
    private Integer regionId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否包含子区域 'true' 'false'
     */
    @TableField(exist = false)
    private String hasChild;
}