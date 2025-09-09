package com.chief.evo.config;

import com.chief.evo.websocket.*;
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

        // PP路径
        registry.addHandler(ppDataHandler(), "/pp")
                .setAllowedOrigins("*");
                
        // Ezugi路径
        registry.addHandler(ezugiDataHandler(), "/ezugi")
                .setAllowedOrigins("*");

        // WL路径
        registry.addHandler(wlDataHandler(), "/wl")
                .setAllowedOrigins("*");

        // BG路径
        registry.addHandler(bgDataHandler(), "/bg")
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

    // PP处理器Bean
    @Bean
    public PPDataHandler ppDataHandler() {
        return new PPDataHandler();
    }


    // Ezugi处理器Bean
    @Bean
    public EzugiDataHandler ezugiDataHandler() {
        return new EzugiDataHandler();
    }

    @Bean
    public WLDataHandler wlDataHandler() {
        return new WLDataHandler();
    }
  @Bean
    public BGDataHandler bgDataHandler() {
        return new BGDataHandler();
    }

}