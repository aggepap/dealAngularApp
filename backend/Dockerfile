#Build gradle
FROM gradle:7.6-jdk17 AS builder

WORKDIR /app

COPY . /app

RUN ./gradlew bootJar

# Runtime stage
FROM openjdk:17-jdk-alpine

COPY --from=builder /app/build/libs/dealsApp-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
