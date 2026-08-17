package com.chronus.application.usecase;

import com.chronus.application.port.EmailNotifier;
import com.chronus.application.port.WhatsAppNotifier;
import com.chronus.domain.entity.Payment;
import com.chronus.domain.repository.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@DisplayName("Accept payment use case with Mockito")
@ExtendWith(MockitoExtension.class)
class AcceptPaymentUseCaseMockitoInteractionTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private EmailNotifier emailNotifier;

    @Mock
    private WhatsAppNotifier whatsAppNotifier;

    @Test
    void shouldStoreAndNotifyUsingMockitoMocks() {
        // Arrange
        AcceptPaymentUseCase acceptPaymentUseCase = new AcceptPaymentUseCase(
                paymentRepository,
                emailNotifier,
                whatsAppNotifier);
        Payment payment = new Payment("1", 100);

        // Act
        acceptPaymentUseCase.execute(payment);

        // Assert
        verify(paymentRepository).save(payment);
        verify(emailNotifier).sendEmail(
                "patient@chronus.com",
                "Pago de 100 aceptado");
        verify(whatsAppNotifier).sendWhatsApp(
                "+56900000000",
                "Pago de 100 aceptado");
    }
}
