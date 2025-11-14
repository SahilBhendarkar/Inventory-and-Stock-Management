# ============ BUILD STAGE ============
FROM eclipse-temurin:21-jdk-alpine AS build

# Install dos2unix to fix CRLF issues (optional but safe)
RUN apk add --no-cache dos2unix

WORKDIR /app

# Copy Maven wrapper + pom
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Fix line endings & make executable
RUN dos2unix mvnw && chmod +x mvnw

# Download dependencies (leverages Docker cache)
RUN --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -B

# Copy source
COPY src ./src

# Build the fat JAR (skip tests in prod)
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw package -DskipTests -B -Dstyle.color=never

# ============ RUNTIME STAGE ============
FROM eclipse-temurin:21-jre-alpine

# Create non-root user
ARG APP_USER=appuser
ARG APP_UID=10001
RUN addgroup -g ${APP_UID} -S ${APP_USER} && \
    adduser -u ${APP_UID} -S -D -G ${APP_USER} ${APP_USER}

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build --chown=${APP_USER}:${APP_USER} \
     /app/target/inventory-management-0.0.1-SNAPSHOT.jar app.jar

# Switch to non-root user
USER ${APP_USER}

# Expose port
EXPOSE 8080

# Health check (Spring Boot Actuator required: /actuator/health)
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# JVM options (tune as needed)
ENV JAVA_OPTS="-Xms256m -Xmx512m \
               -XX:+UseG1GC \
               -Djava.security.egd=file:/dev/./urandom \
               -Dserver.port=8080"

# Run the app
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]