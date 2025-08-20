package com.chief.evo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WlBaccaratResult {
    private Long id;
    private Integer tableId;
    private String roundId;
    private Integer dealerId;
    private Integer round;
    private Integer playerCard1;
    private Integer playerCard2;
    private Integer playerCard3;
    private Integer bankerCard1;
    private Integer bankerCard2;
    private Integer bankerCard3;
    private LocalDateTime createTime;


}
