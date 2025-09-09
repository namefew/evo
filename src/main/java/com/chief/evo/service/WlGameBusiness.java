package com.chief.evo.service;

import com.chief.evo.dto.WLRoom;
import com.chief.evo.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

import static com.chief.evo.dto.WlMessageTypes.*;

@Component
public class WlGameBusiness {
    ObjectMapper mapper = new ObjectMapper();

    private Map<Integer, WLRoom> rooms = new HashMap<Integer, WLRoom>();

    @Autowired
    private WlTableService wlTableService;
    @Autowired
    private WlGameResultService wlGameResultService;

    // 应用启动完成后初始化rooms
    @PostConstruct
    public void initRoomsFromDatabase() {
        Map<Integer,WlTable> tables = wlTableService.getAllTables();
        for (WlTable table : tables.values()) {
            WLRoom room = new WLRoom();
            room.setRoomId(table.getTableId());
            room.setName(table.getTableName());
            room.setType(table.getType());
            rooms.put(table.getTableId(), room);
        }
    }

    // 提供获取rooms的方法（如果其他类需要访问）
    public Map<Integer, WLRoom> getRooms() {
        return Collections.unmodifiableMap(rooms);
    }
    public void onWSMessage(Integer sid, JsonNode data){
        Integer roomId =  data.has("roomId")?data.get("roomId").asInt():null;
        WLRoom room = rooms.get(roomId);
        if (room == null && roomId!=null) {
            room = new WLRoom();
            room.setRoomId(roomId);
            rooms.put(roomId, room);
        }
        switch ( sid){
            case ROOM_LIST://{"group":[{"infos":[{"roomId":8201,"name":"极速 B01","name4H5":"极速 B01","type":1,"subType":82,"state":2,"betDuration":21000,"round":41,"countdown":16047,"roomMin":1000,"roomMax":5000000,"matchInfos":[921022,821202,601002,501002,711001,911301,701001,501001,801300,521002,811101,401001,501001,921212,201000,701001,612001,612001,612001,701000,921202,701000,701000,911101,401022,811021,703002,602001,401022,602001,723002,821202,821002,501021,101002,723002,602001,612001,401001,621012],"dealerInfo":{"dealerName":"Lara","imgNation":2,"imgUrl":"https://d3mp1h85h24821.cloudfront.net/8D03761619348141A3579D1AF291510B.jpeg","imgMD5":"+A8077C743C60525CA77CDFF014A1EEAB","dealerId":80616},"road":{"roomId":8201,"trendStatus":1,"trendId":3,"trendAmount":6,"weight":0,"time":"1755262062117"},"roundId":"5220099FFC43C","scenarioType":1,"roomUserCount":210,"areaLimit":[{"areaId":1,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":2,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":3,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":4,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":5,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":6,"areaBetMin":1000,"areaBetMax":3500000,"roundLimit":30},{"areaId":7,"areaBetMin":1000,"areaBetMax":3500000,"roundLimit":30},{"areaId":8,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":9,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":10,"areaBetMin":1000,"areaBetMax":30000,"roundLimit":30},{"areaId":11,"areaBetMin":1000,"areaBetMax":20000,"roundLimit":30},{"areaId":12,"areaBetMin":1000,"areaBetMax":20000,"roundLimit":30},{"areaId":13,"areaBetMin":1000,"areaBetMax":20000,"roundLimit":30},{"areaId":14,"areaBetMin":1000,"areaBetMax":30000,"roundLimit":30},{"areaId":15,"areaBetMin":1000,"areaBetMax":30000,"roundLimit":30},{"areaId":16,"areaBetMin":1000,"areaBetMax":100000,"roundLimit":30},{"areaId":17,"areaBetMin":1000,"areaBetMax":100000,"roundLimit":30},{"areaId":18,"areaBetMin":1000,"areaBetMax":50000,"roundLimit":30},{"areaId":19,"areaBetMin":1000,"areaBetMax":50000,"roundLimit":30},{"areaId":20,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":21,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":22,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":23,"areaBetMin":1000,"areaBetMax":4000000,"roundLimit":9999},{"areaId":24,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":25,"areaBetMin":1000,"areaBetMax":400000,"roundLimit":30},{"areaId":26,"areaBetMin":1000,"areaBetMax":250000,"roundLimit":30},{"areaId":27,"areaBetMin":1000,"areaBetMax":130000,"roundLimit":30},{"areaId":28,"areaBetMin":1000,"areaBetMax":100000,"roundLimit":30},{"areaId":29,"areaBetMin":1000,"areaBetMax":250000,"roundLimit":30},{"areaId":30,"areaBetMin":1000,"areaBetMax":200000,"roundLimit":9999},{"areaId":31,"areaBetMin":1000,"areaBetMax":2000000,"roundLimit":9999},{"areaId":32,"areaBetMin":1000,"areaBetMax":3000000,"roundLimit":30},{"areaId":33,"areaBetMin":1000,"areaBetMax":2000000,"roundLimit":9999},{"areaId":34,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":35,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":36,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":37,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":38,"areaBetMin":1000,"areaBetMax":200000,"roundLimit":30}]},{"roomId":8202,"name":"极速 B02","name4H5":"极速 B02","type":1,"subType":82,"state":2,"betDuration":21000,"round":19,"countdown":9075,"roomMin":1000,"roomMax":5000000,"matchInfos":[101001,901300,602001,821202,601000,811101,911101,821202,601022,701001,821202,911001,821002,821202,821202,621002,601000,821202],"dealerInfo":{"dealerName":"Joyce","imgNation":1,"imgUrl":"https://d3mp1h85h24821.cloudfront.net/85C02C40BB84DC8402C13BA0D0F0BFB9.jpeg","imgMD5":"+D257678F564E2CF9B94B95BEE3E02232","dealerId":81155},"road":{"roomId":8202,"trendStatus":1,"trendId":1,"trendAmount":5,"weight":1,"time":"1755262055161"},"roundId":"52200A9FFC432","scenarioType":1,"roomUserCount":294,"areaLimit":[{"areaId":1,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":2,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":3,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":4,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":5,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":6,"areaBetMin":1000,"areaBetMax":3500000,"roundLimit":30},{"areaId":7,"areaBetMin":1000,"areaBetMax":3500000,"roundLimit":30},{"areaId":8,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":9,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":10,"areaBetMin":1000,"areaBetMax":30000,"roundLimit":30},{"areaId":11,"areaBetMin":1000,"areaBetMax":20000,"roundLimit":30},{"areaId":12,"areaBetMin":1000,"areaBetMax":20000,"roundLimit":30},{"areaId":13,"areaBetMin":1000,"areaBetMax":20000,"roundLimit":30},{"areaId":14,"areaBetMin":1000,"areaBetMax":30000,"roundLimit":30},{"areaId":15,"areaBetMin":1000,"areaBetMax":30000,"roundLimit":30},{"areaId":16,"areaBetMin":1000,"areaBetMax":100000,"roundLimit":30},{"areaId":17,"areaBetMin":1000,"areaBetMax":100000,"roundLimit":30},{"areaId":18,"areaBetMin":1000,"areaBetMax":50000,"roundLimit":30},{"areaId":19,"areaBetMin":1000,"areaBetMax":50000,"roundLimit":30},{"areaId":20,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":21,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":22,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":23,"areaBetMin":1000,"areaBetMax":4000000,"roundLimit":9999},{"areaId":24,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":25,"areaBetMin":1000,"areaBetMax":400000,"roundLimit":30},{"areaId":26,"areaBetMin":1000,"areaBetMax":250000,"roundLimit":30},{"areaId":27,"areaBetMin":1000,"areaBetMax":130000,"roundLimit":30},{"areaId":28,"areaBetMin":1000,"areaBetMax":100000,"roundLimit":30},{"areaId":29,"areaBetMin":1000,"areaBetMax":250000,"roundLimit":30},{"areaId":30,"areaBetMin":1000,"areaBetMax":200000,"roundLimit":9999},{"areaId":31,"areaBetMin":1000,"areaBetMax":2000000,"roundLimit":9999},{"areaId":32,"areaBetMin":1000,"areaBetMax":3000000,"roundLimit":30},{"areaId":33,"areaBetMin":1000,"areaBetMax":2000000,"roundLimit":9999},{"areaId":34,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":35,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":36,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":37,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":38,"areaBetMin":1000,"areaBetMax":200000,"roundLimit":30}]},{"roomId":8203,"name":"极速 B03","name4H5":"极速 B03","type":1,"subType":82,"state":2,"betDuration":21000,"round":55,"countdown":1990,"roomMin":1000,"roomMax":5000000,"matchInfos":[501002,811001,701001,621002,501001,621012,921202,701000,921002,821002,821002,911101,811101,821202,511001,501002,911101,601002,821002,711001,602001,711001,704002,821202,801300,601002,921202,621022,821202,501001,201002,1000,921202,921202,821012,921002,612001,711001,921302,921202,723002,501001,723002,511001,911101,821222,703002,612001,401001,821002,511001,621002,811101,401011],"dealerInfo":{"dealerName":"Quna","imgNation":2,"imgUrl":"https://d3mp1h85h24821.cloudfront.net/D28A6CFB2A7FF58FBA1EAC41C067C03F.jpeg","imgMD5":"+6C2EFCB7D1AC5A6C3CAD5C8F486B8185","dealerId":80555},"road":{"roomId":8203,"trendStatus":2,"trendId":4,"trendAmount":3,"weight":0,"time":"1755262047636"},"roundId":"52200B9FFC429","scenarioType":1,"roomUserCount":48,"areaLimit":[{"areaId":1,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":2,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":3,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":4,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":5,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":6,"areaBetMin":1000,"areaBetMax":3500000,"roundLimit":30},{"areaId":7,"areaBetMin":1000,"areaBetMax":3500000,"roundLimit":30},{"areaId":8,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":9,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":10,"areaBetMin":1000,"areaBetMax":30000,"roundLimit":30},{"areaId":11,"areaBetMin":1000,"areaBetMax":20000,"roundLimit":30},{"areaId":12,"areaBetMin":1000,"areaBetMax":20000,"roundLimit":30},{"areaId":13,"areaBetMin":1000,"areaBetMax":20000,"roundLimit":30},{"areaId":14,"areaBetMin":1000,"areaBetMax":30000,"roundLimit":30},{"areaId":15,"areaBetMin":1000,"areaBetMax":30000,"roundLimit":30},{"areaId":16,"areaBetMin":1000,"areaBetMax":100000,"roundLimit":30},{"areaId":17,"areaBetMin":1000,"areaBetMax":100000,"roundLimit":30},{"areaId":18,"areaBetMin":1000,"areaBetMax":50000,"roundLimit":30},{"areaId":19,"areaBetMin":1000,"areaBetMax":50000,"roundLimit":30},{"areaId":20,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":21,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":22,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":23,"areaBetMin":1000,"areaBetMax":4000000,"roundLimit":9999},{"areaId":24,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":25,"areaBetMin":1000,"areaBetMax":400000,"roundLimit":30},{"areaId":26,"areaBetMin":1000,"areaBetMax":250000,"roundLimit":30},{"areaId":27,"areaBetMin":1000,"areaBetMax":130000,"roundLimit":30},{"areaId":28,"areaBetMin":1000,"areaBetMax":100000,"roundLimit":30},{"areaId":29,"areaBetMin":1000,"areaBetMax":250000,"roundLimit":30},{"areaId":30,"areaBetMin":1000,"areaBetMax":200000,"roundLimit":9999},{"areaId":31,"areaBetMin":1000,"areaBetMax":2000000,"roundLimit":9999},{"areaId":32,"areaBetMin":1000,"areaBetMax":3000000,"roundLimit":30},{"areaId":33,"areaBetMin":1000,"areaBetMax":2000000,"roundLimit":9999},{"areaId":34,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":35,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":36,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":37,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":38,"areaBetMin":1000,"areaBetMax":200000,"roundLimit":30}]},{"roomId":8204,"name":"极速 B04","name4H5":"极速 B04","type":1,"subType":82,"state":4,"betDuration":21000,"round":45,"countdown":0,"roomMin":1000,"roomMax":5000000,"matchInfos":[711021,612021,401001,821002,601010,301001,911001,811101,821222,821202,601000,811001,821202,821022,811121,911101,811101,911101,101001,723002,811101,612001,602001,301022,911101,612001,821002,921002,601022,301001,501001,811101,811101,911101,701000,401000,701000,501001,921202,701001,621002,723002,821202,821202,621002],"result":{"roomId":8204,"winner":2,"pair":0,"tianwang":0,"lucky":1,"bigOrSmall":0,"invalid":false,"bingoAreas":[4,6,20,23,36],"curMatchInfo":621002,"cards":[{"pos":0,"card":12,"roomId":8204},{"pos":1,"card":28,"roomId":8204},{"pos":2,"card":43,"roomId":8204},{"pos":3,"card":12,"roomId":8204},{"pos":4,"card":26,"roomId":8204},{"pos":5,"card":20,"roomId":8204}],"winnerCount":76,"winnerProfitTotal":"2282500"},"pokers":[{"pos":0,"card":12,"roomId":8204},{"pos":1,"card":28,"roomId":8204},{"pos":2,"card":43,"roomId":8204},{"pos":3,"card":12,"roomId":8204},{"pos":4,"card":26,"roomId":8204},{"pos":5,"card":20,"roomId":8204}],"dealerInfo":{"dealerName":"Carlin","imgNation":2,"imgUrl":"https://d3mp1h85h24821.cloudfront.net/273D79458E605FEDFABA0761090F67D4.jpeg","imgMD5":"+3F3371558122D7DF41339D1D75BACFA4","dealerId":81472},"road":{"roomId":8204,"trendStatus":1,"trendId":1,"trendAmount":5,"weight":1,"time":"1755262069916"},"roundId":"52200C9FFC41C","scenarioType":1,"roomUserCount":186,"areaLimit":[{"areaId":1,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":2,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":3,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":4,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":5,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":6,"areaBetMin":1000,"areaBetMax":3500000,"roundLimit":30},{"areaId":7,"areaBetMin":1000,"areaBetMax":3500000,"roundLimit":30},{"areaId":8,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":9,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":10,"areaBetMin":1000,"areaBetMax":30000,"roundLimit":30},{"areaId":11,"areaBetMin":1000,"areaBetMax":20000,"roundLimit":30},{"areaId":12,"areaBetMin":1000,"areaBetMax":20000,"roundLimit":30},{"areaId":13,"areaBetMin":1000,"areaBetMax":20000,"roundLimit":30},{"areaId":14,"areaBetMin":1000,"areaBetMax":30000,"roundLimit":30},{"areaId":15,"areaBetMin":1000,"areaBetMax":30000,"roundLimit":30},{"areaId":16,"areaBetMin":1000,"areaBetMax":100000,"roundLimit":30},{"areaId":17,"areaBetMin":1000,"areaBetMax":100000,"roundLimit":30},{"areaId":18,"areaBetMin":1000,"areaBetMax":50000,"roundLimit":30},{"areaId":19,"areaBetMin":1000,"areaBetMax":50000,"roundLimit":30},{"areaId":20,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":21,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":22,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":23,"areaBetMin":1000,"areaBetMax":4000000,"roundLimit":9999},{"areaId":24,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":25,"areaBetMin":1000,"areaBetMax":400000,"roundLimit":30},{"areaId":26,"areaBetMin":1000,"areaBetMax":250000,"roundLimit":30},{"areaId":27,"areaBetMin":1000,"areaBetMax":130000,"roundLimit":30},{"areaId":28,"areaBetMin":1000,"areaBetMax":100000,"roundLimit":30},{"areaId":29,"areaBetMin":1000,"areaBetMax":250000,"roundLimit":30},{"areaId":30,"areaBetMin":1000,"areaBetMax":200000,"roundLimit":9999},{"areaId":31,"areaBetMin":1000,"areaBetMax":2000000,"roundLimit":9999},{"areaId":32,"areaBetMin":1000,"areaBetMax":3000000,"roundLimit":30},{"areaId":33,"areaBetMin":1000,"areaBetMax":2000000,"roundLimit":9999},{"areaId":34,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":35,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":36,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":37,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":38,"areaBetMin":1000,"areaBetMax":200000,"roundLimit":30}]},{"roomId":8205,"name":"极速 B05","name4H5":"极速 B05","type":1,"subType":82,"state":2,"betDuration":21000,"round":61,"countdown":14419,"roomMin":1000,"roomMax":5000000,"matchInfos":[821022,811101,821202,704002,911121,601000,811001,601002,811101,921002,811101,821002,301001,911101,511001,911101,601002,811001,723002,911101,921302,703002,601020,301002,711001,704002,921202,811101,911301,911101,911301,201000,911001,501000,601022,723002,723002,811111,621002,701001,811101,301000,811101,811101,601000,711001,501002,201011,711001,811101,901000,921202,921202,401001,701001,921002,401002,821202,621002,921202],"dealerInfo":{"dealerName":"Belin","imgNation":3,"imgUrl":"https://d3mp1h85h24821.cloudfront.net/70ED4459BA21BBD2F2F2627AFDFCCA45.jpeg","imgMD5":"+01C533B3963B700796E6515B3B56DCBE","dealerId":80644},"road":{"roomId":8205,"trendStatus":1,"trendId":1,"trendAmount":5,"weight":1,"time":"1755262052371"},"roundId":"52200D9FFC43A","scenarioType":1,"roomUserCount":214,"areaLimit":[{"areaId":1,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":2,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":3,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":4,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":5,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":6,"areaBetMin":1000,"areaBetMax":3500000,"roundLimit":30},{"areaId":7,"areaBetMin":1000,"areaBetMax":3500000,"roundLimit":30},{"areaId":8,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":9,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":10,"areaBetMin":1000,"areaBetMax":30000,"roundLimit":30},{"areaId":11,"areaBetMin":1000,"areaBetMax":20000,"roundLimit":30},{"areaId":12,"areaBetMin":1000,"areaBetMax":20000,"roundLimit":30},{"areaId":13,"areaBetMin":1000,"areaBetMax":20000,"roundLimit":30},{"areaId":14,"areaBetMin":1000,"areaBetMax":30000,"roundLimit":30},{"areaId":15,"areaBetMin":1000,"areaBetMax":30000,"roundLimit":30},{"areaId":16,"areaBetMin":1000,"areaBetMax":100000,"roundLimit":30},{"areaId":17,"areaBetMin":1000,"areaBetMax":100000,"roundLimit":30},{"areaId":18,"areaBetMin":1000,"areaBetMax":50000,"roundLimit":30},{"areaId":19,"areaBetMin":1000,"areaBetMax":50000,"roundLimit":30},{"areaId":20,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":21,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":22,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":23,"areaBetMin":1000,"areaBetMax":4000000,"roundLimit":9999},{"areaId":24,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":25,"areaBetMin":1000,"areaBetMax":400000,"roundLimit":30},{"areaId":26,"areaBetMin":1000,"areaBetMax":250000,"roundLimit":30},{"areaId":27,"areaBetMin":1000,"areaBetMax":130000,"roundLimit":30},{"areaId":28,"areaBetMin":1000,"areaBetMax":100000,"roundLimit":30},{"areaId":29,"areaBetMin":1000,"areaBetMax":250000,"roundLimit":30},{"areaId":30,"areaBetMin":1000,"areaBetMax":200000,"roundLimit":9999},{"areaId":31,"areaBetMin":1000,"areaBetMax":2000000,"roundLimit":9999},{"areaId":32,"areaBetMin":1000,"areaBetMax":3000000,"roundLimit":30},{"areaId":33,"areaBetMin":1000,"areaBetMax":2000000,"roundLimit":9999},{"areaId":34,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":35,"areaBetMin":1000,"areaBetMax":1000000,"roundLimit":9999},{"areaId":36,"areaBetMin":1000,"areaBetMax":5000000,"roundLimit":9999},{"areaId":37,"areaBetMin":1000,"areaBetMax":500000,"roundLimit":50},{"areaId":38,"areaBetMin":1000,"areaBetMax":200000,"roundLimit":30}]}],"roomType":1}],"lastRoom":0,"selectedGoodRoadIndexes":[1,2,3,4,5],"nodeIdx":0}
                // 解析并更新房间列表
                JsonNode groupArray = data.get("group");
                if (groupArray != null && groupArray.isArray()) {
                    for (JsonNode group : groupArray) {
                        JsonNode infosArray = group.get("infos");
                        if (infosArray != null && infosArray.isArray()) {
                            for (JsonNode roomInfo : infosArray) {
                                roomId = roomInfo.get("roomId").asInt();
                                room = rooms.get(roomId);
                                if (room == null) {
                                    room = new WLRoom();
                                    room.setRoomId(roomId);
                                    rooms.put(roomId, room);
                                }
                                
                                // 更新房间信息
                                room.setRoomId(roomId);
                                room.setName(roomInfo.get("name").asText());
                                room.setName4H5(roomInfo.get("name4H5").asText());
                                room.setType(roomInfo.get("type").asInt());
                                room.setSubType(roomInfo.get("subType").asInt());
                                room.setState(roomInfo.get("state").asInt());
                                room.setBetDuration(roomInfo.get("betDuration").asInt());
                                room.setRound(roomInfo.get("round").asInt());
                                room.setCountdown(roomInfo.get("countdown").asInt());
                                room.setRoomMin(roomInfo.get("roomMin").asInt());
                                room.setRoomMax(roomInfo.get("roomMax").asInt());
                                room.setRoundId(roomInfo.get("roundId").asText());
                                room.setScenarioType(roomInfo.get("scenarioType").asText());
                                room.setRoomUserCount(roomInfo.get("roomUserCount").asInt());
                                
                                // 更新dealer信息
                                JsonNode dealerInfoNode = roomInfo.get("dealerInfo");
                                if (dealerInfoNode != null) {
                                    WLRoom.DealerInfo dealerInfo = convertToDealerInfo(dealerInfoNode);
                                    room.setDealerInfo(dealerInfo);
                                }
                                JsonNode cardsArray = data.get("pokers");
                                if(cardsArray != null) {
                                    List<WLRoom.Poker> pokers = convertToPokers(cardsArray);
                                    room.setPokers(pokers);
                                }
                            }
                        }
                    }
                    List<WlTable> tables = rooms.values().stream().filter(a->a.getRoomId()!=null).map(a -> new WlTable().setTableId(a.getRoomId()).setTableName(a.getName()).setType( a.getType())).collect(Collectors.toList());
                    wlTableService.insertList(tables);
                    List<WlDealer> dealers = rooms.values().stream().filter(a -> a.getDealerInfo() != null).map(a -> new WlDealer().setDealerId(a.getDealerInfo().getDealerId()).setDealerName(a.getDealerInfo().getDealerName()).setImgNation(a.getDealerInfo().getImgNation()).setImgUrl(a.getDealerInfo().getImgUrl()).setImgMD5(a.getDealerInfo().getImgMD5())).collect(Collectors.toList());
                    wlTableService.saveDealers(dealers);
                }

                break;
            case BACCARAT_STOP_BET://{"roomId":8108}
                room.setState(WLRoom.STATE_DEAL_OR_OPEN); // 停止下注后 进入开牌状态
                break;
            case ROOM_GAME_RESULT://{"roomId":8232,"scores":[{"userId":"183824889","score":"0","scoreChange":0}],"winner":1,"pair":1,"tianwang":0,"lucky":2,"bigOrSmall":0,"invalid":false,"bingoAreas":[3,5,6,8,9,21,22,23,24,29,30,36],"curMatchInfo":612011,"cards":[{"pos":0,"card":37,"roomId":8232},{"pos":1,"card":7,"roomId":8232},{"pos":2,"card":9,"roomId":8232},{"pos":3,"card":7,"roomId":8232},{"pos":4,"card":39,"roomId":8232}],"winnerCount":7,"winnerProfitTotal":"164250"}
                room.setState(WLRoom.STATE_SETTLING); //进入结算状态
                JsonNode cardsArray = data.get("cards");
                List<WLRoom.Poker> pokers = convertToPokers(cardsArray);
                room.setPokers(pokers);
                if(room.getType()==null){
                    // 房间类型不正确
                }else {
                    if(WLRoom.TYPE_baccarat==room.getType()||WLRoom.TYPE_dragonTiger==room.getType()){
                        WlBaccaratResult result = new WlBaccaratResult();
                        result.setTableId(room.getRoomId());
                        result.setRoundId(room.getRoundId());
                        result.setDealerId(room.getDealerInfo().getDealerId());
                        result.setRound(room.getRound());
                        WLRoom.Poker other = new WLRoom.Poker();
                        result.setPlayerCard1(pokers.stream().filter(a -> a.getPos() == 0).findFirst().orElse(other).getCard());
                        result.setPlayerCard2(pokers.stream().filter(a -> a.getPos() == 2).findFirst().orElse(other).getCard());
                        result.setPlayerCard3(pokers.stream().filter(a -> a.getPos() == 4).findFirst().orElse(other).getCard());
                        result.setBankerCard1(pokers.stream().filter(a -> a.getPos() == 1).findFirst().orElse(other).getCard());
                        result.setBankerCard2(pokers.stream().filter(a -> a.getPos() == 3).findFirst().orElse(other).getCard());
                        result.setBankerCard3(pokers.stream().filter(a -> a.getPos() == 5).findFirst().orElse(other).getCard());
                        wlGameResultService.saveBaccaratResult(result);
                    }else if(WLRoom.TYPE_sicbo==room.getType()){
                        WlSicboResult result = new WlSicboResult();
                        result.setTableId(room.getRoomId());
                        result.setRoundId(room.getRoundId());
                        result.setDealerId(room.getDealerInfo().getDealerId());
                        result.setRound(room.getRound());
                        result.setDice1(pokers.get(0).getCard());
                        result.setDice2(pokers.get(1).getCard());
                        result.setDice3(pokers.get(2).getCard());
                        wlGameResultService.saveSicboResult(result);
                        // 骰子结果
                    }else if(WLRoom.TYPE_roulette==room.getType()){
                        WlRouletteResult result = new WlRouletteResult();
                        result.setTableId(room.getRoomId());
                        result.setDealerId(room.getDealerInfo().getDealerId());
                        result.setRoundId(room.getRoundId());
                        result.setRound(room.getRound());
                        result.setResult(pokers.get(0).getCard());
                        wlGameResultService.saveRouletteResult( result);
                        //轮盘
                    }else if(WLRoom.TYPE_color_disk==room.getType()){
                        WlColorDiskResult result = new WlColorDiskResult();
                        result.setTableId(room.getRoomId());
                        result.setDealerId(room.getDealerInfo().getDealerId());
                        result.setRoundId(room.getRoundId());
                        result.setRound(room.getRound());
                        result.setResult(pokers.stream().mapToInt(WLRoom.Poker::getCard).sum());
                        wlGameResultService.saveColorDiskResult(result);
                        // 色碟
                    }else if(WLRoom.TYPE_goldenFlowerBomb==room.getType()){
                        // TODO 完成 炸金花 结果 数据保存
                        WlGoldenFlowerResult result = new WlGoldenFlowerResult();
                        result.setTableId(room.getRoomId());
                        result.setRoundId(room.getRoundId());
                        result.setDealerId(room.getDealerInfo().getDealerId());
                        result.setRound(room.getRound());
                        WLRoom.Poker other = new WLRoom.Poker();
                        // 龙方牌
                        result.setDragonCard1(pokers.stream().filter(a -> a.getPos() == 1).findFirst().orElse(other).getCard());
                        result.setDragonCard2(pokers.stream().filter(a -> a.getPos() == 2).findFirst().orElse(other).getCard());
                        result.setDragonCard3(pokers.stream().filter(a -> a.getPos() == 3).findFirst().orElse(other).getCard());
                        // 凤方牌
                        result.setPhoenixCard1(pokers.stream().filter(a -> a.getPos() == 4).findFirst().orElse(other).getCard());
                        result.setPhoenixCard2(pokers.stream().filter(a -> a.getPos() == 5).findFirst().orElse(other).getCard());
                        result.setPhoenixCard3(pokers.stream().filter(a -> a.getPos() == 6).findFirst().orElse(other).getCard());
                        wlGameResultService.saveGoldenFlowerResult(result);
                    }else if(WLRoom.TYPE_bullBull==room.getType()) {
                        //
                    }
                }
                break;
            case SELF_SCORE://{"gold":"0","ccy":"CNY"}
                break;
            case SCORES://{"scores":[{"userId":"183824889","score":"0"}]}
                break;
            case DEAL_CARD://{"pos":5,"card":42,"roomId":8217}
                // 添加牌到房间
                WLRoom.Poker poker = new WLRoom.Poker();
                poker.setPos(data.get("pos").asInt());
                poker.setCard(data.get("card").asInt());
                poker.setRoomId(data.get("roomId").asInt());
                room.getPokers().add(poker);
                break;
            case ROOM_BEGIN_CHIP://{"roomId":9101,"countdown":21000,"clearRecord":0,"betDuration":21000,"round":73,"roundId":"5B238D9FFC449","userCount":100}
                //{"roomId":8108,"countdown":25997,"clearRecord":0,"betDuration":26000,"round":3,"roundId":"511FAC9FFC44A","userCount":9}
                // 新一轮开始，更新房间状态和相关信息
                room.setState(WLRoom.STATE_BET_AVAILABLE);
                room.setCountdown(data.get("countdown").asInt());
                room.setBetDuration(data.get("betDuration").asInt());
                room.setRound(data.get("round").asInt());
                room.setRoundId(data.get("roundId").asText());
                room.setRoomUserCount(data.get("userCount").asInt());
                room.setRoundStartTime(new Date());
                // 清空上一轮的牌和限制信息
                room.getPokers().clear();
                break;
            case BACCARAT_UPDATE_DEALER_INFO://{"roomId":8207,"dealerInfo":{"dealerName":"Susana","imgNation":3,"imgUrl":"https://d3mp1h85h24821.cloudfront.net/1BA261D1BDBE1C2C5D2543CF37C5F258.jpeg","imgMD5":"+EBE835FE8B7611518B3B954AC318E497","dealerId":80718}}
                JsonNode dealerInfoNode = data.get("dealerInfo");
                if (dealerInfoNode != null) {
                    WLRoom.DealerInfo dealerInfo = convertToDealerInfo(dealerInfoNode);
                    room.setDealerInfo(dealerInfo);
                    WlDealer dealer = new WlDealer().setDealerId(dealerInfo.getDealerId()).setDealerName(dealerInfo.getDealerName()).setImgUrl(dealerInfo.getImgUrl()).setImgNation(dealerInfo.getImgNation()).setImgMD5(dealerInfo.getImgMD5());
                    wlTableService.saveDealers(Arrays.asList(dealer));
                }
                break;
            case WASHING_CARD://{"roomId":"8233"}
                room.setState(WLRoom.STATE_SHUFFLING);
                //TODO
            default:
                break;
        }

    }

    private static WLRoom.DealerInfo convertToDealerInfo(JsonNode dealerInfoNode) {
        WLRoom.DealerInfo dealerInfo = new WLRoom.DealerInfo();
        dealerInfo.setDealerName(dealerInfoNode.get("dealerName").asText());
        dealerInfo.setImgNation(dealerInfoNode.get("imgNation").asInt());
        dealerInfo.setImgUrl(dealerInfoNode.get("imgUrl").asText());
        dealerInfo.setImgMD5(dealerInfoNode.get("imgMD5").asText());
        dealerInfo.setDealerId(dealerInfoNode.get("dealerId").asInt());
        return dealerInfo;
    }

    private List<WLRoom.Poker> convertToPokers(JsonNode cardsArray) {
        List<WLRoom.Poker> pokers = new ArrayList<>();
        if (cardsArray != null && cardsArray.isArray()) {
            for(JsonNode cardNode: cardsArray){
                try {
                    WLRoom.Poker poker = mapper.treeToValue(cardNode, WLRoom.Poker.class);
                    pokers.add(poker);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return pokers;
    }



}