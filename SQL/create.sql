CREATE DATABASE IF NOT EXISTS GIBA_EMPLOYEES;

CREATE TABLE IF NOT EXISTS EMPLOYEES
(
    employee_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    first_name  VARCHAR(255)    NOT NULL,
    last_name   VARCHAR(255)    NOT NULL,
    username    VARCHAR(255)    NOT NULL,
    password    VARCHAR(255)    NOT NULL
);

CREATE DATABASE IF NOT EXISTS MAINTENANCE;

CREATE TABLE IF NOT EXISTS TASKS
(
    task_id         INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    task_name       VARCHAR(255),
    frequency       VARCHAR(255),
    details         VARCHAR(255),
    completion_date DATE,
    next_date       DATE
);