package com.krt.msgLog.service.impl;

import org.springframework.stereotype.Service;
import com.krt.msgLog.entity.TMsgLog;
import com.krt.msgLog.mapper.TMsgLogMapper;
import com.krt.msgLog.service.ITMsgLogService;
import com.krt.common.base.BaseServiceImpl;


/**
 * 消息日志服务接口实现层
 *
 * @author 转移
 * @version 1.0
 * @date 2019年06月10日
 */
@Service
public class TMsgLogServiceImpl extends BaseServiceImpl<TMsgLogMapper, TMsgLog> implements ITMsgLogService {

}
