# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the Spring Boot application JAR file
COPY build/libs/backend-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]