/*
    DATABASE restaurant
    TABLE orders
*/

CREATE TABLE IF NOT EXISTS orders
(
    id      BIGINT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    user_id BIGINT         NOT NULL,
    status  VARCHAR        NOT NULL,
    address VARCHAR        NOT NULL,
    payment VARCHAR        NOT NULL,
    cost    NUMERIC(10, 2) NOT NULL,
    date    TIMESTAMP      NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
