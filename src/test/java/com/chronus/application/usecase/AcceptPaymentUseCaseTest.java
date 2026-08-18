package com.chronus.application.usecase;

import com.chronus.application.service.EmailNotifier;
import com.chronus.application.service.WhatsAppNotifier;
import com.chronus.domain.entity.Payment;
import com.chronus.domain.exception.InvalidPaymentException;
import com.chronus.domain.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AcceptPaymentUseCaseTest {

        @Test
        void shouldStoreAndNotifyForPositiveWholePayment() {
                // Arrange: crear el mock de la interfaz de dominio
                PaymentRepository repositoryMock = Mockito.mock(PaymentRepository.class);
                EmailNotifier emailNotifierMock = Mockito.mock(EmailNotifier.class);
                WhatsAppNotifier whatsAppNotifierMock = Mockito.mock(WhatsAppNotifier.class);
                AcceptPaymentUseCase useCase = new AcceptPaymentUseCase(
                                repositoryMock,
                                emailNotifierMock,
                                whatsAppNotifierMock);
                Payment payment = new Payment("1", 150000);

                // Act
                useCase.execute(payment);

                // Assert
                verify(repositoryMock).save(payment);
                verify(emailNotifierMock).sendEmail(
                                "patient@chronus.com",
                                "Pago de 150000 aceptado");
                verify(whatsAppNotifierMock).sendWhatsApp(
                                "+56900000000",
                                "Pago de 150000 aceptado");
        }

        @Test
        void shouldThrowExceptionWhenPaymentAlreadyExists() {
                // Arrange: crear el mock de la interfaz de dominio
                PaymentRepository repositoryMock = Mockito.mock(PaymentRepository.class);
                EmailNotifier emailNotifierMock = Mockito.mock(EmailNotifier.class);
                WhatsAppNotifier whatsAppNotifierMock = Mockito.mock(WhatsAppNotifier.class);
                AcceptPaymentUseCase useCase = new AcceptPaymentUseCase(
                                repositoryMock,
                                emailNotifierMock,
                                whatsAppNotifierMock);

                Payment payment = new Payment("1", 150000);
                when(repositoryMock.findById("1")).thenReturn(Optional.of(payment));
                // Act: ejecutar el use case & Assert: verificar el resultado

                assertThrows(InvalidPaymentException.class, () -> {
                        useCase.execute(payment);
                });

                verify(repositoryMock, never()).save(any());
                verify(emailNotifierMock, never()).sendEmail(anyString(), anyString());
                verify(whatsAppNotifierMock, never()).sendWhatsApp(anyString(), anyString());
        }
}
