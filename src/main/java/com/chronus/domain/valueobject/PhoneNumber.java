package com.chronus.domain.valueobject;

import com.chronus.domain.exception.InvalidPhoneNumberException;

// record is an immutable value object
public record PhoneNumber(String value) {

    // ITU-T E.164: '+' required, 7 to 15 digits, no leading zero. WhatsApp uses
    // this format.
    private static final String E164_PATTERN = "^\\+[1-9]\\d{6,14}$";

    // constructor compacto para la auto-validación del número E.164 de WhatsApp
    public PhoneNumber {
        if (value == null || value.isBlank()) {
            throw new InvalidPhoneNumberException("Phone number is required");
        }
        // normalizar el número de teléfono
        String cleanValue = value.trim();

        if (!cleanValue.matches(E164_PATTERN)) {
            throw new InvalidPhoneNumberException("Invalid phone number");
        }

        value = cleanValue;
    }
}
