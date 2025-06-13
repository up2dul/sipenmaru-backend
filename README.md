<div align="center">
  <h1>🎓 Sipenmaru API</h1>
</div>

> ℹ️ Sipenmaru means "Sistem Pendaftaran Mahasiswa Baru" in Indonesian.

> 🌐 Shipped on [Azure](https://azure.com).

## Table of Contents

- [Table of Contents](#table-of-contents)
- [💻 Tech Stack](#-tech-stack)
- [📚 API Documentation/Specification](#-api-documentationspecification)
- [🟠 Postman Collection](#-postman-collection)
- [Pre-requisites](#pre-requisites)
- [Setup Instructions](#setup-instructions)
  - [1. Clone the Repository](#1-clone-the-repository)
  - [2. Run the `setup-db.sh` script for setting up the database](#2-run-the-setup-dbsh-script-for-setting-up-the-database)
  - [3. Update some `application.properties` values with your own](#3-update-some-applicationproperties-values-with-your-own)
  - [4. Build and run the application](#4-build-and-run-the-application)
  - [5. Access the API](#5-access-the-api)

## 💻 Tech Stack

- Java
- Spring Boot
- Maven
- MySQL

## 📚 API Documentation/Specification

- [Admin](docs/api-admin.md)
- [Applicant](docs/api-applicant.md)
- [Authentication](docs/api-auth.md)

## 🟠 Postman Collection

- [Sipenmaru API](https://www.postman.com/lively-zodiac-424440/workspace/sipenmaru)

## Pre-requisites

- Java `>=17`
- MySQL `>=8.0`
- Maven `>=3.6`

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/up2dul/sipenmaru-backend.git

# or if using ssh
git clone git@github.com:up2dul/sipenmaru-backend.git
```

### 2. Run the `setup-db.sh` script for setting up the database

```bash
chmod +x setup-db.sh
./setup-db.sh

# or if directly using bash
bash setup-db.sh
```

### 3. Update some [`application.properties`](src/main/resources/application.properties) values with your own

```properties
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.datasource.url=jdbc:mysql://localhost:3306/sipenmaru
jwt.secret=YOUR_JWT_SECRET
```
You don't need to update the other properties.

### 4. Build and run the application

```bash
mvn clean package

mvn spring-boot:run
```

### 5. Access the API

Now you can access the API at `http://localhost:8080/api/`. Check the [API Documentation/Specification](#-api-documentationspecification) or [Postman Collection](#-postman-collection) for more information about the endpoints.
