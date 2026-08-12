# =============================================
# Stage 1：使用 Maven 編譯專案，產出 JAR 檔
# =============================================
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /Planetary_Hours

# 再複製完整原始碼並編譯（跳過測試以縮短建構時間）
COPY . .
RUN mvn clean package -DskipTests

# =============================================
# Stage 2：只帶 JAR 到精簡的 JRE 執行環境
# =============================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /Planetary Hours

COPY --from=builder /Planetary_Hours/target/*.jar app.jar

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]

EXPOSE 8080
