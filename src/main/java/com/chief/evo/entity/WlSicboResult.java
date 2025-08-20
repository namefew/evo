package com.chief.evo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WlSicboResult {
    private Long id;
    private Integer tableId;
    private String roundId;
    private Integer dealerId;
    private Integer round;
    private Integer dice1;
    private Integer dice2;
    private Integer dice3;
    private Integer total;

    private LocalDateTime createTime;

    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        WlSicboResult other = (WlSicboResult) obj;
        return tableId.equals(other.tableId) &&dice1!=null&&dice2!=null&&dice3!=null&&
                dice1.equals(other.dice1) &&
                dice2.equals(other.dice2) &&
                dice3.equals(other.dice3);
    }
    public int hashCode(){
        return tableId.hashCode()  + (dice1==null?0:dice1.hashCode()*7) + (dice2==null?0:dice2.hashCode()*49) + (dice3==null?0:dice3.hashCode()*343);
    }
}