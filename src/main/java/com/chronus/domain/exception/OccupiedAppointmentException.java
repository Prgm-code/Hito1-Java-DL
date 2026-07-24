package com.chronus.domain.exception;

/**
 * Se lanza cuando se intenta agendar una cita en una fecha/hora
 * que ya está ocupada por otra cita.
 */
public class OccupiedAppointmentException extends RuntimeException {
    public OccupiedAppointmentException(String message) {
        super(message);
    }
}
