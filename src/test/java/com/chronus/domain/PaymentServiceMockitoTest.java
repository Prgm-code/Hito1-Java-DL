package com.chronus.domain;

import com.chronus.domain.exception.InvalidPaymentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@DisplayName("Payment service with Mockito")
@ExtendWith(MockitoExtension.class)
class PaymentServiceMockitoTest {

    @Mock
    private EmailNotifier emailNotifier;

    @Mock
    private WhatsAppNotifier whatsAppNotifier;

    @Test
    void shouldNotifyBothChannelsForValidPayment() {
        // Arrange
        PaymentRepository paymentRepository = new PaymentRepository();
        PaymentService paymentService = new PaymentService(
                paymentRepository,
                emailNotifier,
                whatsAppNotifier);

        // Act
        paymentService.acceptPayment(new Payment(150));

        // Assert
        assertEquals(1, paymentRepository.findAll().size());
        verify(emailNotifier).sendEmail(
                eq("patient@chronus.com"),
                contains("Pago de 150"));
        verify(whatsAppNotifier).sendWhatsApp(
                eq("+56900000000"),
                contains("Pago de 150"));
    }

    @Test
    void shouldNotNotifyForInvalidPayment() {
        // Arrange
        PaymentRepository paymentRepository = new PaymentRepository();
        PaymentService paymentService = new PaymentService(
                paymentRepository,
                emailNotifier,
                whatsAppNotifier);

        // Act
        InvalidPaymentException exception = assertThrows(
                InvalidPaymentException.class,
                () -> paymentService.acceptPayment(new Payment(0)));

        // Assert
        assertEquals("The payment amount must be a positive whole number.", exception.getMessage());
        assertEquals(0, paymentRepository.findAll().size());
        verify(emailNotifier, never()).sendEmail(
                org.mockito.ArgumentMatchers.anyString(),
                org.mockito.ArgumentMatchers.anyString());
        verify(whatsAppNotifier, never()).sendWhatsApp(
                org.mockito.ArgumentMatchers.anyString(),
                org.mockito.ArgumentMatchers.anyString());
    }
}
