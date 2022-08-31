package com.krt.oauth.mapper;

import com.krt.common.base.BaseMapper;
import com.krt.oauth.entity.OauthCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 认证code表映射层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月12日
 */
@Mapper
public interface OauthCodeMapper extends BaseMapper<OauthCode> {


}
