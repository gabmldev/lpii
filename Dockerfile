# ───────── BUILD ─────────
FROM maven:4.0.0-rc-4-eclipse-temurin-21 AS builder
WORKDIR /workspace
# copiar pom primero para aprovechar cache
COPY pom.xml ./
COPY .mvn .mvn
RUN mvn -B -f pom.xml -T 1C dependency:go-offline

COPY src ./src
RUN mvn -B -DskipTests package

# ───────── RUNTIME ─────────
FROM eclipse-temurin:21-jdk-alpine AS runtime
ARG JAR_NAME=app.jar
WORKDIR /app
# copia jar desde builder
COPY --from=builder /workspace/target/*.jar ${JAR_NAME}
EXPOSE 8080
# usa un usuario no-root en producción (opcional)
# RUN addgroup -S app && adduser -S -G app app
# USER app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
