package com.krt.oauth.mapper;

import com.krt.common.base.BaseMapper;
import com.krt.oauth.entity.ClientDetails;
import org.apache.ibatis.annotations.Mapper;


/**
 * 客户端详情映射层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月20日
 */
@Mapper
public interface ClientDetailsMapper extends BaseMapper<ClientDetails> {

}
