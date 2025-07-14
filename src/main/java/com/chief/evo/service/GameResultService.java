package com.chief.evo.service;

import com.chief.evo.entity.*;
import com.chief.evo.mapper.RouletteResultMapper;
import com.chief.evo.mapper.SicboResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component
public class GameResultService {

    @Autowired
    private RouletteResultMapper rouletteResultMapper;

    @Autowired
    private SicboResultMapper sicboResultMapper;

    public List<SicboResult> addSicboResult(List<SicboResult> sicboResults, GameTable gameTable) {
        if (sicboResults.isEmpty()) return Collections.emptyList();
        Collections.reverse(sicboResults);
        // 获取与传入列表数量相同的数据库最新记录（倒序）
        List<SicboResult> latest = sicboResultMapper.findLatestByTableId(gameTable.getId(), sicboResults.size());
        // 反转数据库记录顺序，使其变成正序（最新记录在后）
        Collections.reverse(latest);
        if (latest.isEmpty()) {
            sicboResultMapper.insertList(sicboResults);
            return sicboResults;
        }

        int matchIndex = -1;
        int matchedLength = -1; // 记录匹配的长度

        for (int i = 0; i <= latest.size(); i++) {
            int compareLength = Math.min(latest.size() - i, sicboResults.size());

            if (compareLength <= 0) continue;

            // 直接比较元素而非创建子列表
            boolean isMatch = true;
            for (int j = 0; j < compareLength; j++) {
                if (!latest.get(i + j).equals(sicboResults.get(j))) {
                    isMatch = false;
                    break;
                }
            }

            if (isMatch) {
                matchIndex = i;
                matchedLength = compareLength; // 记录匹配的长度
                break;
            }
        }

        // 处理匹配结果
        if (matchIndex >= 0) {
            // 匹配上了，那么传入列表中前matchedLength个元素是已经存在的
            if (matchedLength < sicboResults.size()) {
                List<SicboResult> insertList = sicboResults.subList(matchedLength, sicboResults.size());
                if (!insertList.isEmpty()) {
                    sicboResultMapper.insertList(insertList);
                }
                return insertList;
            } else {
                return Collections.emptyList(); // 全部数据已存在
            }
        }

        // 无匹配时插入全部数据
        sicboResultMapper.insertList(sicboResults);
        return sicboResults;
    }

    public List<RouletteResult> addRouletteResult(List<RouletteResult> latestResults, GameTable gameTable) {
        if (latestResults.isEmpty()) return Collections.emptyList();
        Collections.reverse(latestResults);
        // 获取与传入列表数量相同的数据库最新记录（倒序）
        List<RouletteResult> latest = rouletteResultMapper.findLatestByTableId(gameTable.getId(), latestResults.size());
        // 反转数据库记录顺序，使其变成正序（最新记录在后）
        Collections.reverse( latest);
        if (latest.isEmpty()) {
            rouletteResultMapper.insertList(latestResults);
            return latestResults;
        }

        int matchIndex = -1;
        int matchedLength = -1; // 记录匹配的长度

        for (int i = 0; i <= latest.size(); i++) {
            int compareLength = Math.min(latest.size() - i, latestResults.size());

            if (compareLength <= 0) continue;

            // 直接比较元素而非创建子列表
            boolean isMatch = true;
            for (int j = 0; j < compareLength; j++) {
                if (!latest.get(i + j).equals(latestResults.get(j))) {
                    isMatch = false;
                    break;
                }
            }

            if (isMatch) {
                matchIndex = i;
                matchedLength = compareLength; // 记录匹配的长度
                break;
            }
        }

        // 处理匹配结果
        if (matchedLength > 0) {
            // 匹配上了，那么传入列表中前matchedLength个元素是已经存在的
            if (matchedLength < latestResults.size()) {
                List<RouletteResult> insertList = latestResults.subList(matchedLength, latestResults.size());
                if (!insertList.isEmpty()) {
                    rouletteResultMapper.insertList(insertList);
                }
                return insertList;
            } else {
                return Collections.emptyList(); // 全部数据已存在
            }
        }

        // 无匹配时插入全部数据
        rouletteResultMapper.insertList(latestResults);
        return latestResults;
    }

    public List<RouletteStats> queryAllRouletteStats(LocalDate fromDate, LocalDate toDate) {
        return rouletteResultMapper.findByDate(fromDate, toDate);
    }
    public List<SicboStats> queryAllSicboStats(LocalDate fromDate, LocalDate toDate) {
        return sicboResultMapper.findByDate(fromDate, toDate);
    }

}
