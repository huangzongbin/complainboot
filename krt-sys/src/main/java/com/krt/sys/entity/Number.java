package com.krt.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.krt.common.base.BaseEntity;
import com.krt.common.validator.group.InsertGroup;
import com.krt.common.validator.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 流水号实体类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年05月17日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_number")
public class Number extends BaseEntity {

    /**
     * 流水号编码
     */
    @NotBlank(message = "流水号编码不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    private String code;

    /**
     * 流水号名称
     */
    @NotBlank(message = "流水号名称不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 流水号
     */
    private Integer num;

    /**
     * 格式化流水号
     */
    private String formatNum;

    /**
     * 流水号长度
     */
    @NotNull(message = "流水号长度不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    @Size(min = 1,max = 10, message = "流水号长度1-10")
    private Integer numLength;

    /**
     * 日期格式化
     */
    private String dateFormat;
}