package com.chronus.application.service;

import com.chronus.application.port.EmailNotifier;
import com.chronus.application.port.WhatsAppNotifier;
import com.chronus.application.usecase.AcceptPaymentUseCase;
import com.chronus.domain.entity.Payment;
import com.chronus.domain.repository.PaymentRepository;
import com.chronus.domain.valueobject.PaymentAmount;
import com.chronus.infrastructure.persistence.InMemoryPaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Accept payment service with dummies")
class AcceptPaymentServiceDummyTest {

    private static class DummyEmailNotifier implements EmailNotifier {
        @Override
        public void sendEmail(String email, String message) {
        }
    }

    private static class DummyWhatsAppNotifier implements WhatsAppNotifier {
        @Override
        public void sendWhatsApp(String phone, String message) {
        }
    }

    @Test
    void shouldStoreValidPaymentUsingDummyNotifiers() {
        // Arrange
        PaymentRepository paymentRepository = new InMemoryPaymentRepository();
        AcceptPaymentUseCase acceptPaymentUseCase = new AcceptPaymentService(
                paymentRepository,
                new DummyEmailNotifier(),
                new DummyWhatsAppNotifier());
        Payment payment = new Payment(new PaymentAmount(100));

        // Act
        acceptPaymentUseCase.acceptPayment(payment);

        // Assert
        assertEquals(1, paymentRepository.findAll().size());
        assertEquals(payment, paymentRepository.findAll().get(0));
    }
}
