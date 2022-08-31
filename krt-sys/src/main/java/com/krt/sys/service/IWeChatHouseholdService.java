package com.krt.sys.service;

import com.krt.sys.entity.WeChatHousehold;
import com.krt.common.base.IBaseService;


/**
 * 微信与住户绑定关系服务接口层
 *
 * @author zhangdb
 * @version 1.0
 * @date 2019年06月10日
 */
public interface IWeChatHouseholdService extends IBaseService<WeChatHousehold>{


    /**
     * 解绑用户微信与租户信息
     * @param weChatHousehold
     */
    void unbound(WeChatHousehold weChatHousehold);
}
