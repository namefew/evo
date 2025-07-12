package com.chief.evo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import com.chief.evo.entity.SicboResult;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SicboResultMapper {
    // 修改为批量插入实现
    @Insert({
        "<script>",
        "INSERT INTO sicbo_result(table_id, round_id, dice1, dice2, dice3) VALUES ",
        "<foreach collection='resultList' item='item' separator=','>",
        "(#{item.tableId}, #{item.roundId}, #{item.dice1}, #{item.dice2}, #{item.dice3})",
        "</foreach>",
        "</script>"
    })
    void insertList(List<SicboResult> resultList);

    @Select("SELECT * FROM sicbo_result WHERE table_id = #{tableId} ORDER BY id DESC LIMIT #{limit}")
    List<SicboResult> findLatestByTableId(String tableId, int limit);

}