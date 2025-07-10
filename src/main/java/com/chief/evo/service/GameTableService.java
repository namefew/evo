package com.chief.evo.service;

import com.chief.evo.entity.GameTable;
import com.chief.evo.mapper.GameTableMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameTableService {
    @Autowired
    GameTableMapper gameTableMapper;

    Map<String,GameTable> allTables;
    public List<GameTable> queryAll(){
        return gameTableMapper.queryAll();
    }

    public void insertList(List<GameTable> gameTables){
        gameTableMapper.insertList(gameTables);
        allTables = queryAll().stream().collect(Collectors.toMap(GameTable::getId, a->a,(a,b)->b));
    }

    public Map<String,GameTable> getAllTables(){
        if(allTables==null){
            allTables = queryAll().stream().collect(Collectors.toMap(GameTable::getId, a->a,(a,b)->b));
        }
        return allTables;
    }
}
