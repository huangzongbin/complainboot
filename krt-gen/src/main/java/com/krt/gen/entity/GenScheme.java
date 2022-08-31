package com.krt.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.krt.common.base.BaseEntity;
import lombok.Data;


/**
 * 生成方案实体类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年05月03日
 */
@Data
@TableName("gen_scheme")
public class GenScheme extends BaseEntity {

    /**
     * 名称
     */
    private String template;

    /**
     * 表单类型
     */
    private String formType;

    /**
     * 生成结构
     */
    private String fileType;

    /**
     * 生成包路径
     */
    private String packageName;

    /**
     * 权限模块名
     */
    private String permissionName;

    /**
     * 程序员
     */
    private String coder;

    /**
     * 代码生成路径
     */
    private String codePath;

    /**
     * 生成表编号
     */
    private Integer tableId;

}