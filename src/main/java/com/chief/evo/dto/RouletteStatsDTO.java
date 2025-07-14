package com.chief.evo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class RouletteStatsDTO {

    private String tableId;
    private String tableTitle;
    private LocalDate date;
    private Integer totalCount;
    private Integer bigCount; // 大(19-36)
    private Integer smallCount; // 小(1-18)
    private Integer oddCount; // 单数
    private Integer evenCount; // 双数
    private Integer redCount; // 红色(1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36)
    private Integer blackCount; // 黑色(2,4,6,8,10,11,13,15,17,20,22,24,26,28,29,31,33,35)
    private Integer section1_12; // 1-12区间
    private Integer section13_24; // 13-24区间
    private Integer section25_36; // 25-36区间

    private Map<Integer,Integer> numbs; //每个数字出现的次数

    public Double getBigWinRate() {
        return bigCount*2.0/totalCount-1.0;
    }
    public Double getSmallWinRate() {
        return smallCount*2.0/totalCount-1.0;
    }
    public Double getOddWinRate() {
        return oddCount*2.0/totalCount-1.0;
    }
    public Double getEvenWinRate() {
        return evenCount*2.0/totalCount-1.0;
    }
    public Double getRedWinRate() {
        return redCount*2.0/totalCount-1.0;
    }
    public Double getBlackWinRate() {
        return blackCount*2.0/totalCount-1.0;
    }
    public Double getSection1_12WinRate() {
        return section1_12*3.0/totalCount-1.0;
    }
    public Double getSection13_24WinRate() {
        return section13_24*3.0/totalCount-1.0;
    }
    public Double getSection25_36WinRate() {
        return section25_36*3.0/totalCount-1.0;
    }
}
