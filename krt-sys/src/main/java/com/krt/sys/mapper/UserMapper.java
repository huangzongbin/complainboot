package com.krt.sys.mapper;

import com.krt.common.base.BaseMapper;
import com.krt.sys.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户映射层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年03月9:44日
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据username查询User
     */
    User selectByUsername(User user);

    /**
     * 根据idcard删除
     */
    int deleteByIdcard(User user);

    /**
     * 通过idcard查找
     */
    User selectByIdcard(User user);
}
