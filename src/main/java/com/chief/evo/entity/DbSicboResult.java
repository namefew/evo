package com.chief.evo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DbSicboResult {
    private Long id;
    private Integer tableId;
    private Integer dice1;
    private Integer dice2;
    private Integer dice3;
    private Integer total;
    private LocalDateTime createTime;
}