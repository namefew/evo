package com.chief.evo.websocket;

import com.chief.evo.dto.DBConstant;
import com.chief.evo.dto.RoadParser;
import com.chief.evo.entity.DbRouletteResult;
import com.chief.evo.entity.DbTable;
import com.chief.evo.service.DbGameResultService;
import com.chief.evo.service.DbTableService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
public class DBDataHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    DbTableService dbTableService;
    @Autowired
    DbGameResultService dbGameResultService;

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
            Integer protocolId = rootNode.has("protocolId")?rootNode.path("protocolId").asInt():-1;
            Integer messageId = rootNode.has("messageId")?rootNode.get("messageId").asInt():-1;
            boolean success = rootNode.path("success").asBoolean(true); // 默认为 true
            if (rootNode.has("jsonData")) {
                String jsonData = rootNode.get("jsonData").asText();
                ((ObjectNode)rootNode).put("jsonData","{}");
                JsonNode jsonNode = objectMapper.readTree(jsonData);
                int id = jsonNode.has("id")?jsonNode.get("id").asInt():-1;
                if (jsonNode.has("data")) {
                    String dataStr = jsonNode.get("data").asText();
                    ((ObjectNode)jsonNode).put("data","{}");
                    JsonNode dataNode = objectMapper.readTree(dataStr);
                    log.info("Message : {}\n jsonData : {}\n data : {}",rootNode.toString(),jsonNode.toString(),dataNode.toString());
                    if (id==DBConstant.TABLE_DATA_UPDATE){
                        JsonNode gameTableMap = jsonNode.path("gameTableMap");
                        updateGameTableMap(gameTableMap);
                    }
                } else {
                    log.info("Message : {}\n jsonData : {}",rootNode.toString(),jsonNode.toString());
                }

            } else {
                log.info("Message : {}",rootNode.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("处理消息失败", e);
        }
    }

    private void updateGameTableMap(JsonNode gameTableMap) {
        List<DbTable> dbTables = convertGameTableMapToDbTableList(gameTableMap);
        Map<Integer, DbTable> dbTableMap = dbTableService.getAllTables();
        List<DbTable> toBeAdded = new ArrayList<>();
        for (DbTable dbTable : dbTables) {
            if (!dbTableMap.containsKey(dbTable.getTableId())) {
                toBeAdded.add(dbTable);
            }
        }
        if(!toBeAdded.isEmpty()){
            dbTableService.insertList(toBeAdded);
        }
        gameTableMap.fields().forEachRemaining(entry -> {
            String tableIdStr = entry.getKey();

            DbTable dbTable =  dbTableService.getAllTables().get(Integer.parseInt(tableIdStr));
            if(dbTable==null)return;
            if(DBConstant.gameTypeId_roulette==dbTable.getGameTypeId()){
                JsonNode tableInfo = entry.getValue();
                if(tableInfo.has("roadPaper")) {
                    JsonNode roadInfo = tableInfo.path("roadPaper");
                    List<Integer> winNumbers =RoadParser.RouletteParser.parseBoot(roadInfo.path("winNumberRoad").asText());
                    List<DbRouletteResult> resultList = new ArrayList<>();
                    for (int i = 0; i < winNumbers.size(); i++) {
                        DbRouletteResult result = new DbRouletteResult();
                        result.setTableId(dbTable.getTableId());
                        result.setResult(winNumbers.get(i));
                        resultList.add(result);
                    }
                    dbGameResultService.addDbRouletteResult(resultList);
                }
            }else if(DBConstant.gameTypeId_sicbo==dbTable.getGameTypeId()){
                //TODO
            }else if(DBConstant.gameTypeId_color_disk==dbTable.getGameTypeId()){
                //TODO
            }
        });
    }

    private List<DbTable> convertGameTableMapToDbTableList(JsonNode gameTableMap) {
        List<DbTable> dbTables = new ArrayList<>();
        gameTableMap.fields().forEachRemaining(entry -> {
            String tableIdStr = entry.getKey();
            JsonNode tableInfo = entry.getValue();
            
            DbTable dbTable = new DbTable();
            dbTable.setTableId(Integer.parseInt(tableIdStr));
            dbTable.setTableName(tableInfo.path("tableName").asText());
            dbTable.setGameCasinoId(tableInfo.path("gameCasinoId").asInt());
            dbTable.setGameCasinoName(tableInfo.path("gameCasinoName").asText());
            dbTable.setGameTypeId(tableInfo.path("gameTypeId").asInt());
            dbTable.setGameTypeName(tableInfo.path("gameTypeName").asText());
            dbTable.setCreateTime(new Date());
            
            dbTables.add(dbTable);
        });
        return dbTables;
    }

}
