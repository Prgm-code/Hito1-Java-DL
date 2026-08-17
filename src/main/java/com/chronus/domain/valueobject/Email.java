package com.chronus.domain.valueobject;

import com.chronus.domain.exception.InvalidEmailException;

// record is a immutable value object
public record Email(String value)

{

    // constructor compacto para la auto-validación de la dirección de email
    public Email {

        if (value == null || value.isBlank()) {
            throw new InvalidEmailException("Email is required");
        }

        // normalizar el email antes de validarlo
        String cleanValue = value.trim().toLowerCase();

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!cleanValue.matches(emailRegex)) {
            throw new InvalidEmailException("Invalid email address");
        }

        value = cleanValue;
    }

}
