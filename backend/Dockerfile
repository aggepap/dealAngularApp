# Use a Java image as a base
FROM openjdk:17-jdk-alpine

# Copy the Spring Boot JAR file
COPY build/libs/dealsApp-1.0-SNAPSHOT.jar app.jar


# Expose port 8080 (Spring Boot's default port)
EXPOSE 8080

# Start the Spring Boot app
CMD ["java", "-jar", "app.jar"]
