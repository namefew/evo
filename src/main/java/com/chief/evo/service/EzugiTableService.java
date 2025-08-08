package com.chief.evo.service;

import com.chief.evo.entity.EzugiTable;
import com.chief.evo.mapper.EzugiTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EzugiTableService {
    @Autowired
    EzugiTableMapper ezugiTableMapper;

    Map<String, EzugiTable> allTables;

    public List<EzugiTable> queryAll() {
        return ezugiTableMapper.queryAll();
    }

    public void insertList(List<EzugiTable> ezugiTables) {
        ezugiTableMapper.insertList(ezugiTables);
        allTables = queryAll().stream().collect(Collectors.toMap(EzugiTable::getTableId, a -> a, (a, b) -> b));
    }

    public Map<String, EzugiTable> getAllTables() {
        if (allTables == null) {
            allTables = queryAll().stream().collect(Collectors.toMap(EzugiTable::getTableId, a -> a, (a, b) -> b));
        }
        return allTables;
    }
}