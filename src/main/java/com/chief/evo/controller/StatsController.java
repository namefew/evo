package com.chief.evo.controller;
import com.chief.evo.dto.RouletteStatsDTO;
import com.chief.evo.dto.SicboStatsDTO;
import com.chief.evo.entity.GameTable;
import com.chief.evo.entity.RouletteStats;
import com.chief.evo.entity.SicboStats;
import com.chief.evo.service.GameTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.chief.evo.service.GameResultService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private GameResultService gameResultService;

    @Autowired
    private GameTableService gameTableService;

    @GetMapping("/roulette")
    public List<RouletteStatsDTO> rouletteStats(@RequestParam(name="from")  @DateTimeFormat(pattern = "yyyy-M-d") // 添加日期格式注解
                                                    LocalDate fromDate, @RequestParam(name="to") @DateTimeFormat(pattern = "yyyy-M-d") LocalDate toDate) {
        List<RouletteStats> stats =  gameResultService.queryAllRouletteStats(fromDate, toDate);
        Map<String, GameTable> tableMap = gameTableService.getAllTables();
        List<RouletteStatsDTO> results = new ArrayList<>();
        for (RouletteStats stat : stats) {
            RouletteStatsDTO result = new RouletteStatsDTO();
            result.setTableId(stat.getTableId());
            result.setTableTitle(tableMap.get(stat.getTableId()).getTitle());
            result.setDate(stat.getDate());
            result.setTotalCount(stat.getTotalCount());
            result.setBigCount(stat.getBigCount());
            result.setSmallCount(stat.getSmallCount());
            result.setOddCount(stat.getOddCount());
            result.setEvenCount(stat.getEvenCount());
            result.setRedCount(stat.getRedCount());
            result.setBlackCount(stat.getBlackCount());
            result.setSection1_12(stat.getSection1());
            result.setSection13_24(stat.getSection2());
            result.setSection25_36(stat.getSection3());
            Map< Integer,Integer> numbs = new LinkedHashMap<>();
            result.setNumbs(numbs);
            numbs.put(0,stat.getNum0());
            numbs.put(1,stat.getNum1());
            numbs.put(2,stat.getNum2());
            numbs.put(3,stat.getNum3());
            numbs.put(4,stat.getNum4());
            numbs.put(5,stat.getNum5());
            numbs.put(6,stat.getNum6());
            numbs.put(7,stat.getNum7());
            numbs.put(8,stat.getNum8());
            numbs.put(9,stat.getNum9());
            numbs.put(10,stat.getNum10());
            numbs.put(11,stat.getNum11());
            numbs.put(12,stat.getNum12());
            numbs.put(13,stat.getNum13());
            numbs.put(14,stat.getNum14());
            numbs.put(15,stat.getNum15());
            numbs.put(16,stat.getNum16());
            numbs.put(17,stat.getNum17());
            numbs.put(18,stat.getNum18());
            numbs.put(19,stat.getNum19());
            numbs.put(20,stat.getNum20());
            numbs.put(21,stat.getNum21());
            numbs.put(22,stat.getNum22());
            numbs.put(23,stat.getNum23());
            numbs.put(24,stat.getNum24());
            numbs.put(25,stat.getNum25());
            numbs.put(26,stat.getNum26());
            numbs.put(27,stat.getNum27());
            numbs.put(28,stat.getNum28());
            numbs.put(29,stat.getNum29());
            numbs.put(30,stat.getNum30());
            numbs.put(31,stat.getNum31());
            numbs.put(32,stat.getNum32());
            numbs.put(33,stat.getNum33());
            numbs.put(34,stat.getNum34());
            numbs.put(35,stat.getNum35());
            numbs.put(36,stat.getNum36());
            results.add(result);
        }
        return results;
    }

    @GetMapping("/sicbo")
    public List<SicboStatsDTO> sicboStats(@RequestParam(name="from")  @DateTimeFormat(pattern = "yyyy-M-d") // 添加日期格式注解
                                                LocalDate fromDate, @RequestParam(name="to") @DateTimeFormat(pattern = "yyyy-M-d") LocalDate toDate) {
        List<SicboStats> stats =  gameResultService.queryAllSicboStats(fromDate, toDate);
        Map<String, GameTable> tableMap = gameTableService.getAllTables();
        List<SicboStatsDTO> results = new ArrayList<>();
        for (SicboStats stat : stats) {
            SicboStatsDTO result = new SicboStatsDTO();
            result.setTableId(stat.getTableId());
            result.setTableTitle(tableMap.get(stat.getTableId()).getTitle());
            result.setDate(stat.getDate());
            result.setTotalCount(stat.getTotalCount());
            result.setBigCount(stat.getBigCount());
            result.setSmallCount(stat.getSmallCount());
            result.setOddCount(stat.getOddCount());
            result.setEvenCount(stat.getEvenCount());
            result.setDot1Count(stat.getDot1Count());
            result.setDot2Count(stat.getDot2Count());
            result.setDot3Count(stat.getDot3Count());
            result.setDot4Count(stat.getDot4Count());
            result.setDot5Count(stat.getDot5Count());
            result.setDot6Count(stat.getDot6Count());
            result.setTripleCount(stat.getTripleCount());

            Map< Integer,Integer> numbs = new LinkedHashMap<>();
            result.setSumNumbs(numbs);
            numbs.put(4,stat.getNum4());
            numbs.put(5,stat.getNum5());
            numbs.put(6,stat.getNum6());
            numbs.put(7,stat.getNum7());
            numbs.put(8,stat.getNum8());
            numbs.put(9,stat.getNum9());
            numbs.put(10,stat.getNum10());
            numbs.put(11,stat.getNum11());
            numbs.put(12,stat.getNum12());
            numbs.put(13,stat.getNum13());
            numbs.put(14,stat.getNum14());
            numbs.put(15,stat.getNum15());
            numbs.put(16,stat.getNum16());
            numbs.put(17,stat.getNum17());
            results.add(result);
        }
        return results;
    }

}