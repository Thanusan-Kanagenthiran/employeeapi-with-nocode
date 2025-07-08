-- Create database
CREATE DATABASE IF NOT EXISTS employee_db;
USE employee_db;

-- Create employee table (structure matches your JPA entity)
CREATE TABLE IF NOT EXISTS employee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL
);

-- Insert sample data
INSERT INTO employee (first_name, last_name, email, department) VALUES
('John', 'Doe', 'john.doe@email.com', 'IT'),
('Jane', 'Smith', 'jane.smith@email.com', 'HR'),
('Alice', 'Brown', 'alice.brown@email.com', 'Finance');
