package com.chief.evo.config;

import com.chief.evo.websocket.EVODataHandler;
import com.chief.evo.websocket.DBDataHandler; // 导入新的处理器类
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 原有路径
        registry.addHandler(evoDataHandler(), "/evo")
                .setAllowedOrigins("*");

        // 新增路径
        registry.addHandler(dbDataHandler(), "/db")
                .setAllowedOrigins("*");
    }

    @Bean
    public EVODataHandler evoDataHandler() {
        return new EVODataHandler();
    }

    // 新增处理器Bean
    @Bean
    public DBDataHandler dbDataHandler() {
        return new DBDataHandler();
    }
}
