package com.chief.evo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class WLRoom {
    public static final Integer STATE_INIT = 0;//初始化
    public static final Integer STATE_WAIT_OTHERS = 1;//等待其他玩家
    public static final Integer STATE_BET_AVAILABLE = 2;//下注可用
    public static final Integer STATE_DEAL_OR_OPEN = 3;//开牌
    public static final Integer STATE_SETTLING = 4;//结算中
    public static final Integer STATE_SHUFFLING = 5;//洗牌
    public static final Integer STATE_MAINTENANCE = 6;//维护中
    public static final Integer STATE_INVALID = 7;//无效

    public static final int TYPE_baccarat = 1;
    public static final int TYPE_sicbo = 3;
    public static final int TYPE_roulette = 4;
    public static final int TYPE_dragonTiger = 5;
    public static final int TYPE_goldenFlowerBomb = 6;
    public static final int TYPE_bullBull = 7;
    public static final int TYPE_color_disk = 8;



    private Integer state;
    private String roundId;
    private Integer round;
    private Integer betDuration;
    private Integer countdown ;
    private Date roundStartTime;
    private Integer roomUserCount;
    private Integer roomId;
    private String name;
    private String name4H5;
    private Integer type;
    private Integer subType;
    private Integer roomMin;
    private Integer roomMax;
    private DealerInfo dealerInfo = new DealerInfo().setDealerId(-1).setDealerName("").setImgUrl("").setImgNation(-1).setImgMD5("");

    private String scenarioType;
    private List<Integer> matchInfos = new ArrayList<>();
    private List<Poker> pokers = new ArrayList<>();

    @Data
    @Accessors(chain = true)
    public static class DealerInfo {
        private String dealerName;
        private Integer imgNation;
        private String imgUrl;
        private String imgMD5;
        private Integer dealerId;

    }



    @Data
    public static class Poker {
        private Integer pos;
        private Integer card;
        private Integer roomId;


    }


}