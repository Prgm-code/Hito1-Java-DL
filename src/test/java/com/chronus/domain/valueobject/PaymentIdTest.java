package com.chronus.domain.valueobject;

import com.chronus.domain.exception.InvalidPaymentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Payment ID value object")
class PaymentIdTest {

    @Test
    void shouldKeepValidPaymentId() {
        PaymentId paymentId = new PaymentId("1");

        assertEquals("1", paymentId.value());
    }

    @Test
    void shouldRejectBlankPaymentId() {
        InvalidPaymentException exception = assertThrows(
                InvalidPaymentException.class,
                () -> new PaymentId(" "));

        assertEquals("The payment id is required.", exception.getMessage());
    }
}
