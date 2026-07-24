package com.chronus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.chronus.domain.exception.InvalidPaymentException;

@DisplayName("Servicio de Pagos - Mockito")
@ExtendWith(MockitoExtension.class)
public class PaymentServiceMockitoTest {

    @Mock
    private EmailNotifier emailNotifier;

    @Mock
    private WhatsAppNotifier whatsAppNotifier;

    @Test
    @DisplayName("Debe notificar por email y WhatsApp al aceptar un pago válido")
    void shouldNotifyWhenAcceptingValidPayment() {
        // Arrange
        PaymentRepository repository = new PaymentRepository();
        PaymentService paymentService = new PaymentService(
                repository,
                emailNotifier,
                whatsAppNotifier);

        // Act
        paymentService.acceptPayment(new Payment(150));

        // Assert
        assertEquals(1, repository.findAll().size());
        verify(emailNotifier).sendEmail(anyString(), anyString());
        verify(emailNotifier).sendEmail(eq("patient@chronus.com"), startsWith("Payment of"));
        verify(emailNotifier).sendEmail(anyString(), contains("150"));
        verify(whatsAppNotifier).sendWhatsApp(anyString(), anyString());
        verify(whatsAppNotifier).sendWhatsApp(eq("+56900000000"), contains("150"));
    }

    @Test
    @DisplayName("No debe notificar si el monto es inválido")
    void shouldNotNotifyWhenPaymentAmountIsInvalid() {
        // Arrange
        PaymentRepository repository = new PaymentRepository();
        PaymentService paymentService = new PaymentService(
                repository,
                emailNotifier,
                whatsAppNotifier);

        // Act & Assert
        assertThrows(InvalidPaymentException.class, () -> {
            paymentService.acceptPayment(new Payment(-10));
        });
        assertEquals(0, repository.findAll().size());
        verify(emailNotifier, never()).sendEmail(anyString(), anyString());
        verify(whatsAppNotifier, never()).sendWhatsApp(anyString(), anyString());
    }

    @Test
    @DisplayName("No debe notificar si el monto es cero")
    void shouldNotNotifyWhenPaymentAmountIsZero() {
        // Arrange
        PaymentRepository repository = new PaymentRepository();
        PaymentService paymentService = new PaymentService(
                repository,
                emailNotifier,
                whatsAppNotifier);

        // Act & Assert
        assertThrows(InvalidPaymentException.class, () -> {
            paymentService.acceptPayment(new Payment(0));
        });
        assertEquals(0, repository.findAll().size());
        verify(emailNotifier, never()).sendEmail(anyString(), anyString());
        verify(whatsAppNotifier, never()).sendWhatsApp(anyString(), anyString());
    }

    @Test
    @DisplayName("No debe notificar si el monto es decimal")
    void shouldNotNotifyWhenPaymentAmountIsDecimal() {
        // Arrange
        PaymentRepository repository = new PaymentRepository();
        PaymentService paymentService = new PaymentService(
                repository,
                emailNotifier,
                whatsAppNotifier);

        // Act & Assert
        assertThrows(InvalidPaymentException.class, () -> {
            paymentService.acceptPayment(new Payment(100.50));
        });
        assertEquals(0, repository.findAll().size());
        verify(emailNotifier, never()).sendEmail(anyString(), anyString());
        verify(whatsAppNotifier, never()).sendWhatsApp(anyString(), anyString());
    }
}
