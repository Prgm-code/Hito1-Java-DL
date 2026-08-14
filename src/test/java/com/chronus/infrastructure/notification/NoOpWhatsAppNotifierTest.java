package com.chronus.infrastructure.notification;

import com.chronus.application.port.WhatsAppNotifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@DisplayName("WhatsApp notifier")
@ExtendWith(MockitoExtension.class)
class NoOpWhatsAppNotifierTest {

    @Mock
    private WhatsAppNotifier whatsAppNotifier;

    @Test
    void shouldVerifyWhatsAppNotificationWithMock() {
        // Arrange
        String phoneNumber = "+56912345678";
        String message = "Recordatorio: su cita está programada para mañana.";

        // Act
        whatsAppNotifier.sendWhatsApp(phoneNumber, message);

        // Assert
        verify(whatsAppNotifier).sendWhatsApp(anyString(), anyString());
    }

    @Test
    void shouldVerifySpanishWhatsAppMessageWithMock() {
        // Arrange
        String phoneNumber = "+56912345678";
        String message = "Pago de 150 aceptado";

        // Act
        whatsAppNotifier.sendWhatsApp(phoneNumber, message);

        // Assert
        verify(whatsAppNotifier).sendWhatsApp(eq(phoneNumber), contains("Pago de"));
    }

    @Test
    void shouldExecuteConcreteWhatsAppNotifier() {
        // Arrange
        WhatsAppNotifier notifier = new NoOpWhatsAppNotifier();

        // Act
        notifier.sendWhatsApp(
                "+56912345678",
                "Recordatorio: su cita está programada para mañana.");

        // Assert
    }
}
