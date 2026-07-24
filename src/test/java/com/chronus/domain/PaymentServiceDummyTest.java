package com.chronus.domain;

import com.chronus.domain.exception.InvalidPaymentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Payment service with dummies")
class PaymentServiceDummyTest {

    private static class DummyEmailNotifier extends EmailNotifier {
        @Override
        public void sendEmail(String email, String message) {
        }
    }

    private static class DummyWhatsAppNotifier extends WhatsAppNotifier {
        @Override
        public void sendWhatsApp(String phone, String message) {
        }
    }

    @Test
    void shouldStoreValidPaymentUsingDummyNotifiers() {
        // Arrange
        PaymentRepository paymentRepository = new PaymentRepository();
        PaymentService paymentService = new PaymentService(
                paymentRepository,
                new DummyEmailNotifier(),
                new DummyWhatsAppNotifier());
        Payment payment = new Payment(100);

        // Act
        paymentService.acceptPayment(payment);

        // Assert
        assertEquals(1, paymentRepository.findAll().size());
        assertEquals(payment, paymentRepository.findAll().get(0));
    }

    @Test
    void shouldRejectInvalidPaymentUsingDummyNotifiers() {
        // Arrange
        PaymentRepository paymentRepository = new PaymentRepository();
        PaymentService paymentService = new PaymentService(
                paymentRepository,
                new DummyEmailNotifier(),
                new DummyWhatsAppNotifier());

        // Act
        InvalidPaymentException exception = assertThrows(
                InvalidPaymentException.class,
                () -> paymentService.acceptPayment(new Payment(-1)));

        // Assert
        assertEquals("The payment amount must be a positive whole number.", exception.getMessage());
        assertEquals(0, paymentRepository.findAll().size());
    }
}
