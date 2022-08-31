package com.krt.sys.entity;

import com.krt.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信与住户绑定关系实体类
 *
 * @author zhangdb
 * @version 1.0
 * @date 2019年06月10日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_wechat_household")
public class WeChatHousehold extends BaseEntity {

    /**
     * 微信号唯一标识
     */
    private String wechatUuid;

    /**
     * 住户身份证号
     */
    private String idCard;

    /**
     * 状态 （1 正常；2 已解除绑定）
     */
    private Integer status;

}