package com.krt.captcha.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * 滑块验证VO
 *
 * @author 殷帅
 * @version 1.0
 * @date 2019年03月02日
 */
@Data
public class SliderImageVO implements Serializable {

    /**
     * 大图数据
     */
    private String bigImg;

    /**
     * 小图数据
     */
    private String smallImg;
}
