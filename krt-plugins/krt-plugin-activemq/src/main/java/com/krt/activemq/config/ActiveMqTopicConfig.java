package com.krt.activemq.config;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Topic;

/**
 * Topic模式初始化
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年08月17日
 */
@Configuration
public class ActiveMqTopicConfig {

    /**
     * 定义主题
     */
    @Bean(name = "defaultTopic")
    public Topic defaultTopic() {
        return new ActiveMQTopic("defaultTopic");
    }

}
