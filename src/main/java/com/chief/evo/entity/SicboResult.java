package com.chief.evo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SicboResult {
    private Long id;
    private String tableId;
    private String roundId; // 轮次ID
    private Integer dice1;  // 骰子1点数
    private Integer dice2;  // 骰子2点数
    private Integer dice3;  // 骰子3点数
    private LocalDateTime createTime;
}