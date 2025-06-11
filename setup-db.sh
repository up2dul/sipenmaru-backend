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

# Function to show usage
show_usage() {
    echo -e "${YELLOW}Usage:${NC}"
    echo -e "  ./setup-db.sh"
    echo -e "\nDescription:"
    echo -e "  Creates the database and tables for the application."
    echo -e "  The database will be seeded automatically when the application starts."
}

# Check if MySQL is installed
if ! command_exists mysql; then
    echo -e "${RED}Error: MySQL is not installed. Please install MySQL first.${NC}"
    exit 1
fi

# Function to run SQL file
run_sql_file() {
    local file=$1
    local description=$2
    
    echo -e "${GREEN}Running $description...${NC}"
    echo -e "${YELLOW}Enter MySQL root password (or 'q' to cancel):${NC}"
    read -s password
    if [ "$password" = "q" ]; then
        echo -e "${YELLOW}Operation cancelled by user${NC}"
        exit 0
    fi
    if mysql -u root -p"$password" < "$file"; then
        echo -e "${GREEN}Successfully ran $description${NC}"
    else
        echo -e "${RED}Error running $description${NC}"
        exit 1
    fi
}

# Handle command line arguments
case "$1" in
    "help"|"-h"|"--help")
        show_usage
        exit 0
        ;;
    *)
        run_sql_file "database.sql" "database setup"
        ;;
esac

echo -e "${GREEN}Operation completed successfully!${NC}"
echo -e "${YELLOW}Note: The database will be seeded automatically when you start the application.${NC}" 