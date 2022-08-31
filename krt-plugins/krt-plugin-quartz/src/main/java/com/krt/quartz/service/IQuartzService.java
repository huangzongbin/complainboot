package com.krt.quartz.service;

import com.krt.common.base.IBaseService;
import com.krt.quartz.entity.Quartz;
import org.quartz.SchedulerException;

/**
 * 任务调度服务接口层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月22日
 */
public interface IQuartzService extends IBaseService<Quartz> {

    /**
     * 启动 or 停止任务
     *
     * @param id     任务id
     * @param status 任务状态
     * @throws SchedulerException
     */
    void updateStatus(Integer id, String status) throws SchedulerException;

    /**
     * 停止任务
     *
     * @param quartz 任务
     * @throws SchedulerException
     */
    void deleteJob(Quartz quartz) throws SchedulerException;

    /**
     * 添加任务
     *
     * @param quartz 任务
     * @throws SchedulerException
     */
    void addJob(Quartz quartz) throws SchedulerException;


    /**
     * 立即执行一次任务
     *
     * @param id 任务id
     * @throws SchedulerException
     */
    void doOnce(Integer id) throws SchedulerException;


    /**
     * 服务器启动初始化正在运行的任务
     *
     * @throws Exception
     */
    void init() throws Exception;

    /**
     * 异步更新定时任务
     *
     * @param quartz 定时器
     */
    void updateQuartz(Quartz quartz);
}
