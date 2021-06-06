package com.example.restaurant.service.exception;

/**
 * Custom exception class for Service operations.
 *
 * @author Zhadan Artem
 * @see java.lang.Exception
 */

public class ServiceException extends Exception {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
