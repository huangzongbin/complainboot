package com.krt.common.bean;

import lombok.Data;

/**
 * 资源树结构
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年04月29日
 */
@Data
public class TreeBean {

    /**
     * 资源id
     */
    private Integer id;
    /**
     * 资源父id
     */
    private Integer pid;
    /**
     * 资源编码
     */
    private String code;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 是否打开
     */
    private boolean open;
    /**
     * 是否选中
     */
    private boolean checked;
}
