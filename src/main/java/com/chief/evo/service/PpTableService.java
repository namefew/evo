package com.chief.evo.service;

import com.chief.evo.entity.PpTable;
import com.chief.evo.mapper.PpTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PpTableService {
    @Autowired
    PpTableMapper ppTableMapper;

    Map<String, PpTable> allTables;

    public List<PpTable> queryAll() {
        return ppTableMapper.queryAll();
    }

    public void insertList(List<PpTable> ppTables) {
        ppTableMapper.insertList(ppTables);
        allTables = queryAll().stream().collect(Collectors.toMap(PpTable::getTableId, a -> a, (a, b) -> b));
    }

    public Map<String, PpTable> getAllTables() {
        if (allTables == null) {
            allTables = queryAll().stream().collect(Collectors.toMap(PpTable::getTableId, a -> a, (a, b) -> b));
        }
        return allTables;
    }
}