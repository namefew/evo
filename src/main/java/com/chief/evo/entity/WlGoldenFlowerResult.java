package com.chief.evo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WlGoldenFlowerResult {
    private Long id;
    private Integer tableId;
    private String roundId;
    private Integer dealerId;
    private Integer round;
    private Integer dragonCard1;
    private Integer dragonCard2;
    private Integer dragonCard3;
    private Integer phoenixCard1;
    private Integer phoenixCard2;
    private Integer phoenixCard3;
    private LocalDateTime createTime;
}