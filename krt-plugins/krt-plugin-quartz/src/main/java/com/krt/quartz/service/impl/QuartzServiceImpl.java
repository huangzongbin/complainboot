package com.krt.quartz.service.impl;


import com.krt.common.base.BaseServiceImpl;
import com.krt.common.util.SpringUtils;
import com.krt.quartz.entity.Quartz;
import com.krt.quartz.enums.JobConcurrentEnum;
import com.krt.quartz.enums.JobStausEnum;
import com.krt.quartz.mapper.QuartzMapper;
import com.krt.quartz.service.IQuartzService;
import com.krt.quartz.util.QuartzFactory;
import com.krt.quartz.util.QuartzFactoryDisallowConcurrentExecution;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 任务调度服务实现层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月22日
 */
@Slf4j
@Service
public class QuartzServiceImpl extends BaseServiceImpl<QuartzMapper, Quartz> implements IQuartzService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 添加定时任务
     *
     * @param quartz 定时任务
     */
    @Override
    public boolean insert(Quartz quartz) {
        quartz.setStatus(JobStausEnum.START.getValue());
        return super.insert(quartz);
    }

    /**
     * 启动 or 停止任务
     */
    @Override
    public void updateStatus(Integer id, String status) throws SchedulerException {
        Quartz quartzJob = selectById(id);
        if (quartzJob == null) {
            return;
        }
        if (JobStausEnum.STOP.getValue().equals(status)) {
            deleteJob(quartzJob);
            quartzJob.setStatus(JobStausEnum.START.getValue());
        } else if (JobStausEnum.START.getValue().equals(status)) {
            quartzJob.setStatus(JobStausEnum.STOP.getValue());
            addJob(quartzJob);
        }
        baseMapper.updateById(quartzJob);
    }

    /**
     * 停止任务
     */
    @Override
    public void deleteJob(Quartz quartz) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(quartz.getName(), quartz.getGroupName());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 添加任务
     */
    @Override
    public void addJob(Quartz quartz) throws SchedulerException {
        if (quartz == null || !JobStausEnum.STOP.getValue().equals(quartz.getStatus())) {
            return;
        }
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(quartz.getName(), quartz.getGroupName());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 不存在，创建一个
        if (null == trigger) {
            Class clazz = JobConcurrentEnum.YES.getValue().equals(quartz.getIsConcurrent()) ? QuartzFactory.class : QuartzFactoryDisallowConcurrentExecution.class;
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(quartz.getName(), quartz.getGroupName()).build();
            jobDetail.getJobDataMap().put("quartz", quartz);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getExpression());
            trigger = TriggerBuilder.newTrigger().withIdentity(quartz.getName(), quartz.getGroupName()).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getExpression());
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    /**
     * 立即执行一次任务
     */
    @Override
    public void doOnce(Integer id) throws SchedulerException {
        Quartz quartzJob = selectById(id);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(quartzJob.getName(), quartzJob.getGroupName());
        scheduler.triggerJob(jobKey);
    }


    /**
     * 服务器启动初始化正在运行的任务
     */
    @Override
    @PostConstruct
    public void init() throws Exception {
        //获取所有需要运行的任务
        List<Quartz> jobList = selectList();
        for (Quartz quartz : jobList) {
            //多个web项目共享定时任务
            Object obj = null;
            try {
                if (quartz.getSpringId() != null && !"".equals(quartz.getSpringId())) {
                    obj = SpringUtils.getBean(quartz.getSpringId());
                } else {
                    Class clazz = Class.forName(quartz.getBeanClass());
                    obj = clazz.newInstance();
                }
            } catch (Exception e) {
                log.info("执行任务类不存在");
            }
            if (obj != null) {
                addJob(quartz);
            }
        }
    }

    /**
     * 异步更新定时任务
     */
    @Override
    @Async("taskExecutor")
    public void updateQuartz(Quartz quartz) {
        updateById(quartz);
    }

}
