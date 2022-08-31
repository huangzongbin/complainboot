package com.krt.jpush.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * 推送DTO
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年08月03日
 */
@ApiModel(value = " 推送Dto")
@Data
public class PushDTO implements Serializable {

    /**
     * 设备类型
     */
    @ApiModelProperty(value = "设备类型：android||ios,单发必填，群发非必填")
    private String type;

    /**
     * 推送类型
     */
    @ApiModelProperty(value = "推送类型：alert||message,(alert:普通推送 message:自定义推送)")
    private String pushType;

    /**
     * 注册id
     */
    @ApiModelProperty(value = "注册id 推送标志,单发必填 多个\",\"隔开，群发非必填")
    private String registrationId;

    /**
     * 推送内容
     */
    @ApiModelProperty(value = "推送内容", required = true)
    @NotNull(message = "推送内容不能为空")
    @Length(min = 1, max = 200, message = "内容长度为1-200")
    private String content;

    /**
     * 自定义参数json字符串
     */
    @ApiModelProperty(value = "自定义参数json字符串 当ptype为message无效")
    private String param;

    /**
     * IOS证书推送环境
     */
    @ApiModelProperty(value = "IOS证书推送环境：true正式版 false测试版")
    private Boolean apns;

    /**
     * 推送目标
     */
    @ApiModelProperty(value = "推送目标 群发：all 非群发:one", required = true)
    @NotNull(message = "推送目标不能为空")
    private String audience;

    /**
     * 通知提醒方式
     */
    @ApiModelProperty(value = "通知提醒方式 可选范围为 -1 ～ 7 (安卓单推有效)")
    private Integer alertType;

    /**
     * 自定义
     */
    @ApiModelProperty(hidden = true)
    private Map<String,Object> extras;
}
