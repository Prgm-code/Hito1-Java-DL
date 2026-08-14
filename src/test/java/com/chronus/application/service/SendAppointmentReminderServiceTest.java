package com.chronus.application.service;

import com.chronus.application.port.EmailNotifier;
import com.chronus.application.port.WhatsAppNotifier;
import com.chronus.application.usecase.SendAppointmentReminderUseCase;
import com.chronus.domain.entity.Appointment;
import com.chronus.domain.entity.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@DisplayName("Send appointment reminder service")
@ExtendWith(MockitoExtension.class)
class SendAppointmentReminderServiceTest {

    @Mock
    private EmailNotifier emailNotifier;

    @Mock
    private WhatsAppNotifier whatsAppNotifier;

    private SendAppointmentReminderUseCase sendAppointmentReminderUseCase;

    @BeforeEach
    void setUp() {
        sendAppointmentReminderUseCase = new SendAppointmentReminderService(
                emailNotifier, whatsAppNotifier);
    }

    @Test
    void shouldSendReminderUsingPatientContactData() {
        // Arrange
        Patient patient = new Patient(
                "Juanito Pérez",
                "juanito.perez@example.com",
                "+56912345678");
        Appointment appointment = new Appointment(LocalDateTime.of(2026, 8, 1, 10, 30));
        String message = "Recordatorio: su cita está programada para 2026-08-01T10:30.";

        // Act
        sendAppointmentReminderUseCase.sendReminder(patient, appointment);

        // Assert
        verify(emailNotifier).sendEmail("juanito.perez@example.com", message);
        verify(whatsAppNotifier).sendWhatsApp("+56912345678", message);
    }
}
