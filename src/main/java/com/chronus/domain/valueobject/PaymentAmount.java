package com.chronus.domain.valueobject;

import com.chronus.domain.exception.InvalidPaymentException;

// Objeto de valor inmutable y autovalidado para el monto de un pago.
public record PaymentAmount(double value) {

    public PaymentAmount {
        double cleanValue = Math.floor(value);
        if (!Double.isFinite(cleanValue) || cleanValue <= 0 || cleanValue != Math.floor(cleanValue)) {
            throw new InvalidPaymentException(
                    "The payment amount must be a positive whole number.");
        }

        value = cleanValue;
    }
}
