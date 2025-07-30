package com.chief.evo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DbTable {
    private Integer tableId;
    private String tableName;
    private Integer gameCasinoId;
    private String gameCasinoName;
    private Integer gameTypeId;
    private String gameTypeName;
    private Date createTime;
}