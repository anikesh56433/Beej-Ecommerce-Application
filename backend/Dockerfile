# Use Maven image for building
FROM maven:3.9-openjdk-17 AS builder

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use OpenJDK 17 for running the application
FROM openjdk:17-jre-slim

WORKDIR /app

# Copy the built jar file
COPY --from=builder /app/target/*.jar app.jar

# Create non-root user
RUN addgroup --system beej && adduser --system beej --ingroup beej
USER beej

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
