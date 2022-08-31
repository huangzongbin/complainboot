package com.krt.quartz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 任务调度器初始化
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年04月12日
 */
@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactory() {
        return new SchedulerFactoryBean();
    }
}