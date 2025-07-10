package com.chief.evo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RouletteResult {
    private Long id;
    private String tableId;
    private String roundId; // 轮次ID
    private Integer result; // 轮盘结果数字
    private String color;   // 轮盘颜色
    private LocalDateTime createTime;
}