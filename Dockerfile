# Build stage
FROM gradle:8.10.2-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# Run stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL:-jdbc:postgresql://db:5432/reading_progress}
ENV SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME:-postgres}
ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD:-StrongPassword123}

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"] 