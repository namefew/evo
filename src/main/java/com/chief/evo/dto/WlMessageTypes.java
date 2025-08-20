package com.chief.evo.dto;

import java.util.HashMap;
import java.util.Map;

public class WlMessageTypes {

    public static Map<Integer, String> getMessageTypesMap() {
        Map<Integer, String> messageTypes = new HashMap<>();
        
        messageTypes.put(1, "HEARTBEATS");
        messageTypes.put(106, "LOGIN_SUCCESS");
        messageTypes.put(107, "LOGIN_ERROR");
        messageTypes.put(600, "ERROR");
        messageTypes.put(700, "SERVER_MAINTAIN");
        messageTypes.put(702, "ACCOUNT_REMOTE_LOGIN");
        messageTypes.put(720, "UPDATE_SCORE");
        messageTypes.put(1001, "ROOM_INFOS");
        messageTypes.put(1002, "ROOM_BEGIN_CHIP");
        messageTypes.put(1004, "ROOM_GAME_RESULT");
        messageTypes.put(1006, "ENTER_INNER_ROOM");
        messageTypes.put(1008, "BACCARAT_BET");
        messageTypes.put(1009, "BACCARAT_STOP_BET");
        messageTypes.put(1101, "BACCARAT_ONLINE_LIST");
        messageTypes.put(1102, "SELF_SCORE");
        messageTypes.put(1201, "GAME_CONFIG");
        messageTypes.put(1202, "BET_STAGE_ERROR");
        messageTypes.put(1300, "DEAL_CARD");
        messageTypes.put(1301, "EDIT_CARDS");
        messageTypes.put(1302, "WASHING_CARD");
        messageTypes.put(1303, "GOOD_ROAD_CHANGE");
        messageTypes.put(1304, "INTO_MAINTAIN");
        messageTypes.put(1305, "ROAD_SET_SUCCEED");
        messageTypes.put(1314, "CHECK_BETTING_ROOM");
        messageTypes.put(1308, "SET_SHOW_OPTION_SUCCEED");
        messageTypes.put(1315, "BET_INFO_RES");
        messageTypes.put(1316, "BACCARAT_KICK_USER_2_LIST");
        messageTypes.put(1317, "BACCARAT_UPDATE_DEALER_INFO");
        messageTypes.put(1400, "BACCARAT_MI_START");
        messageTypes.put(1401, "BACCARAT_MI_INFO");
        messageTypes.put(1402, "BACCARAT_MI_END");
        messageTypes.put(1403, "BACCARAT_MI_RIGHT_INFO");
        messageTypes.put(1404, "BACCARAT_MI_OTHER_OPEN");
        messageTypes.put(1405, "BACCARAT_MI_CAN_ENTER_WITH_SIT");
        messageTypes.put(1406, "BACCARAT_MI_ENTER_WITH_SIT_RES");
        messageTypes.put(1407, "BACCARAT_ROOM_VIDEO_URL_INFO");
        messageTypes.put(1409, "BACCARAT_BET_RES");
        messageTypes.put(1410, "ROOM_VIDEO");
        messageTypes.put(1430, "BACCARAT_CLIENT_CONFIG");
        messageTypes.put(1441, "BACCARAT_SELF_CHIPS");
        messageTypes.put(1999, "SYNC_BET");
        messageTypes.put(2999, "ROOM_LIST");
        
        return messageTypes;
    }
    public static final int HEARTBEATS = 1;
    public static final int LOGIN_SUCCESS = 106;
    public static final int LOGIN_ERROR = 107;
    public static final int ERROR = 600;
    public static final int SERVER_MAINTAIN = 700;
    public static final int ACCOUNT_REMOTE_LOGIN = 702;
    public static final int UPDATE_SCORE = 720;
    public static final int ROOM_INFOS = 1001;
    public static final int ROOM_BEGIN_CHIP = 1002;
    public static final int ROOM_GAME_RESULT = 1004;
    public static final int ENTER_INNER_ROOM = 1006;
    public static final int BACCARAT_BET = 1008;
    public static final int BACCARAT_STOP_BET = 1009;
    public static final int SCORES  = 1100;
    public static final int BACCARAT_ONLINE_LIST = 1101;
    public static final int SELF_SCORE = 1102;
    public static final int GAME_CONFIG = 1201;
    public static final int DEAL_CARD = 1301;
    public static final int WASHING_CARD = 1302;
    public static final int GOOD_ROAD_CHANGE = 1303;
    public static final int INTO_MAINTAIN = 1304;
    public static final int ROAD_SET_SUCCEED = 1305;
    public static final int SET_SHOW_OPTION_SUCCEED = 1308;
    public static final int CHECK_BETTING_ROOM = 1314;
    public static final int BET_INFO_RES = 1315;
    public static final int BACCARAT_KICK_USER_2_LIST = 1316;
    public static final int BACCARAT_UPDATE_DEALER_INFO = 1317;
    public static final int BACCARAT_MI_START = 1400 ;
    public static final int BACCARAT_MI_INFO = 1401;
    public static final int BACCARAT_MI_END = 1402;
    public static final int BACCARAT_MI_RIGHT_INFO = 1403;
    public static final int BACCARAT_MI_OTHER_OPEN = 1404;
    public static final int BACCARAT_MI_CAN_ENTER_WITH_SIT = 1405;
    public static final int BACCARAT_MI_ENTER_WITH_SIT_RES = 1406;
    public static final int BACCARAT_ROOM_VIDEO_URL_INFO = 1407;
    public static final int BACCARAT_BET_RES = 1409;
    public static final int ROOM_VIDEO = 1410;
    public static final int BACCARAT_CLIENT_CONFIG = 1430;
    public static final int BACCARAT_SELF_CHIPS = 1441;
    public static final int SYNC_BET = 1999;
    public static final int ROOM_LIST = 2999;







}