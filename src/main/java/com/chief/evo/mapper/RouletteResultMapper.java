package com.chief.evo.mapper;


import com.chief.evo.entity.RouletteResult;
import com.chief.evo.entity.RouletteStats;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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


    @Select({
        "<script>",
        "SELECT",
        "   t.table_id as tableId,",
        "   t.date,",
        "   SUM(t.cnt) AS totalCount,",
        "   SUM(CASE WHEN t.result BETWEEN 19 AND 36 THEN t.cnt ELSE 0 END) AS bigCount,",
        "   SUM(CASE WHEN t.result BETWEEN 1 AND 18 THEN t.cnt ELSE 0 END) AS smallCount,",
        "   SUM(CASE WHEN t.result % 2 = 1 THEN t.cnt ELSE 0 END) AS oddCount,",
        "   SUM(CASE WHEN t.result % 2 = 0 AND t.result!=0 THEN t.cnt ELSE 0 END) AS evenCount,",
        "   SUM(CASE WHEN t.result IN (1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36) THEN t.cnt ELSE 0 END) AS redCount,",
        "   SUM(CASE WHEN t.result IN (2,4,6,8,10,11,13,15,17,20,22,24,26,28,29,31,33,35) THEN t.cnt ELSE 0 END) AS blackCount,",
        "   SUM(CASE WHEN t.result BETWEEN 1 AND 12 THEN t.cnt ELSE 0 END) AS section1,",
        "   SUM(CASE WHEN t.result BETWEEN 13 AND 24 THEN t.cnt ELSE 0 END) AS section2,",
        "   SUM(CASE WHEN t.result BETWEEN 25 AND 36 THEN t.cnt ELSE 0 END) AS section3,",
        "   SUM(CASE WHEN t.result = 0 THEN t.cnt ELSE 0 END) AS num0,",
        "   SUM(CASE WHEN t.result = 1 THEN t.cnt ELSE 0 END) AS num1,",
        "   SUM(CASE WHEN t.result = 2 THEN t.cnt ELSE 0 END) AS num2,",
        "   SUM(CASE WHEN t.result = 3 THEN t.cnt ELSE 0 END) AS num3,",
        "   SUM(CASE WHEN t.result = 4 THEN t.cnt ELSE 0 END) AS num4,",
        "   SUM(CASE WHEN t.result = 5 THEN t.cnt ELSE 0 END) AS num5,",
        "   SUM(CASE WHEN t.result = 6 THEN t.cnt ELSE 0 END) AS num6,",
        "   SUM(CASE WHEN t.result = 7 THEN t.cnt ELSE 0 END) AS num7,",
        "   SUM(CASE WHEN t.result = 8 THEN t.cnt ELSE 0 END) AS num8,",
        "   SUM(CASE WHEN t.result = 9 THEN t.cnt ELSE 0 END) AS num9,",
        "   SUM(CASE WHEN t.result = 10 THEN t.cnt ELSE 0 END) AS num10,",
        "   SUM(CASE WHEN t.result = 11 THEN t.cnt ELSE 0 END) AS num11,",
        "   SUM(CASE WHEN t.result = 12 THEN t.cnt ELSE 0 END) AS num12,",
        "   SUM(CASE WHEN t.result = 13 THEN t.cnt ELSE 0 END) AS num13,",
        "   SUM(CASE WHEN t.result = 14 THEN t.cnt ELSE 0 END) AS num14,",
        "   SUM(CASE WHEN t.result = 15 THEN t.cnt ELSE 0 END) AS num15,",
        "   SUM(CASE WHEN t.result = 16 THEN t.cnt ELSE 0 END) AS num16,",
        "   SUM(CASE WHEN t.result = 17 THEN t.cnt ELSE 0 END) AS num17,",
        "   SUM(CASE WHEN t.result = 18 THEN t.cnt ELSE 0 END) AS num18,",
        "   SUM(CASE WHEN t.result = 19 THEN t.cnt ELSE 0 END) AS num19,",
        "   SUM(CASE WHEN t.result = 20 THEN t.cnt ELSE 0 END) AS num20,",
        "   SUM(CASE WHEN t.result = 21 THEN t.cnt ELSE 0 END) AS num21,",
        "   SUM(CASE WHEN t.result = 22 THEN t.cnt ELSE 0 END) AS num22,",
        "   SUM(CASE WHEN t.result = 23 THEN t.cnt ELSE 0 END) AS num23,",
        "   SUM(CASE WHEN t.result = 24 THEN t.cnt ELSE 0 END) AS num24,",
        "   SUM(CASE WHEN t.result = 25 THEN t.cnt ELSE 0 END) AS num25,",
        "   SUM(CASE WHEN t.result = 26 THEN t.cnt ELSE 0 END) AS num26,",
        "   SUM(CASE WHEN t.result = 27 THEN t.cnt ELSE 0 END) AS num27,",
        "   SUM(CASE WHEN t.result = 28 THEN t.cnt ELSE 0 END) AS num28,",
        "   SUM(CASE WHEN t.result = 29 THEN t.cnt ELSE 0 END) AS num29,",
        "   SUM(CASE WHEN t.result = 30 THEN t.cnt ELSE 0 END) AS num30,",
        "   SUM(CASE WHEN t.result = 31 THEN t.cnt ELSE 0 END) AS num31,",
        "   SUM(CASE WHEN t.result = 32 THEN t.cnt ELSE 0 END) AS num32,",
        "   SUM(CASE WHEN t.result = 33 THEN t.cnt ELSE 0 END) AS num33,",
        "   SUM(CASE WHEN t.result = 34 THEN t.cnt ELSE 0 END) AS num34,",
        "   SUM(CASE WHEN t.result = 35 THEN t.cnt ELSE 0 END) AS num35,",
        "   SUM(CASE WHEN t.result = 36 THEN t.cnt ELSE 0 END) AS num36",
        "FROM (",
        "       SELECT table_id, DATE(create_time) AS date,result,count(result) as cnt",
        "       FROM roulette_result",
        "       WHERE create_time BETWEEN #{startDate} AND #{endDate}",
        "       GROUP BY table_id, DATE(create_time), result" +
        "   ) t group by table_id,date ORDER BY table_id,t.date",
        "</script>"
    })
    List<RouletteStats> findByDate(LocalDate startDate, LocalDate endDate);

    @Select("SELECT MAX(create_time) AS latest_time FROM roulette_result")
    Optional<LocalDateTime> findLatestCreateTimestamp();

}