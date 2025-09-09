package com.chief.evo.service;

import com.chief.evo.entity.*;
import com.chief.evo.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class WlGameResultService {

    @Autowired
    private WlRouletteResultMapper wlRouletteResultMapper;

    @Autowired
    private WlSicboResultMapper wlSicboResultMapper;

    @Autowired
    private WlColorDiskResultMapper wlColorDiskResultMapper;

    @Autowired
    private WlBaccaratResultMapper wlBaccaratResultMapper;

    @Autowired
    private WlGoldenFlowerResultMapper wlGoldenFlowerResultMapper;

    public void saveRouletteResult(WlRouletteResult  result){
        wlRouletteResultMapper.insertList(Arrays.asList( result));
    }
    public void saveSicboResult(WlSicboResult result){
        wlSicboResultMapper.insertList(Arrays.asList( result));
    }
    public void saveColorDiskResult(WlColorDiskResult result){
        wlColorDiskResultMapper.insertList(Arrays.asList( result));
    }

    public void saveBaccaratResult(WlBaccaratResult result){
        wlBaccaratResultMapper.insertList(Arrays.asList( result));
    }
    public List<RouletteStats> queryAllRouletteStats(LocalDate fromDate, LocalDate toDate) {
        return wlRouletteResultMapper.findByDate(fromDate, toDate);
    }
    
    public List<SicboStats> queryAllSicboStats(LocalDate fromDate, LocalDate toDate) {
        return wlSicboResultMapper.findByDate(fromDate, toDate);
    }

    public Optional<LocalDateTime> findLatestRouletteCreateTime() {
        return wlRouletteResultMapper.findLatestCreateTimestamp();
    }

    public Optional<LocalDateTime> findLatestSicboCreateTime() {
        return wlSicboResultMapper.findLatestCreateTimestamp();
    }

    public List<ColorDiskStats> queryAllColorDiskStats(LocalDate fromDate, LocalDate toDate) {
        return wlColorDiskResultMapper.findByDate(fromDate, toDate);
    }

    public Optional<LocalDateTime> findLatestColorDiskCreateTime() {
        return wlColorDiskResultMapper.findLatestCreateTimestamp();
    }
    public void saveGoldenFlowerResult(WlGoldenFlowerResult result){
        wlGoldenFlowerResultMapper.insertList(Arrays.asList( result));
    }

    public Optional<LocalDateTime> findLatestGoldenFlowerCreateTime() {
        return wlGoldenFlowerResultMapper.findLatestCreateTimestamp();
    }
}