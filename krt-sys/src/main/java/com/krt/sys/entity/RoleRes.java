package com.krt.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色资源实体
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年06月28日
 */
@Data
@NoArgsConstructor
@TableName("sys_role_res")
public class RoleRes {
    /**
     * 数据库id
     */
    @TableId
    private Integer id;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 资源id
     */
    private Integer resId;
}

