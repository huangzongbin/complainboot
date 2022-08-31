package com.krt.quartz.util;

import com.krt.common.util.SpringUtils;
import com.krt.quartz.entity.Quartz;
import com.krt.quartz.service.IQuartzService;
import com.krt.quartz.service.impl.QuartzServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import java.lang.reflect.Method;


/**
 * 任务操作工具类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年3月8日
 */
@Slf4j
public class TaskUtils {

    private static IQuartzService quartzService = SpringUtils.getBean(QuartzServiceImpl.class);

    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param scheduleJob
     */
    public static void invokMethod(Quartz scheduleJob, JobExecutionContext context) {
        Object object = null;
        Class clazz;
        if (scheduleJob.getSpringId() != null && !"".equals(scheduleJob.getSpringId())) {
            object = SpringUtils.getBean(scheduleJob.getSpringId());
        } else if (scheduleJob.getBeanClass() != null && !"".equals(scheduleJob.getBeanClass())) {
            try {
                clazz = Class.forName(scheduleJob.getBeanClass());
                object = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (object == null) {
            log.error("任务名称 = {} ---------------未启动成功，请检查是否配置正确！！！", scheduleJob.getName());
            return;
        }
        clazz = object.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(scheduleJob.getMethod());
        } catch (NoSuchMethodException e) {
            log.error("任务名称 = {} ---------------未启动成功，方法名设置错误！！！", scheduleJob.getName());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (method != null) {
            try {
                method.invoke(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        scheduleJob.setPreviousFireTime(context.getPreviousFireTime());
        scheduleJob.setNextFireTime(context.getNextFireTime());
        quartzService.updateQuartz(scheduleJob);
        log.debug("任务名称 = {} ----------启动成功", scheduleJob.getName());
    }
}
