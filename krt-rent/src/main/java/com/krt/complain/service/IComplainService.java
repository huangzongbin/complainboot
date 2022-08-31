package com.krt.complain.service;

import com.krt.complain.entity.Complain;
import com.krt.common.base.IBaseService;


/**
 * 投诉信息服务接口层
 *
 * @author 转移
 * @version 1.0
 * @date 2019年06月10日
 */
public interface IComplainService extends IBaseService<Complain>{

    boolean insertAndMsgLog(Complain entity);

    boolean updateByIdAndMsgLog(Complain entity);
}
