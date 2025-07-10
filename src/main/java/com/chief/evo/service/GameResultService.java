package com.chief.evo.service;

import com.chief.evo.entity.GameTable;
import com.chief.evo.entity.RouletteResult;
import com.chief.evo.entity.SicboResult;
import com.chief.evo.mapper.RouletteResultMapper;
import com.chief.evo.mapper.SicboResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

        // 获取与传入列表数量相同的数据库最新记录
        List<SicboResult> latest = sicboResultMapper.findLatestByTableId(gameTable.getId(), sicboResults.size());
        if (latest.isEmpty()) {
            sicboResultMapper.insertList(sicboResults);
            return sicboResults;
        }

        // 查找匹配位置
        int matchIndex = -1;
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
                break;
            }
        }

        // 处理匹配结果
        if (matchIndex >= 0) {
            int startIndex = latest.size() - matchIndex;
            if (startIndex < sicboResults.size()) {
                List<SicboResult> insertList = sicboResults.subList(startIndex, sicboResults.size());
                if (!insertList.isEmpty()) {
                    sicboResultMapper.insertList(insertList);
                }
                return insertList;
            }
            return Collections.emptyList(); // 全部数据已存在
        }

        // 无匹配时插入全部数据
        sicboResultMapper.insertList(sicboResults);
        return sicboResults;
    }


    public List<RouletteResult> addRouletteResult(List<RouletteResult> latestResults, GameTable gameTable) {
        if (latestResults.isEmpty()) return Collections.emptyList();

        // 获取与传入列表数量相同的数据库最新记录
        List<RouletteResult> latest = rouletteResultMapper.findLatestByTableId(gameTable.getId(), latestResults.size());
        if (latest.isEmpty()) {
            rouletteResultMapper.insertList(latestResults);
            return latestResults;
        }

        // 查找匹配位置
        int matchIndex = -1;
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
                break;
            }
        }

        // 处理匹配结果
        if (matchIndex >= 0) {
            int startIndex = latest.size() - matchIndex;
            if (startIndex < latestResults.size()) {
                List<RouletteResult> insertList = latestResults.subList(startIndex, latestResults.size());
                if (!insertList.isEmpty()) {
                    rouletteResultMapper.insertList(insertList);
                }
                return insertList;
            }
            return Collections.emptyList(); // 全部数据已存在
        }

        // 无匹配时插入全部数据
        rouletteResultMapper.insertList(latestResults);
        return latestResults;
    }

}
