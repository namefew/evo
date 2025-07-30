package com.chief.evo.service;

import com.chief.evo.entity.*;
import com.chief.evo.mapper.DbColorDiskResultMapper;
import com.chief.evo.mapper.DbRouletteResultMapper;
import com.chief.evo.mapper.DbSicboResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component
public class DbGameResultService {

    @Autowired
    private DbRouletteResultMapper dbRouletteResultMapper;

    @Autowired
    private DbSicboResultMapper dbSicboResultMapper;

    @Autowired
    private DbColorDiskResultMapper dbColorDiskResultMapper;


    public List<DbRouletteResult> addDbRouletteResult(List<DbRouletteResult> rouletteResults, DbTable gameTable) {
        if (rouletteResults.isEmpty()) return Collections.emptyList();
        // Collections.reverse(rouletteResults);
        // 获取与传入列表数量相同的数据库最新记录（倒序）
        List<DbRouletteResult> latest = dbRouletteResultMapper.findLatestByTableId(gameTable.getTableId(), rouletteResults.size());
        // 反转数据库记录顺序，使其变成正序（最新记录在后）
        Collections.reverse(latest);
        if (latest.isEmpty()) {
            dbRouletteResultMapper.insertList(rouletteResults);
            return rouletteResults;
        }

        int matchIndex = -1;
        int matchedLength = -1; // 记录匹配的长度

        for (int i = 0; i <= latest.size(); i++) {
            int compareLength = Math.min(latest.size() - i, rouletteResults.size());

            if (compareLength <= 0) continue;

            // 直接比较元素而非创建子列表
            boolean isMatch = true;
            for (int j = 0; j < compareLength; j++) {
                if (!latest.get(i + j).equals(rouletteResults.get(j))) {
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
            if (matchedLength < rouletteResults.size()) {
                List<DbRouletteResult> insertList = rouletteResults.subList(matchedLength, rouletteResults.size());
                if (!insertList.isEmpty()) {
                    dbRouletteResultMapper.insertList(insertList);
                }
                return insertList;
            } else {
                return Collections.emptyList(); // 全部数据已存在
            }
        }

        // 无匹配时插入全部数据
        dbRouletteResultMapper.insertList(rouletteResults);
        return rouletteResults;
    }

    public List<DbSicboResult> addDbSicboResult(List<DbSicboResult> sicboResults, DbTable gameTable) {
        if (sicboResults.isEmpty()) return Collections.emptyList();
        // Collections.reverse(sicboResults);
        // 获取与传入列表数量相同的数据库最新记录（倒序）
        List<DbSicboResult> latest = dbSicboResultMapper.findLatestByTableId(gameTable.getTableId(), sicboResults.size());
        // 反转数据库记录顺序，使其变成正序（最新记录在后）
        Collections.reverse(latest);
        if (latest.isEmpty()) {
            dbSicboResultMapper.insertList(sicboResults);
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
                List<DbSicboResult> insertList = sicboResults.subList(matchedLength, sicboResults.size());
                if (!insertList.isEmpty()) {
                    dbSicboResultMapper.insertList(insertList);
                }
                return insertList;
            } else {
                return Collections.emptyList(); // 全部数据已存在
            }
        }

        // 无匹配时插入全部数据
        dbSicboResultMapper.insertList(sicboResults);
        return sicboResults;
    }


    public List<DbColorDiskResult> addDbColorDiskResult(List<DbColorDiskResult> colorDiskResults, DbTable gameTable) {
        if (colorDiskResults.isEmpty()) return Collections.emptyList();
        // Collections.reverse(colorDiskResults);
        // 获取与传入列表数量相同的数据库最新记录（倒序）
        List<DbColorDiskResult> latest = dbColorDiskResultMapper.findLatestByTableId(gameTable.getTableId(), colorDiskResults.size());
        // 反转数据库记录顺序，使其变成正序（最新记录在后）
        Collections.reverse(latest);
        if (latest.isEmpty()) {
            dbColorDiskResultMapper.insertList(colorDiskResults);
            return colorDiskResults;
        }

        int matchIndex = -1;
        int matchedLength = -1; // 记录匹配的长度

        for (int i = 0; i <= latest.size(); i++) {
            int compareLength = Math.min(latest.size() - i, colorDiskResults.size());

            if (compareLength <= 0) continue;

            // 直接比较元素而非创建子列表
            boolean isMatch = true;
            for (int j = 0; j < compareLength; j++) {
                if (!latest.get(i + j).equals(colorDiskResults.get(j))) {
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
            if (matchedLength < colorDiskResults.size()) {
                List<DbColorDiskResult> insertList = colorDiskResults.subList(matchedLength, colorDiskResults.size());
                if (!insertList.isEmpty()) {
                    dbColorDiskResultMapper.insertList(insertList);
                }
                return insertList;
            } else {
                return Collections.emptyList(); // 全部数据已存在
            }
        }

        // 无匹配时插入全部数据
        dbColorDiskResultMapper.insertList(colorDiskResults);
        return colorDiskResults;
    }


    public List<RouletteStats> queryAllRouletteStats(LocalDate fromDate, LocalDate toDate) {
        return dbRouletteResultMapper.findByDate(fromDate, toDate);
    }
    public List<SicboStats> queryAllSicboStats(LocalDate fromDate, LocalDate toDate) {
        return dbSicboResultMapper.findByDate(fromDate, toDate);
    }

    public List<ColorDiskStats> queryAllColorDiskStats(LocalDate fromDate, LocalDate toDate) {
        return dbColorDiskResultMapper.findByDate(fromDate, toDate);
    }
}