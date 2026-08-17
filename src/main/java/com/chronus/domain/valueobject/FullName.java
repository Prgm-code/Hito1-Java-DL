package com.chronus.domain.valueobject;

import com.chronus.domain.exception.InvalidPatientDataException;

// Objeto de valor inmutable y autovalidado para el nombre completo de un paciente.
public record FullName(String value) {

    public FullName {
        if (value == null || value.isBlank()) {
            throw new InvalidPatientDataException("The patient full name is required.");
        }
        String cleanValue = value.trim();
        String fullNameRegex = "^[\\p{L}]+(?:[ '-][\\p{L}]+)*$";
        if (!cleanValue.matches(fullNameRegex)) {
            throw new InvalidPatientDataException("The patient full name has an invalid format.");
        }
        value = cleanValue;
    }
}
