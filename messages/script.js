class Ps extends Za {
    parseBoot(e) {
        if (this.row = 6,
        !e)
            return;
        const t = new rs(e);
        this.version = t.getNextInteger(8);
        let a = t.getNextInteger(8) * t.getNextInteger(8)
          , s = null;
        for (; a > 0; ) {
            const e = this.parseSingle(t);
            (null == s || s.length >= 6) && (s = new Array,
            this._list.push(s)),
            s.push(e),
            a--
        }
        const i = new rs(e);
        this.version = i.getNextInteger(8);
        let n = i.getNextInteger(8) * i.getNextInteger(8)
          , l = null;
        for (; n > 0; ) {
            const e = this.parseSingle(i);
            (null == l || l.length >= 3) && (l = new Array,
            this._list3.push(l)),
            l.push(e),
            n--
        }
        const o = this._list3[this._list3.length - 1];
        o[0] || o[1] || o[2] || this._list3.pop()
    }
    parseRound(e) {
        if (!e)
            return;
        const t = new rs(e)
          , a = t.getNextInteger(8)
          , s = this.version - a == -1;
        if (!s)
            return s;
        this.version = a;
        const i = t.getNextInteger(8)
          , n = t.getNextInteger(8)
          , l = this.parseSingle(t);
        let o = this._list[n];
        return o || (o = new Array,
        this._list[n] = o),
        o[i] = l,
        s
    }
    parseSingle(e) {
        const t = 1 != e.getNextInteger(1)
          , a = e.getNextInteger(6);
        return t ? "" : za.rouletteWinNumberCharList[a]
    }
}

class rs {
    constructor(e) {
        i(this, "_bitString"),
        i(this, "_pointer", 0),
        i(this, "_length", 0),
        i(this, "_stringBuilder"),
        this._stringBuilder = new ns;
        const t = es.decode(e);
        this._bitString = this.getBits(t),
        this._length = this._bitString.length
    }
    get length() {
        return this._length
    }
    getBits(e) {
        const t = new is(e)
          , a = t.bytesAvailable;
        for (let s = 0; s < a; s++) {
            const e = t.bytes[s];
            let a;
            os.has(e) ? a = os.get(e) : (a = e.toString(2),
            os.set(e, a)),
            8 - a.length > 0 && this._stringBuilder.append(ls[8 - a.length - 1]),
            this._stringBuilder.append(a)
        }
        return this._stringBuilder.toString()
    }
    getNextInteger(e) {
        const t = parseInt(this._bitString.substr(this._pointer, e), 2);
        return this._pointer += e,
        t
    }
}
var e, t, a, s = Object.defineProperty, i = (e, t, a) => (( (e, t, a) => {
    t in e ? s(e, t, {
        enumerable: !0,
        configurable: !0,
        writable: !0,
        value: a
    }) : e[t] = a
}
)(e, "symbol" != typeof t ? t + "" : t, a),
a);

