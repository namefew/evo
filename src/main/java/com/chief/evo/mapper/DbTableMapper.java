package com.chief.evo.mapper;

import com.chief.evo.entity.DbTable;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DbTableMapper {
    @Insert({
        "<script>",
        "INSERT INTO db_tables (table_id, table_name, game_casino_id, game_casino_name, game_type_id, game_type_name) ",
        "VALUES ",
        "<foreach collection='dbTables' item='item' separator=','>",
        "(#{item.tableId}, #{item.tableName}, #{item.gameCasinoId}, #{item.gameCasinoName}, #{item.gameTypeId}, #{item.gameTypeName})",
        "</foreach>",
        "ON DUPLICATE KEY UPDATE table_id=table_id",
        "</script>"
    })
    void insertList(List<DbTable> dbTables);

    @Select("SELECT * FROM db_tables")
    List<DbTable> queryAll();
}