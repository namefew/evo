package com.chief.evo.mapper;

import com.chief.evo.dto.WLRoom;
import com.chief.evo.entity.EzugiTable;
import com.chief.evo.entity.WlDealer;
import com.chief.evo.entity.WlTable;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WlTableMapper {
    
    @Select("SELECT * FROM wl_table WHERE id = #{id}")
    WlTable selectById(Integer id);
    
    @Select("SELECT * FROM wl_table")
    List<WlTable> queryAll();

    @Insert({
            "<script>",
            "INSERT INTO wl_table (table_id, table_name, type) ",
            "VALUES ",
            "<foreach collection='wlTables' item='item' separator=','>",
            "(#{item.tableId}, #{item.tableName}, #{item.type})",
            "</foreach>",
            "ON DUPLICATE KEY UPDATE table_id=table_id",
            "</script>"
    })
    void insertList(List<WlTable> wlTables);


    @Insert({
        "<script>",
            "INSERT ignore INTO wl_dealer_info (dealer_id, dealer_name,img_url,img_nation,img_md5) ",
            "VALUES ",
            "<foreach collection='dealers' item='item' separator=','>",
                "(#{item.dealerId}, #{item.dealerName}, #{item.imgUrl},#{item.imgNation},#{item.imgMD5})",
            "</foreach>",
        "</script>"
    })
    void saveDealers(List<WlDealer> dealers);
}