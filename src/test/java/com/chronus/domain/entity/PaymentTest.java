package com.chronus.domain.entity;

import com.chronus.domain.valueobject.PaymentAmount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Payment")
class PaymentTest {

    @Test
    void shouldKeepRequiredPaymentAmount() {
        // Arrange
        PaymentAmount amount = new PaymentAmount(150);

        // Act
        Payment payment = new Payment(amount);

        // Assert
        assertEquals(amount, payment.getAmount());
    }

    @Test
    void shouldUpdatePaymentAmount() {
        // Arrange
        Payment payment = new Payment(new PaymentAmount(150));
        PaymentAmount updatedAmount = new PaymentAmount(200);

        // Act
        payment.updateAmount(updatedAmount);

        // Assert
        assertEquals(updatedAmount, payment.getAmount());
    }
}
