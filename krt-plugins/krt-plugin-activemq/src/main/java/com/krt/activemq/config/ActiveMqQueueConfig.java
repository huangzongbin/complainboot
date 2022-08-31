package com.krt.activemq.config;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * queue模式初始配置
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年08月17日
 */
@Configuration
public class ActiveMqQueueConfig {


    /**
     * 定义死信队列
     */
    @Bean(name = "dlqQueue")
    public Queue dlqQueue() {
        return new ActiveMQQueue("ActiveMQ.DLQ");
    }

    /**
     * 定义队列
     */
    @Bean(name = "defaultQueue")
    public Queue defaultQueue() {
        return new ActiveMQQueue("defaultQueue");
    }

    /**
     * 租金微信支付的回调队列
     */
    @Bean(name = "weiRentalPayBackQueue2")
    public Queue weiRentalPayBackQueue2() {
        return new ActiveMQQueue("weiRentalPayBackQueue2");
    }

    /**
     * 租金微信支付的回调队列3
     */
    @Bean(name = "weiRentalPayBackQueue3")
    public Queue weiRentalPayBackQueue3() {
        return new ActiveMQQueue("weiRentalPayBackQueue3");
    }

}
