/*
    SQL Scripts to create tables users, password_salts, users_tokens
    DATABASE restaurant
*/

/*
    TABLE users
*/

CREATE TABLE IF NOT EXISTS users
(
    id           BIGINT  NOT NULL GENERATED ALWAYS AS IDENTITY,
    email        VARCHAR NOT NULL UNIQUE,
    password     VARCHAR NOT NULL,
    phone_number VARCHAR NOT NULL UNIQUE,
    name         VARCHAR NOT NULL,
    role         VARCHAR NOT NULL,
    authorized   BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

/*
    TABLE password_salts
*/

CREATE TABLE IF NOT EXISTS password_salts
(
    id      BIGINT  NOT NULL GENERATED ALWAYS AS IDENTITY,
    salt    VARCHAR NOT NULL,
    user_id BIGINT  NOT NULL UNIQUE,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

/*
    TABLE users_tokens
*/

CREATE TABLE IF NOT EXISTS users_tokens
(
    id      BIGINT  NOT NULL GENERATED ALWAYS AS IDENTITY,
    user_id BIGINT  NOT NULL UNIQUE,
    token   VARCHAR NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
