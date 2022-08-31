package com.krt.oauth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.krt.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * 认证code表实体类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月12日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("oauth_code")
public class OauthCode extends BaseEntity {

    /**
     * 认证code
     */
    private String code;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 客户端id
     */
    private String clientId;

}