package com.chronus.application.usecase;

import com.chronus.application.port.EmailNotifier;
import com.chronus.application.port.WhatsAppNotifier;
import com.chronus.domain.entity.Appointment;
import com.chronus.domain.entity.Patient;
import com.chronus.domain.exception.InvalidPatientDataException;
import com.chronus.domain.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SendAppointmentReminderUseCaseTest {

        @Test
        void shouldSendReminderUsingPatientContactData() {
                // Arrange: crear el mock de la interfaz de dominio
                PatientRepository repositoryMock = Mockito.mock(PatientRepository.class);
                EmailNotifier emailNotifierMock = Mockito.mock(EmailNotifier.class);
                WhatsAppNotifier whatsAppNotifierMock = Mockito.mock(WhatsAppNotifier.class);
                SendAppointmentReminderUseCase useCase = new SendAppointmentReminderUseCase(
                                repositoryMock,
                                emailNotifierMock,
                                whatsAppNotifierMock);
                Patient patient = new Patient(
                                "123",
                                "Juanito Pérez",
                                "juanito.perez@example.com",
                                "+56912345678");
                LocalDateTime dateTime = LocalDateTime.now().plusDays(1).withNano(0);
                Appointment appointment = new Appointment("1", dateTime);
                String message = "Recordatorio: su cita está programada para " + dateTime + ".";

                // Act
                when(repositoryMock.findById("123")).thenReturn(Optional.of(patient));
                useCase.execute("123", appointment);

                // Assert
                verify(repositoryMock).findById("123");
                verify(emailNotifierMock).sendEmail("juanito.perez@example.com", message);
                verify(whatsAppNotifierMock).sendWhatsApp("+56912345678", message);
        }

        @Test
        void shouldThrowExceptionWhenPatientDoesNotExist() {
                // Arrange: crear el mock de la interfaz de dominio
                PatientRepository repositoryMock = Mockito.mock(PatientRepository.class);
                EmailNotifier emailNotifierMock = Mockito.mock(EmailNotifier.class);
                WhatsAppNotifier whatsAppNotifierMock = Mockito.mock(WhatsAppNotifier.class);
                SendAppointmentReminderUseCase useCase = new SendAppointmentReminderUseCase(
                                repositoryMock,
                                emailNotifierMock,
                                whatsAppNotifierMock);

                Appointment appointment = new Appointment(
                                "1",
                                LocalDateTime.now().plusDays(1));
                when(repositoryMock.findById("999")).thenReturn(Optional.empty());

                // Act: ejecutar el use case & Assert: verificar el resultado
                assertThrows(InvalidPatientDataException.class, () -> {
                        useCase.execute("999", appointment);
                });

                verify(emailNotifierMock, never()).sendEmail(anyString(), anyString());
                verify(whatsAppNotifierMock, never()).sendWhatsApp(anyString(), anyString());
        }
}
