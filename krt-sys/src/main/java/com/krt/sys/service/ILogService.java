package com.krt.sys.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krt.common.base.IBaseService;
import com.krt.sys.entity.Log;

import java.util.Map;

/**
 * 日志服务接口层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年03月29日
 */
public interface ILogService extends IBaseService<Log> {

    /**
     * 清空日志
     */
    void deleteAll();

    /**
     * 添加日志
     *
     * @param log 日志
     */
    void insertLog(Log log);


}
