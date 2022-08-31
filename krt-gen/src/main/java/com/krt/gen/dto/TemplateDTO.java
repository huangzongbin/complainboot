package com.krt.gen.dto;

import lombok.Data;

import java.util.Map;

/**
 * 模板DTO
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月04日
 */
@Data
public class TemplateDTO {

    /**
     * 模板渲染需要的参数
     */
    private Map<String, Object> root;

    /**
     * 实体文件
     */
    private String entityFile;
}
