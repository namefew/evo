package com.chief.evo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EzugiColorDiskResult {
    private Long id;
    private String tableId;
    private String roundId;
    private Integer result;
    private LocalDateTime createTime;

    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EzugiColorDiskResult other = (EzugiColorDiskResult) obj;
        return tableId.equals(other.tableId) && result!=null &&
                result.equals(other.result);
    }
    
    public int hashCode(){
        return tableId.hashCode() * 11 + result.hashCode();
    }
}