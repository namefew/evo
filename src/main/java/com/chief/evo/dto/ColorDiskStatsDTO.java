package com.chief.evo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class ColorDiskStatsDTO {

    private String tableId;
    private String tableTitle;
    private LocalDate date;
    private Integer totalCount;
    private Integer bigCount; // 大(3,4)
    private Integer smallCount; // 小(0,1)
    private Integer oddCount; // 单数(1,3)
    private Integer evenCount; // 双数(0,2,4)


    private Map<Integer,Integer> numbs; //每个数字出现的次数

    public Double getBigWinRate() {
        return (bigCount*1.96+numbs.get(2))/totalCount-1.0;
    }
    public Double getSmallWinRate() {
        return (smallCount*1.96+numbs.get(2))/totalCount-1.0;
    }
    public Double getOddWinRate() {
        return (oddCount*1.96)/totalCount-1.0;
    }
    public Double getEvenWinRate() {
        return (evenCount*1.96)/totalCount-1.0;
    }
}
