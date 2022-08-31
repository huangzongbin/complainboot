package com.krt.oauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.krt.common.base.BaseServiceImpl;
import com.krt.oauth.entity.ClientDetails;
import com.krt.oauth.mapper.ClientDetailsMapper;
import com.krt.oauth.service.IClientDetailsService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;


/**
 * 客户端详情服务层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月20日
 */
@Service
public class ClientDetailsServiceImpl extends BaseServiceImpl<ClientDetailsMapper, ClientDetails> implements IClientDetailsService {

    /**
     * 修改客户端
     *
     * @param clientDetails
     */
    @Override
    @CacheEvict(value = "clientDetailCache", key = "#clientDetails.clientId")
    public boolean updateById(ClientDetails clientDetails) {
        return super.updateById(clientDetails);
    }

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     * @return true:成功 false:失败
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteByIds(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            deleteById(id);
        }
        return true;
    }

    /**
     * 删除客户端详情
     *
     * @param id
     */
    @Override
    public boolean deleteById(Serializable id) {
        ClientDetails clientDetails = selectById(id);
        return deleteClident(clientDetails);
    }

    /**
     * 删除客户端
     *
     * @param clientDetails
     */
    @CacheEvict(value = "clientDetailCache", key = "#clientDetails.clientId")
    public boolean deleteClident(ClientDetails clientDetails) {
        return super.deleteById(clientDetails.getId());
    }


    /**
     * 根据客户端id查询
     *
     * @param clientId 客户端id
     */
    @Override
    @Cacheable(value = "clientDetailCache", key = "#clientId" ,unless = "#result == null")
    public ClientDetails selectByClientId(String clientId) {
        LambdaQueryWrapper<ClientDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ClientDetails::getClientId, clientId);
        queryWrapper.eq(ClientDetails::getState, "0");
        return selectOne(queryWrapper);
    }
}
