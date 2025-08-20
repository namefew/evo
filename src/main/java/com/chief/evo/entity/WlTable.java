package com.chief.evo.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WlTable {
    private Integer tableId;
    private String tableName;
    private Integer type;
    private Date createTime;
}