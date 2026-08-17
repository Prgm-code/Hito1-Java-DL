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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@DisplayName("Accept payment service with Mockito")
@ExtendWith(MockitoExtension.class)
class AcceptPaymentServiceMockitoTest {

    @Mock
    private EmailNotifier emailNotifier;

    @Mock
    private WhatsAppNotifier whatsAppNotifier;

    @Test
    void shouldNotifyBothChannelsForValidPayment() {
        // Arrange
        PaymentRepository paymentRepository = new InMemoryPaymentRepository();
        AcceptPaymentUseCase acceptPaymentUseCase = new AcceptPaymentService(
                paymentRepository,
                emailNotifier,
                whatsAppNotifier);

        // Act
        acceptPaymentUseCase.acceptPayment(new Payment(new PaymentAmount(150)));

        // Assert
        assertEquals(1, paymentRepository.findAll().size());
        verify(emailNotifier).sendEmail(
                eq("patient@chronus.com"),
                contains("Pago de 150"));
        verify(whatsAppNotifier).sendWhatsApp(
                eq("+56900000000"),
                contains("Pago de 150"));
    }
}
