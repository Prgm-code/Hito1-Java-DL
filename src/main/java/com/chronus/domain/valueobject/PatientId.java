package com.chronus.domain.valueobject;

import com.chronus.domain.exception.InvalidPatientDataException;

// Objeto de valor inmutable y autovalidado para el identificador de un paciente.
public record PatientId(String value) {

    public PatientId {
        if (value == null || value.isBlank()) {
            throw new InvalidPatientDataException("The patient id is required.");

        }
        String cleanValue = value.trim();
        if (!cleanValue.matches("^[0-9]+$")) {
            throw new InvalidPatientDataException("The patient id must be a number.");
        }
        value = cleanValue;
    }

}
