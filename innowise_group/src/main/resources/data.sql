DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(20) UNIQUE,
    password varchar(100) NOT NULL
);

INSERT INTO users (email, password)
VALUES ('user11@mail.com', 'ergrsge123'),
       ('user12@mail.com', 'regegewf'),
       ('user13@mail.com', '3ewfw4t4t3'),
       ('user14@mail.com', 'ergergeaf');