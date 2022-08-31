package com.krt.websocket.config;

import com.krt.websocket.handler.TestHandler;
import com.krt.websocket.interceptor.WebSocketInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket配置器
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月18日
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //handler是webSocket的核心，配置入口
        registry.addHandler(new TestHandler(), "/ws/testHandle")
                //允许跨域
                .setAllowedOrigins("*")
                .addInterceptors(new WebSocketInterceptor());
    }
}
