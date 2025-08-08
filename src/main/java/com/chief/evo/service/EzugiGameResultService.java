package com.chief.evo.service;

import com.chief.evo.entity.*;
import com.chief.evo.mapper.EzugiColorDiskResultMapper;
import com.chief.evo.mapper.EzugiRouletteResultMapper;
import com.chief.evo.mapper.EzugiSicboResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class EzugiGameResultService {

    @Autowired
    private EzugiRouletteResultMapper ezugiRouletteResultMapper;

    @Autowired
    private EzugiSicboResultMapper ezugiSicboResultMapper;

    public List<EzugiRouletteResult> addEzugiRouletteResult(List<EzugiRouletteResult> rouletteResults, String tableId) {
        if (rouletteResults.isEmpty()) return Collections.emptyList();
        // 获取与传入列表数量相同的数据库最新记录（倒序）
        List<EzugiRouletteResult> latest = ezugiRouletteResultMapper.findLatestByTableId(tableId, Math.max(rouletteResults.size(),50));
        // 反转数据库记录顺序，使其变成正序（最新记录在后）
        latest.removeIf(rouletteResult -> latest.stream().anyMatch(lr -> rouletteResult.getRoundId().equals(lr.getRoundId())));

        if(!rouletteResults.isEmpty())
            ezugiRouletteResultMapper.insertList(rouletteResults);
        return rouletteResults;
    }

    public List<EzugiSicboResult> addEzugiSicboResult(List<EzugiSicboResult> sicboResults, String tableId) {
        if (sicboResults.isEmpty()) return Collections.emptyList();
        // 获取与传入列表数量相同的数据库最新记录（倒序）
        List<EzugiSicboResult> latest = ezugiSicboResultMapper.findLatestByTableId(tableId, Math.max(sicboResults.size(),50));
        // 反转数据库记录顺序，使其变成正序（最新记录在后）
        sicboResults.removeIf(sicboResult -> latest.stream().anyMatch(lr -> sicboResult.getRoundId().equals(lr.getRoundId())));
        // 无匹配时插入全部数据
        if(!sicboResults.isEmpty())
            ezugiSicboResultMapper.insertList(sicboResults);
        return sicboResults;
    }

    public List<RouletteStats> queryAllRouletteStats(LocalDate fromDate, LocalDate toDate) {
        return ezugiRouletteResultMapper.findByDate(fromDate, toDate);
    }
    
    public List<SicboStats> queryAllSicboStats(LocalDate fromDate, LocalDate toDate) {
        return ezugiSicboResultMapper.findByDate(fromDate, toDate);
    }

    public Optional<LocalDateTime> findLatestRouletteCreateTime() {
        return ezugiRouletteResultMapper.findLatestCreateTimestamp();
    }

    public Optional<LocalDateTime> findLatestSicboCreateTime() {
        return ezugiSicboResultMapper.findLatestCreateTimestamp();
    }
    @Autowired
    private EzugiColorDiskResultMapper ezugiColorDiskResultMapper;

    public List<EzugiColorDiskResult> addEzugiColorDiskResult(List<EzugiColorDiskResult> colorDiskResults, String tableId) {
        if (colorDiskResults.isEmpty()) return Collections.emptyList();
        // 获取与传入列表数量相同的数据库最新记录（倒序）
        List<EzugiColorDiskResult> latest = ezugiColorDiskResultMapper.findLatestByTableId(tableId, Math.max(colorDiskResults.size(),50));
        colorDiskResults.removeIf(colorDiskResult -> latest.stream().anyMatch(lr -> colorDiskResult.getRoundId().equals(lr.getRoundId())));
        // 无匹配时插入全部数据
        if(!colorDiskResults.isEmpty())
            ezugiColorDiskResultMapper.insertList(colorDiskResults);
        return colorDiskResults;
    }

    public List<ColorDiskStats> queryAllColorDiskStats(LocalDate fromDate, LocalDate toDate) {
        return ezugiColorDiskResultMapper.findByDate(fromDate, toDate);
    }

    public Optional<LocalDateTime> findLatestColorDiskCreateTime() {
        return ezugiColorDiskResultMapper.findLatestCreateTimestamp();
    }
}