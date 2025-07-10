package com.chief.evo.service;

import com.chief.evo.entity.GameTable;
import com.chief.evo.entity.RouletteResult;
import com.chief.evo.entity.SicboResult;
import com.chief.evo.mapper.RouletteResultMapper;
import com.chief.evo.mapper.SicboResultMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GameResultService {

    @Autowired
    private RouletteResultMapper rouletteResultMapper;

    @Autowired
    private SicboResultMapper sicboResultMapper;

    public List<SicboResult> addSicboResult(List<SicboResult> sicboResults, GameTable gameTable){
       return sicboResults;
    }

    public List<RouletteResult> addRouletteResult(List<RouletteResult> latestResults,GameTable gameTable) {
        return latestResults;
    }
}
