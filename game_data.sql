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
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (table_id) REFERENCES games(id)
);
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
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (table_id) REFERENCES table_info(id)
);
create index idx_create_time on sicbo_result(create_time);



