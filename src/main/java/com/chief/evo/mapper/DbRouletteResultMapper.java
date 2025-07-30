package com.chief.evo.mapper;

import com.chief.evo.entity.DbRouletteResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DbRouletteResultMapper {
    @Insert({
        "<script>",
        "INSERT INTO db_roulette_result(table_id, result) VALUES ",
        "<foreach collection='resultList' item='item' separator=','>",
        "(#{item.tableId}, #{item.result})",
        "</foreach>",
        "</script>"
    })
    void insertList(List<DbRouletteResult> resultList);
    // 添加findLatestByTableId方法，参考RouletteResultMapper中的实现
    @Select("SELECT * FROM db_roulette_result WHERE table_id = #{tableId} ORDER BY id DESC LIMIT #{limit}")
    List<DbRouletteResult> findLatestByTableId(Integer tableId, int limit);
}