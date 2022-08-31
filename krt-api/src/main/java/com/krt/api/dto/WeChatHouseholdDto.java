package com.krt.api.dto;

import com.krt.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信与住户绑定关系实体类(现已不在使用)
 *
 * @author zhangdb
 * @version 1.0
 * @date 2019年06月10日
 */
@ApiModel(value = "登录用户信息")
@Data
public class WeChatHouseholdDto extends BaseEntity {

    /**
     * 微信号唯一标识
     */
    @ApiModelProperty(value = "微信在公众号唯一标识")
    private String uniqueId;

    /**
     * 住户姓名
     */
    @ApiModelProperty(value = "住户姓名")

    private String name;

    /**
     * 住户身份证号
     */
    @ApiModelProperty(value = "住户身份证号")

    private String idCard;

    /**
     * 状态
     */
    @ApiModelProperty(value = "1 代表绑定；2代表解除绑定")
    private Integer status;

}