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
        PaymentAmount amount = new PaymentAmount(150);

        // Act
        Payment payment = new Payment(paymentId, amount);

        // Assert
        assertEquals(paymentId, payment.getPaymentId());
        assertEquals(amount, payment.getAmount());
    }

    @Test
    void shouldUpdatePaymentAmount() {
        // Arrange
        Payment payment = new Payment("1", new PaymentAmount(150));
        PaymentAmount updatedAmount = new PaymentAmount(200);

        // Act
        payment.updateAmount(updatedAmount);

        // Assert
        assertEquals("1", payment.getPaymentId());
        assertEquals(updatedAmount, payment.getAmount());
    }

    @Test
    void shouldRejectPaymentWithoutId() {
        // Arrange
        PaymentAmount amount = new PaymentAmount(150);

        // Act
        InvalidPaymentException exception = assertThrows(
                InvalidPaymentException.class,
                () -> new Payment(null, amount));

        // Assert
        assertEquals("The payment id is required.", exception.getMessage());
    }
}
