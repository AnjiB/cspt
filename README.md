# CSPT - Selenium Performance Testing Framework

This is a multi-module Maven project for Selenium performance testing.

## Modules

1. **cspt-metric-service**: A Spring Boot application that provides a REST API for storing and retrieving performance metrics.
2. **ui-tests**: Selenium-based UI tests that collect performance metrics.

## Prerequisites

- Java 17
- Maven
- Docker and Docker Compose

## Getting Started

### Running the entire suite

The easiest way to run everything is to use the provided script:

```bash
./setup_and_run.sh
```

This script will:
1. Start the metric service using Docker Compose
2. Wait for services to be ready
3. Run the UI tests
4. Clean up when done

### Manual Setup

#### Setting up the Metric Service

```bash
cd cspt-metric-service
docker-compose up -d
```

The metric service includes:
- MySQL database for storing metrics
- Spring Boot REST API application
- Grafana for visualizing metrics

Access endpoints:
- REST API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- Grafana: http://localhost:3000 (admin/admin)

#### Running UI Tests

```bash
cd ui-tests
mvn clean test
```

## Configuration

- Database configuration: Edit `cspt-metric-service/.env` to change database credentials
- API configuration: Edit `cspt-metric-service/src/main/resources/application.properties`
- Test configuration: Edit `ui-tests/src/test/resources/junit-platform.properties`

## Test Reports

- Allure reports: Generated in `ui-tests/allure-results`
- Surefire reports: Generated in `ui-tests/target/surefire-reports`

## Performance Dashboard

Grafana dashboards are available at http://localhost:3000 after starting the services.
Default credentials are admin/admin.