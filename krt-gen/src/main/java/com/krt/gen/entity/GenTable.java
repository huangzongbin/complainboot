package com.krt.gen.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.krt.common.base.BaseEntity;
import com.krt.gen.enums.BooleanEnum;
import lombok.Data;

import java.util.List;


/**
 * 业务表实体类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年05月03日
 */
@Data
@TableName("gen_table")
public class GenTable extends BaseEntity {

    /**
     * 表名
     */
    private String name;

    /**
     * 注释
     */
    private String comment;

    /**
     * 类名
     */
    private String className;

    /**
     * 父表名
     */
    private String parentTable;

    /**
     * 父表关联字段
     */
    private String parentTableFk;

    /**
     * 父表关联字段驼峰
     */
    @TableField(exist = false)
    private String parentTableFkTf;

    /**
     * 实例名称
     */
    @TableField(exist = false)
    private String entityName;

    /**
     * 生成方案
     */
    @TableField(exist = false)
    private GenScheme genScheme;

    /**
     * 子表
     */
    @TableField(exist = false)
    private List<GenTable> cgenTables;

    /**
     * 字段
     */
    @TableField(exist = false)
    private List<GenTableColumn> genTableColumns;

    /**
     * 是否包含导入 0:包含1:不包含
     */
    @TableField(exist = false)
    private String excelIn;

    /**
     * 是否包含导出 0:包含1:不包含
     */
    @TableField(exist = false)
    private String excelOut;

    /**
     * 是否包含单选树 0:包含1:不包含
     */
    @TableField(exist = false)
    private String rTree;

    /**
     * 是否包含多选树 0:包含1:不包含
     */
    @TableField(exist = false)
    private String cTree;

    /**
     * 是否包含单选List 0:包含1:不包含
     */
    @TableField(exist = false)
    private String rList;

    /**
     * 是否包含多选List 0:包含1:不包含
     */
    @TableField(exist = false)
    private String cList;

    public void init() {
        this.setExcelIn(BooleanEnum.NO.getValue());
        this.setExcelOut(BooleanEnum.NO.getValue());
        this.setRTree(BooleanEnum.NO.getValue());
        this.setCTree(BooleanEnum.NO.getValue());
        this.setRList(BooleanEnum.NO.getValue());
        this.setCList(BooleanEnum.NO.getValue());
        for (GenTableColumn column : genTableColumns) {
            if (BooleanEnum.YES.getValue().equals(column.getExcelIn())) {
                this.setExcelIn(BooleanEnum.YES.getValue());
            }
            if (BooleanEnum.YES.getValue().equals(column.getExcelOut())) {
                this.setExcelOut(BooleanEnum.YES.getValue());
            }
            if ("rTree".equals(column.getShowType())) {
                this.setRTree(BooleanEnum.YES.getValue());
            }
            if ("cTree".equals(column.getShowType())) {
                this.setCTree(BooleanEnum.YES.getValue());
            }
            if ("rList".equals(column.getShowType())) {
                this.setRList(BooleanEnum.YES.getValue());
            }
            if ("cList".equals(column.getShowType())) {
                this.setCList(BooleanEnum.YES.getValue());
            }
        }
    }

}