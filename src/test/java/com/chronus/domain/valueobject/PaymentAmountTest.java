package com.chronus.domain.valueobject;

import com.chronus.domain.exception.InvalidPaymentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Payment amount value object")
class PaymentAmountTest {

    @Test
    void shouldCreatePositiveWholePaymentAmount() {
        PaymentAmount amount = new PaymentAmount(150);

        assertEquals(150, amount.value());
    }

    @Test
    void shouldRejectNonFinitePaymentAmount() {
        assertInvalidAmount(Double.POSITIVE_INFINITY);
    }

    @Test
    void shouldRejectNonPositivePaymentAmount() {
        assertInvalidAmount(0);
    }

    @Test
    void shouldRejectFractionalPaymentAmount() {
        assertInvalidAmount(100.50);
    }

    private static void assertInvalidAmount(double value) {
        InvalidPaymentException exception = assertThrows(
                InvalidPaymentException.class,
                () -> new PaymentAmount(value));

        assertEquals(
                "The payment amount must be a positive whole number.",
                exception.getMessage());
    }
}
