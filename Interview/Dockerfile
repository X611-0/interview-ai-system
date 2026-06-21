# Multi-stage build: build frontend (Vite) + backend (Spring Boot), then run as single jar

# 1) Build frontend
FROM node:20-alpine AS frontend-builder
WORKDIR /frontend
COPY 前端/ ./
RUN npm install && npm run build

# 2) Build backend and embed frontend static resources into Spring Boot jar
FROM maven:3.9-eclipse-temurin-17 AS backend-builder
WORKDIR /workspace
COPY 后端/ ./后端
# Ensure static is a directory (some repos accidentally have it as a file)
RUN rm -rf ./后端/src/main/resources/static && mkdir -p ./后端/src/main/resources/static
COPY --from=frontend-builder /frontend/dist/ ./后端/src/main/resources/static/
RUN mvn -f 后端/pom.xml clean package -DskipTests

# 3) Runtime image
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=backend-builder /workspace/后端/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
