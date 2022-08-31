package com.krt.complain.entity;

import com.krt.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
    import java.util.Date;
    import org.springframework.format.annotation.DateTimeFormat;

/**
 * 投诉信息实体类
 *
 * @author 转移
 * @version 1.0
 * @date 2019年06月10日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_complain")
public class Complain extends BaseEntity {

    /**
     * 投诉人姓名
     */
    private String linkName;

    /**
     * 投诉人电话
     */
    private String linkPhone;

    /**
     * 投诉举报内容
     */
    private String cpnContent;

    /**
     * 证明图片
     */
    private String cpnImg;

    /**
     * 投诉举报时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cpnTime;

    /**
     * 状态：1待回复2已回复
     */
    private String status;

    /**
     * 办理情况
     */
    private String returnContent;

    /**
     * 回复人
     */
    private String optName;

    /**
     * 小区
     */
    private String community;

    /**
     * 回复时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date optTime;

    /**
     * 电话回复 1否 2是
     */
    private Integer isPhoneReply;

    private String file;


}