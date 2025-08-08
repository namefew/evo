package com.chief.evo.mapper;

import com.chief.evo.entity.EzugiTable;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EzugiTableMapper {
    @Insert({
        "<script>",
        "INSERT INTO ezugi_tables (table_id, table_name, table_name_en, table_type) ",
        "VALUES ",
        "<foreach collection='ezugiTables' item='item' separator=','>",
        "(#{item.tableId}, #{item.tableName}, #{item.tableNameEn}, #{item.tableType})",
        "</foreach>",
        "ON DUPLICATE KEY UPDATE table_id=table_id",
        "</script>"
    })
    void insertList(List<EzugiTable> ezugiTables);

    @Select("SELECT * FROM ezugi_tables")
    List<EzugiTable> queryAll();
}