package com.krt.sys.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 殷帅
 * @version 1.0
 * @Description: 在线用户实体类
 * @date 2017年10月25日
 */
@Data
@NoArgsConstructor
public class UserOnline implements Serializable {

    /**
     * session id
     */
    private String id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户主机地址
     */
    private String host;

    /**
     * session创建时间
     */
    private Date startTimestamp;
    /**
     * session最后访问时间
     */
    private Date lastAccessTime;

    /**
     * 超时时间
     */
    private Long timeout;

    /**
     * 是否是当前session
     */
    private boolean isSelf;
}
