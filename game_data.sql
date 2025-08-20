-- 桌子信息表
DROP TABLE IF EXISTS games;
CREATE TABLE games (
    id VARCHAR(50) PRIMARY KEY COMMENT '游戏唯一标识符',
    title VARCHAR(255) NOT NULL COMMENT '游戏标题',
    frontend_app VARCHAR(255) DEFAULT NULL COMMENT '前端应用名称',
    game_provider VARCHAR(100) NOT NULL COMMENT '游戏提供商',
    game_type VARCHAR(100) NOT NULL COMMENT '游戏类型',
    gv VARCHAR(50) NOT NULL COMMENT '游戏变体（如 live/rng）',
    published BOOLEAN NOT NULL DEFAULT TRUE COMMENT '是否已发布',
    opens_at TIME DEFAULT NULL COMMENT '开放时间',
    lang VARCHAR(10) DEFAULT NULL COMMENT '语言版本',
	min_bet DECIMAL(18, 2) NOT NULL COMMENT '最小投注金额',
    max_bet DECIMAL(18, 2) NOT NULL COMMENT '最大投注金额',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 轮盘结果表
DROP TABLE IF EXISTS roulette_result;
CREATE TABLE roulette_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id VARCHAR(50) NOT NULL,
    round_id VARCHAR(50) COMMENT '轮次ID',
    result INT NOT NULL COMMENT '轮盘结果数字',
    color VARCHAR(10) COMMENT '轮盘颜色',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP

);
create index idx_table_id on roulette_result(table_id);
create index idx_create_time on roulette_result(create_time);

-- 骰子结果表
DROP TABLE IF EXISTS sicbo_result;
CREATE TABLE sicbo_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id VARCHAR(50) NOT NULL,
    round_id VARCHAR(50) COMMENT '轮次ID',
    dice1 INT NOT NULL COMMENT '骰子1点数',
    dice2 INT NOT NULL COMMENT '骰子2点数',
    dice3 INT NOT NULL COMMENT '骰子3点数',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
create index idx_table_id on sicbo_result(table_id);
create index idx_create_time on sicbo_result(create_time);

DROP TABLE IF EXISTS db_tables;
CREATE TABLE db_tables (
    table_id INT PRIMARY KEY COMMENT '桌子ID',
    table_name VARCHAR(255) NOT NULL COMMENT '桌子名称',
    game_casino_id INT NOT NULL COMMENT '游戏大厅ID',
    game_casino_name VARCHAR(255) NOT NULL COMMENT '游戏大厅名称',
    game_type_id INT NOT NULL COMMENT '游戏类型， 轮盘：2007  骰宝：2008  色碟：2022',
    game_type_name VARCHAR(50) NOT NULL COMMENT '游戏类型名称:轮盘，骰宝，色碟，百家乐',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 轮盘结果表
DROP TABLE IF EXISTS db_roulette_result;
CREATE TABLE db_roulette_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id INT NOT NULL,
    result INT NOT NULL COMMENT '轮盘结果数字',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP

);
create index idx_table_id on db_roulette_result(table_id);
create index idx_create_time on db_roulette_result(create_time);

-- 骰子结果表
DROP TABLE IF EXISTS db_sicbo_result;
CREATE TABLE db_sicbo_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id INT NOT NULL,
    dice1 INT NOT NULL COMMENT '骰子1点数',
    dice2 INT NOT NULL COMMENT '骰子2点数',
    dice3 INT NOT NULL COMMENT '骰子3点数',
    total INT NOT NULL COMMENT '骰子点数和',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
create index idx_table_id on db_sicbo_result(table_id);
create index idx_create_time on db_sicbo_result(create_time);

-- 轮盘结果表
DROP TABLE IF EXISTS db_color_disk_result;
CREATE TABLE db_color_disk_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id INT NOT NULL,
    result INT NOT NULL COMMENT '色碟结果数字',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP

);
create index idx_table_id on db_color_disk_result(table_id);
create index idx_create_time on db_color_disk_result(create_time);



-- 轮盘结果表 (PP)
DROP TABLE IF EXISTS pp_roulette_result;
CREATE TABLE pp_roulette_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id VARCHAR(50) NOT NULL,
    round_id VARCHAR(50) COMMENT '轮次ID',
    result INT NOT NULL COMMENT '轮盘结果数字',
    color VARCHAR(20) COMMENT '轮盘颜色',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP

);
create index idx_table_id on pp_roulette_result(table_id);
create index idx_create_time on pp_roulette_result(create_time);

-- 骰子结果表 (PP)
DROP TABLE IF EXISTS pp_sicbo_result;
CREATE TABLE pp_sicbo_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id VARCHAR(50) NOT NULL,
    round_id VARCHAR(50) COMMENT '轮次ID',
    dice1 INT NOT NULL COMMENT '骰子1点数',
    dice2 INT NOT NULL COMMENT '骰子2点数',
    dice3 INT NOT NULL COMMENT '骰子3点数',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
create index idx_table_id on pp_sicbo_result(table_id);
create index idx_create_time on pp_sicbo_result(create_time);

