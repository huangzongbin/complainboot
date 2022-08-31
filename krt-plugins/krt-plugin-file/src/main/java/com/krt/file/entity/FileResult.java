package com.krt.file.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.krt.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 文件实体类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年09月11日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("file_result")
public class FileResult extends BaseEntity {

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件md5
     */
    private String md5;

    /**
     * 文件大小
     */
    private Long fileLenght;

    /**
     * 文件链接
     */
    private String url;
    
    /**
     * 文件存储地址
     */
    private String savePath;

}