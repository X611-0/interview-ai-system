-- ============================================================
-- 智能模拟面试系统 — 数据库参考 Schema
-- 日常开发使用 JPA ddl-auto=update 自动建表，此文件仅作参考
-- 生产环境建议使用 Flyway / Liquibase 管理数据库版本
-- ============================================================

CREATE DATABASE IF NOT EXISTS interview_ai DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE interview_ai;

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    email VARCHAR(100),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 面试会话表
CREATE TABLE IF NOT EXISTS interview_sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    position VARCHAR(100),
    difficulty VARCHAR(20),
    status VARCHAR(20) DEFAULT 'IN_PROGRESS',
    start_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    end_time DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 面试题目表
CREATE TABLE IF NOT EXISTS interview_questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT,
    question_text TEXT,
    category VARCHAR(50),
    difficulty VARCHAR(20),
    display_order INT,
    reference_answer TEXT,
    tags VARCHAR(255),
    FOREIGN KEY (session_id) REFERENCES interview_sessions(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 用户答案表
CREATE TABLE IF NOT EXISTS user_answers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT,
    question_id BIGINT,
    user_id BIGINT,
    answer_text TEXT,
    score DOUBLE,
    feedback TEXT,
    answer_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    analysis_time DATETIME,
    FOREIGN KEY (session_id) REFERENCES interview_sessions(id),
    FOREIGN KEY (question_id) REFERENCES interview_questions(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
