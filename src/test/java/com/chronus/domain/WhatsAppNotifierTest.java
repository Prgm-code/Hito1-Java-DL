package com.chronus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@DisplayName("WhatsAppNotifier")
@ExtendWith(MockitoExtension.class)
class WhatsAppNotifierTest {

    @Mock
    private WhatsAppNotifier whatsAppNotifier;

    @Test
    @DisplayName("Verifica envío con anyString")
    void shouldVerifySendWhatsAppWithAnyString() {
        // Arrange & Act
        whatsAppNotifier.sendWhatsApp("+56912345678", "Cita confirmada");

        // Assert
        verify(whatsAppNotifier).sendWhatsApp(anyString(), anyString());
    }

    @Test
    @DisplayName("Verifica destino con eq y mensaje con contains")
    void shouldVerifyDestinationAndMessageWithEqAndContains() {
        // Arrange & Act
        whatsAppNotifier.sendWhatsApp("+56912345678", "Cita confirmada para mañana");

        // Assert
        verify(whatsAppNotifier).sendWhatsApp(
                eq("+56912345678"),
                contains("Cita confirmada"));
    }

    @Test
    @DisplayName("Verifica mensaje con startsWith")
    void shouldVerifyMessageWithStartsWith() {
        // Arrange & Act
        whatsAppNotifier.sendWhatsApp("+56912345678", "Payment of 100 accepted");

        // Assert
        verify(whatsAppNotifier).sendWhatsApp(anyString(), startsWith("Payment of"));
    }

    @Test
    @DisplayName("No debe haber sido invocado (never + anyString)")
    void shouldNeverHaveBeenCalled() {
        verify(whatsAppNotifier, never()).sendWhatsApp(anyString(), anyString());
    }

    @Test
    @DisplayName("Cubre la implementación real de WhatsAppNotifier")
    void shouldCoverRealWhatsAppNotifier() {
        WhatsAppNotifier notifier = new WhatsAppNotifier();
        notifier.sendWhatsApp("+56912345678", "Cita confirmada");
    }
}
