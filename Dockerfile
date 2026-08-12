# =============================================
# Stage 1：使用 Maven + Java 21 編譯
# =============================================

FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /Planetary_Hours

COPY . .

RUN mvn clean package -DskipTests


# =============================================
# Stage 2：使用 Java 21 JRE 執行
# =============================================

FROM eclipse-temurin:21-jre-alpine

WORKDIR /Planetary_Hours

COPY --from=builder /Planetary_Hours/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080