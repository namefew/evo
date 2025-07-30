package com.chief.evo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DbRouletteResult {
    private Long id;
    private Integer tableId;
    private Integer result;
    private LocalDateTime createTime;
}