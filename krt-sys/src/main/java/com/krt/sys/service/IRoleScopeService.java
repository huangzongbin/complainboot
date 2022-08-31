package com.krt.sys.service;

import com.krt.common.base.IBaseService;
import com.krt.sys.entity.RoleScope;
import com.krt.sys.entity.User;

import java.util.List;

/**
 * 角色数据权限服务接口层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年06月28日
 */
public interface IRoleScopeService extends IBaseService<RoleScope> {

    
    /**
     * 获取用户的数据权限
     *
     * @param user
     * @return {@link List<String>}
     */
    List<String> selectUserDataScope(User user);
}
