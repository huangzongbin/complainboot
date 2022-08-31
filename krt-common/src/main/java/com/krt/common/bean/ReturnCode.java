package com.krt.common.bean;


/**
 * 全局返回码
 * <p>
 * ----------------------------------------------------------------------------
 * 200 OK - [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）。
 * 400 INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
 * 401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
 * 403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
 * 404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
 * 406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
 * 410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
 * 415 METHOD_ERROR -[*]：用户请求的方法错误
 * 422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
 * 500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。
 * 600 UN_KNOW_ERROR - 未知错误
 * ----------------------------------------------------------------------------
 * </p>
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年8月1日
 */
public enum ReturnCode {

    /**
     * 操作成功
     */
    OK(200, "操作成功"),
    /**
     * 参数错误
     */
    INVALID_REQUEST(400, "参数错误"),
    /**
     * 没有权限
     */
    UNAUTHORIZED(401, "没有权限"),
    /**
     * 禁止访问
     */
    FORBIDDEN(403, "禁止访问"),
    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),
    /**
     * 请求的格式不正确
     */
    NOT_ACCEPTABLE(406, "请求的格式不正确"),
    /**
     * 数据被删除
     */
    GONE(410, "数据被删除"),
    /**
     * 请求方法错误
     */
    METHOD_ERROR(415, "请求方法错误"),
    /**
     * 参数验证错误
     */
    UNPROCESABLE_ENTITY(422, "参数验证错误"),
    /**
     * 操作失败
     */
    ERROR(500, "操作失败"),
    /**
     * 未知错误
     */
    UN_KNOW_ERROR(600, "未知错误"),
    /**
     * accessToken已过期
     */
    ACCESS_TOKEN_EXPIRE(5001, "accessToken已过期"),
    /**
     * accessToken不能为空
     */
    ACCESS_TOKEN_NULL(5002, "accessToken不能为空"),
    /**
     * accessToken错误
     */
    ACCESS_TOKEN_ERROR(5003, "accessToken错误"),
    /**
     * refreshToken失效,请重新登录
     */
    REFRESH_TOKEN_ERROR(5004, "refreshToken失效,请重新登录");

    private int code;

    private String msg;


    ReturnCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
