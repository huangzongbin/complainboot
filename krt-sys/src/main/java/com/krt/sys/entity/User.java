package com.krt.sys.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.krt.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * 用户实体类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年03月27日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class User extends BaseEntity {

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @JSONField(serialize = false)
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度不能小于6位,大于20位")
    private String password;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String name;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 性别（1：男 2：女）
     */
    private Integer sex;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 账号状态 0：正常 1：禁用
     */
    private String status;

    /**
     * 加密密码的盐
     */
    @JSONField(serialize = false)
    private String salt;

    /**
     * 机构id
     */
    private Integer organId;

    /**
     * 用户角色
     */
    @TableField(exist = false)
    private List<Role> roles;

    /**
     * app客户端类型
     */
    @JSONField(serialize = false)
    private String appType;

    /**
     * 推送id
     */
    @JSONField(serialize = false)
    private String registrationId;

    /**
     * 身份证号码
     */
    @Excel(name="身份证号码")
    private String idCard;

}