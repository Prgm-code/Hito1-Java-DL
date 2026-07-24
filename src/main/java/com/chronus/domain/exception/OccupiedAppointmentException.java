package com.chronus.domain.exception;

/**
 * Raised when an appointment is requested for an occupied date and time.
 */
public class OccupiedAppointmentException extends RuntimeException {
    public OccupiedAppointmentException(String message) {
        super(message);
    }
}
