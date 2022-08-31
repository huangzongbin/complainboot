package com.krt.activemq.consumer;

import com.krt.common.bean.ReturnBean;
import com.krt.common.exception.KrtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 消息消费者
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年08月17日
 */
@Slf4j
@Component
public class ActiveMqConsumer {

    @Autowired
    @Qualifier("dlqQueue")
    private Queue dlqQueue;

    /**
     * 消费主题消息
     *
     * @param message
     */
    @JmsListener(destination = "defaultTopic", containerFactory = "jmsListenerContainerTopic")
    public void receiveTopic(TextMessage message, Session session) throws JMSException {
        try {
            log.debug("接收到defaultTopic消息===========》{}",message.getText());
            throw new KrtException(ReturnBean.error());
        } catch (Exception e) {
            session.recover();
        }
    }

    /**
     * 消费队列消息
     *
     * @param message
     */
    @JmsListener(destination = "defaultQueue", containerFactory = "jmsListenerContainerQueue")
    public void receiveQueue(TextMessage message, Session session) throws JMSException {
        try {
            log.debug("接收到defaultQueue消息===========》{}",message.getText());
            message.acknowledge();
            throw new KrtException(ReturnBean.error());
        } catch (Exception e) {
            session.recover();
        }
    }
}
