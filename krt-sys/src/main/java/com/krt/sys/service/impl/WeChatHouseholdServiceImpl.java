package com.krt.sys.service.impl;

import org.springframework.stereotype.Service;
import com.krt.sys.entity.WeChatHousehold;
import com.krt.sys.mapper.WeChatHouseholdMapper;
import com.krt.sys.service.IWeChatHouseholdService;
import com.krt.common.base.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;


/**
 * 微信与住户绑定关系服务接口实现层
 *
 * @author zhangdb
 * @version 1.0
 * @date 2019年06月10日
 */
@Service
public class WeChatHouseholdServiceImpl extends BaseServiceImpl<WeChatHouseholdMapper, WeChatHousehold> implements IWeChatHouseholdService {

    @Override
    public void unbound(WeChatHousehold weChatHousehold) {
        baseMapper.unbound(weChatHousehold);
    }
}
