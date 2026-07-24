package com.chronus.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@DisplayName("Appointment reminder")
@ExtendWith(MockitoExtension.class)
class AppointmentReminderTest {

    @Mock
    private EmailNotifier emailNotifier;

    @Mock
    private WhatsAppNotifier whatsAppNotifier;

    private AppointmentReminder appointmentReminder;

    @BeforeEach
    void setUp() {
        appointmentReminder = new AppointmentReminder(emailNotifier, whatsAppNotifier);
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
        appointmentReminder.sendReminder(patient, appointment);

        // Assert
        verify(emailNotifier).sendEmail("juanito.perez@example.com", message);
        verify(whatsAppNotifier).sendWhatsApp("+56912345678", message);
    }
}
