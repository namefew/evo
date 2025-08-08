package com.chief.evo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EzugiRouletteResult {
    private Long id;
    private String tableId;
    private String roundId;
    private Integer result;
    private String color;
    private LocalDateTime createTime;

    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EzugiRouletteResult other = (EzugiRouletteResult) obj;
        return tableId.equals(other.tableId) && result!=null &&
                result.equals(other.result);
    }
    
    public int hashCode(){
        return tableId.hashCode() * 11 + result.hashCode();
    }
}