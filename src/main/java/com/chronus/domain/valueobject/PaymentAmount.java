package com.chronus.domain.valueobject;

import com.chronus.domain.exception.InvalidPaymentException;

// Objeto de valor inmutable y autovalidado para el monto de un pago.
public record PaymentAmount(double value) {

    public PaymentAmount {
        if (!Double.isFinite(value) || value <= 0 || value != Math.floor(value)) {
            throw new InvalidPaymentException(
                    "The payment amount must be a positive whole number.");
        }
        // el value se asigna directamente al constructor, no se necesita sanitizar
        // value = value;
    }
}
