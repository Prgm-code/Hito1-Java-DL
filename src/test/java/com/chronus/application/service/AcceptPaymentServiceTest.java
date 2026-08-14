package com.chronus.application.service;

import com.chronus.application.port.EmailNotifier;
import com.chronus.application.port.WhatsAppNotifier;
import com.chronus.application.usecase.AcceptPaymentUseCase;
import com.chronus.domain.entity.Payment;
import com.chronus.domain.exception.InvalidPaymentException;
import com.chronus.domain.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@DisplayName("Accept payment service")
@ExtendWith(MockitoExtension.class)
class AcceptPaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private EmailNotifier emailNotifier;

    @Mock
    private WhatsAppNotifier whatsAppNotifier;

    private AcceptPaymentUseCase acceptPaymentUseCase;

    @BeforeEach
    void setUp() {
        acceptPaymentUseCase = new AcceptPaymentService(
                paymentRepository, emailNotifier, whatsAppNotifier);
    }

    @Test
    void shouldStoreAndNotifyForPositiveWholePayment() {
        // Arrange
        Payment payment = new Payment(150);

        // Act
        acceptPaymentUseCase.acceptPayment(payment);

        // Assert
        verify(paymentRepository).save(payment);
        verify(emailNotifier).sendEmail(
                "patient@chronus.com",
                "Pago de 150 aceptado");
        verify(whatsAppNotifier).sendWhatsApp(
                "+56900000000",
                "Pago de 150 aceptado");
    }

    @Test
    void shouldRejectNegativePaymentWithoutExternalInteractions() {
        // Arrange
        Payment payment = new Payment(-1);

        // Act
        InvalidPaymentException exception = assertThrows(
                InvalidPaymentException.class,
                () -> acceptPaymentUseCase.acceptPayment(payment));

        // Assert
        assertEquals("The payment amount must be a positive whole number.", exception.getMessage());
        verifyNoInteractions(paymentRepository, emailNotifier, whatsAppNotifier);
    }

    @Test
    void shouldRejectZeroPaymentWithoutExternalInteractions() {
        // Arrange
        Payment payment = new Payment(0);

        // Act
        InvalidPaymentException exception = assertThrows(
                InvalidPaymentException.class,
                () -> acceptPaymentUseCase.acceptPayment(payment));

        // Assert
        assertEquals("The payment amount must be a positive whole number.", exception.getMessage());
        verifyNoInteractions(paymentRepository, emailNotifier, whatsAppNotifier);
    }

    @Test
    void shouldRejectFractionalPaymentWithoutExternalInteractions() {
        // Arrange
        Payment payment = new Payment(100.50);

        // Act
        InvalidPaymentException exception = assertThrows(
                InvalidPaymentException.class,
                () -> acceptPaymentUseCase.acceptPayment(payment));

        // Assert
        assertEquals("The payment amount must be a positive whole number.", exception.getMessage());
        verifyNoInteractions(paymentRepository, emailNotifier, whatsAppNotifier);
    }
}
