package com.test.atos.exception;

/**
 * Exception thrown when validation of input data fails.
 */
public class ValidationException extends RuntimeException {
    /**
     * Constructs a new ValidationException with the specified detail message.
     *
     * @param message The detail message.
     */
    public ValidationException(String message) {
        super(message);
    }
}
