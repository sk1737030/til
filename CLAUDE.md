# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a "Today I Learned" (TIL) repository containing multiple independent Java/Kotlin modules for exploring various technologies and concepts. Each subdirectory is a self-contained Gradle subproject focused on a specific topic or technology.

## Build System

**Gradle Multi-Project Build**
- Gradle version: 7.6.3
- Root project configured in `build.gradle` with shared dependencies
- All subprojects inherit common configuration (Spring Boot 2.3.0.RELEASE, Java 11, Lombok)
- Each subproject has its own `build.gradle` that can override or extend the base configuration

## Common Commands

### Building
```bash
# Build all subprojects
./gradlew build

# Build a specific subproject
./gradlew :coupon-best-comination:build
./gradlew :rest-assured:build

# Clean build artifacts
./gradlew clean
```

### Running Tests
```bash
# Run all tests
./gradlew test

# Run tests for a specific subproject
./gradlew :til-jpa:test
./gradlew :spring-kafka-graceful-shutdown:test

# Run a single test class
./gradlew :til-jpa:test --tests "com.example.tiljpa.problem.ProblemRepositoryTest"
```

### Running Applications
```bash
# Run a Spring Boot application from a subproject
./gradlew :demo:bootRun
./gradlew :spring-cloud-gateway:bootRun

# Run a standalone Java application
./gradlew :coupon-best-comination:run
```

### React Project (react-tutorial)
```bash
cd react-tutorial
npm install
npm start      # Development server
npm test       # Run tests
npm run build  # Production build
```

## Project Structure

The repository contains 30+ independent modules organized by topic:

**Spring Boot Projects** (most subprojects)
- Standard Spring Boot structure: `src/main/java`, `src/test/java`
- Configuration in `application.properties` or `application.yml`
- Inherit from root `build.gradle` (Spring Boot 2.3.0.RELEASE, Java 11)

**Key Technology Areas:**
- **JPA/Database**: `til-jpa`, `transactional`, `outbox-example`
- **Kafka**: `simple-kafka`, `spring-kafka-graceful-shutdown`, `kafka-repartitioning`
- **Spring Cloud**: `spring-cloud-gateway`, `spring-cloud-gateway-late-limiter`
- **Reactive Programming**: `reactive-programming`, `demo` (WebClient)
- **Testing**: `rest-assured`, `bumgun-tdd`, `my-xunit`
- **Architecture**: `clean-architecture`, `common-response`
- **Kotlin**: `cradle-kotlin` (Kotlin JVM project with JDK 8/17)
- **Frontend**: `react-tutorial`, `springboot-with-react-example`

## Architecture Patterns

### Multi-Module Independence
Each subproject is completely independent with its own:
- Source code and tests
- Dependencies (though inheriting base config)
- Application configuration
- Package structure (various root packages: `com.example.*`, `dongle.til.*`, `org.example.*`)

### Common Patterns
- **Lombok**: Widely used across projects (`@Getter`, `@AllArgsConstructor`)
- **JUnit 5**: Test framework (`useJUnitPlatform()` configured)
- **Spring Boot Testing**: Standard test annotations and patterns
- **Domain-Driven Design**: Some projects organize by domain entities (`Order`, `Reservation`, etc.)

### Transaction Testing (`transactional`)
Dedicated module for exploring Spring transaction behavior with service-to-service calls.

### JPA Experimentation (`til-jpa`)
Explores JPA relationships, repositories, and common persistence patterns.

### API Testing (`rest-assured`)
Uses REST Assured library for HTTP API testing, includes Spring Cloud OpenFeign.

## Development Workflow

1. **Adding a New Module**: Add to `settings.gradle` and create subdirectory with `build.gradle`
2. **Module Conventions**: Each module should be self-contained and runnable independently
3. **Testing**: All tests use JUnit 5 platform
4. **Dependencies**: Modules can override dependencies from root `build.gradle` as needed

## Kotlin Projects

The `cradle-kotlin` module uses:
- Kotlin JVM plugin (version 2.3.0)
- Dual JVM toolchain configuration (JDK 8 and 17)
- Kotlin standard library and test framework

## Notes

- This is a learning/experimentation repository, not production code
- Each module explores specific concepts or technologies
- Modules may have commented-out code for alternative scenarios
- Some modules have minimal structure (learning exercises)