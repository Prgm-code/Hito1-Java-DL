package com.chronus.domain.entity;

import com.chronus.domain.exception.InvalidPaymentException;
import com.chronus.domain.valueobject.PaymentAmount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Payment")
class PaymentTest {

    @Test
    void shouldKeepRequiredPaymentIdentityAndAmount() {
        // Arrange
        String paymentId = "1";
        double amount = 150;

        // Act
        Payment payment = new Payment(paymentId, amount);

        // Assert
        assertEquals(paymentId, payment.getPaymentId());
        assertEquals(new PaymentAmount(amount), payment.getAmount());
    }

    @Test
    void shouldUpdatePaymentAmount() {
        // Arrange
        Payment payment = new Payment("1", 150);
        double updatedAmount = 200;

        // Act
        payment.updateAmount(updatedAmount);

        // Assert
        assertEquals("1", payment.getPaymentId());
        assertEquals(new PaymentAmount(updatedAmount), payment.getAmount());
    }

    @Test
    void shouldRejectPaymentWithoutId() {
        // Arrange
        double amount = 150;

        // Act
        InvalidPaymentException exception = assertThrows(
                InvalidPaymentException.class,
                () -> new Payment(null, amount));

        // Assert
        assertEquals("The payment id is required.", exception.getMessage());
    }

    @Test
    void shouldRejectPaymentWithInvalidAmount() {
        // Act
        InvalidPaymentException exception = assertThrows(
                InvalidPaymentException.class,
                () -> new Payment("1", Double.NaN));

        // Assert
        assertEquals("The payment amount must be a positive whole number.", exception.getMessage());
    }
}
