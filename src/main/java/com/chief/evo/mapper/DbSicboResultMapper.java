package com.chief.evo.mapper;

import com.chief.evo.entity.DbSicboResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DbSicboResultMapper {
    @Insert({
        "<script>",
        "INSERT INTO db_sicbo_result(table_id, dice1, dice2, dice3, total) VALUES ",
        "<foreach collection='resultList' item='item' separator=','>",
        "(#{item.tableId}, #{item.dice1}, #{item.dice2}, #{item.dice3}, #{item.total})",
        "</foreach>",
        "</script>"
    })
    void insertList(List<DbSicboResult> resultList);

    // 添加findLatestByTableId方法，参考SicboResultMapper中的实现
    @Select("SELECT * FROM db_sicbo_result WHERE table_id = #{tableId} ORDER BY id DESC LIMIT #{limit}")
    List<DbSicboResult> findLatestByTableId(Integer tableId, int limit);
}