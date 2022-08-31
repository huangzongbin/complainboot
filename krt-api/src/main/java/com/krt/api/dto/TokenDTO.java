package com.krt.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口tokenVo
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月02日
 */
@ApiModel(value = "鉴权token")
@Data
public class TokenDTO implements Serializable{

    /**
     * 认证token
     */
    @ApiModelProperty(value = "认证token")
    private String accessToken;

    /**
     * 刷新token
     */
    @ApiModelProperty(value = "刷新token")
    private String refreshToken;

}
