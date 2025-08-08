package com.chief.evo.controller;
import com.chief.evo.dto.ColorDiskStatsDTO;
import com.chief.evo.dto.RouletteStatsDTO;
import com.chief.evo.dto.SicboStatsDTO;
import com.chief.evo.entity.*;
import com.chief.evo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private GameResultService gameResultService;

    @Autowired
    private GameTableService gameTableService;

    @Autowired
    private DbGameResultService dbGameResultService;

    @Autowired
    private DbTableService dbTableService;

    @Autowired
    private PpTableService ppTableService;

    @GetMapping("/roulette")
    public List<RouletteStatsDTO> rouletteStats(@RequestParam(name="from")  @DateTimeFormat(pattern = "yyyy-M-d") 
                                                LocalDate fromDate, @RequestParam(name="to") @DateTimeFormat(pattern = "yyyy-M-d") LocalDate toDate) {
        List<RouletteStats> stats =  gameResultService.queryAllRouletteStats(fromDate, toDate);
        Map<String, GameTable> tableMap = gameTableService.getAllTables();
        List<RouletteStatsDTO> results = new ArrayList<>();
        for (RouletteStats stat : stats) {
            RouletteStatsDTO result = converteRouletteState(stat);
            result.setTableTitle(tableMap.get(stat.getTableId()).getTitle());
            result.setTableId(stat.getTableId());
            results.add(result);
        }
        Map<String, List<RouletteStatsDTO>> groups = results.stream().collect(Collectors.groupingBy(RouletteStatsDTO::getTableId));
        List<RouletteStatsDTO> finalResults = new ArrayList<>();
        groups.forEach((tableId, group) -> {
            // 添加原始数据
            finalResults.addAll(group);

            // 创建聚合对象
            RouletteStatsDTO sum = new RouletteStatsDTO();
            sum.setTableId(tableId);
            sum.setTableTitle(group.get(0).getTableTitle());
            sum.setDate(null); // 聚合对象日期为空

            // 聚合统计值
            sum.setTotalCount(group.stream().mapToInt(RouletteStatsDTO::getTotalCount).sum());
            sum.setBigCount(group.stream().mapToInt(RouletteStatsDTO::getBigCount).sum());
            sum.setSmallCount(group.stream().mapToInt(RouletteStatsDTO::getSmallCount).sum());
            sum.setOddCount(group.stream().mapToInt(RouletteStatsDTO::getOddCount).sum());
            sum.setEvenCount(group.stream().mapToInt(RouletteStatsDTO::getEvenCount).sum());
            sum.setRedCount(group.stream().mapToInt(RouletteStatsDTO::getRedCount).sum());
            sum.setBlackCount(group.stream().mapToInt(RouletteStatsDTO::getBlackCount).sum());
            sum.setSection1_12(group.stream().mapToInt(RouletteStatsDTO::getSection1_12).sum());
            sum.setSection13_24(group.stream().mapToInt(RouletteStatsDTO::getSection13_24).sum());
            sum.setSection25_36(group.stream().mapToInt(RouletteStatsDTO::getSection25_36).sum());

            // 聚合数字统计
            Map<Integer, Integer> sumNumbs = new LinkedHashMap<>();
            for (int i = 0; i <= 36; i++) {
                int finalI = i;
                int total = group.stream()
                        .mapToInt(dto -> dto.getNumbs().getOrDefault(finalI, 0))
                        .sum();
                sumNumbs.put(i, total);
            }
            sum.setNumbs(sumNumbs);

            finalResults.add(sum);
        });

        // 按tableId和date排序
        finalResults.sort(Comparator
                .comparing(RouletteStatsDTO::getTableId)
                .thenComparing(RouletteStatsDTO::getDate,
                        Comparator.nullsLast(Comparator.naturalOrder()))
        );

        return finalResults;
    }

    @GetMapping("/db/roulette")
    public List<RouletteStatsDTO> dbRouletteStats(@RequestParam(name="from")  @DateTimeFormat(pattern = "yyyy-M-d")
                                                LocalDate fromDate, @RequestParam(name="to") @DateTimeFormat(pattern = "yyyy-M-d") LocalDate toDate) {
        List<RouletteStats> stats =  dbGameResultService.queryAllRouletteStats(fromDate, toDate);
        Map<Integer, DbTable> tableMap = dbTableService.getAllTables();
        List<RouletteStatsDTO> results = new ArrayList<>();
        for (RouletteStats stat : stats) {
            RouletteStatsDTO result = converteRouletteState(stat);
            result.setTableTitle(tableMap.get(Integer.parseInt(stat.getTableId())).getTableName());
            result.setTableId(stat.getTableId());
            results.add(result);
        }
        Map<String, List<RouletteStatsDTO>> groups = results.stream().collect(Collectors.groupingBy(RouletteStatsDTO::getTableId));
        List<RouletteStatsDTO> finalResults = new ArrayList<>();
        groups.forEach((tableId, group) -> {
            // 添加原始数据
            finalResults.addAll(group);

            // 创建聚合对象
            RouletteStatsDTO sum = new RouletteStatsDTO();
            sum.setTableId(tableId);
            sum.setTableTitle(group.get(0).getTableTitle());
            sum.setDate(null); // 聚合对象日期为空

            // 聚合统计值
            sum.setTotalCount(group.stream().mapToInt(RouletteStatsDTO::getTotalCount).sum());
            sum.setBigCount(group.stream().mapToInt(RouletteStatsDTO::getBigCount).sum());
            sum.setSmallCount(group.stream().mapToInt(RouletteStatsDTO::getSmallCount).sum());
            sum.setOddCount(group.stream().mapToInt(RouletteStatsDTO::getOddCount).sum());
            sum.setEvenCount(group.stream().mapToInt(RouletteStatsDTO::getEvenCount).sum());
            sum.setRedCount(group.stream().mapToInt(RouletteStatsDTO::getRedCount).sum());
            sum.setBlackCount(group.stream().mapToInt(RouletteStatsDTO::getBlackCount).sum());
            sum.setSection1_12(group.stream().mapToInt(RouletteStatsDTO::getSection1_12).sum());
            sum.setSection13_24(group.stream().mapToInt(RouletteStatsDTO::getSection13_24).sum());
            sum.setSection25_36(group.stream().mapToInt(RouletteStatsDTO::getSection25_36).sum());

            // 聚合数字统计
            Map<Integer, Integer> sumNumbs = new LinkedHashMap<>();
            for (int i = 0; i <= 36; i++) {
                int finalI = i;
                int total = group.stream()
                        .mapToInt(dto -> dto.getNumbs().getOrDefault(finalI, 0))
                        .sum();
                sumNumbs.put(i, total);
            }
            sum.setNumbs(sumNumbs);

            finalResults.add(sum);
        });

        // 按tableId和date排序
        finalResults.sort(Comparator
                .comparing(RouletteStatsDTO::getTableId)
                .thenComparing(RouletteStatsDTO::getDate,
                        Comparator.nullsLast(Comparator.naturalOrder()))
        );

        return finalResults;
    }

    private static RouletteStatsDTO converteRouletteState(RouletteStats stat) {
        RouletteStatsDTO result = new RouletteStatsDTO();
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
        numbs.put(0, stat.getNum0());
        numbs.put(1, stat.getNum1());
        numbs.put(2, stat.getNum2());
        numbs.put(3, stat.getNum3());
        numbs.put(4, stat.getNum4());
        numbs.put(5, stat.getNum5());
        numbs.put(6, stat.getNum6());
        numbs.put(7, stat.getNum7());
        numbs.put(8, stat.getNum8());
        numbs.put(9, stat.getNum9());
        numbs.put(10, stat.getNum10());
        numbs.put(11, stat.getNum11());
        numbs.put(12, stat.getNum12());
        numbs.put(13, stat.getNum13());
        numbs.put(14, stat.getNum14());
        numbs.put(15, stat.getNum15());
        numbs.put(16, stat.getNum16());
        numbs.put(17, stat.getNum17());
        numbs.put(18, stat.getNum18());
        numbs.put(19, stat.getNum19());
        numbs.put(20, stat.getNum20());
        numbs.put(21, stat.getNum21());
        numbs.put(22, stat.getNum22());
        numbs.put(23, stat.getNum23());
        numbs.put(24, stat.getNum24());
        numbs.put(25, stat.getNum25());
        numbs.put(26, stat.getNum26());
        numbs.put(27, stat.getNum27());
        numbs.put(28, stat.getNum28());
        numbs.put(29, stat.getNum29());
        numbs.put(30, stat.getNum30());
        numbs.put(31, stat.getNum31());
        numbs.put(32, stat.getNum32());
        numbs.put(33, stat.getNum33());
        numbs.put(34, stat.getNum34());
        numbs.put(35, stat.getNum35());
        numbs.put(36, stat.getNum36());
        return result;
    }

    @GetMapping("/sicbo")
    public List<SicboStatsDTO> sicboStats(@RequestParam(name="from")  @DateTimeFormat(pattern = "yyyy-M-d") 
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

        // 按tableId分组
        Map<String, List<SicboStatsDTO>> groups = results.stream()
                .collect(Collectors.groupingBy(SicboStatsDTO::getTableId));

        // 创建新的结果集，包含原始数据和聚合数据
        List<SicboStatsDTO> finalResults = new ArrayList<>();

        groups.forEach((tableId, group) -> {
            // 添加原始数据
            finalResults.addAll(group);

            // 创建聚合对象
            SicboStatsDTO sum = new SicboStatsDTO();
            sum.setTableId(tableId);
            sum.setTableTitle(group.get(0).getTableTitle());
            sum.setDate(null); // 聚合对象日期为空

            // 聚合基本统计值
            sum.setTotalCount(group.stream().mapToInt(SicboStatsDTO::getTotalCount).sum());
            sum.setBigCount(group.stream().mapToInt(SicboStatsDTO::getBigCount).sum());
            sum.setSmallCount(group.stream().mapToInt(SicboStatsDTO::getSmallCount).sum());
            sum.setOddCount(group.stream().mapToInt(SicboStatsDTO::getOddCount).sum());
            sum.setEvenCount(group.stream().mapToInt(SicboStatsDTO::getEvenCount).sum());
            sum.setDot1Count(group.stream().mapToInt(SicboStatsDTO::getDot1Count).sum());
            sum.setDot2Count(group.stream().mapToInt(SicboStatsDTO::getDot2Count).sum());
            sum.setDot3Count(group.stream().mapToInt(SicboStatsDTO::getDot3Count).sum());
            sum.setDot4Count(group.stream().mapToInt(SicboStatsDTO::getDot4Count).sum());
            sum.setDot5Count(group.stream().mapToInt(SicboStatsDTO::getDot5Count).sum());
            sum.setDot6Count(group.stream().mapToInt(SicboStatsDTO::getDot6Count).sum());
            sum.setTripleCount(group.stream().mapToInt(SicboStatsDTO::getTripleCount).sum());

            // 聚合数字统计
            Map<Integer, Integer> sumNumbs = new LinkedHashMap<>();
            for (int i = 4; i <= 17; i++) {
                int finalI = i;
                int total = group.stream()
                        .mapToInt(dto -> dto.getSumNumbs().getOrDefault(finalI, 0))
                        .sum();
                sumNumbs.put(i, total);
            }
            sum.setSumNumbs(sumNumbs);

            finalResults.add(sum);
        });

        // 按tableId和date排序
        finalResults.sort(Comparator
                .comparing(SicboStatsDTO::getTableId)
                .thenComparing(SicboStatsDTO::getDate,
                        Comparator.nullsLast(Comparator.naturalOrder()))
        );

        return finalResults;
    }

    @GetMapping("/db/sicbo")
    public List<SicboStatsDTO> dbSicboStats(@RequestParam(name="from")  @DateTimeFormat(pattern = "yyyy-M-d")
                                          LocalDate fromDate, @RequestParam(name="to") @DateTimeFormat(pattern = "yyyy-M-d") LocalDate toDate) {
        List<SicboStats> stats =  dbGameResultService.queryAllSicboStats(fromDate, toDate);
        Map<Integer, DbTable> tableMap = dbTableService.getAllTables();
        List<SicboStatsDTO> results = new ArrayList<>();
        for (SicboStats stat : stats) {
            SicboStatsDTO result = new SicboStatsDTO();
            result.setTableId(stat.getTableId());
            result.setTableTitle(tableMap.get(Integer.parseInt(stat.getTableId())).getTableName());
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

        // 按tableId分组
        Map<String, List<SicboStatsDTO>> groups = results.stream()
                .collect(Collectors.groupingBy(SicboStatsDTO::getTableId));

        // 创建新的结果集，包含原始数据和聚合数据
        List<SicboStatsDTO> finalResults = new ArrayList<>();

        groups.forEach((tableId, group) -> {
            // 添加原始数据
            finalResults.addAll(group);

            // 创建聚合对象
            SicboStatsDTO sum = new SicboStatsDTO();
            sum.setTableId(tableId);
            sum.setTableTitle(group.get(0).getTableTitle());
            sum.setDate(null); // 聚合对象日期为空

            // 聚合基本统计值
            sum.setTotalCount(group.stream().mapToInt(SicboStatsDTO::getTotalCount).sum());
            sum.setBigCount(group.stream().mapToInt(SicboStatsDTO::getBigCount).sum());
            sum.setSmallCount(group.stream().mapToInt(SicboStatsDTO::getSmallCount).sum());
            sum.setOddCount(group.stream().mapToInt(SicboStatsDTO::getOddCount).sum());
            sum.setEvenCount(group.stream().mapToInt(SicboStatsDTO::getEvenCount).sum());
            sum.setDot1Count(group.stream().mapToInt(SicboStatsDTO::getDot1Count).sum());
            sum.setDot2Count(group.stream().mapToInt(SicboStatsDTO::getDot2Count).sum());
            sum.setDot3Count(group.stream().mapToInt(SicboStatsDTO::getDot3Count).sum());
            sum.setDot4Count(group.stream().mapToInt(SicboStatsDTO::getDot4Count).sum());
            sum.setDot5Count(group.stream().mapToInt(SicboStatsDTO::getDot5Count).sum());
            sum.setDot6Count(group.stream().mapToInt(SicboStatsDTO::getDot6Count).sum());
            sum.setTripleCount(group.stream().mapToInt(SicboStatsDTO::getTripleCount).sum());

            // 聚合数字统计
            Map<Integer, Integer> sumNumbs = new LinkedHashMap<>();
            for (int i = 4; i <= 17; i++) {
                int finalI = i;
                int total = group.stream()
                        .mapToInt(dto -> dto.getSumNumbs().getOrDefault(finalI, 0))
                        .sum();
                sumNumbs.put(i, total);
            }
            sum.setSumNumbs(sumNumbs);

            finalResults.add(sum);
        });

        // 按tableId和date排序
        finalResults.sort(Comparator
                .comparing(SicboStatsDTO::getTableId)
                .thenComparing(SicboStatsDTO::getDate,
                        Comparator.nullsLast(Comparator.naturalOrder()))
        );

        return finalResults;
    }

    @GetMapping("/db/color_disk")
    public List<ColorDiskStatsDTO> dbColorDiskStats(@RequestParam(name="from")  @DateTimeFormat(pattern = "yyyy-M-d")
                                                  LocalDate fromDate, @RequestParam(name="to") @DateTimeFormat(pattern = "yyyy-M-d") LocalDate toDate) {
        List<ColorDiskStats> stats =  dbGameResultService.queryAllColorDiskStats(fromDate, toDate);
        Map<Integer, DbTable> tableMap = dbTableService.getAllTables();
        List<ColorDiskStatsDTO> results = new ArrayList<>();
        for (ColorDiskStats stat : stats) {
            ColorDiskStatsDTO result = converteColorDiskState(stat);
            result.setTableTitle(tableMap.get(Integer.parseInt(stat.getTableId())).getTableName());
            result.setTableId(stat.getTableId());
            results.add(result);
        }
        Map<String, List<ColorDiskStatsDTO>> groups = results.stream().collect(Collectors.groupingBy(ColorDiskStatsDTO::getTableId));
        List<ColorDiskStatsDTO> finalResults = new ArrayList<>();
        groups.forEach((tableId, group) -> {
            // 添加原始数据
            finalResults.addAll(group);

            // 创建聚合对象
            ColorDiskStatsDTO sum = new ColorDiskStatsDTO();
            sum.setTableId(tableId);
            sum.setTableTitle(group.get(0).getTableTitle());
            sum.setDate(null); // 聚合对象日期为空

            // 聚合统计值
            sum.setTotalCount(group.stream().mapToInt(ColorDiskStatsDTO::getTotalCount).sum());
            sum.setBigCount(group.stream().mapToInt(ColorDiskStatsDTO::getBigCount).sum());
            sum.setSmallCount(group.stream().mapToInt(ColorDiskStatsDTO::getSmallCount).sum());
            sum.setOddCount(group.stream().mapToInt(ColorDiskStatsDTO::getOddCount).sum());
            sum.setEvenCount(group.stream().mapToInt(ColorDiskStatsDTO::getEvenCount).sum());
            // 聚合数字统计
            Map<Integer, Integer> sumNumbs = new LinkedHashMap<>();
            for (int i = 0; i <= 4; i++) {
                int finalI = i;
                int total = group.stream()
                        .mapToInt(dto -> dto.getNumbs().getOrDefault(finalI, 0))
                        .sum();
                sumNumbs.put(i, total);
            }
            sum.setNumbs(sumNumbs);

            finalResults.add(sum);
        });

        // 按tableId和date排序
        finalResults.sort(Comparator
                .comparing(ColorDiskStatsDTO::getTableId)
                .thenComparing(ColorDiskStatsDTO::getDate,
                        Comparator.nullsLast(Comparator.naturalOrder()))
        );

        return finalResults;
    }

    private ColorDiskStatsDTO converteColorDiskState(ColorDiskStats stat) {
        ColorDiskStatsDTO result = new ColorDiskStatsDTO();
        result.setDate(stat.getDate());
        result.setTotalCount(stat.getTotalCount());
        result.setBigCount(stat.getBigCount());
        result.setSmallCount(stat.getSmallCount());
        result.setOddCount(stat.getOddCount());
        result.setEvenCount(stat.getEvenCount());
        Map< Integer,Integer> numbs = new LinkedHashMap<>();
        result.setNumbs(numbs);
        numbs.put(0, stat.getNum0());
        numbs.put(1, stat.getNum1());
        numbs.put(2, stat.getNum2());
        numbs.put(3, stat.getNum3());
        numbs.put(4, stat.getNum4());
        return result;
    }

    @GetMapping("/roulette/latest")
    public ResponseEntity<String> rouletteLatestRecordTime() {
        Optional<LocalDateTime> latestTime = gameResultService.findLatestRouletteCreateTime();
        return latestTime.map(time -> ResponseEntity.ok(time.toString()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/sicbo/latest")
    public ResponseEntity<String> sicboLatestRecordTime() {
        Optional<LocalDateTime> latestTime = gameResultService.findLatestSicboCreateTime();
        return latestTime.map(time -> ResponseEntity.ok(time.toString()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/db/roulette/latest")
    public ResponseEntity<String> dbRouletteLatestRecordTime() {
        Optional<LocalDateTime> latestTime = dbGameResultService.findLatestRouletteCreateTime();
        return latestTime.map(time -> ResponseEntity.ok(time.toString()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/db/sicbo/latest")
    public ResponseEntity<String> dbSicboLatestRecordTime() {
        Optional<LocalDateTime> latestTime = dbGameResultService.findLatestSicboCreateTime();
        return latestTime.map(time -> ResponseEntity.ok(time.toString()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/db/color_disk/latest")
    public ResponseEntity<String> dbColorDiskLatestRecordTime() {
        Optional<LocalDateTime> latestTime = dbGameResultService.findLatestColorDiskCreateTime();
        return latestTime.map(time -> ResponseEntity.ok(time.toString()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Autowired
    private PpGameResultService ppGameResultService;

    // 新增Ezugi相关服务
    @Autowired
    private EzugiGameResultService ezugiGameResultService;
    
    @Autowired
    private EzugiTableService ezugiTableService;

    @GetMapping("/pp/roulette")
    public List<RouletteStatsDTO> ppRouletteStats(@RequestParam(name="from")  @DateTimeFormat(pattern = "yyyy-M-d")
                                                  LocalDate fromDate, @RequestParam(name="to") @DateTimeFormat(pattern = "yyyy-M-d") LocalDate toDate) {
        List<RouletteStats> stats =  ppGameResultService.queryAllRouletteStats(fromDate, toDate);
        Map<String, PpTable> tableMap = ppTableService.getAllTables();
        List<RouletteStatsDTO> results = new ArrayList<>();
        for (RouletteStats stat : stats) {
            RouletteStatsDTO result = converteRouletteState(stat);
            PpTable table = tableMap.get(stat.getTableId());
            result.setTableTitle(table==null?"":table.getTableName());
            result.setTableId(stat.getTableId());
            results.add(result);
        }
        Map<String, List<RouletteStatsDTO>> groups = results.stream().collect(Collectors.groupingBy(RouletteStatsDTO::getTableId));
        List<RouletteStatsDTO> finalResults = new ArrayList<>();
        groups.forEach((tableId, group) -> {
            // 添加原始数据
            finalResults.addAll(group);

            // 创建聚合对象
            RouletteStatsDTO sum = new RouletteStatsDTO();
            sum.setTableId(tableId);
            sum.setTableTitle(group.get(0).getTableTitle());
            sum.setDate(null); // 聚合对象日期为空

            // 聚合统计值
            sum.setTotalCount(group.stream().mapToInt(RouletteStatsDTO::getTotalCount).sum());
            sum.setBigCount(group.stream().mapToInt(RouletteStatsDTO::getBigCount).sum());
            sum.setSmallCount(group.stream().mapToInt(RouletteStatsDTO::getSmallCount).sum());
            sum.setOddCount(group.stream().mapToInt(RouletteStatsDTO::getOddCount).sum());
            sum.setEvenCount(group.stream().mapToInt(RouletteStatsDTO::getEvenCount).sum());
            sum.setRedCount(group.stream().mapToInt(RouletteStatsDTO::getRedCount).sum());
            sum.setBlackCount(group.stream().mapToInt(RouletteStatsDTO::getBlackCount).sum());
            sum.setSection1_12(group.stream().mapToInt(RouletteStatsDTO::getSection1_12).sum());
            sum.setSection13_24(group.stream().mapToInt(RouletteStatsDTO::getSection13_24).sum());
            sum.setSection25_36(group.stream().mapToInt(RouletteStatsDTO::getSection25_36).sum());

            // 聚合数字统计
            Map<Integer, Integer> sumNumbs = new LinkedHashMap<>();
            for (int i = 0; i <= 36; i++) {
                int finalI = i;
                int total = group.stream()
                        .mapToInt(dto -> dto.getNumbs().getOrDefault(finalI, 0))
                        .sum();
                sumNumbs.put(i, total);
            }
            sum.setNumbs(sumNumbs);

            finalResults.add(sum);
        });

        // 按tableId和date排序
        finalResults.sort(Comparator
                .comparing(RouletteStatsDTO::getTableId)
                .thenComparing(RouletteStatsDTO::getDate,
                        Comparator.nullsLast(Comparator.naturalOrder()))
        );

        return finalResults;
    }

    @GetMapping("/ezugi/roulette")
    public List<RouletteStatsDTO> ezugiRouletteStats(@RequestParam(name="from")  @DateTimeFormat(pattern = "yyyy-M-d")
                                                  LocalDate fromDate, @RequestParam(name="to") @DateTimeFormat(pattern = "yyyy-M-d") LocalDate toDate) {
        List<RouletteStats> stats =  ezugiGameResultService.queryAllRouletteStats(fromDate, toDate);
        Map<String, EzugiTable> tableMap = ezugiTableService.getAllTables();
        List<RouletteStatsDTO> results = new ArrayList<>();
        for (RouletteStats stat : stats) {
            RouletteStatsDTO result = converteRouletteState(stat);
            EzugiTable table = tableMap.get(stat.getTableId());
            result.setTableTitle(table==null?"":table.getTableName());
            result.setTableId(stat.getTableId());
            results.add(result);
        }
        Map<String, List<RouletteStatsDTO>> groups = results.stream().collect(Collectors.groupingBy(RouletteStatsDTO::getTableId));
        List<RouletteStatsDTO> finalResults = new ArrayList<>();
        groups.forEach((tableId, group) -> {
            // 添加原始数据
            finalResults.addAll(group);

            // 创建聚合对象
            RouletteStatsDTO sum = new RouletteStatsDTO();
            sum.setTableId(tableId);
            sum.setTableTitle(group.get(0).getTableTitle());
            sum.setDate(null); // 聚合对象日期为空

            // 聚合统计值
            sum.setTotalCount(group.stream().mapToInt(RouletteStatsDTO::getTotalCount).sum());
            sum.setBigCount(group.stream().mapToInt(RouletteStatsDTO::getBigCount).sum());
            sum.setSmallCount(group.stream().mapToInt(RouletteStatsDTO::getSmallCount).sum());
            sum.setOddCount(group.stream().mapToInt(RouletteStatsDTO::getOddCount).sum());
            sum.setEvenCount(group.stream().mapToInt(RouletteStatsDTO::getEvenCount).sum());
            sum.setRedCount(group.stream().mapToInt(RouletteStatsDTO::getRedCount).sum());
            sum.setBlackCount(group.stream().mapToInt(RouletteStatsDTO::getBlackCount).sum());
            sum.setSection1_12(group.stream().mapToInt(RouletteStatsDTO::getSection1_12).sum());
            sum.setSection13_24(group.stream().mapToInt(RouletteStatsDTO::getSection13_24).sum());
            sum.setSection25_36(group.stream().mapToInt(RouletteStatsDTO::getSection25_36).sum());

            // 聚合数字统计
            Map<Integer, Integer> sumNumbs = new LinkedHashMap<>();
            for (int i = 0; i <= 36; i++) {
                int finalI = i;
                int total = group.stream()
                        .mapToInt(dto -> dto.getNumbs().getOrDefault(finalI, 0))
                        .sum();
                sumNumbs.put(i, total);
            }
            sum.setNumbs(sumNumbs);

            finalResults.add(sum);
        });

        // 按tableId和date排序
        finalResults.sort(Comparator
                .comparing(RouletteStatsDTO::getTableId)
                .thenComparing(RouletteStatsDTO::getDate,
                        Comparator.nullsLast(Comparator.naturalOrder()))
        );

        return finalResults;
    }

    @GetMapping("/pp/sicbo")
    public List<SicboStatsDTO> ppSicboStats(@RequestParam(name="from")  @DateTimeFormat(pattern = "yyyy-M-d")
                                            LocalDate fromDate, @RequestParam(name="to") @DateTimeFormat(pattern = "yyyy-M-d") LocalDate toDate) {
        List<SicboStats> stats =  ppGameResultService.queryAllSicboStats(fromDate, toDate);
        Map<String, PpTable> tableMap = ppTableService.getAllTables();
        List<SicboStatsDTO> results = new ArrayList<>();
        for (SicboStats stat : stats) {
            SicboStatsDTO result = new SicboStatsDTO();
            result.setTableId(stat.getTableId());
            PpTable table = tableMap.get(stat.getTableId());
            result.setTableTitle(table==null?"":table.getTableName());
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

        // 按tableId分组
        Map<String, List<SicboStatsDTO>> groups = results.stream()
                .collect(Collectors.groupingBy(SicboStatsDTO::getTableId));

        // 创建新的结果集，包含原始数据和聚合数据
        List<SicboStatsDTO> finalResults = new ArrayList<>();

        groups.forEach((tableId, group) -> {
            // 添加原始数据
            finalResults.addAll(group);

            // 创建聚合对象
            SicboStatsDTO sum = new SicboStatsDTO();
            sum.setTableId(tableId);
            sum.setTableTitle(group.get(0).getTableTitle());
            sum.setDate(null); // 聚合对象日期为空

            // 聚合基本统计值
            sum.setTotalCount(group.stream().mapToInt(SicboStatsDTO::getTotalCount).sum());
            sum.setBigCount(group.stream().mapToInt(SicboStatsDTO::getBigCount).sum());
            sum.setSmallCount(group.stream().mapToInt(SicboStatsDTO::getSmallCount).sum());
            sum.setOddCount(group.stream().mapToInt(SicboStatsDTO::getOddCount).sum());
            sum.setEvenCount(group.stream().mapToInt(SicboStatsDTO::getEvenCount).sum());
            sum.setDot1Count(group.stream().mapToInt(SicboStatsDTO::getDot1Count).sum());
            sum.setDot2Count(group.stream().mapToInt(SicboStatsDTO::getDot2Count).sum());
            sum.setDot3Count(group.stream().mapToInt(SicboStatsDTO::getDot3Count).sum());
            sum.setDot4Count(group.stream().mapToInt(SicboStatsDTO::getDot4Count).sum());
            sum.setDot5Count(group.stream().mapToInt(SicboStatsDTO::getDot5Count).sum());
            sum.setDot6Count(group.stream().mapToInt(SicboStatsDTO::getDot6Count).sum());
            sum.setTripleCount(group.stream().mapToInt(SicboStatsDTO::getTripleCount).sum());

            // 聚合数字统计
            Map<Integer, Integer> sumNumbs = new LinkedHashMap<>();
            for (int i = 4; i <= 17; i++) {
                int finalI = i;
                int total = group.stream()
                        .mapToInt(dto -> dto.getSumNumbs().getOrDefault(finalI, 0))
                        .sum();
                sumNumbs.put(i, total);
            }
            sum.setSumNumbs(sumNumbs);

            finalResults.add(sum);
        });

        // 按tableId和date排序
        finalResults.sort(Comparator
                .comparing(SicboStatsDTO::getTableId)
                .thenComparing(SicboStatsDTO::getDate,
                        Comparator.nullsLast(Comparator.naturalOrder()))
        );

        return finalResults;
    }

    @GetMapping("/ezugi/sicbo")
    public List<SicboStatsDTO> ezugiSicboStats(@RequestParam(name="from")  @DateTimeFormat(pattern = "yyyy-M-d")
                                            LocalDate fromDate, @RequestParam(name="to") @DateTimeFormat(pattern = "yyyy-M-d") LocalDate toDate) {
        List<SicboStats> stats =  ezugiGameResultService.queryAllSicboStats(fromDate, toDate);
        Map<String, EzugiTable> tableMap = ezugiTableService.getAllTables();
        List<SicboStatsDTO> results = new ArrayList<>();
        for (SicboStats stat : stats) {
            SicboStatsDTO result = new SicboStatsDTO();
            result.setTableId(stat.getTableId());
            EzugiTable table = tableMap.get(stat.getTableId());
            result.setTableTitle(table==null?"":table.getTableName());
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

        // 按tableId分组
        Map<String, List<SicboStatsDTO>> groups = results.stream()
                .collect(Collectors.groupingBy(SicboStatsDTO::getTableId));

        // 创建新的结果集，包含原始数据和聚合数据
        List<SicboStatsDTO> finalResults = new ArrayList<>();

        groups.forEach((tableId, group) -> {
            // 添加原始数据
            finalResults.addAll(group);

            // 创建聚合对象
            SicboStatsDTO sum = new SicboStatsDTO();
            sum.setTableId(tableId);
            sum.setTableTitle(group.get(0).getTableTitle());
            sum.setDate(null); // 聚合对象日期为空

            // 聚合基本统计值
            sum.setTotalCount(group.stream().mapToInt(SicboStatsDTO::getTotalCount).sum());
            sum.setBigCount(group.stream().mapToInt(SicboStatsDTO::getBigCount).sum());
            sum.setSmallCount(group.stream().mapToInt(SicboStatsDTO::getSmallCount).sum());
            sum.setOddCount(group.stream().mapToInt(SicboStatsDTO::getOddCount).sum());
            sum.setEvenCount(group.stream().mapToInt(SicboStatsDTO::getEvenCount).sum());
            sum.setDot1Count(group.stream().mapToInt(SicboStatsDTO::getDot1Count).sum());
            sum.setDot2Count(group.stream().mapToInt(SicboStatsDTO::getDot2Count).sum());
            sum.setDot3Count(group.stream().mapToInt(SicboStatsDTO::getDot3Count).sum());
            sum.setDot4Count(group.stream().mapToInt(SicboStatsDTO::getDot4Count).sum());
            sum.setDot5Count(group.stream().mapToInt(SicboStatsDTO::getDot5Count).sum());
            sum.setDot6Count(group.stream().mapToInt(SicboStatsDTO::getDot6Count).sum());
            sum.setTripleCount(group.stream().mapToInt(SicboStatsDTO::getTripleCount).sum());

            // 聚合数字统计
            Map<Integer, Integer> sumNumbs = new LinkedHashMap<>();
            for (int i = 4; i <= 17; i++) {
                int finalI = i;
                int total = group.stream()
                        .mapToInt(dto -> dto.getSumNumbs().getOrDefault(finalI, 0))
                        .sum();
                sumNumbs.put(i, total);
            }
            sum.setSumNumbs(sumNumbs);

            finalResults.add(sum);
        });

        // 按tableId和date排序
        finalResults.sort(Comparator
                .comparing(SicboStatsDTO::getTableId)
                .thenComparing(SicboStatsDTO::getDate,
                        Comparator.nullsLast(Comparator.naturalOrder()))
        );

        return finalResults;
    }

    @GetMapping("/pp/roulette/latest")
    public ResponseEntity<String> ppRouletteLatestRecordTime() {
        Optional<LocalDateTime> latestTime = ppGameResultService.findLatestRouletteCreateTime();
        return latestTime.map(time -> ResponseEntity.ok(time.toString()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/pp/sicbo/latest")
    public ResponseEntity<String> ppSicboLatestRecordTime() {
        Optional<LocalDateTime> latestTime = ppGameResultService.findLatestSicboCreateTime();
        return latestTime.map(time -> ResponseEntity.ok(time.toString()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/ezugi/roulette/latest")
    public ResponseEntity<String> ezugiRouletteLatestRecordTime() {
        Optional<LocalDateTime> latestTime = ezugiGameResultService.findLatestRouletteCreateTime();
        return latestTime.map(time -> ResponseEntity.ok(time.toString()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/ezugi/sicbo/latest")
    public ResponseEntity<String> ezugiSicboLatestRecordTime() {
        Optional<LocalDateTime> latestTime = ezugiGameResultService.findLatestSicboCreateTime();
        return latestTime.map(time -> ResponseEntity.ok(time.toString()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/ezugi/color_disk")
    public List<ColorDiskStatsDTO> ezugiColorDiskStats(@RequestParam(name="from")  @DateTimeFormat(pattern = "yyyy-M-d")
                                                       LocalDate fromDate, @RequestParam(name="to") @DateTimeFormat(pattern = "yyyy-M-d") LocalDate toDate) {
        List<ColorDiskStats> stats =  ezugiGameResultService.queryAllColorDiskStats(fromDate, toDate);
        Map<String, EzugiTable> tableMap = ezugiTableService.getAllTables();
        List<ColorDiskStatsDTO> results = new ArrayList<>();
        for (ColorDiskStats stat : stats) {
            ColorDiskStatsDTO result = converteColorDiskState(stat);
            EzugiTable table = tableMap.get(stat.getTableId());
            result.setTableTitle(table==null?"":table.getTableName());
            result.setTableId(stat.getTableId());
            results.add(result);
        }
        Map<String, List<ColorDiskStatsDTO>> groups = results.stream().collect(Collectors.groupingBy(ColorDiskStatsDTO::getTableId));
        List<ColorDiskStatsDTO> finalResults = new ArrayList<>();
        groups.forEach((tableId, group) -> {
            // 添加原始数据
            finalResults.addAll(group);

            // 创建聚合对象
            ColorDiskStatsDTO sum = new ColorDiskStatsDTO();
            sum.setTableId(tableId);
            sum.setTableTitle(group.get(0).getTableTitle());
            sum.setDate(null); // 聚合对象日期为空

            // 聚合统计值
            sum.setTotalCount(group.stream().mapToInt(ColorDiskStatsDTO::getTotalCount).sum());
            sum.setBigCount(group.stream().mapToInt(ColorDiskStatsDTO::getBigCount).sum());
            sum.setSmallCount(group.stream().mapToInt(ColorDiskStatsDTO::getSmallCount).sum());
            sum.setOddCount(group.stream().mapToInt(ColorDiskStatsDTO::getOddCount).sum());
            sum.setEvenCount(group.stream().mapToInt(ColorDiskStatsDTO::getEvenCount).sum());
            // 聚合数字统计
            Map<Integer, Integer> sumNumbs = new LinkedHashMap<>();
            for (int i = 0; i <= 4; i++) {
                int finalI = i;
                int total = group.stream()
                        .mapToInt(dto -> dto.getNumbs().getOrDefault(finalI, 0))
                        .sum();
                sumNumbs.put(i, total);
            }
            sum.setNumbs(sumNumbs);

            finalResults.add(sum);
        });

        // 按tableId和date排序
        finalResults.sort(Comparator
                .comparing(ColorDiskStatsDTO::getTableId)
                .thenComparing(ColorDiskStatsDTO::getDate,
                        Comparator.nullsLast(Comparator.naturalOrder()))
        );

        return finalResults;
    }


    @GetMapping("/ezugi/color_disk/latest")
    public ResponseEntity<String> ezugiColorDiskLatestRecordTime() {
        Optional<LocalDateTime> latestTime = ezugiGameResultService.findLatestColorDiskCreateTime();
        return latestTime.map(time -> ResponseEntity.ok(time.toString()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}