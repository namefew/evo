package com.chief.evo.service;

import com.chief.evo.entity.EzugiTable;
import com.chief.evo.entity.WlDealer;
import com.chief.evo.entity.WlTable;
import com.chief.evo.mapper.WlTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WlTableService {
    @Autowired
    WlTableMapper wlTableMapper;

    Map<Integer, WlTable> allTables;

    public List<WlTable> queryAll() {
        return wlTableMapper.queryAll();
    }

    public void insertList(List<WlTable> wlTables) {
        wlTableMapper.insertList(wlTables);
        allTables = queryAll().stream().collect(Collectors.toMap(WlTable::getTableId, a -> a, (a, b) -> b));
    }

    public Map<Integer, WlTable> getAllTables() {
        if (allTables == null) {
            allTables = queryAll().stream().collect(Collectors.toMap(WlTable::getTableId, a -> a, (a, b) -> b));
        }
        return allTables;
    }

    public void saveDealers(List<WlDealer> dealers){
        wlTableMapper.saveDealers(dealers);
    }
}