package com.krt.sys.mapper;

import com.krt.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.krt.sys.entity.WeChatHousehold;

/**
 * 微信与住户绑定关系映射层
 *
 * @author zhangdb
 * @version 1.0
 * @date 2019年06月10日
 */
@Mapper
public interface WeChatHouseholdMapper extends BaseMapper<WeChatHousehold>{

    void unbound(WeChatHousehold weChatHousehold);
}
