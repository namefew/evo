package com.chief.evo.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WlDealer {
    private Integer dealerId;
    private String dealerName;
    private Integer imgNation;
    private String imgUrl;
    private String imgMD5;

}
