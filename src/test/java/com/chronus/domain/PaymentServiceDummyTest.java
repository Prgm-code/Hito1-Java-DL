package com.chronus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.chronus.domain.exception.InvalidPaymentException;

@DisplayName("Servicio de Pagos - Dummy")
public class PaymentServiceDummyTest {

    /** Dummy vacío: cascarón de EmailNotifier. */
    private static class DummyEmailNotifier extends EmailNotifier {
        @Override
        public void sendEmail(String email, String message) {
            // es un cascarón
        }
    }

    /** Dummy vacío: cascarón de WhatsAppNotifier. */
    private static class DummyWhatsAppNotifier extends WhatsAppNotifier {
        @Override
        public void sendWhatsApp(String phone, String message) {
            // es un cascarón
        }
    }

    @Test
    @DisplayName("Debe aceptar un pago válido usando dummies")
    void shouldAcceptValidPaymentWithDummyNotifiers() {
        // Arrange
        PaymentRepository repository = new PaymentRepository();
        EmailNotifier dummyEmailNotifier = new DummyEmailNotifier() {
        };
        WhatsAppNotifier dummyWhatsAppNotifier = new DummyWhatsAppNotifier() {
        };
        PaymentService paymentService = new PaymentService(
                repository,
                dummyEmailNotifier,
                dummyWhatsAppNotifier);

        // Act
        paymentService.acceptPayment(new Payment(100));

        // Assert
        assertEquals(1, repository.findAll().size());
    }

    @Test
    @DisplayName("Debe rechazar un pago inválido usando dummies")
    void shouldRejectInvalidPaymentWithDummyNotifiers() {
        // Arrange
        PaymentRepository repository = new PaymentRepository();
        EmailNotifier dummyEmailNotifier = new DummyEmailNotifier() {
        };
        WhatsAppNotifier dummyWhatsAppNotifier = new DummyWhatsAppNotifier() {
        };
        PaymentService paymentService = new PaymentService(
                repository,
                dummyEmailNotifier,
                dummyWhatsAppNotifier);

        // Act
        assertThrows(InvalidPaymentException.class, () -> {
            paymentService.acceptPayment(new Payment(-1));
        });

        // Assert
        assertEquals(0, repository.findAll().size());
    }
}
