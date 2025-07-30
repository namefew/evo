package com.chief.evo.mapper;

import com.chief.evo.entity.DbColorDiskResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DbColorDiskResultMapper {
    @Insert({
        "<script>",
        "INSERT INTO db_color_disk_result(table_id, result) VALUES ",
        "<foreach collection='resultList' item='item' separator=','>",
        "(#{item.tableId}, #{item.result})",
        "</foreach>",
        "</script>"
    })
    void insertList(List<DbColorDiskResult> resultList);
    
    // 添加findLatestByTableId方法，参考DbRouletteResultMapper中的实现
    @Select("SELECT * FROM db_color_disk_result WHERE table_id = #{tableId} ORDER BY id DESC LIMIT #{limit}")
    List<DbColorDiskResult> findLatestByTableId(Integer tableId, int limit);
}