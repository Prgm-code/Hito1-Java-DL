package com.chronus.domain.exception;

public class InvalidDateAppointmentException extends RuntimeException {
    public InvalidDateAppointmentException(String message) {
        super(message);
    }
}
