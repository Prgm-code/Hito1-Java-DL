package com.chronus.domain.valueobject;

import com.chronus.domain.exception.InvalidDateAppointmentException;

// Objeto de valor inmutable y autovalidado para el identificador de una cita.
public record AppointmentId(String value) {

    public AppointmentId {
        if (value == null || value.isBlank()) {
            throw new InvalidDateAppointmentException("The appointment id is required.");
        }
        value = value.trim();
    }
}
