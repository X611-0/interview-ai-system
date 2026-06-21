# 智能模拟面试系统

一个基于 AI 的智能模拟面试系统，帮助用户进行面试练习并获得实时反馈。

## 技术栈

### 后端
- Spring Boot 3.4.6
- Spring Security + JWT
- JPA + MySQL
- WebSocket
- 千问 AI API

### 前端
- Vue 3 + TypeScript
- Vite
- Element Plus
- Tailwind CSS

## 项目结构

```
Interview/
├── 前端/          # Vue 3 前端项目
└── 后端/          # Spring Boot 后端项目
```

## 快速开始

### 环境要求
- JDK 17+
- Node.js 16+
- MySQL 8.0+

### 配置

1. 复制环境变量示例文件：
```bash
cp Interview/后端/.env.example Interview/后端/.env
```

2. 修改 `.env` 文件，填入真实的配置信息

### 运行

#### 后端
```bash
cd Interview/后端
./mvnw spring-boot:run
```

#### 前端
```bash
cd Interview/前端
npm install
npm run dev
```

## 功能特性

- 用户注册登录
- AI 生成面试题目
- 实时面试练习
- AI 评分和反馈
- 面试报告生成

## 开发进度

- [x] 项目初始化
- [x] 数据库设计
- [x] 用户认证
- [x] AI 服务集成
- [x] 前端基础架构
- [x] 核心业务功能
- [ ] 性能优化
- [ ] 测试覆盖

## 许可证

MIT
