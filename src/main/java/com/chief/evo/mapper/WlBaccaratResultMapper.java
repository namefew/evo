package com.chief.evo.mapper;

import com.chief.evo.entity.WlBaccaratResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface WlBaccaratResultMapper {

    @Select("SELECT MAX(create_time) AS latest_time FROM wl_baccarat_result")
    Optional<LocalDateTime> findLatestCreateTimestamp();

    @Insert({
            "<script>",
            "INSERT IGNORE INTO wl_baccarat_result(table_id, round_id, dealer_id, round, " +
                    "player_card1, player_card2, player_card3, banker_card1, banker_card2, banker_card3) VALUES ",
            "<foreach collection='resultList' item='item' separator=','>",
            "(#{item.tableId}, #{item.roundId}, #{item.dealerId}, #{item.round}, " +
                    "#{item.playerCard1}, #{item.playerCard2}, #{item.playerCard3}, " +
                    "#{item.bankerCard1}, #{item.bankerCard2}, #{item.bankerCard3})",
            "</foreach>",
            "</script>"
    })
    void insertList(List<WlBaccaratResult> resultList);
}
