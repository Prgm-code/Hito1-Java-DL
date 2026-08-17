package com.chronus.domain.valueobject;

import com.chronus.domain.exception.InvalidPaymentException;

// Objeto de valor inmutable y autovalidado para el identificador de un pago.
public record PaymentId(String value) {

    public PaymentId {
        if (value == null || value.isBlank()) {
            throw new InvalidPaymentException("The payment id is required.");
        }
        value = value.trim();
    }
}
