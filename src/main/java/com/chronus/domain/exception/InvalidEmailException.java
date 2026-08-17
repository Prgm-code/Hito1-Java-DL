package com.chronus.domain.exception;

/**
 * Raised when a patient email is missing or does not have a valid format.
 */
public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
