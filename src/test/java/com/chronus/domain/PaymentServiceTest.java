package com.chronus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.chronus.domain.exception.InvalidPaymentException;

@DisplayName("Servicio de Pagos")
public class PaymentServiceTest {

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

    private final PaymentRepository paymentRepository = new PaymentRepository();
    private final EmailNotifier dummyEmailNotifier = new DummyEmailNotifier() {
    };
    private final WhatsAppNotifier dummyWhatsAppNotifier = new DummyWhatsAppNotifier() {
    };
    private final PaymentService paymentService = new PaymentService(
            paymentRepository,
            dummyEmailNotifier,
            dummyWhatsAppNotifier);

    @Test
    // Acepta y guarda un pago válido.
    void shouldAcceptPayment() {
        paymentService.acceptPayment(new Payment(100));
        assertEquals(1, paymentRepository.findAll().size());
    }

    @Test
    // Rechaza un pago con un monto inválido.
    void shouldRejectPayment() {
        assertThrows(InvalidPaymentException.class, () -> {
            paymentService.acceptPayment(new Payment(-1));
        });
    }

    @Test
    // Rechaza un pago con un monto negativo.
    void shouldRejectPaymentWithInvalidAmount() {
        assertThrows(InvalidPaymentException.class, () -> {
            paymentService.acceptPayment(new Payment(-100));
        });
        assertEquals(0, paymentRepository.findAll().size());
    }

    @Test
    // Rechaza un pago con un monto cero.
    void shouldRejectPaymentWithZeroAmount() {
        assertThrows(InvalidPaymentException.class, () -> {
            paymentService.acceptPayment(new Payment(0));
        });
        assertEquals(0, paymentRepository.findAll().size());
    }

    @Test
    // Rechaza un pago con un monto decimal.
    void shouldRejectPaymentWithDecimalAmount() {
        assertThrows(InvalidPaymentException.class, () -> {
            paymentService.acceptPayment(new Payment(100.50));
        });
        assertEquals(0, paymentRepository.findAll().size());
    }
}
