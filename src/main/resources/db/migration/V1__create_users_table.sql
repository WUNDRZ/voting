CREATE SEQUENCE users_id_seq INCREMENT BY 50;

CREATE TABLE users
(
    id       BIGINT       NOT NULL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE
);
