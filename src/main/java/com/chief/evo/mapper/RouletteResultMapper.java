package com.chief.evo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import com.chief.evo.entity.RouletteResult;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RouletteResultMapper {
    @Insert({
        "<script>",
        "INSERT INTO roulette_result(table_id, round_id, result, color) VALUES ",
        "<foreach collection='resultList' item='item' separator=','>",
        "(#{item.tableId}, #{item.roundId}, #{item.result}, #{item.color})",
        "</foreach>",
        "</script>"
    })
    void insertList(List<RouletteResult> resultList);

    @Select("SELECT * FROM roulette_result WHERE table_id = #{tableId} ORDER BY id DESC LIMIT #{limit}")
    List<RouletteResult> findLatestByTableId(String tableId, int limit);


}