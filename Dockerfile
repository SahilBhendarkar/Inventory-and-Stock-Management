# Start from a Java 21 base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper scripts and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Give execute permission to mvnw
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy the rest of the project
COPY . .

# Build the Spring Boot app
RUN ./mvnw package -DskipTests

# Expose the port
EXPOSE 8080

# Run the jar
CMD ["java", "-jar", "target/inventory-management-0.0.1-SNAPSHOT.jar"]
