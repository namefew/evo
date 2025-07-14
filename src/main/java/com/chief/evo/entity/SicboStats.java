package com.chief.evo.entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class SicboStats {
    private String tableId;
    private LocalDate date;
    private Integer totalCount;

    private Integer bigCount; // 大(11-17)
    private Integer smallCount; // 小(4-10)
    private Integer oddCount; // 单数
    private Integer evenCount; // 双数

    private Integer dot1Count;
    private Integer dot2Count;
    private Integer dot3Count;
    private Integer dot4Count;
    private Integer dot5Count;
    private Integer dot6Count;


    private Integer num4;
    private Integer num5;
    private Integer num6;
    private Integer num7;
    private Integer num8;
    private Integer num9;
    private Integer num10;
    private Integer num11;
    private Integer num12;
    private Integer num13;
    private Integer num14;
    private Integer num15;
    private Integer num16;
    private Integer num17;

    private Integer tripleCount;


}
