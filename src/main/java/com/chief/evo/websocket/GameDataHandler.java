package com.chief.evo.websocket;

import com.chief.evo.service.GameResultService;
import com.chief.evo.service.GameTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.Session;

import com.chief.evo.entity.GameTable;
import com.chief.evo.entity.RouletteResult;
import com.chief.evo.entity.SicboResult;
import org.springframework.beans.factory.annotation.Autowired;
@Slf4j
public class GameDataHandler extends TextWebSocketHandler {
    
    // 内存中维护的桌子信息 Map (id -> 桌子信息)
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // 注入Mapper
    @Autowired
    private GameTableService gameTableService;
    @Autowired
    private GameResultService gameResultService;

    
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
        log.info("Received message: " + payload);
        log.info("Received message: " + payload);

        try {
            JsonNode rootNode = objectMapper.readTree(payload);
            String messageType = rootNode.path("type").asText();
            
            // 处理 lobby.configs 消息
            if ("lobby.configs".equals(messageType)) {
                processConfigsMessage(rootNode.path("args").path("configs"));
            } 
            // 处理 lobby.historyUpdated 消息
            else if ("lobby.historyUpdated".equals(messageType)) {
                processHistoryUpdatedMessage(rootNode.path("args"));
            }
            // 处理 lobby.histories 消息
            else if ("lobby.histories".equals(messageType)) {
                processHistoriesMessage(rootNode.path("args").path("histories"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 处理解析异常
        }
    }

    /**
     * 将lobby.configs的JSON数据转换为GameTable对象列表
     * @param configsNode 包含桌子配置的JSON节点
     * @return GameTable对象列表
     */
    private List<GameTable> convertConfigsToGameTableList(JsonNode configsNode) {
        List<GameTable> tables = new ArrayList<>();
        configsNode.fields().forEachRemaining(entry -> {
            String tableId = entry.getKey();
            JsonNode tableInfo = entry.getValue();
            GameTable table = new GameTable();
            
            // 设置基本字段
            table.setId(tableId);
            table.setTitle(tableInfo.path("title").asText());
            table.setGameType(tableInfo.path("gt").asText());
            table.setFrontendApp(tableInfo.path("frontendApp").asText());
            table.setGameProvider(tableInfo.path("gp").asText());
            table.setGv(tableInfo.path("gv").asText());
            table.setPublished(tableInfo.path("published").asBoolean());
            table.setLang(tableInfo.path("lang").asText());
            
            // 解析opensAt时间
            JsonNode opensAtNode = tableInfo.path("opensAt");
            if (!opensAtNode.isMissingNode()) {
                String timeStr = opensAtNode.path("time").asText();
                try {
                    DateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    table.setOpensAt(timeFormat.parse(timeStr));
                } catch (ParseException e) {
                    // 记录解析错误
                    log.error("Error parsing opensAt time: " + timeStr);
                }
            }
            
            // 解析投注限制
            JsonNode blNode = tableInfo.path("bl");
            if (!blNode.isMissingNode()) {
                table.setMinBet(blNode.path("min").asDouble());
                table.setMaxBet(blNode.path("max").asDouble());
            }
            
            tables.add(table);
        });
        return tables;
    }

    /**
     * 处理桌子配置信息
     * @param configsNode 桌子配置的JSON节点
     */
    private void processConfigsMessage(JsonNode configsNode) {
        List<GameTable> tables = convertConfigsToGameTableList(configsNode);
        gameTableService.insertList(tables);
    }

    /**
     * 处理历史更新消息
     * @param argsNode 历史数据的JSON节点
     */
    private void processHistoryUpdatedMessage(JsonNode argsNode) {
        argsNode.fields().forEachRemaining(entry -> {
            String tableId = entry.getKey();
            JsonNode historyData = entry.getValue();
            // 从内存Map获取桌子类型
            GameTable gameTable = gameTableService.getAllTables().get(tableId);
            if (gameTable == null) return;
            // 根据游戏类型处理不同数据
            if ("roulette".equals(gameTable.getGameType())) {
                processRouletteResult(gameTable, historyData);
            } else if ("sicbo".equals(gameTable.getGameType())) {
                processSicboResult(gameTable, historyData);
            }
        });
    }

    /**
     * 处理历史数据消息 (lobby.histories)
     * @param argsNode 历史数据的JSON节点
     */
    private void processHistoriesMessage(JsonNode argsNode) {
        argsNode.fields().forEachRemaining(entry -> {
            String tableId = entry.getKey();
            JsonNode historyArray = entry.getValue();
            // 从内存Map获取桌子类型
            GameTable gameTable = gameTableService.getAllTables().get(tableId);
            if (gameTable == null) return;
            if ("roulette".equals(gameTable.getGameType())) {
                processRouletteResult(gameTable, historyArray.path("results"));
            } else if ("sicbo".equals(gameTable.getGameType())) {
                processSicboResult(gameTable, historyArray.path("results"));
            }

        });
    }


    private List<RouletteResult> processRouletteResult(GameTable gameTable, JsonNode data) {
       List<RouletteResult> rouletteResults = convertResultsToRouletteResultList(data, gameTable.getId());
       return gameResultService.addRouletteResult(rouletteResults, gameTable);
    }


    private List<RouletteResult> convertResultsToRouletteResultList(JsonNode data, String tableId) {
        List<RouletteResult> rouletteResults = new ArrayList<>();
        if (data.isArray()) {
            for (JsonNode roundNode : data) {
                if (roundNode.isArray() && roundNode.size() > 0) {
                    JsonNode resultNode = roundNode.get(0);
                    RouletteResult result = new RouletteResult();
                    result.setTableId(tableId);
                    result.setResult(resultNode.path("number").asInt());
                    
                    // 处理颜色字段（可能不存在）
                    String color = resultNode.path("color").asText(null);
                    if (color != null) {
                        // 简化颜色值为单字母（如"Red"->"R"）
                        result.setColor(color.substring(0, 1).toUpperCase());
                    }
                    rouletteResults.add(result);
                }
            }
        }
        return rouletteResults;
    }

    private List<SicboResult> convertResultsToSicboResultList(JsonNode results, String tableId) {
        List<SicboResult> sicboResults = new ArrayList<>();
        if (results.isArray()) {
            for (JsonNode resultNode : results) {
                JsonNode valueNode = resultNode.path("value");
                if (valueNode.isArray() && valueNode.size() == 3) {
                    SicboResult result = new SicboResult();
                    result.setTableId(tableId);
                    
                    // 将骰子符号转换为数字
                    result.setDice1(convertDieSymbolToNumber(valueNode.get(0).asText()));
                    result.setDice2(convertDieSymbolToNumber(valueNode.get(1).asText()));
                    result.setDice3(convertDieSymbolToNumber(valueNode.get(2).asText()));
                    sicboResults.add(result);
                }
            }
        }
        return sicboResults;
    }
    
    private int convertDieSymbolToNumber(String symbol) {
        switch (symbol) {
            case "⚀" : return 1;
            case "⚁" : return 2;
            case "⚂" : return 3;
            case "⚃" : return 4;
            case "⚄" : return 5;
            case "⚅" : return 6;
            default : return 0;
        }
    }

    private void processSicboResult(GameTable gameTable, JsonNode results) {
        List<SicboResult> sicboResults = convertResultsToSicboResultList(results, gameTable.getId());
        gameResultService.addSicboResult(sicboResults, gameTable);
    }

}