package com.chief.evo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import com.chief.evo.entity.GameTable;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GameTableMapper {
    // 修改插入语句：添加所有字段（create_time除外），使用foreach实现批量插入，并添加主键冲突忽略逻辑
    @Insert({
        "<script>",
        "INSERT INTO games (id, title, game_type, frontend_app, game_provider, gv, published, opens_at, lang, min_bet, max_bet) ",
        "VALUES ",
        "<foreach collection='gameTables' item='item' separator=','>",
        "(#{item.id}, #{item.title}, #{item.gameType}, #{item.frontendApp}, #{item.gameProvider}, #{item.gv}, #{item.published}, #{item.opensAt}, #{item.lang}, #{item.minBet}, #{item.maxBet})",
        "</foreach>",
        "ON DUPLICATE KEY UPDATE id=id", // 主键冲突时忽略操作
        "</script>"
    })
    void insertList(List<GameTable> gameTables);

    @Select("SELECT * FROM games")
    List<GameTable> queryAll();
}