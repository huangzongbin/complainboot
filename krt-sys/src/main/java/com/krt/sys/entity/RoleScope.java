package com.krt.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色数据权限实体
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年06月28日
 */
@Data
@NoArgsConstructor
@TableName("sys_role_scope")
public class RoleScope {
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
     * 权限字段
     */
    private String dataId;
}

