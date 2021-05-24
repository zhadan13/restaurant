package com.example.restaurant.constants;

public final class SQLQuery {
    public static final String CHECK_IF_USER_EXISTS_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    public static final String CHECK_IF_USER_EXISTS_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";
    public static final String CHECK_IF_USER_EXISTS_BY_PHONE_NUMBER = "SELECT * FROM users WHERE phone_number = ?";
    public static final String INSERT_NEW_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    public static final String GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    public static final String GET_ALL_USERS = "SELECT * FROM users";
    public static final String INSERT_NEW_PASSWORD_SALT = "INSERT INTO password_salts VALUES (DEFAULT, ?, ?)";
    public static final String UPDATE_USER_BY_ID = "UPDATE users SET email = ?, password = ?, phone_number = ?, name = ? WHERE id = ?";
    public static final String UPDATE_USER_INFO_BY_ID = "UPDATE users SET email = ?, phone_number = ?, name = ? WHERE id = ?";
    public static final String UPDATE_PASSWORD_SALT = "UPDATE password_salts SET salt = ? WHERE user_id = ?";
    public static final String DELETE_PASSWORD_SALT_BY_ID = "DELETE FROM password_salts WHERE id = ?";
    public static final String GET_PASSWORD_SALT_BY_ID = "SELECT * FROM password_salts WHERE id = ?";
    public static final String GET_PASSWORD_SALT_BY_USER_ID = "SELECT * FROM password_salts WHERE user_id = ?";
    public static final String GET_ALL_PASSWORD_SALTS = "SELECT * FROM password_salts";
    public static final String INSERT_NEW_PRODUCT = "INSERT INTO products VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
    public static final String DELETE_PRODUCT_BY_ID = "DELETE FROM products WHERE id = ?";
    public static final String UPDATE_PRODUCT_BY_ID = "UPDATE products SET name = ?, details = ?, category = ?, price = ?, weight = ?, popularity = ? WHERE id = ?";
    public static final String UPDATE_PRODUCT_POPULARITY_BY_ID = "UPDATE products SET popularity = ? WHERE id = ?";
    public static final String GET_PRODUCT_BY_ID = "SELECT * FROM products WHERE id = ?";
    public static final String GET_ALL_PRODUCTS = "SELECT * FROM products";
    public static final String INSERT_NEW_ORDER = "INSERT INTO orders VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
    public static final String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE id = ?";
    public static final String UPDATE_ORDER_BY_ID = "UPDATE orders SET status = ?, address = ?, payment = ?, cost = ?, date = ? WHERE id = ?";
    public static final String UPDATE_ORDER_STATUS_BY_ID = "UPDATE orders SET status = ? WHERE id = ?";
    public static final String GET_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ?";
    public static final String GET_ALL_ORDERS = "SELECT * FROM orders";
    public static final String GET_ALL_PRODUCTS_ID_BY_ORDER_ID = "SELECT product_id FROM orders_products WHERE order_id = ?";
    public static final String INSERT_NEW_ORDERS_PRODUCTS_BY_ID = "INSERT INTO orders_products VALUES (?, ?)";
    public static final String DELETE_ALL_PRODUCTS_BY_ORDER_ID = "DELETE FROM orders_products WHERE order_id = ?";
    public static final String DELETE_ALL_PRODUCTS_BY_ORDER_AND_PRODUCT_ID = "DELETE FROM orders_products WHERE order_id = ? AND product_id = ?";
    public static final String GET_ALL_ORDERS_PRODUCTS_BY_ORDER_ID = "SELECT * FROM orders_products WHERE order_id = ?";
    public static final String GET_ALL_ORDERS_PRODUCTS = "SELECT * FROM orders_products";
    public static final String GET_ALL_USER_ORDERS_BY_USER_ID = "SELECT * FROM orders WHERE user_id = ? ORDER BY date DESC";
    public static final String GET_UNCOMPLETED_USER_ORDERS_BY_USER_ID = "SELECT * FROM orders WHERE user_id = ? AND status NOT IN ('COMPLETED', 'REJECTED')";

    private SQLQuery() {

    }
}
