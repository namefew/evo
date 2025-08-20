package com.chief.evo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WlRouletteResult {
    private Long id;
    private Integer tableId;
    private String roundId;
    private Integer round;
    private Integer dealerId;
    private Integer result;
    private LocalDateTime createTime;

    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        WlRouletteResult other = (WlRouletteResult) obj;
        return tableId.equals(other.tableId) &&result!=null&&
                result.equals(other.result);
    }
    public int hashCode(){
        return tableId.hashCode() * 11 + result.hashCode();
    }

}