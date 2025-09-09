package com.chief.evo.mapper;

import com.chief.evo.entity.WlGoldenFlowerResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface WlGoldenFlowerResultMapper {

    @Select("SELECT MAX(create_time) AS latest_time FROM wl_golden_flower")
    Optional<LocalDateTime> findLatestCreateTimestamp();

    @Insert({
            "<script>",
            "INSERT IGNORE INTO wl_golden_flower(table_id, round_id, dealer_id, round, " +
                    "dragon_card1, dragon_card2, dragon_card3, phoenix_card1, phoenix_card2, phoenix_card3) VALUES ",
            "<foreach collection='resultList' item='item' separator=','>",
            "(#{item.tableId}, #{item.roundId}, #{item.dealerId}, #{item.round}, " +
                    "#{item.dragonCard1}, #{item.dragonCard2}, #{item.dragonCard3}, " +
                    "#{item.phoenixCard1}, #{item.phoenixCard2}, #{item.phoenixCard3})",
            "</foreach>",
            "</script>"
    })
    void insertList(List<WlGoldenFlowerResult> resultList);
}