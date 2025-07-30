package com.chief.evo.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ColorDiskStats {
    private String tableId;
    private LocalDate date;
    private Integer totalCount;
    private Integer bigCount; // 大(3,4)
    private Integer smallCount; // 小(0,1)
    private Integer oddCount; // 单数(1,3)
    private Integer evenCount; // 双数(0,2,4)
    
    private Integer num0;
    private Integer num1;
    private Integer num2;
    private Integer num3;
    private Integer num4;

}
