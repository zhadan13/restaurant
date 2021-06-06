package com.example.restaurant.db.dao.exception;

/**
 * Custom exception class for DAO operations.
 *
 * @author Zhadan Artem
 * @see java.lang.Exception
 */

public class DAOException extends Exception {
    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
