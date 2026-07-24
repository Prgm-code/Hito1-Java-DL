package com.chronus.domain.exception;

/**
 * Raised when a patient is created without required contact data.
 */
public class InvalidPatientDataException extends RuntimeException {
    public InvalidPatientDataException(String message) {
        super(message);
    }
}
