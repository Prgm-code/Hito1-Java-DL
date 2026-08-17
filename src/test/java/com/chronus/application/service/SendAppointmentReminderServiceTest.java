package com.chronus.application.service;

import com.chronus.application.port.EmailNotifier;
import com.chronus.application.port.WhatsAppNotifier;
import com.chronus.application.usecase.SendAppointmentReminderUseCase;
import com.chronus.domain.entity.Appointment;
import com.chronus.domain.entity.Patient;
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
                "123",
                "Juanito Pérez",
                new Email("juanito.perez@example.com"),
                new PhoneNumber("+56912345678"));
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1).withNano(0);
        Appointment appointment = new Appointment("1", new AppointmentDateTime(dateTime));
        String message = "Recordatorio: su cita está programada para " + dateTime + ".";

        // Act
        sendAppointmentReminderUseCase.sendReminder(patient, appointment);

        // Assert
        verify(emailNotifier).sendEmail("juanito.perez@example.com", message);
        verify(whatsAppNotifier).sendWhatsApp("+56912345678", message);
    }
}
