package com.chief.evo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PpTable {
    private String tableId;
    private String tableName;
    private String tableNameEn;
    private String tableType;
    private Date createTime;
}