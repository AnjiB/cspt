#!/bin/bash

# Set up the environment
echo "Setting up the environment..."

# Build the metric service
echo "Building metric service..."
cd cspt-metric-service
mvn clean package -DskipTests

# Start docker compose in the metric service
echo "Starting metric service with Docker Compose..."
docker-compose up -d

# Wait for services to be ready
echo "Waiting for services to be ready..."
sleep 10

# Check if services are up
echo "Checking if services are up..."
docker-compose ps

# Now run the UI tests
echo "Running UI tests..."
cd ../ui-tests
mvn clean test

# Show test results
echo "Test results:"
ls -la target/surefire-reports/

# Cleanup
echo "Cleaning up..."
cd ../cspt-metric-service
docker-compose down

echo "Done!"