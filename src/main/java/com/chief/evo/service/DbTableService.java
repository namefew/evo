package com.chief.evo.service;

import com.chief.evo.entity.DbTable;
import com.chief.evo.mapper.DbTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DbTableService {
    @Autowired
    DbTableMapper dbTableMapper;

    Map<Integer, DbTable> allTables;

    public List<DbTable> queryAll() {
        return dbTableMapper.queryAll();
    }

    public void insertList(List<DbTable> dbTables) {
        dbTableMapper.insertList(dbTables);
        allTables = queryAll().stream().collect(Collectors.toMap(DbTable::getTableId, a -> a, (a, b) -> b));
    }

    public Map<Integer, DbTable> getAllTables() {
        if (allTables == null) {
            allTables = queryAll().stream().collect(Collectors.toMap(DbTable::getTableId, a -> a, (a, b) -> b));
        }
        return allTables;
    }
}