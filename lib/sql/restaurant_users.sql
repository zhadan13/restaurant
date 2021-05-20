/*
    DATABASE restaurant
    TABLES users, password_salts
*/

CREATE TABLE IF NOT EXISTS users
(
    id           BIGINT  NOT NULL GENERATED ALWAYS AS IDENTITY,
    email        VARCHAR NOT NULL UNIQUE,
    password     VARCHAR NOT NULL,
    phone_number VARCHAR NOT NULL UNIQUE,
    name         VARCHAR NOT NULL,
    role         VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS password_salts
(
    id      BIGINT  NOT NULL GENERATED ALWAYS AS IDENTITY,
    salt    VARCHAR NOT NULL,
    user_id BIGINT  NOT NULL UNIQUE,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
