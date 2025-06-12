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
    echo -e "${YELLOW}Enter MySQL root password (press Enter if no password):${NC}"
    
    # Use read with -s flag to hide password input
    read -s -p "" password
    echo  # Add newline after hidden input
    
    # Test MySQL connection with appropriate authentication method
    if [ -z "$password" ]; then
        # Empty password - connect without -p flag
        if ! mysql -u root -e "SELECT 1;" >/dev/null 2>&1; then
            echo -e "${RED}Error: Failed to connect to MySQL with empty password.${NC}"
            exit 1
        fi
        # Run the database.sql file without password
        if mysql -u root < "database.sql" 2>/dev/null; then
            echo -e "${GREEN}Database setup completed successfully!${NC}"
            echo -e "${YELLOW}Note: The database will be seeded automatically when you start the application.${NC}"
        else
            echo -e "${RED}Error: Failed to execute database.sql${NC}"
            exit 1
        fi
    else
        # Non-empty password - connect with -p flag
        if ! mysql -u root -p"$password" -e "SELECT 1;" >/dev/null 2>&1; then
            echo -e "${RED}Error: Failed to connect to MySQL. Please check your password.${NC}"
            exit 1
        fi
        # Run the database.sql file with password
        if mysql -u root -p"$password" < "database.sql" 2>/dev/null; then
            echo -e "${GREEN}Database setup completed successfully!${NC}"
            echo -e "${YELLOW}Note: The database will be seeded automatically when you start the application.${NC}"
        else
            echo -e "${RED}Error: Failed to execute database.sql${NC}"
            exit 1
        fi
    fi
}

# Run the database setup
run_database_setup