package com.krt.gen.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.krt.common.base.BaseEntity;
import lombok.Data;

/**
 * 业务表字段实体类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年05月03日
 */
@Data
@TableName("gen_table_column")
public class GenTableColumn extends BaseEntity {

    /**
     * 归属表编号
     */
    private Integer tableId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String comment;

    /**
     * 列的数据类型的字节长度
     */
    private String jdbcType;

    /**
     * JAVA类型
     */
    private String javaType;

    /**
     * JAVA字段名
     */
    private String javaField;

    /**
     * 是否为插入字段
     */
    private String isInsert;

    /**
     * 是否编辑字段
     */
    private String isEdit;

    /**
     * 是否列表字段
     */
    private String isList;

    /**
     * 是否查询字段
     */
    private String isQuery;

    /**
     * 导入
     */
    private String excelIn;

    /**
     * 导出
     */
    private String excelOut;

    /**
     * 栅格布局
     */
    private String grid;

    /**
     * 查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）
     */
    private String queryType;

    /**
     * 字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）
     */
    private String showType;

    /**
     * 字典类型
     */
    private String dicType;

    /**
     * 绑定数据表
     */
    private String dataTable;

    /**
     * 验证规则
     */
    private String validate;

    /**
     * 其它设置（扩展字段JSON）
     */
    private String settings;

    /**
     * 排序（升序）
     */
    private Integer sort;

    /**
     * 绑定数据表信息
     */
    @TableField(exist = false)
    private GenTable genTable;

}