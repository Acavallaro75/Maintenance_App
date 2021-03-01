CREATE DATABASE IF NOT EXISTS GIBA_EMPLOYEES;

CREATE TABLE IF NOT EXISTS EMPLOYEES
(
    employee_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    first_name  VARCHAR(255)    NOT NULL,
    last_name   VARCHAR(255)    NOT NULL,
    username    VARCHAR(255)    NOT NULL,
    password    VARCHAR(255)    NOT NULL
);

INSERT INTO GIBA_EMPLOYEES.employees (first_name, last_name, username, password)
VALUES ('Roger', 'Jones', 'TBD', 'TBD'),
       ('Scot', 'Van Atten', 'TBD', 'TBD'),
       ('Andrew', 'Cavallaro', 'acavallaro', 'ashley75'),
       ('Kathy', 'Banson', 'TBD', 'TBD');

CREATE DATABASE IF NOT EXISTS MAINTENANCE;

CREATE TABLE IF NOT EXISTS TASKS
(
    task_id         INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    employee_id     INT             NOT NULL,
    task_name       VARCHAR(255)    NOT NULL,
    frequency       VARCHAR(255)    NOT NULL,
    details         VARCHAR(255)    NOT NULL,
    completion_date DATE            NOT NULL,
    next_date       DATE            NOT NULL
);

INSERT INTO MAINTENANCE.tasks (task_name, frequency, details, completion_date, next_date)
VALUES ();