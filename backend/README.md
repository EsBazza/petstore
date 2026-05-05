# Petstore Backend API

Spring Boot REST API backend for the Petstore application.

## Technology Stack

- **Java**: 17 LTS
- **Framework**: Spring Boot 3.2+
- **Build Tool**: Maven 3.8+
- **Database**: PostgreSQL 14+
- **Testing**: JUnit 5, Mockito

## Prerequisites

- Java 17 or later
- Maven 3.8+
- PostgreSQL 14+ running (use Docker Compose or local installation)

## Setup & Build

### 1. Install Dependencies
```bash
mvn clean install
```

### 2. Configure Database
Create a `.env` file or update `application.properties` with PostgreSQL credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/petstore_db
spring.datasource.username=petstore_user
spring.datasource.password=petstore_password
```

### 3. Run Database Migrations
Migrations run automatically on application startup via Flyway.

### 4. Build the Application
```bash
mvn clean package
```

### 5. Run the Application
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080/api`

## API Documentation

Once running, Swagger UI is available at:
```
http://localhost:8080/api/swagger-ui.html
```

## Testing

### Run All Tests
```bash
mvn test
```

### Run Tests with Coverage
```bash
mvn test jacoco:report
```

### Run Integration Tests Only
```bash
mvn verify -DskipUnitTests=true
```

## Project Structure

```
backend/
├── src/main/java/com/petstore/
│   ├── PetstoreApplication.java
│   ├── controller/           # REST endpoints
│   ├── service/              # Business logic
│   ├── repository/           # Data access
│   ├── entity/               # JPA entities
│   ├── dto/                  # Data transfer objects
│   ├── exception/            # Custom exceptions
│   └── config/               # Spring configuration
├── src/main/resources/
│   ├── application.properties
│   └── db/migration/         # Flyway migrations
└── src/test/java/com/petstore/
    ├── controller/           # API tests
    ├── service/              # Service unit tests
    └── repository/           # Repository tests
```

## Code Quality Standards

- Follow Google Java Style Guide
- Comprehensive Javadoc for public APIs
- Unit test coverage ≥ 80%
- Integration tests for all endpoints
- Use Lombok to reduce boilerplate

## Contributing

1. Create a feature branch: `git checkout -b feature/your-feature`
2. Write tests first (TDD)
3. Implement feature
4. Run `mvn clean verify` to ensure all tests pass
5. Submit PR for code review

## License

School Project - MIT License
