package com.chief.evo.mapper;

import com.chief.evo.entity.SicboStats;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import com.chief.evo.entity.SicboResult;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
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


    @Select({"<script>",
        "SELECT",
        "   table_id AS tableId,",
        "   DATE(create_time) AS date,",
        "   COUNT(*) AS totalCount,",
        "   SUM(CASE WHEN dice1=dice2 and dice2=dice3 THEN 1 ELSE 0 END) as tripleCount,",
        "   SUM(CASE WHEN NOT is_triple AND total BETWEEN 11 AND 17 THEN 1 ELSE 0 END) AS bigCount,",
        "   SUM(CASE WHEN NOT is_triple AND total BETWEEN 4 AND 10 THEN 1 ELSE 0 END) AS smallCount,",
        "   SUM(CASE WHEN NOT is_triple AND total % 2 = 1 THEN 1 ELSE 0 END) AS oddCount,",
        "   SUM(CASE WHEN NOT is_triple AND total % 2 = 0 THEN 1 ELSE 0 END) AS evenCount,",
        "   SUM(CASE WHEN dice1 = 1 THEN 1 ELSE 0 END + CASE WHEN dice2 = 1 THEN 1 ELSE 0 END + CASE WHEN dice3 = 1 THEN 1 ELSE 0 END) AS dot1Count,",
        "   SUM(CASE WHEN dice1 = 2 THEN 1 ELSE 0 END + CASE WHEN dice2 = 2 THEN 1 ELSE 0 END + CASE WHEN dice3 = 2 THEN 1 ELSE 0 END) AS dot2Count,",
        "   SUM(CASE WHEN dice1 = 3 THEN 1 ELSE 0 END + CASE WHEN dice2 = 3 THEN 1 ELSE 0 END + CASE WHEN dice3 = 3 THEN 1 ELSE 0 END) AS dot3Count,",
        "   SUM(CASE WHEN dice1 = 4 THEN 1 ELSE 0 END + CASE WHEN dice2 = 4 THEN 1 ELSE 0 END + CASE WHEN dice3 = 4 THEN 1 ELSE 0 END) AS dot4Count,",
        "   SUM(CASE WHEN dice1 = 5 THEN 1 ELSE 0 END + CASE WHEN dice2 = 5 THEN 1 ELSE 0 END + CASE WHEN dice3 = 5 THEN 1 ELSE 0 END) AS dot5Count,",
        "   SUM(CASE WHEN dice1 = 6 THEN 1 ELSE 0 END + CASE WHEN dice2 = 6 THEN 1 ELSE 0 END + CASE WHEN dice3 = 6 THEN 1 ELSE 0 END) AS dot6Count,",
        "   SUM(CASE WHEN total = 4 THEN 1 ELSE 0 END) AS num4,",
        "   SUM(CASE WHEN total = 5 THEN 1 ELSE 0 END) AS num5,",
        "   SUM(CASE WHEN total = 6 THEN 1 ELSE 0 END) AS num6,",
        "   SUM(CASE WHEN total = 7 THEN 1 ELSE 0 END) AS num7,",
        "   SUM(CASE WHEN total = 8 THEN 1 ELSE 0 END) AS num8,",
        "   SUM(CASE WHEN total = 9 THEN 1 ELSE 0 END) AS num9,",
        "   SUM(CASE WHEN total = 10 THEN 1 ELSE 0 END) AS num10,",
        "   SUM(CASE WHEN total = 11 THEN 1 ELSE 0 END) AS num11,",
        "   SUM(CASE WHEN total = 12 THEN 1 ELSE 0 END) AS num12,",
        "   SUM(CASE WHEN total = 13 THEN 1 ELSE 0 END) AS num13,",
        "   SUM(CASE WHEN total = 14 THEN 1 ELSE 0 END) AS num14,",
        "   SUM(CASE WHEN total = 15 THEN 1 ELSE 0 END) AS num15,",
        "   SUM(CASE WHEN total = 16 THEN 1 ELSE 0 END) AS num16,",
        "   SUM(CASE WHEN total = 17 THEN 1 ELSE 0 END) AS num17,",
        "   SUM(is_triple) as tripleCount",
        "FROM (",
        "   SELECT *, (dice1 + dice2 + dice3) AS total ,case when dice1=dice2 and dice1=dice3 then 1 else 0 end as is_triple FROM sicbo_result",
        "   WHERE create_time BETWEEN #{startDate} AND #{endDate}",
        ") t",
        "GROUP BY table_id, DATE(create_time)",
        "ORDER BY table_id,date",
        "</script>"
    })
    List<SicboStats> findByDate(LocalDate startDate, LocalDate endDate);

}