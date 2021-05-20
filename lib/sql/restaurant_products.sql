/*
    DATABASE restaurant
    TABLE products
*/

CREATE TABLE IF NOT EXISTS products
(
    id         BIGINT        NOT NULL GENERATED ALWAYS AS IDENTITY,
    name       VARCHAR       NOT NULL UNIQUE,
    details    VARCHAR       NOT NULL,
    category   VARCHAR       NOT NULL,
    price      NUMERIC(8, 2) NOT NULL,
    weight     NUMERIC(8, 2) NOT NULL,
    popularity INTEGER       NOT NULL,
    PRIMARY KEY (id)
);
