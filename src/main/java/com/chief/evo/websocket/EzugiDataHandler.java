package com.chief.evo.websocket;

import com.chief.evo.dto.EzugiTableNames;
import com.chief.evo.dto.RoadParser;
import com.chief.evo.entity.EzugiColorDiskResult;
import com.chief.evo.entity.EzugiRouletteResult;
import com.chief.evo.entity.EzugiSicboResult;
import com.chief.evo.entity.EzugiTable;
import com.chief.evo.service.EzugiGameResultService;
import com.chief.evo.service.EzugiTableService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.Session;
import java.util.*;

@Slf4j
public class EzugiDataHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    EzugiTableService ezugiTableService;
    @Autowired
    EzugiGameResultService ezugiGameResultService;

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
            log.info("Ezugi Message : {}", payload);

            JsonNode jsonNode = objectMapper.readTree(payload);
            String messageType = jsonNode.has("MessageType") ? jsonNode.get("MessageType").asText() : null;
            String subMessageType = jsonNode.has("subMessageType") ? jsonNode.get("subMessageType").asText() : null;

            if ("InitialData".equals(messageType)) {
                handleInitialData(jsonNode);
            } else if ("UPDATED_LOBBY_DATA".equals(messageType)) {
                if ("UPDATED_TABLE_HISTORY".equals(subMessageType) ||
                        "UPDATE_FREE_SEATS".equals(subMessageType)
                       ) {
                    updateTableHistory(jsonNode);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("处理消息失败", e);
        }
    }



    private void handleInitialData(JsonNode initialData) {
        // 处理games字段，转化为EzugiTable列表并添加到数据库
        JsonNode gamesNode = initialData.path("games");
        if (gamesNode.isArray()) {
            List<EzugiTable> ezugiTables = new ArrayList<>();
            for (JsonNode gameNode : gamesNode) {
                EzugiTable table = new EzugiTable();
                table.setTableId(gameNode.path("TableID").asText());
                String tableEnName = gameNode.path("TableName").asText();
                table.setTableNameEn(tableEnName);
                table.setTableName(EzugiTableNames.getCNName(table.getTableId(),tableEnName));
                table.setTableType(gameNode.path("GameTypeID").asText());
                table.setCreateTime(new Date());
                ezugiTables.add(table);
            }
            
            if (!ezugiTables.isEmpty()) {
                // 获取已存在的表，避免重复插入
                Map<String, EzugiTable> existingTables = ezugiTableService.getAllTables();
                List<EzugiTable> newTables = new ArrayList<>();
                for (EzugiTable table : ezugiTables) {
                    if (!existingTables.containsKey(table.getTableId())) {
                        newTables.add(table);
                    }
                }
                if (!newTables.isEmpty()) {
                    ezugiTableService.insertList(newTables);
                }
            }
        }
        
        // 处理WidgetData字段中的History数据
        JsonNode widgetDataNode = initialData.path("WidgetData");
        if (widgetDataNode.isArray()) {
            for (JsonNode tableNode : widgetDataNode) {
                String tableId = tableNode.path("TableId").asText();
                if(!tableNode.has("History"))continue;
                JsonNode historyNode = tableNode.path("History");
                if ( historyNode.isArray() && !historyNode.isEmpty()) {
                   processHistoryData(tableId, historyNode);

                }
            }
        }
    }

    private void updateTableHistory(JsonNode historyData) {
        String tableId = historyData.has("TableId")?historyData.path("TableId").asText():historyData.has("tableId")?historyData.path("tableId").asText():null   ;
        if (tableId == null || tableId.isEmpty()) {
            return;
        }

        if (historyData.has("History")) {
            JsonNode historyNode = historyData.path("History");
            if (historyNode.isArray() && !historyNode.isEmpty()) {
                processHistoryData(tableId, historyNode);
            }
        }

        if (historyData.has("GameResults")) {
            String gameType = historyData.has("gameType")?historyData.path("gameType").asText():null;
            EzugiTable table = ezugiTableService.getAllTables().get(tableId);
            if(table==null &&gameType!=null){
                table = new EzugiTable();
                table.setTableId(tableId);
                table.setTableType(gameType);
                String tableName = EzugiTableNames.getCNName(tableId,null);
                if(tableName!=null){
                    table.setTableName(tableName);
                    table.setTableNameEn(tableName);
                    ezugiTableService.insertList(Arrays.asList( table));
                }
            }
            if(table!=null &&gameType==null){
                gameType = table.getTableType();
            }
            JsonNode gameResultsNode = historyData.path("GameResults");
            if (!gameResultsNode.isNull()) {
                processGameResults(gameType,tableId, gameResultsNode);
            }
        }

        if (historyData.has("AvailableSeats")) {
            // 处理座位信息，主要用于 Blackjack 游戏
            JsonNode availableSeatsNode = historyData.path("AvailableSeats");
            if (availableSeatsNode.isArray()) {
                // 可以根据需要处理座位信息
            }
        }
    }

    private void processHistoryData(String tableId, JsonNode historyNode) {
        EzugiTable table = ezugiTableService.getAllTables().getOrDefault(tableId, null);
        String gameType = table != null ? table.getTableType():historyNode.has("gameType")?historyNode.path("gameType").asText():null;
        if(EzugiTableNames.isSicboTable(gameType)){
            List<EzugiSicboResult> sicboResults = new ArrayList<>();
            for (JsonNode historyItem : historyNode) {
                EzugiSicboResult result = new EzugiSicboResult();
                result.setTableId(tableId);
                result.setDice1(historyItem.path("DiceOne").asInt());
                result.setDice2(historyItem.path("DiceTwo").asInt());
                result.setDice3(historyItem.path("DiceThree").asInt());
                String roundId = historyItem.has("RoundId")?historyItem.path("RoundId").asText():historyItem.has("roundId")?historyItem.path("roundId").asText():null ;
                result.setRoundId(roundId);
                sicboResults.add(result);
            }
            if (!sicboResults.isEmpty()) {
                ezugiGameResultService.addEzugiSicboResult(sicboResults, tableId);
            }
        }else if(EzugiTableNames.isRouletteTable(gameType)){
            List<EzugiRouletteResult> rouletteResults = new ArrayList<>();
            for (JsonNode historyItem : historyNode) {
                Integer resultValue = historyItem.path("WinningNumber").asInt();
                String roundId = historyItem.path("RoundId").asText();

                EzugiRouletteResult result = new EzugiRouletteResult();
                result.setTableId(tableId);
                result.setRoundId(roundId);
                result.setResult(resultValue);
                rouletteResults.add(result);
            }
            if (!rouletteResults.isEmpty()) {
                ezugiGameResultService.addEzugiRouletteResult(rouletteResults, tableId);
            }
        }else if(EzugiTableNames.isColorDiskTable(gameType)){
            List<EzugiColorDiskResult> sicboResults = new ArrayList<>();
            for (JsonNode historyItem : historyNode) {
                EzugiColorDiskResult result = new EzugiColorDiskResult();
                result.setTableId(tableId);
                String roundId = historyItem.has("RoundId")?historyItem.path("RoundId").asText():historyItem.has("roundId")?historyItem.path("roundId").asText():null ;
                result.setRoundId(roundId);
                result.setResult(4-historyItem.path("WhitesNumber").asInt());
                sicboResults.add(result);
            }
            if (!sicboResults.isEmpty()) {
                ezugiGameResultService.addEzugiColorDiskResult(sicboResults, tableId);
            }
        }
    }

    private void processGameResults(String gameType,String tableId, JsonNode gameResultsNode) {
        String roundId = gameResultsNode.has("RoundId")?gameResultsNode.path("RoundId").asText():gameResultsNode.has("roundId")?gameResultsNode.path("roundId").asText():"";
        if (EzugiTableNames.isSicboTable(gameType)) {
            EzugiSicboResult result = new EzugiSicboResult();
            result.setTableId(tableId);
            result.setDice1(gameResultsNode.path("DiceOne").asInt());
            result.setDice2(gameResultsNode.path("DiceTwo").asInt());
            result.setDice3(gameResultsNode.path("DiceThree").asInt());
            result.setRoundId(roundId);
            List<EzugiSicboResult> results = new ArrayList<>();
            results.add(result);
            ezugiGameResultService.addEzugiSicboResult(results, tableId);
        } else if (EzugiTableNames.isRouletteTable(gameType)) {
            EzugiRouletteResult result = new EzugiRouletteResult();
            result.setTableId(tableId);
            result.setRoundId(roundId);
            result.setResult(gameResultsNode.path("WinningNumber").asInt());
            

            List<EzugiRouletteResult> results = new ArrayList<>();
            results.add(result);
            ezugiGameResultService.addEzugiRouletteResult(results, tableId);
        } else if (EzugiTableNames.isColorDiskTable(gameType)) {
            EzugiColorDiskResult result = new EzugiColorDiskResult();
            result.setTableId(tableId);
            result.setRoundId(roundId);
            result.setResult(4-gameResultsNode.path("WhitesNumber").asInt());
            List<EzugiColorDiskResult> results = new ArrayList<>();
            results.add(result);
            ezugiGameResultService.addEzugiColorDiskResult(results, tableId);
        }
    }
}