package com.krt.sys.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 登录表单DTO
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年04月29日
 */
@Data
public class LoginFormDTO {

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String username;
    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String password;
    /**
     * 验证码ticket
     */
    @NotNull(message = "验证码错误")
    private String ticket;

}
