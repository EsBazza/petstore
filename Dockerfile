# Multi-stage Dockerfile for Petstore application
# Stage 1: Build backend JAR
FROM maven:3.8.4-openjdk-17 as backend-builder
WORKDIR /app
COPY backend/pom.xml ./backend/
COPY backend/src ./backend/src
RUN cd backend && mvn clean package -DskipTests

# Stage 2: Build frontend
FROM node:20-alpine as frontend-builder
WORKDIR /app
COPY frontend/package*.json ./frontend/
RUN cd frontend && npm ci
COPY frontend ./frontend
RUN cd frontend && npm run build

# Stage 3: Runtime - Spring Boot with static frontend files
FROM openjdk:17-alpine
WORKDIR /app

# Copy built backend JAR
COPY --from=backend-builder /app/backend/target/petstore-api-1.0.0.jar ./

# Copy built frontend to backend's static resources
COPY --from=frontend-builder /app/frontend/dist /app/static

# Expose port
EXPOSE 8080

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV SPRING_WEB_RESOURCES_STATIC_LOCATIONS=file:/app/static/

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "petstore-api-1.0.0.jar"]
