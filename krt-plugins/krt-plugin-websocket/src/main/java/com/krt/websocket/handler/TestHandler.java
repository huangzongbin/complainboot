package com.krt.websocket.handler;

import com.alibaba.fastjson.JSONObject;
import com.krt.common.constant.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 测试处理器 可以根据代码编写自己的处理器
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月18日
 */
@Slf4j
public class TestHandler implements WebSocketHandler {

    /**
     * 在线用户列表
     */
    private static final ConcurrentHashMap<String, WebSocketSession> USERS = new ConcurrentHashMap<>();


    /**
     * 在WebSocket协商成功后调用，并且打开WebSocket连接准备使用
     *
     * @param webSocketSession
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        log.debug("成功建立连接");
        String userId = webSocketSession.getAttributes().get(GlobalConstant.SESSION_USER).toString();
        USERS.put(userId, webSocketSession);
        sendMessageToUser(userId, new TextMessage("成功建立socket连接~~~~~~~~~~"));
        log.debug("当前在线人数：" + USERS.size());
    }

    /**
     * 处理收到的webSocketMessage
     *
     * @param webSocketSession
     * @param webSocketMessage
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        try {
            JSONObject jsonobject = JSONObject.parseObject((String) webSocketMessage.getPayload());
            log.debug("服务器接收到来自：{} 的消息：{}", webSocketSession.getAttributes().get(GlobalConstant.SESSION_USER), jsonobject);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 处理来自底层WebSocket消息传输的错误
     *
     * @param webSocketSession
     * @param throwable        错误
     * @throws Exception 异常
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        log.debug("-------连接出错------------");
        USERS.remove(getUserId(webSocketSession));
    }

    /**
     * 在网络套接字连接关闭后或在传输错误发生后调用。
     * 尽管从技术上讲，会话可能仍然是开放的，但取决于底层实现，在这一点上发送消息是不鼓励的，而且很可能不会成功。
     *
     * @param webSocketSession webSocketSession
     * @param closeStatus      closeStatus
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        if (webSocketSession != null && webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        USERS.remove(getUserId(webSocketSession));
        log.debug("---------安全退出了websocket---------");
    }

    /**
     * WebSocketHandler是否处理部分消息
     *
     * @return 标志
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 发送信息给指定用户
     *
     * @param userId
     * @param message
     */
    public static boolean sendMessageToUser(String userId, TextMessage message) {
        if (USERS.get(userId) == null) {
            return false;
        }
        WebSocketSession session = USERS.get(userId);
        log.debug("发送消息给用户{}, 内容 {}", userId, message);
        if (session == null ) {
            return false;
        }
        if(!session.isOpen()){
            return false;
        }
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 广播信息
     *
     * @param message
     */
    public static boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        Set<String> clientIds = USERS.keySet();
        WebSocketSession session;
        log.debug("广播消息,内容 {}", message);
        for (String clientId : clientIds) {
            try {
                session = USERS.get(clientId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                } else {
                    USERS.remove(clientId);
                }
            } catch (IOException e) {
                e.printStackTrace();
                allSendSuccess = false;
            }
        }
        return allSendSuccess;
    }


    /**
     * 获取通道标识
     *
     * @param webSocketSession
     */
    private String getUserId(WebSocketSession webSocketSession) {
        try {
            return (String) webSocketSession.getAttributes().get(GlobalConstant.SESSION_USER);
        } catch (Exception e) {
            return null;
        }
    }
}