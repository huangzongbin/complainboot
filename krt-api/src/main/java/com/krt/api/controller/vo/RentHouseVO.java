package com.krt.api.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 房屋返回vo
 *
 * @author hzb
 * @date 2021-12-29
 */
@ApiModel(value = "房屋返回vo")
@Data
public class RentHouseVO implements Serializable {

    @ApiModelProperty(value = "id")
    private Integer id ;

    @ApiModelProperty(value = "地址")
    private String address;
}