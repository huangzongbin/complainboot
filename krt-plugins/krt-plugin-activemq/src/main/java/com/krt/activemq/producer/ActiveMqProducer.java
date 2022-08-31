package com.krt.activemq.producer;

import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * 消息生产者
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年08月17日
 */
@Component
public class ActiveMqProducer {

    @Autowired
    @Qualifier("jmsTemplateTopic")
    private JmsTemplate jmsTemplateTopic;

    @Autowired
    @Qualifier("jmsTemplateQueue")
    private JmsTemplate jmsTemplateQueue;

    @Autowired
    @Qualifier("defaultTopic")
    private Topic defaultTopic;

    @Autowired
    @Qualifier("defaultQueue")
    private Queue defaultQueue;

    /**
     * 发送消息
     */
    public void send() {
        jmsTemplateTopic.convertAndSend(this.defaultTopic, "发送的topic数据!");
        jmsTemplateQueue.convertAndSend(this.defaultQueue, "发送的queue数据!");
    }

    /**
     * 发送消息
     *
     * @param destination 目标
     * @param message     内容，一般为JSON
     */
    public void send(Destination destination, String message) {
        jmsTemplateQueue.convertAndSend(destination, message);
    }


    /**
     * 延时发送
     *
     * @param destination 目标
     * @param message     内容，一般为JSON
     * @param time        单位：ms）
     */
    public void delaySend(Destination destination, String message, Long time) {
        // 发送消息
        jmsTemplateQueue.send(destination, session -> {
            TextMessage textMessage = session.createTextMessage(message);
            // 设置延时时间【关键】
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
            return textMessage;
        });
    }

}
