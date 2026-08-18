package com.chronus.infrastructure.persistence;

import com.chronus.domain.entity.Payment;
import com.chronus.domain.repository.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("In-memory payment repository")
class InMemoryPaymentRepositoryTest {

    @Test
    void shouldStorePaymentAndReturnImmutableCopy() {
        // Arrange
        PaymentRepository paymentRepository = new InMemoryPaymentRepository();
        Payment payment = new Payment("1", 150);

        // Act
        paymentRepository.save(payment);
        List<Payment> payments = paymentRepository.findAll();

        // Assert
        assertEquals(List.of(payment), payments);
        assertThrows(UnsupportedOperationException.class, () -> payments.add(payment));
    }

    @Test
    void shouldFindPaymentById() {
        // Arrange
        PaymentRepository paymentRepository = new InMemoryPaymentRepository();
        Payment payment = new Payment("1", 150);
        paymentRepository.save(payment);

        // Act
        Optional<Payment> foundPayment = paymentRepository.findById("1");

        // Assert
        assertEquals(Optional.of(payment), foundPayment);
    }

    @Test
    void shouldReturnEmptyWhenPaymentIdDoesNotExist() {
        // Arrange
        PaymentRepository paymentRepository = new InMemoryPaymentRepository();

        // Act
        Optional<Payment> foundPayment = paymentRepository.findById("999");

        // Assert
        assertTrue(foundPayment.isEmpty());
    }
}
