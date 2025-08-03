package com.chief.evo.websocket;

import com.chief.evo.dto.PPTableName;
import com.chief.evo.entity.PpRouletteResult;
import com.chief.evo.entity.PpSicboResult;
import com.chief.evo.entity.PpTable;
import com.chief.evo.service.PpGameResultService;
import com.chief.evo.service.PpTableService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.Session;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Slf4j
public class PPDataHandler extends TextWebSocketHandler {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // 注入Mapper
    @Autowired
    private PpTableService ppTableService;
    @Autowired
    private PpGameResultService ppGameResultService;

    
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
        try {
            JsonNode rootNode = objectMapper.readTree(payload);
            if(!rootNode.has("tableId")){
                return;
            }
            String tableId = rootNode.path("tableId").asText();
            PpTable ppTable =ppTableService.getAllTables().getOrDefault(tableId,null);
            if(ppTable==null && rootNode.has("tableName")){
                    PpTable table = new PpTable();
                    table.setTableId(tableId);
                    table.setTableNameEn(rootNode.path("tableName").asText());
                    table.setTableType(rootNode.path("tableType").asText());
                    table.setTableName(PPTableName.getTableNameCN(table.getTableNameEn()));
                    ppTableService.insertList(Arrays.asList( table));
                    ppTable = table;
            }
            if(ppTable==null){
                return;
            }
            if(rootNode.has("last20Results")){
                JsonNode historyArray = rootNode.path("last20Results");
                if ("roulette".equalsIgnoreCase(ppTable.getTableType())) {
                    processRouletteResult(ppTable, historyArray);
                } else if ("sicbo".equalsIgnoreCase(ppTable.getTableType())) {
                    processSicboResult(ppTable,historyArray);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 处理解析异常
        }
    }



    private List<PpRouletteResult> processRouletteResult(PpTable gameTable, JsonNode data) {
       List<PpRouletteResult> rouletteResults = convertResultsToRouletteResultList(data, gameTable.getTableId());
       return ppGameResultService.addRouletteResult(rouletteResults, gameTable);
    }


    private List<PpRouletteResult> convertResultsToRouletteResultList(JsonNode data, String tableId) {
        List<PpRouletteResult> rouletteResults = new ArrayList<>();
        if (data.isArray()) {
            for (JsonNode roundNode : data) {
                PpRouletteResult result = new PpRouletteResult();
                result.setTableId(tableId);
                result.setResult(roundNode.path("result").asInt());
                // 处理颜色字段（可能不存在）
                String color = roundNode.path("color").asText(null);
                result.setColor(color);
                String time = roundNode.path("time").asText(null);
                if(time!=null){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm:ss a", Locale.ENGLISH);
                    try {
                        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
                        result.setCreateTime(localDateTime);
                    } catch (Exception e) {
                        log.error("Failed to parse time: " + time, e);
                    }
                }
                result.setRoundId(roundNode.path("gameId").asText());
                rouletteResults.add(result);
            }
        }
        return rouletteResults;
    }

    private List<PpSicboResult> convertResultsToSicboResultList(JsonNode results, String tableId) {
        List<PpSicboResult> sicboResults = new ArrayList<>();
        if (results.isArray()) {
            for (JsonNode resultNode : results) {
                PpSicboResult result = new PpSicboResult();
                result.setTableId(tableId);

                // 将骰子符号转换为数字
                result.setDice1(Integer.parseInt(resultNode.path("die1").asText()));
                result.setDice2(Integer.parseInt(resultNode.path("die2").asText()));
                result.setDice3(Integer.parseInt(resultNode.path("die3").asText()));
                result.setRoundId(resultNode.path("gameId").asText());
                String time = resultNode.path("time").asText(null);
                if(time!=null){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy h:mm:ss a",Locale.ENGLISH);
                    try {
                        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
                        result.setCreateTime(localDateTime);
                    } catch (Exception e) {
                        log.error("Failed to parse time: " + time, e);
                    }
                }
                sicboResults.add(result);
            }
        }
        return sicboResults;
    }


    private void processSicboResult(PpTable gameTable, JsonNode results) {
        List<PpSicboResult> sicboResults = convertResultsToSicboResultList(results, gameTable.getTableId());
        ppGameResultService.addSicboResult(sicboResults, gameTable);
    }

}