class Za {
    constructor() {
        i(this, "_gameType", $e.NONE),
        i(this, "_roadPaperType", la.MAIN_ROAD),
        i(this, "_column", 0),
        i(this, "_row", 0),
        i(this, "_version", 0),
        i(this, "_list", new Array),
        i(this, "_list3", new Array)
    }
    set gameType(e) {
        this._gameType = e
    }
    get gameType() {
        return this._gameType
    }
    set roadPaperType(e) {
        this._roadPaperType = e
    }
    get roadPaperType() {
        return this._roadPaperType
    }
    set column(e) {
        this._column = e
    }
    get column() {
        return this._column
    }
    set row(e) {
        this._row = e
    }
    get row() {
        return this._row
    }
    get version() {
        return this._version
    }
    set version(e) {
        this._version = e
    }
    get list() {
        return this._list
    }
    destroy() {}
    clear() {
        this._list3 = new Array,
        this._list = new Array,
        this._list3 = new Array,
        this.version = 0,
        this.row = 0,
        this.column = 0
    }
    toString(e) {
        let t = 0
          , a = 0;
        this._list.length < e ? a = e - this._list.length : (t = this._list.length - e + 1,
        a++);
        let s = "";
        for (let i = 0; i < this.row; ++i) {
            for (let e = t; e < this._list.length + a; ++e) {
                const t = this._list[e];
                s += null == t || null == t ? "" : t[i] ? t[i] : ""
            }
            s += "\n"
        }
        return s
    }
    toStringRow3(e) {
        let t = 0
          , a = 0;
        this._list3.length < e ? a = e - this._list3.length : (t = this._list3.length - e + 1,
        a++);
        let s = "";
        for (let i = 0; i < this.row; ++i) {
            for (let e = t; e < this._list3.length + a; ++e) {
                const t = this._list3[e];
                s += null == t || null == t ? "" : t[i] ? t[i] : ""
            }
            s += "\n"
        }
        return s
    }
    toStringSicbo3(e) {
        let t = 0
          , a = 0;
        this._list.length < e ? a = e - this._list.length : (t = this._list.length - e + 1,
        a++);
        let s = "";
        for (let i = 3; i < 6; ++i) {
            for (let e = t; e < this._list.length + a; ++e) {
                const t = this._list[e];
                s += null == t || null == t ? "" : t[i] ? t[i] : ""
            }
            s += "\n"
        }
        return s
    }
}

