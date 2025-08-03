package com.chief.evo.service;

import com.chief.evo.entity.*;
import com.chief.evo.mapper.PpRouletteResultMapper;
import com.chief.evo.mapper.PpSicboResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class PpGameResultService {

    @Autowired
    private PpRouletteResultMapper ppRouletteResultMapper;

    @Autowired
    private PpSicboResultMapper ppSicboResultMapper;

    public List<PpSicboResult> addSicboResult(List<PpSicboResult> sicboResults, PpTable gameTable) {
        if (sicboResults.isEmpty()) return Collections.emptyList();
        Collections.reverse(sicboResults);
        // 获取与传入列表数量相同的数据库最新记录（倒序）
        List<PpSicboResult> latest = ppSicboResultMapper.findLatestByTableId(gameTable.getTableId(), sicboResults.size());
        // 反转数据库记录顺序，使其变成正序（最新记录在后）
        Collections.reverse(latest);
        if (latest.isEmpty()) {
            ppSicboResultMapper.insertList(sicboResults);
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
                List<PpSicboResult> insertList = sicboResults.subList(matchedLength, sicboResults.size());
                if (!insertList.isEmpty()) {
                    ppSicboResultMapper.insertList(insertList);
                }
                return insertList;
            } else {
                return Collections.emptyList(); // 全部数据已存在
            }
        }

        // 无匹配时插入全部数据
        ppSicboResultMapper.insertList(sicboResults);
        return sicboResults;
    }

    public List<PpRouletteResult> addRouletteResult(List<PpRouletteResult> latestResults, PpTable gameTable) {
        if (latestResults.isEmpty()) return Collections.emptyList();
        Collections.reverse(latestResults);
        // 获取与传入列表数量相同的数据库最新记录（倒序）
        List<PpRouletteResult> latest = ppRouletteResultMapper.findLatestByTableId(gameTable.getTableId(), latestResults.size());
        // 反转数据库记录顺序，使其变成正序（最新记录在后）
        Collections.reverse( latest);
        if (latest.isEmpty()) {
            ppRouletteResultMapper.insertList(latestResults);
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
                List<PpRouletteResult> insertList = latestResults.subList(matchedLength, latestResults.size());
                if (!insertList.isEmpty()) {
                    ppRouletteResultMapper.insertList(insertList);
                }
                return insertList;
            } else {
                return Collections.emptyList(); // 全部数据已存在
            }
        }

        // 无匹配时插入全部数据
        ppRouletteResultMapper.insertList(latestResults);
        return latestResults;
    }

    public List<RouletteStats> queryAllRouletteStats(LocalDate fromDate, LocalDate toDate) {
        return ppRouletteResultMapper.findByDate(fromDate, toDate);
    }
    public List<SicboStats> queryAllSicboStats(LocalDate fromDate, LocalDate toDate) {
        return ppSicboResultMapper.findByDate(fromDate, toDate);
    }

    public Optional<LocalDateTime> findLatestRouletteCreateTime() {
        return ppRouletteResultMapper.findLatestCreateTimestamp();
    }
    public Optional<LocalDateTime> findLatestSicboCreateTime() {
        return ppSicboResultMapper.findLatestCreateTimestamp();
    }
}