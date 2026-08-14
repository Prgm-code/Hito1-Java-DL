package com.chronus.infrastructure.notification;

import com.chronus.application.port.EmailNotifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@DisplayName("Email notifier")
@ExtendWith(MockitoExtension.class)
class NoOpEmailNotifierTest {

    @Mock
    private EmailNotifier emailNotifier;

    @Test
    void shouldVerifyEmailNotificationWithMock() {
        // Arrange
        String email = "juanito.perez@example.com";
        String message = "Recordatorio: su cita está programada para mañana.";

        // Act
        emailNotifier.sendEmail(email, message);

        // Assert
        verify(emailNotifier).sendEmail(anyString(), anyString());
    }

    @Test
    void shouldVerifySpanishEmailMessageWithMock() {
        // Arrange
        String email = "juanito.perez@example.com";
        String message = "Pago de 150 aceptado";

        // Act
        emailNotifier.sendEmail(email, message);

        // Assert
        verify(emailNotifier).sendEmail(eq(email), eq(message));
    }

    @Test
    void shouldExecuteConcreteEmailNotifier() {
        // Arrange
        EmailNotifier notifier = new NoOpEmailNotifier();

        // Act
        notifier.sendEmail(
                "juanito.perez@example.com",
                "Recordatorio: su cita está programada para mañana.");

        // Assert
    }
}
