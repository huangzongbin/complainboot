package com.krt.quartz.util;

import com.krt.quartz.entity.Quartz;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年3月8日
 */
@DisallowConcurrentExecution
public class QuartzFactoryDisallowConcurrentExecution implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        Quartz scheduleJob = (Quartz) context.getMergedJobDataMap().get("quartz");
        TaskUtils.invokMethod(scheduleJob,context);
    }
}