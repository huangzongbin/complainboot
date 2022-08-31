package com.krt.msgLog.entity;

import com.krt.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 消息日志实体类
 *
 * @author 转移
 * @version 1.0
 * @date 2019年06月10日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_msg_log")
public class TMsgLog extends BaseEntity {

    /**
     * 发送号码
     */
    private String phone;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 返回信息
     */
    private String returnMsg;

    /**
     * 关联Id
     */
    private Integer cid;

    /**
     * 短信类型
     * msg01开始缴费验证码
     * msg02结束缴费验证码
     * msg03 导入租金数据验证码
     * ""  其他
     */
    private String type;

}