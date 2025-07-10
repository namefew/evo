package com.chief.evo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameTable {
    private String id;
    private String title;
    private String gameType;
    private String frontendApp;
    private String gameProvider;
    private String gv;
    private Boolean published;
    private Date opensAt;
    private String lang;
    private Double minBet;
    private Double maxBet;
    private Date createTime;
}