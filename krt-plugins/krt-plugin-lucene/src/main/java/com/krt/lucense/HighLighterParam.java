package com.krt.lucense;


import lombok.Data;

/**
 * 高亮操作
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年11月24日
 */
@Data
public class HighLighterParam {
    /**
     * 是否需要设置高亮
     */
    private boolean highlight;
    /**
     * 需要设置高亮的属性名
     */
    private String fieldName;
    /**
     * 高亮前缀
     */
    private String prefix;
    /**
     * 高亮后缀
     */
    private String stuffix;
    /**
     * 显示摘要最大长度
     */
    private int fragmenterLength;
}