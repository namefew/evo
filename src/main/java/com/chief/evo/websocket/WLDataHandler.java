package com.chief.evo.websocket;

import com.chief.evo.dto.WlMessageTypes;
import com.chief.evo.service.WlGameBusiness;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.Session;

@Slf4j
public class WLDataHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WlGameBusiness wlGameBusiness;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 设置WebSocket会话的最大文本消息缓冲区大小为1MB
        if (session instanceof StandardWebSocketSession) {
            Session nativeSession = ((StandardWebSocketSession) session).getNativeSession();
            nativeSession.setMaxTextMessageBufferSize(1024 * 1024);
        }
        super.afterConnectionEstablished(session);
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        try {
            JsonNode rootNode = objectMapper.readTree(payload);
            if(rootNode.has("sid")) {
                Integer sid = rootNode.path("sid").asInt();
                String action = WlMessageTypes.getMessageTypesMap().get(sid);
                JsonNode dataNode = rootNode.path("data");
                wlGameBusiness.onWSMessage(sid, dataNode);
                log.info("WL Message : {} {}",  action==null?sid:action,dataNode);
            }else{
                log.info("WL Message : {}", payload);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("处理消息失败", e);
        }
    }

}