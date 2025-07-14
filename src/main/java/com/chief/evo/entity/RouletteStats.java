package com.chief.evo.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RouletteStats {
    private String tableId;
    private LocalDate date;
    private Integer totalCount;
    private Integer bigCount; // 大(19-36)
    private Integer smallCount; // 小(1-18)
    private Integer oddCount; // 单数
    private Integer evenCount; // 双数
    private Integer redCount; // 红色(1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36)
    private Integer blackCount; // 黑色(2,4,6,8,10,11,13,15,17,20,22,24,26,28,29,31,33,35)
    private Integer section1; // 1-12区间
    private Integer section2; // 13-24区间
    private Integer section3; // 25-36区间
    
    private Integer num0;
    private Integer num1;
    private Integer num2;
    private Integer num3;
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
    private Integer num18;
    private Integer num19;
    private Integer num20;
    private Integer num21;
    private Integer num22;
    private Integer num23;
    private Integer num24;
    private Integer num25;
    private Integer num26;
    private Integer num27;
    private Integer num28;
    private Integer num29;
    private Integer num30;
    private Integer num31;
    private Integer num32;
    private Integer num33;
    private Integer num34;
    private Integer num35;
    private Integer num36;

}
