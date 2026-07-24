package com.chronus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Payment repository")
class PaymentRepositoryTest {

    @Test
    void shouldStorePaymentAndReturnImmutableCopy() {
        // Arrange
        PaymentRepository paymentRepository = new PaymentRepository();
        Payment payment = new Payment(150);

        // Act
        paymentRepository.save(payment);
        List<Payment> payments = paymentRepository.findAll();

        // Assert
        assertEquals(List.of(payment), payments);
        assertThrows(UnsupportedOperationException.class, () -> payments.add(payment));
    }
}
