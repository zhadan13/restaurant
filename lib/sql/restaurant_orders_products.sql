/*
    DATABASE restaurant
    TABLE orders_products
*/

CREATE TABLE IF NOT EXISTS orders_products
(
    order_id   BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);
