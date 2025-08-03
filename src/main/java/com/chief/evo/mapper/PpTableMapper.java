package com.chief.evo.mapper;

import com.chief.evo.entity.PpTable;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PpTableMapper {
    @Insert({
        "<script>",
        "INSERT INTO pp_tables (table_id, table_name, table_name_en, table_type) ",
        "VALUES ",
        "<foreach collection='ppTables' item='item' separator=','>",
        "(#{item.tableId}, #{item.tableName}, #{item.tableNameEn}, #{item.tableType})",
        "</foreach>",
        "ON DUPLICATE KEY UPDATE table_id=table_id",
        "</script>"
    })
    void insertList(List<PpTable> ppTables);

    @Select("SELECT * FROM pp_tables")
    List<PpTable> queryAll();
}