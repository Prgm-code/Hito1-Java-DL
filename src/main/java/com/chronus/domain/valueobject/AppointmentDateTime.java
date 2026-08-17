package com.chronus.domain.valueobject;

import com.chronus.domain.exception.InvalidDateAppointmentException;

import java.time.LocalDateTime;

// Objeto de valor inmutable y autovalidado para la fecha y hora de una cita.
public record AppointmentDateTime(LocalDateTime value) {

    public AppointmentDateTime {
        if (value == null) {
            throw new InvalidDateAppointmentException(
                    "The appointment date and time is required.");
        }
        if (!value.isAfter(LocalDateTime.now())) {
            throw new InvalidDateAppointmentException(
                    "The appointment date and time must be in the future.");
        }
        value = value.withNano(0);
    }
}
