package com.chronus.application.service;

import com.chronus.application.port.EmailNotifier;
import com.chronus.application.port.WhatsAppNotifier;
import com.chronus.application.usecase.SendAppointmentReminderUseCase;
import com.chronus.domain.entity.Appointment;
import com.chronus.domain.entity.Patient;
import com.chronus.domain.exception.InvalidPatientDataException;
import com.chronus.domain.repository.PatientRepository;
import com.chronus.domain.valueobject.AppointmentDateTime;
import com.chronus.domain.valueobject.Email;
import com.chronus.domain.valueobject.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Send appointment reminder use case")
@ExtendWith(MockitoExtension.class)
class SendAppointmentReminderUseCaseTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private EmailNotifier emailNotifier;

    @Mock
    private WhatsAppNotifier whatsAppNotifier;

    private SendAppointmentReminderUseCase sendAppointmentReminderUseCase;

    @BeforeEach
    void setUp() {
        sendAppointmentReminderUseCase = new SendAppointmentReminderUseCase(
                patientRepository, emailNotifier, whatsAppNotifier);
    }

    @Test
    void shouldSendReminderUsingPatientContactData() {
        // Arrange
        Patient patient = new Patient(
                "123",
                "Juanito Pérez",
                new Email("juanito.perez@example.com"),
                new PhoneNumber("+56912345678"));
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1).withNano(0);
        Appointment appointment = new Appointment("1", new AppointmentDateTime(dateTime));
        String message = "Recordatorio: su cita está programada para " + dateTime + ".";
        when(patientRepository.findById("123")).thenReturn(patient);

        // Act
        sendAppointmentReminderUseCase.execute("123", appointment);

        // Assert
        verify(patientRepository).findById("123");
        verify(emailNotifier).sendEmail("juanito.perez@example.com", message);
        verify(whatsAppNotifier).sendWhatsApp("+56912345678", message);
    }

    @Test
    void shouldRejectReminderWhenPatientDoesNotExist() {
        // Arrange
        Appointment appointment = new Appointment(
                "1",
                new AppointmentDateTime(LocalDateTime.now().plusDays(1)));
        when(patientRepository.findById("999")).thenReturn(null);

        // Act
        InvalidPatientDataException exception = assertThrows(
                InvalidPatientDataException.class,
                () -> sendAppointmentReminderUseCase.execute("999", appointment));

        // Assert
        assertEquals("The patient was not found.", exception.getMessage());
        verify(patientRepository).findById("999");
        verify(emailNotifier, never()).sendEmail(anyString(), anyString());
        verify(whatsAppNotifier, never()).sendWhatsApp(anyString(), anyString());
    }
}
