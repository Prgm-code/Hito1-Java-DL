package com.chronus.application.service;

import com.chronus.application.port.EmailNotifier;
import com.chronus.application.port.WhatsAppNotifier;
import com.chronus.application.usecase.AcceptPaymentUseCase;
import com.chronus.domain.entity.Payment;
import com.chronus.domain.repository.PaymentRepository;
import com.chronus.domain.valueobject.PaymentAmount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@DisplayName("Accept payment use case with Mockito")
@ExtendWith(MockitoExtension.class)
class AcceptPaymentUseCaseMockitoTest {

    @Mock
    private EmailNotifier emailNotifier;

    @Mock
    private WhatsAppNotifier whatsAppNotifier;

    @Mock
    private PaymentRepository paymentRepository;

    @Test
    void shouldNotifyBothChannelsForValidPayment() {
        // Arrange
        AcceptPaymentUseCase acceptPaymentUseCase = new AcceptPaymentUseCase(
                paymentRepository,
                emailNotifier,
                whatsAppNotifier);
        Payment payment = new Payment("1", new PaymentAmount(150));

        // Act
        acceptPaymentUseCase.execute(payment);

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
