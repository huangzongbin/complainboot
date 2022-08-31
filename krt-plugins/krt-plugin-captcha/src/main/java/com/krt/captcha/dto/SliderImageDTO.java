package com.krt.captcha.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * 滑块验证DTO
 *
 * @author 殷帅
 * @version 1.0
 * @date 2019年03月02日
 */
@Data
public class SliderImageDTO implements Serializable {

    /**
     * 验证坐标
     */
    private Integer x;

    /**
     * 大图数据
     */
    private String bigImg;

    /**
     * 小图数据
     */
    private String smallImg;
}
