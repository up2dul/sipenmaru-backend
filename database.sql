DROP DATABASE IF EXISTS sipenmaru;

CREATE DATABASE sipenmaru;

USE sipenmaru;

-- USERS TABLE (abstract, untuk user dan admin)
CREATE TABLE
  users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM ('ADMIN', 'USER') NOT NULL,
    token VARCHAR(255) UNIQUE,
    token_expired_at BIGINT
  ) ENGINE = InnoDB;

-- APPLICANTS TABLE (mahasiswa/pendaftar)
CREATE TABLE
  applicants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    registration_code VARCHAR(20) NOT NULL UNIQUE,
    payment_status ENUM ('PAID', 'UNPAID') DEFAULT 'UNPAID',
    selection_status ENUM ('IN_PROGRESS', 'PASSED', 'FAILED') DEFAULT 'IN_PROGRESS',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
  ) ENGINE = InnoDB;

-- BIODATA TABLE (data diri/formulir)
CREATE TABLE
  biodata (
    id INT AUTO_INCREMENT PRIMARY KEY,
    applicant_id INT NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    address VARCHAR(255) NOT NULL,
    gender ENUM ('MALE', 'FEMALE') NOT NULL,
    selected_major VARCHAR(100) NOT NULL,
    diploma_file_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (applicant_id) REFERENCES applicants (id)
  ) ENGINE = InnoDB;

-- PAYMENT TABLE
CREATE TABLE
  payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    applicant_id INT NOT NULL,
    amount DOUBLE NOT NULL,
    payment_method VARCHAR(100) NOT NULL,
    payment_proof_url VARCHAR(255),
    status ENUM ('PENDING', 'COMPLETED', 'INVALID') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (applicant_id) REFERENCES applicants (id)
  ) ENGINE = InnoDB;

-- SELECTION TABLE (Seleksi Administrasi)
CREATE TABLE
  selections (
    id INT AUTO_INCREMENT PRIMARY KEY,
    applicant_id INT NOT NULL,
    result BOOLEAN,
    note VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (applicant_id) REFERENCES applicants (id)
  ) ENGINE = InnoDB;
