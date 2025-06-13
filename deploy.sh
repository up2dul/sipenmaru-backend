#!/bin/bash

# Exit on error
set -e

echo "Starting deployment process... 🕹️"

echo "Pulling the latest changes from the main branch... 🔄"
git checkout main
git pull origin main

echo "Build the Spring Boot application... 🍃"
mvn clean package -DskipTests
docker compose down -v
docker compose up -d --build

echo "Deployment process completed! 🎉"