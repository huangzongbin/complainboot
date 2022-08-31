package com.krt.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.krt.common.base.BaseServiceImpl;
import com.krt.sys.entity.Log;
import com.krt.sys.mapper.LogMapper;
import com.krt.sys.service.ILogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 日志服务实现层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年03月29日
 */
@Service
public class LogServiceImpl extends BaseServiceImpl<LogMapper, Log> implements ILogService {

    /**
     * 添加日志
     *
     * @param log
     * @return true 添加成功 false 添加失败
     */
    @Override
    @Async("taskExecutor")
    public void insertLog(Log log) {
        super.insert(log);
    }

    /**
     * 清空日志
     */
    @Override
    public void deleteAll() {
        delete(new LambdaQueryWrapper<>());
    }
}
