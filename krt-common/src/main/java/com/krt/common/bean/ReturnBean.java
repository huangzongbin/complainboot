package com.krt.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回bean
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月16日
 */
@Data
@ApiModel(value = "接口返回")
public class ReturnBean<T> implements Serializable {

    @ApiModelProperty(value = "响应码")
    private int code;

    @ApiModelProperty(value = "信息提示")
    private String msg;

    @ApiModelProperty(value = "数据信息")
    private T data;

    private ReturnBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ReturnBean(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功
     *
     * @return ReturnBean
     */
    public static ReturnBean ok() {
        return new ReturnBean(ReturnCode.OK.getCode(), ReturnCode.OK.getMsg());
    }

    /**
     * 成功
     *
     * @param data 返回数据
     * @return ReturnBean
     */
    public static <T> ReturnBean ok(T data) {
        return new ReturnBean<>(ReturnCode.OK.getCode(), ReturnCode.OK.getMsg(), data);
    }


    /**
     * 失败
     *
     * @return ReturnBean
     */
    public static ReturnBean error() {
        return new ReturnBean(ReturnCode.ERROR.getCode(), ReturnCode.ERROR.getMsg());
    }

    /**
     * 自定义失败信息(自定义错误msg)
     *
     * @return ReturnBean
     */
    public static ReturnBean error(String msg) {
        return new ReturnBean(ReturnCode.ERROR.getCode(), msg);
    }

    /**
     * 参数错误
     *
     * @return ReturnBean
     */
    public static ReturnBean paramError(String msg) {
        return new ReturnBean(ReturnCode.INVALID_REQUEST.getCode(), msg);
    }

    /**
     * 自定义失败信息(使用全局的响应码和消息提示)
     *
     * @param rc 错误码
     * @return ReturnBean
     */
    public static ReturnBean error(ReturnCode rc) {
        return new ReturnBean(rc.getCode(), rc.getMsg());
    }

    /**
     * 自定义失败信息
     *
     * @param code 错误码
     * @return ReturnBean
     */
    public static ReturnBean error(int code, String msg) {
        return new ReturnBean(code, msg);
    }


}
