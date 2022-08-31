package com.krt.oauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.krt.common.base.BaseServiceImpl;
import com.krt.oauth.entity.OauthCode;
import com.krt.oauth.mapper.OauthCodeMapper;
import com.krt.oauth.service.IOauthCodeService;
import org.springframework.stereotype.Service;

/**
 * 认证code接口实现层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月27日
 */
@Service
public class OauthCodeServiceImpl extends BaseServiceImpl<OauthCodeMapper, OauthCode> implements IOauthCodeService {

    /**
     * 查询认证编码
     *
     * @param username 用户名
     * @param clientId 客户端
     */
    @Override
    public OauthCode selectOauthCodeByUsernameClientId(String username, String clientId) {
        LambdaQueryWrapper<OauthCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OauthCode::getUsername,username);
        queryWrapper.eq(OauthCode::getClientId,clientId);
        return selectOne(queryWrapper);
    }

    /**
     * 查询认证编码
     *
     * @param code     认证编码
     * @param clientId 客户端
     */
    @Override
    public OauthCode selectOauthCodeByCodeAndClientId(String code, String clientId) {
        LambdaQueryWrapper<OauthCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OauthCode::getCode,code);
        queryWrapper.eq(OauthCode::getClientId,clientId);
        return selectOne(queryWrapper);
    }
}