class Za {
    constructor() {
        i(this, "_gameType", $e.NONE),
        i(this, "_roadPaperType", la.MAIN_ROAD),
        i(this, "_column", 0),
        i(this, "_row", 0),
        i(this, "_version", 0),
        i(this, "_list", new Array),
        i(this, "_list3", new Array)
    }
    set gameType(e) {
        this._gameType = e
    }
    get gameType() {
        return this._gameType
    }
    set roadPaperType(e) {
        this._roadPaperType = e
    }
    get roadPaperType() {
        return this._roadPaperType
    }
    set column(e) {
        this._column = e
    }
    get column() {
        return this._column
    }
    set row(e) {
        this._row = e
    }
    get row() {
        return this._row
    }
    get version() {
        return this._version
    }
    set version(e) {
        this._version = e
    }
    get list() {
        return this._list
    }
    destroy() {}
    clear() {
        this._list3 = new Array,
        this._list = new Array,
        this._list3 = new Array,
        this.version = 0,
        this.row = 0,
        this.column = 0
    }
    toString(e) {
        let t = 0
          , a = 0;
        this._list.length < e ? a = e - this._list.length : (t = this._list.length - e + 1,
        a++);
        let s = "";
        for (let i = 0; i < this.row; ++i) {
            for (let e = t; e < this._list.length + a; ++e) {
                const t = this._list[e];
                s += null == t || null == t ? "" : t[i] ? t[i] : ""
            }
            s += "\n"
        }
        return s
    }
    toStringRow3(e) {
        let t = 0
          , a = 0;
        this._list3.length < e ? a = e - this._list3.length : (t = this._list3.length - e + 1,
        a++);
        let s = "";
        for (let i = 0; i < this.row; ++i) {
            for (let e = t; e < this._list3.length + a; ++e) {
                const t = this._list3[e];
                s += null == t || null == t ? "" : t[i] ? t[i] : ""
            }
            s += "\n"
        }
        return s
    }
    toStringSicbo3(e) {
        let t = 0
          , a = 0;
        this._list.length < e ? a = e - this._list.length : (t = this._list.length - e + 1,
        a++);
        let s = "";
        for (let i = 3; i < 6; ++i) {
            for (let e = t; e < this._list.length + a; ++e) {
                const t = this._list[e];
                s += null == t || null == t ? "" : t[i] ? t[i] : ""
            }
            s += "\n"
        }
        return s
    }
}
const Ya = class e {
    static getBullFightResult(e, t, a) {
        return a ? e ? this.fullFightProRedList[t] : this.fullFightProBlueList[t] : this.fullFightProGrayList[t]
    }
    static getPaiGowResult(e, t, a) {
        return a ? e ? this.paiGowRedList[t] : this.paiGowBlueList[t] : this.paiGowGrayList[t]
    }
    static getThreeTrumpsResult(e, t, a) {
        switch (e) {
        case 0:
            return this.zeroTrumpList[a][t];
        case 1:
            return this.oneTrumpList[a][t];
        case 2:
            return this.twoTrumpList[a][t];
        case 3:
            return this.threeTrumpList[a]
        }
        return ""
    }
    static discIsBigSmallTie(e) {
        return this.discBig[4] == e || this.discBig[3] == e
    }
    static getSicboResultChar(e, t) {
        switch (e) {
        case ha.POINT:
            return this.sicboDicedPointList[t];
        case ha.NUMBER:
            return this.sicboNumberList[t];
        case ha.TOTAL_POINT:
            return this.sicboTotalNumberList[t];
        case ha.BIG_SMALL:
            return this.sicboBigSmallList[t];
        case ha.ODD_EVEN:
            return this.sicboOddEvenList[t]
        }
        return ""
    }
    static getDiscResultChar(e, t, a) {
        switch (e) {
        case Ia.ZHUPAN:
            return this.discZhupan[t];
        case Ia.BIG_SMALL:
            return t < 2 && a && a > 0 ? this.discBig[t + 3] : this.discBig[t];
        case Ia.SINGLE_DOUBLE:
            return this.discSingle[t];
        case Ia.NUMBER:
            return this.discNumber[t]
        }
        return ""
    }
    static getFanTanResultChar(e, t) {
        switch (e) {
        case _a.NUMBER:
            return this.fantanNumberList[t];
        case _a.ODD_EVEN:
            return this.fantanOddEvenList[t]
        }
        return ""
    }
    static getBacaratRoadPaperEmpty(t) {
        return e.emptyList[t]
    }
    static getIndiaResultChar(e, t, a) {
        return {
            result: this.indiaZhupan[4 * e + t],
            redResult: a ? "心" : "行"
        }
    }
    static getIndiaBigSmallResultChar(e, t) {
        return ""
    }
    static getGame3DResultChar(e, t) {
        switch (e) {
        case ua.BEAT_PLATE:
            return this.game3DNumberList[t + ""];
        case ua.BAI_BIG_SMALL:
        case ua.SHI_BIG_SMALL:
        case ua.GE_BIG_SMALL:
            return this.game3DBigSmallList[t];
        case ua.BAI_SING_DOUBLE:
        case ua.SHI_SING_DOUBLE:
        case ua.GE_SING_DOUBLE:
            return this.game3DOddEvenList[t]
        }
        return ""
    }
    static getGame5DResultChar(e, t) {
        switch (e) {
        case Ca.BEAT_PLATE:
            return this.game3DNumberList[t];
        case Ca.WAN_BIG_SMALL:
        case Ca.QIAN_BIG_SMALL:
        case Ca.BAI_BIG_SMALL:
        case Ca.SHI_BIG_SMALL:
        case Ca.GE_BIG_SMALL:
            return this.game3DBigSmallList[t];
        case Ca.WAN_SING_DOUBLE:
        case Ca.QIAN_SING_DOUBLE:
        case Ca.BAI_SING_DOUBLE:
        case Ca.SHI_SING_DOUBLE:
        case Ca.GE_SING_DOUBLE:
            return this.game3DOddEvenList[t]
        }
        return ""
    }
    static getDollyResult(e, t, a) {
        return a ? e ? this.dollyRedList[t - 1] : this.dollyBlueList[t - 1] : this.dollyGrayList[t - 1]
    }
}
;
i(Ya, "baccaratAskRoad", ["装", "闲"]),
i(Ya, "baccaratDWAskRoad", ["庄", "闲"]),
i(Ya, "dragonTigerAskRoad", ["龙", "虎"]),
i(Ya, "winThreeCardsAskRoad", ["风", "弄"]),
i(Ya, "mainRoadBankerWin", ["装", "妆", "撞", "庄"]),
i(Ya, "mainRoadPlayerWin", ["闲", "现", "县", "线"]),
i(Ya, "mainRoadBankerSix", ["壮", "狀", "戆", "僮"]),
i(Ya, "mainRoadBankerSuperSix", ["壮2", "狀2", "戆2", "僮2"]),
i(Ya, "mainRoadBankerSeven", ["憩", "企", "器", "起"]),
i(Ya, "mainRoadBankerSuperSeven", ["旗", "气", "其", "齐"]),
i(Ya, "mainRoadTie", ["合", "河", "荷", "和"]),
i(Ya, "mainRoadOtherList", ["龙", "虎", "弄", "虎", "风"]),
i(Ya, "dragonRoadBankerSevenList", ["dbSeven一", "dbSeven三", "dbSeven二", "dbSeven四"]),
i(Ya, "dragonRoadBankerSuperSevenList", ["dbSuperSeven一", "dbSuperSeven三", "dbSuperSeven二", "dbSuperSeven四"]),
i(Ya, "dragonRoadBankerWinList", ["龙", "隆", "拢", "珑", "庄", "装", "撞", "状"]),
i(Ya, "dragonRoadBankerSixList", ["流", "六", "留", "扭"]),
i(Ya, "dragonRoadBankerSuperSixList", ["流2", "六2", "留2", "扭2"]),
i(Ya, "dragonRoadPlayerWinList", ["聋", "农", "浓", "侬", "闲", "先", "嫌", "线"]),
i(Ya, "dragonRoadPlayerTieList", ["合", "喝", "荷", "何"]),
i(Ya, "dragonRoadDragonVsTaigerWinList", ["bd1", "bd2", "bt1", "bt2", "bdt"]),
i(Ya, "dragonRoadDragonVsBigList", [["d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "d10", "d11", "d12", "d13", "d14", "d15", "d16"], ["bd1", "bd2", "bd3", "bd4", "bd5", "bd6", "bd7", "bd8", "bd9", "bd10", "bd11", "bd12", "bd13", "bd14", "bd15", "bd16"], ["t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8", "t9", "t10", "t11", "t12", "t13", "t14", "t15", "t16"], ["bt1", "bt2", "bt3", "bt4", "bt5", "bt6", "bt7", "bt8", "bt9", "bt10", "bt11", "bt12", "bt13", "bt14", "bt15", "bt16"], ["dt1", "dt2", "dt3", "dt4", "dt5", "dt6", "dt7", "dt8", "dt9", "dt10", "dt11", "dt12", "dt13", "dt14", "dt15", "dt16"]]),
i(Ya, "winPointRoadBankerWinList", ["鳞亿而闪式物流器把酒食益铒馓", "磷以尔山师吴六气爸久", "邻意儿删试午留起吧就", "凛翼饵伞四屋扭琪捌纠"]),
i(Ya, "winPointRoadPlayerWinList", ["麟一贰善思舞溜期罢救湿易洱霰", "霖亦耳扇寺五柳骑巴旧", "淋伊二擅诗吾刘其八九", "令咦鲕三斯武硫妻霸玖"]),
i(Ya, "winPointRoadTieList", ["琳依鸸杉室误妞弃芭鸠尸艺尓毵", "林宜洏散是乌牛棋扒韭", "临义佴潵石无瘤启拔揪", "零衣迩衫使伍纽鳍疤究"]),
i(Ya, "bigSmallRoadBigList", ["大", "打", "搭", "答"]),
i(Ya, "bigSmallRoadSmallList", ["小", "笑", "萧", "肖"]),
i(Ya, "zhisha", ["百", "杀"]),
i(Ya, "lightningMainRoad", ["桩", "显", "禾"]),
i(Ya, "lightningBigRoad", ["摆", "殿", "颠", "店", "垫"]),
i(Ya, "bigRoadBankerWin", [["骝", "锈", "绣", "秀"], ["留", "裤", "窟", "齁"], ["刘", "库", "鎏", "琉"], ["流", "旒", "继", "疁"], ["柳", "果", "裹", "过"], ["溜", "节", "界", "接"], ["瘤", "母", "体", "木"], ["硫", "围", "喂", "未"], ["陆", "茨", "慈", "次"], ["遛", "紫", "兹", "自"], ["馏", "哒", "嗒", "大"], ["瑠", "血", "邪", "写"], ["绺", "客", "渴", "刻"], ["锍", "葛", "格", "革"], ["镏", "即", "级", "基"], ["旈", "活", "奤", "祸"]]),
i(Ya, "bigRoadBankerSixWin", [["鲅", "蔻", "硁", "徎"], ["蕖", "刳", "鸲", "秸"], ["钚", "湫", "莒", "圪"], ["桀", "莙", "洸", "窖"], ["钯", "猓", "硌", "趏"], ["俐", "嗟", "墂", "桦"], ["揆", "焜", "赦", "钰"], ["炯", "泂", "迦", "桧"], ["鳍", "只", "姬", "指"], ["焗", "蛰", "瑰", "噷"], ["狃", "舵", "陀", "怼"], ["伽", "沫", "溴", "敷"], ["绯", "窠", "鲲", "远"], ["稗", "峻", "氪", "迁"], ["钧", "剀", "噱", "倩"], ["铂", "骅", "劼", "苑"]]),
i(Ya, "bigRoadPlayerWin", [["或", "羞", "休", "修"], ["先", "县", "线", "仙"], ["含", "酷", "哭", "焊"], ["寒", "翰", "撼", "涵"], ["韩", "国", "郭", "锅"], ["汉", "借", "阶", "截"], ["真", "姆", "提", "目"], ["帧", "位", "维", "为"], ["还", "词", "刺", "此"], ["海", "子", "仔", "字"], ["害", "答", "搭", "达"], ["嗨", "谢", "鞋", "些"], ["咳", "珂", "课", "壳"], ["耗", "阁", "戈", "镉"], ["亥", "机", "集", "鸡"], ["伤", "货", "蝦", "霍"]]),
i(Ya, "bigRoadTie", [["一", "亦", "已", "以"], ["二", "而", "儿", "尔"], ["三", "潵", "帴", "糁"], ["四", "蝈", "帼", "椁"], ["五", "皆", "介", "杰"], ["六", "替", "题", "踢"], ["七", "威", "韦", "魏"], ["八", "玼", "赐", "辞"], ["九", "姿", "梓", "资"], ["十", "笪", "怛", "妲"], ["幺", "蟹", "协", "斜"], ["哈", "可", "克", "科"], ["黑", "个", "哥", "歌"], ["红", "及", "急", "己"], ["打", "蛤", "铪", "虾"]]),
i(Ya, "bigRoadBankerSeven", [["seven一", "seven三", "seven二", "seven四"], ["seven五", "seven七", "seven六", "seven八"], ["seven九", "seven十一", "seven十", "seven十二"], ["seven十三", "seven十五", "seven十四", "seven十六"], ["seven十七", "seven十九", "seven十八", "seven二十"], ["seven二十一", "seven二十三", "seven二十二", "seven二十四"], ["seven二十五", "seven二十七", "seven二十六", "seven二十八"], ["seven二十九", "seven三十一", "seven三十", "seven三十二"], ["seven三十三", "seven三十五", "seven三十四", "seven三十六"], ["seven三十七", "seven三十九", "seven三十八", "seven四十"], ["seven四十一", "seven四十三", "seven四十二", "seven四十四"], ["seven四十五", "seven四十七", "seven四十六", "seven四十八"], ["seven四十九", "seven五十一", "seven五十", "seven五十二"], ["seven五十一", "seven五十三", "seven五十二", "seven五十四"], ["seven五十五", "seven五十七", "seven五十六", "seven五十八"], ["seven五十九", "seven六十一", "seven六十", "seven六十二"]]),
i(Ya, "bigRoadBankerSuperSeven", [["superSeven一", "superSeven三", "superSeven二", "superSeven四"], ["superSeven五", "superSeven七", "superSeven六", "superSeven八"], ["superSeven九", "superSeven十一", "superSeven十", "superSeven十二"], ["superSeven十三", "superSeven十五", "superSeven十四", "superSeven十六"], ["superSeven十七", "superSeven十九", "superSeven十八", "superSeven二十"], ["superSeven二十一", "superSeven二十三", "superSeven二十二", "superSeven二十四"], ["superSeven二十五", "superSeven二十七", "superSeven二十六", "superSeven二十八"], ["superSeven二十九", "superSeven三十一", "superSeven三十", "superSeven三十二"], ["superSeven三十三", "superSeven三十五", "superSeven三十四", "superSeven三十六"], ["superSeven三十七", "superSeven三十九", "superSeven三十八", "superSeven四十"], ["superSeven四十一", "superSeven四十三", "superSeven四十二", "superSeven四十四"], ["superSeven四十五", "superSeven四十七", "superSeven四十六", "superSeven四十八"], ["superSeven四十九", "superSeven五十一", "superSeven五十", "superSeven五十二"], ["superSeven五十一", "superSeven五十三", "superSeven五十二", "superSeven五十四"], ["superSeven五十五", "superSeven五十七", "superSeven五十六", "superSeven五十八"], ["superSeven五十九", "superSeven六十一", "superSeven六十", "superSeven六十二"]]),
i(Ya, "bigRoadBankerSix", [["six一", "six三", "six二", "six四"], ["six五", "six七", "six六", "six八"], ["six九", "six十一", "six十", "six十二"], ["six十三", "six十五", "six十四", "six十六"], ["six十七", "six十九", "six十八", "six二十"], ["six二十一", "six二十三", "six二十二", "six二十四"], ["six二十五", "six二十七", "six二十六", "six二十八"], ["six二十九", "six三十一", "six三十", "six三十二"], ["six三十三", "six三十五", "six三十四", "six三十六"], ["six三十七", "six三十九", "six三十八", "six四十"], ["six四十一", "six四十三", "six四十二", "six四十四"], ["six四十五", "six四十七", "six四十六", "six四十八"], ["six四十九", "six五十一", "six五十", "six五十二"], ["six五十一", "six五十三", "six五十二", "six五十四"], ["six五十五", "six五十七", "six五十六", "six五十八"], ["six五十九", "six六十一", "six六十", "six六十二"]]),
i(Ya, "bigRoadBankerSuperSix", [["superSix一", "superSix三", "superSix二", "superSix四"], ["superSix五", "superSix七", "superSix六", "superSix八"], ["superSix九", "superSix十一", "superSix十", "superSix十二"], ["superSix十三", "superSix十五", "superSix十四", "superSix十六"], ["superSix十七", "superSix十九", "superSix十八", "superSix二十"], ["superSix二十一", "superSix二十三", "superSix二十二", "superSix二十四"], ["superSix二十五", "superSix二十七", "superSix二十六", "superSix二十八"], ["superSix二十九", "superSix三十一", "superSix三十", "superSix三十二"], ["superSix三十三", "superSix三十五", "superSix三十四", "superSix三十六"], ["superSix三十七", "superSix三十九", "superSix三十八", "superSix四十"], ["superSix四十一", "superSix四十三", "superSix四十二", "superSix四十四"], ["superSix四十五", "superSix四十七", "superSix四十六", "superSix四十八"], ["superSix四十九", "superSix五十一", "superSix五十", "superSix五十二"], ["superSix五十一", "superSix五十三", "superSix五十二", "superSix五十四"], ["superSix五十五", "superSix五十七", "superSix五十六", "superSix五十八"], ["superSix五十九", "superSix六十一", "superSix六十", "superSix六十二"]]),
i(Ya, "emptyList", ["摆", "百"]),
i(Ya, "bigEyeRoadList", ["喝", "话"]),
i(Ya, "smallRoadList", ["活", "厚"]),
i(Ya, "smallQRoadList", ["酒", "回"]),
i(Ya, "fullFightProRedList", ["零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "牛", "公"]),
i(Ya, "fullFightProBlueList", ["宁", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "扒", "玖", "妞", "工"]),
i(Ya, "fullFightProGrayList", ["领", "以", "而", "山", "事", "吴", "流", "器", "把", "酒", "扭", "宫"]),
i(Ya, "fullFightEtrRedList", ["您", "意", "洏", "潵", "斯", "武", "硫", "期", "巴", "鸠", "纽", "功"]),
i(Ya, "fullFightEtrBlueList", ["凝", "亦", "鸸", "闪", "时", "舞", "拗", "起", "爸", "究", "留", "供"]),
i(Ya, "fullFightEtrGrayList", ["令", "翼", "佴", "杉", "试", "午", "钮", "骑", "罢", "韭", "斿", "攻"]),
i(Ya, "paiGowRedList", ["服", "副", "付", "负", "福", "府", "富", "赴", "扶", "傅", "牛", "公", "您", "意", "洏", "潵", "斯", "武", "硫", "期", "巴", "鸠", "纽", "功", "先", "县", "线", "仙", "喝", "话", "汉", "借"]),
i(Ya, "paiGowBlueList", ["凝", "异", "鸸", "陕", "使", "物", "斿", "弃", "疤", "舅", "妞", "工", "宁", "亦", "阄", "闪", "时", "舞", "拗", "起", "爸", "究", "留", "供", "含", "酷", "哭", "焊", "韩", "国", "阶", "截"]),
i(Ya, "paiGowGrayList", ["佞", "伊", "儿", "擅", "似", "乌", "瘤", "妻", "叭", "柩", "扭", "宫", "令", "翼", "佴", "杉", "试", "午", "钮", "骑", "罢", "韭", "旒", "攻", "寒", "翰", "撼", "涵", "郭", "锅", "真", "姆"]),
i(Ya, "zeroTrumpList", [["凝", "异", "鸸", "陕", "使", "物", "斿", "弃", "疤", "舅"], ["佞", "伊", "儿", "擅", "似", "乌", "瘤", "妻", "叭", "柩"], ["柠", "乙", "洱", "扇", "氏", "雾", "遛", "齐", "坝", "臼"], ["服", "副", "付", "负", "福", "府", "富", "赴", "扶", "傅"]]),
i(Ya, "oneTrumpList", [["领", "亦", "二", "三", "四", "无", "硫", "起", "把", "就"], ["零", "意", "而", "潵", "死", "五", "留", "期", "吧", "鸠"], ["令", "一", "佴", "伞", "斯", "午", "流", "气", "爸", "酒"], ["红", "宏", "哄", "洪", "鸿", "虹", "弘", "轰", "泓", "烘"]]),
i(Ya, "twoTrumpList", [["林", "亿", "洏", "闪", "是", "屋", "牛", "琪", "八", "韭"], ["凛", "衣", "鲕", "山", "试", "伍", "妞", "其", "捌", "究"], ["淋", "宜", "饵", "杉", "时", "吾", "纽", "启", "芭", "揪"], ["双", "爽", "霜", "孀", "泷", "雙", "滝", "孇", "艭", "礵"]]),
i(Ya, "threeTrumpList", ["公", "共", "攻", "几"]),
i(Ya, "threeTrumpsBPList", ["庄", "闲"]),
i(Ya, "discZhupan", ["0", "1", "2", "3", "4"]),
i(Ya, "discBig", ["小", "大", "合", "消", "哒"]),
i(Ya, "discSingle", ["单", "双"]),
i(Ya, "discNumber", ["零", "一", "二", "三", "四"]),
i(Ya, "sicboDicedPointList", ["", "一", "二", "三", "四", "五", "六"]),
i(Ya, "sicboNumberList", ["0", "1", "2", "3", "4", "5", "6"]),
i(Ya, "sicboTotalNumberList", ["", "", "", "山", "是", "无", "妞", "期", "爸", "究", "斯", "要", "而", "闪", "使", "屋", "牛", "器", "仈"]),
i(Ya, "sicboBigSmallList", ["小", "大", "围"]),
i(Ya, "sicboOddEvenList", ["单", "双", "围"]),
i(Ya, "sicboBalck", "黑"),
i(Ya, "sicboEmpty", "000"),
i(Ya, "fantanNumberList", ["四", "一", "二", "三"]),
i(Ya, "fantanOddEvenList", ["单", "双"]),
i(Ya, "fantanEmpty", ""),
i(Ya, "rouletteWinNumberCharList", ["0", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "要", "而", "闪", "诗", "无", "牛", "器", "把", "就", "是", "药", "儿", "山", "时", "吴", "妞", "起", "爸", "救", "市", "咬", "耳", "散", "使", "唔", "钮"]),
i(Ya, "MarkSixBigSmallCharList", ["小", "大", "零"]),
i(Ya, "MarkSixOddEvenCharList", ["单", "双", "零"]),
i(Ya, "rouletteBigSmallCharList", ["小", "大", "零"]),
i(Ya, "rouletteRedBlackCharList", ["黑", "红", "零"]),
i(Ya, "rouletteOddEvenCharList", ["双", "单", "零"]),
i(Ya, "rouletteDozenColumnRoadCharList", ["洪", "兰", "绿"]),
i(Ya, "DouNiuBeadPlateList", ["闲", "庄", "和"]),
i(Ya, "andabahaBigRoad", ["安", "巴"]),
i(Ya, "indiaZhupan", ["A", "诶", "欸", "誒", "B", "必", "比", "币", "和", "合", "喝", "盒"]),
i(Ya, "indiaBig", []),
i(Ya, "obBallBigRoad", ["零", "一", "二", "叁", "肆", "伍", "陆", "柒", "捌", "九", "十", "要", "而", "闪", "诗", "无", "妞", "期", "爸", "究"]),
i(Ya, "obBallBig", ["大", "小"]),
i(Ya, "obBallSingle", ["单", "双"]),
i(Ya, "obBallNumber", ["零", "一", "二", "三", "四", "五", "六", "七", "八", "玖", "食"]),
i(Ya, "game3DNumberList", {
    0: "0",
    1: "1",
    2: "2",
    3: "3",
    4: "4",
    5: "5",
    6: "6",
    7: "7",
    8: "8",
    9: "9"
}),
i(Ya, "game3DBigSmallList", ["大", "小"]),
i(Ya, "game3DOddEvenList", ["单", "双"]),
i(Ya, "dollyRedList", ["时", "咎", "岜", "骑", "遛", "午", "司", "散", "儿", "易", "久", "扒", "漆", "流", "武", "思", "仨", "饵", "衣", "凌", "吾"]),
i(Ya, "dollyBlueList", ["拾", "玖", "捌", "柒", "陆", "伍", "肆", "叁", "贰", "壹", "九", "八", "七", "六", "五", "四", "三", "二", "一", "零", "无"]),
i(Ya, "dollyGrayList", ["实", "韭", "巴", "期", "刘", "物", "丝", "伞", "耳", "亿", "酒", "把", "器", "瘤", "舞", "事", "山", "而", "以", "灵", "吴"]);
let za = Ya;

class es {
    static encode(e) {
        const t = new Uint8Array(e)
          , a = t.length;
        let s = "";
        for (let i = 0; i < a; i += 3)
            s += ts[t[i] >> 2],
            s += ts[(3 & t[i]) << 4 | t[i + 1] >> 4],
            s += ts[(15 & t[i + 1]) << 2 | t[i + 2] >> 6],
            s += ts[63 & t[i + 2]];
        return a % 3 == 2 ? s = s.substring(0, s.length - 1) + "=" : a % 3 == 1 && (s = s.substring(0, s.length - 2) + "=="),
        s
    }
    static decode(e) {
        let t = .75 * e.length;
        const a = e.length;
        let s = 0
          , i = 0
          , n = 0
          , l = 0
          , o = 0;
        "=" === e[e.length - 1] && (t--,
        "=" === e[e.length - 2] && t--);
        const r = new ArrayBuffer(t)
          , d = new Uint8Array(r);
        for (let c = 0; c < a; c += 4)
            i = as[e.charCodeAt(c)],
            n = as[e.charCodeAt(c + 1)],
            l = as[e.charCodeAt(c + 2)],
            o = as[e.charCodeAt(c + 3)],
            d[s++] = i << 2 | n >> 4,
            d[s++] = (15 & n) << 4 | l >> 2,
            d[s++] = (3 & l) << 6 | 63 & o;
        return r
    }
}

const ts = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
  , as = new Uint8Array(256);
for (let x5 = 0; x5 < ts.length; x5++)
    as[ts.charCodeAt(x5)] = x5;