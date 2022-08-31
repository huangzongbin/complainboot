package com.krt.common.datascope;

import lombok.Data;

import java.util.List;

/**
 * 数据范围
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月30日
 */
@Data
public class DataScope {

    /**
     * 限制范围的字段名称
     */
    private String scopeName = "organ_id";

    /**
     * 具体的数据范围
     */
    private List<Object> scopeValue;
}
