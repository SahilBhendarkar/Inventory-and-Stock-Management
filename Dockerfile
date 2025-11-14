# Start from a Java 21 base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies first (for caching)
COPY pom.xml .
RUN ./mvnw dependency:go-offline

# Copy the project
COPY . .

# Build the Spring Boot app
RUN ./mvnw package -DskipTests

# Expose the port your app runs on
EXPOSE 8080

# Run the jar
CMD ["java", "-jar", "target/inventory-management-0.0.1-SNAPSHOT.jar"]
