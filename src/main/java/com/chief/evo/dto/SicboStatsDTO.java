package com.chief.evo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class SicboStatsDTO {
    private String tableId;
    private String tableTitle;
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

    private Map<Integer,Integer> sumNumbs;

    private Integer tripleCount;


}
