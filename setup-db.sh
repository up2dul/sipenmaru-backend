#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to check if a command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Check if MySQL is installed
if ! command_exists mysql; then
    echo -e "${RED}Error: MySQL is not installed. Please install MySQL first.${NC}"
    exit 1
fi

# Check if database.sql file exists
if [ ! -f "database.sql" ]; then
    echo -e "${RED}Error: database.sql file not found in current directory.${NC}"
    exit 1
fi

# Function to run database setup
run_database_setup() {
    echo -e "${GREEN}Setting up database...${NC}"
    
    # In Docker, we can use the environment variable for the root password
    if mysql -u root -p"${MYSQL_ROOT_PASSWORD}" < "database.sql" 2>/dev/null; then
        echo -e "${GREEN}Database setup completed successfully!${NC}"
    else
        echo -e "${RED}Error: Failed to execute database.sql${NC}"
        exit 1
    fi
}

# Run the database setup
run_database_setup