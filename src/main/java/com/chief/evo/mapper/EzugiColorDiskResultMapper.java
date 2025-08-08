package com.chief.evo.mapper;

import com.chief.evo.entity.ColorDiskStats;
import com.chief.evo.entity.EzugiColorDiskResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface EzugiColorDiskResultMapper {
    @Insert({
        "<script>",
        "INSERT ignore INTO ezugi_color_disk_result(table_id, round_id, result) VALUES ",
        "<foreach collection='resultList' item='item' separator=','>",
        "(#{item.tableId}, #{item.roundId}, #{item.result})",
        "</foreach>",
        "</script>"
    })
    void insertList(List<EzugiColorDiskResult> resultList);

    @Select("SELECT * FROM ezugi_color_disk_result WHERE table_id = #{tableId} ORDER BY id DESC LIMIT #{limit}")
    List<EzugiColorDiskResult> findLatestByTableId(String tableId, int limit);

    @Select({
            "<script>",
            "SELECT",
            "   t.table_id as tableId,",
            "   t.date,",
            "   SUM(t.cnt) AS totalCount,",
            "   SUM(CASE WHEN t.result=3 or t.result=4 THEN t.cnt ELSE 0 END) AS bigCount,",
            "   SUM(CASE WHEN t.result=0 or t.result=1 THEN t.cnt ELSE 0 END) AS smallCount,",
            "   SUM(CASE WHEN t.result % 2 = 1 THEN t.cnt ELSE 0 END) AS oddCount,",
            "   SUM(CASE WHEN t.result % 2 = 0 THEN t.cnt ELSE 0 END) AS evenCount,",
            "   SUM(CASE WHEN t.result = 0 THEN t.cnt ELSE 0 END) AS num0,",
            "   SUM(CASE WHEN t.result = 1 THEN t.cnt ELSE 0 END) AS num1,",
            "   SUM(CASE WHEN t.result = 2 THEN t.cnt ELSE 0 END) AS num2,",
            "   SUM(CASE WHEN t.result = 3 THEN t.cnt ELSE 0 END) AS num3,",
            "   SUM(CASE WHEN t.result = 4 THEN t.cnt ELSE 0 END) AS num4",
            "FROM (",
            "       SELECT table_id, DATE(create_time) AS date,result,count(result) as cnt",
            "       FROM ezugi_color_disk_result",
            "       WHERE create_time BETWEEN #{startDate} AND #{endDate}",
            "       GROUP BY table_id, DATE(create_time), result" +
                    "   ) t group by table_id,date ORDER BY table_id,t.date",
            "</script>"
    })
    List<ColorDiskStats> findByDate(LocalDate startDate, LocalDate endDate);

    @Select("SELECT MAX(create_time) AS latest_time FROM ezugi_color_disk_result")
    Optional<LocalDateTime> findLatestCreateTimestamp();
}