DROP TABLE IF EXISTS pp_tables;
CREATE TABLE pp_tables (
    table_id varchar(50) PRIMARY KEY COMMENT '桌子ID',
    table_name VARCHAR(255) NOT NULL COMMENT '桌子名称',
    table_name_en VARCHAR(255) NOT NULL COMMENT '桌子名称英文名称',
    table_type VARCHAR(50) NOT NULL COMMENT '游戏类型名称:ROULETTE，SICBO',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Ezugi相关表
DROP TABLE IF EXISTS ezugi_tables;
CREATE TABLE ezugi_tables (
    table_id varchar(50) PRIMARY KEY COMMENT '桌子ID',
    table_name VARCHAR(255) NOT NULL COMMENT '桌子名称',
    table_name_en VARCHAR(255) NOT NULL COMMENT '桌子名称英文名称',
    table_type VARCHAR(50) NOT NULL COMMENT '游戏类型名称:ROULETTE，SICBO',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 轮盘结果表 (Ezugi)
DROP TABLE IF EXISTS ezugi_roulette_result;
CREATE TABLE ezugi_roulette_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id VARCHAR(50) NOT NULL,
    round_id VARCHAR(50) COMMENT '轮次ID',
    result INT NOT NULL COMMENT '轮盘结果数字',
    color VARCHAR(20) COMMENT '轮盘颜色',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_round_id (round_id)
);
create index idx_table_id on ezugi_roulette_result(table_id);
create index idx_create_time on ezugi_roulette_result(create_time);

-- 骰子结果表 (Ezugi)
DROP TABLE IF EXISTS ezugi_sicbo_result;
CREATE TABLE ezugi_sicbo_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id VARCHAR(50) NOT NULL,
    round_id VARCHAR(50) COMMENT '轮次ID',
    dice1 INT NOT NULL COMMENT '骰子1点数',
    dice2 INT NOT NULL COMMENT '骰子2点数',
    dice3 INT NOT NULL COMMENT '骰子3点数',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_round_id (round_id),
    Key idx_table_id(table_id),
    key idx_create_time(create_time)
);

-- 轮盘结果表
DROP TABLE IF EXISTS ezugi_color_disk_result;
CREATE TABLE ezugi_color_disk_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id INT NOT NULL,
    round_id VARCHAR(50) COMMENT '轮次ID',
    result INT NOT NULL COMMENT '色碟结果数字',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_round_id (round_id),
    Key idx_table_id(table_id),
    key idx_create_time(create_time)
);

-- WL相关表
DROP TABLE IF EXISTS wl_table;
CREATE TABLE wl_table (
    table_id varchar(50) PRIMARY KEY COMMENT '桌子ID',
    table_name VARCHAR(255) NOT NULL COMMENT '桌子名称',
    type INT NOT NULL COMMENT '游戏类型，1：百家乐，2：轮盘，3：骰宝，4：色碟',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    Key idx_create_time(create_time)
);

-- 轮盘结果表 (WL)
DROP TABLE IF EXISTS wl_roulette_result;
CREATE TABLE wl_roulette_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id INT NOT NULL,
    result INT NOT NULL COMMENT '轮盘结果数字',
    round_id varchar(50) not null,
    round INT  COMMENT '轮次',
    dealer_id INT default 0 COMMENT '荷官ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_round_id (round_id),
    Key idx_table_id(table_id),
    key idx_create_time(create_time)
);

-- 骰子结果表 (WL)
DROP TABLE IF EXISTS wl_sicbo_result;
CREATE TABLE wl_sicbo_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id INT NOT NULL,
    round_id varchar(50) not null,
    round INT  COMMENT '轮次',
    dice1 INT NOT NULL COMMENT '骰子1点数',
    dice2 INT NOT NULL COMMENT '骰子2点数',
    dice3 INT NOT NULL COMMENT '骰子3点数',
    total INT NOT NULL COMMENT '骰子点数和',
    dealer_id INT default 0 COMMENT '荷官ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_round_id (round_id),
    Key idx_table_id(table_id),
    key idx_create_time(create_time)
);

-- 色碟结果表 (WL)
DROP TABLE IF EXISTS wl_color_disk_result;
CREATE TABLE wl_color_disk_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id INT NOT NULL,
    result INT NOT NULL COMMENT '色碟结果数字',
    round_id varchar(50) not null ,
    round INT  COMMENT '轮次',
    dealer_id INT default 0 COMMENT '荷官ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_round_id (round_id),
    Key idx_table_id(table_id),
    key idx_create_time(create_time)
);

DROP TABLE IF EXISTS wl_baccarat_result;
CREATE TABLE wl_baccarat_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id INT NOT NULL,
    round_id varchar(50) not null,
    round INT  COMMENT '轮次',
    player_card1 INT  COMMENT '玩家牌1点数',
    player_card2 INT  COMMENT '玩家牌2点数',
    player_card3 INT  COMMENT '玩家牌3点数',
    banker_card1 INT COMMENT '庄家牌1点数',
    banker_card2 INT  COMMENT '庄家牌2点数',
    banker_card3 INT  COMMENT '庄家牌3点数',
    dealer_id INT default 0 COMMENT '荷官ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_round_id (round_id),
    Key idx_table_id(table_id),
    key idx_create_time(create_time)
);

DROP TABLE IF EXISTS wl_dealer_info;
CREATE TABLE wl_dealer_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dealer_id INT NOT NULL COMMENT '荷官ID',
    dealer_name VARCHAR(50) NOT NULL COMMENT '荷官名称',
    img_nation INT COMMENT '荷官民族',
    img_url VARCHAR(128) NOT NULL COMMENT '荷官头像链接',
    img_md5 VARCHAR(128) NOT NULL COMMENT '荷官头像MD5',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_dealer_id (dealer_id),
    Key idx_create_time(create_time)
)

