# 智能模拟面试系统

> **在线演示地址**: http://101.34.248.77:8080

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
├── 前端/                          # Vue 3 前端项目
│   ├── src/
│   │   ├── views/                 # 页面组件
│   │   │   ├── HomeView.vue       # 首页
│   │   │   ├── LoginView.vue      # 登录页
│   │   │   ├── InterviewView.vue  # 面试页
│   │   │   └── ReportView.vue     # 报告页
│   │   ├── services/              # API 服务
│   │   │   ├── authApi.ts         # 认证 API
│   │   │   ├── ernieApi.js        # AI API
│   │   │   └── http.ts            # HTTP 客户端
│   │   ├── router/                # 路由配置
│   │   ├── types/                 # TypeScript 类型定义
│   │   ├── App.vue                # 根组件
│   │   ├── main.ts                # 入口文件
│   │   └── style.css              # 全局样式
│   ├── index.html                 # HTML 模板
│   ├── package.json               # 依赖配置
│   ├── vite.config.js             # Vite 配置
│   ├── tailwind.config.js         # Tailwind CSS 配置
│   └── postcss.config.js          # PostCSS 配置
│
├── 后端/                          # Spring Boot 后端项目
│   ├── src/main/
│   │   ├── java/com/example/demo/
│   │   │   ├── config/            # 配置类
│   │   │   │   ├── SecurityConfig.java      # 安全配置
│   │   │   │   ├── WebSocketConfig.java     # WebSocket 配置
│   │   │   │   ├── PasswordConfig.java      # 密码编码配置
│   │   │   │   └── RestTemplateConfig.java  # REST 模板配置
│   │   │   ├── controller/        # 控制器层
│   │   │   │   ├── AuthController.java      # 认证控制器
│   │   │   │   ├── AIController.java        # AI 控制器
│   │   │   │   ├── InterviewController.java # 面试控制器
│   │   │   │   └── UserController.java      # 用户控制器
│   │   │   ├── service/           # 服务层
│   │   │   │   ├── AIService.java           # AI 服务
│   │   │   │   ├── UserService.java         # 用户服务
│   │   │   │   ├── InterviewService.java    # 面试服务
│   │   │   │   ├── ErnieService.java        # 文心一言服务
│   │   │   │   └── ErnieAuthService.java    # 文心一言认证
│   │   │   ├── repository/        # 数据访问层
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── InterviewSessionRepository.java
│   │   │   │   ├── InterviewQuestionRepository.java
│   │   │   │   └── UserAnswerRepository.java
│   │   │   ├── entity/            # 实体类
│   │   │   │   ├── User.java
│   │   │   │   ├── InterviewSession.java
│   │   │   │   ├── InterviewQuestion.java
│   │   │   │   ├── UserAnswer.java
│   │   │   │   └── SessionStatus.java
│   │   │   ├── dto/               # 数据传输对象
│   │   │   │   ├── AuthRequest.java
│   │   │   │   ├── AuthResponse.java
│   │   │   │   ├── ApiResponse.java
│   │   │   │   ├── StartInterviewRequest.java
│   │   │   │   ├── SubmitAnswerRequest.java
│   │   │   │   └── AnswerFeedback.java
│   │   │   ├── filter/            # 过滤器
│   │   │   │   └── JwtAuthenticationFilter.java
│   │   │   ├── handler/           # 处理器
│   │   │   │   └── InterviewWebSocketHandler.java
│   │   │   ├── exception/         # 异常处理
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── util/              # 工具类
│   │   │   │   └── JwtUtil.java
│   │   │   └── DemoApplication.java # 应用入口
│   │   └── resources/
│   │       ├── application.properties # 应用配置
│   │       ├── schema.sql             # 数据库架构
│   │       └── static/                # 静态资源
│   ├── pom.xml                    # Maven 配置
│   ├── .env.example               # 环境变量示例
│   └── mvnw / mvnw.cmd            # Maven Wrapper
│
└── Dockerfile                     # Docker 部署文件
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
