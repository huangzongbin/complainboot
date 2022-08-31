package com.krt.file.fastdfs.enums;

/**
 * 上传类型
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年09月10日
 */
public enum UploadTypeEnum {

    /**
     * 普通上传（结果返回 ReturnBean）
     */
    DEFAULT("0"),
    /**
     * 编辑器上传 （结果返回 编辑器格式）
     */
    KINDEDITOR("1"),
    /**
     * 切片
     */
    CHUNK("2");

    UploadTypeEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

}
