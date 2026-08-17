package com.chronus.application.service;

import com.chronus.application.port.EmailNotifier;
import com.chronus.application.port.WhatsAppNotifier;
import com.chronus.application.usecase.AcceptPaymentUseCase;
import com.chronus.domain.entity.Payment;
import com.chronus.domain.repository.PaymentRepository;
import com.chronus.domain.valueobject.PaymentAmount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

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
        Payment payment = new Payment(new PaymentAmount(150));

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
}
