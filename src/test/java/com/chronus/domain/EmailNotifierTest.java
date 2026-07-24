package com.chronus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@DisplayName("EmailNotifier")
@ExtendWith(MockitoExtension.class)
class EmailNotifierTest {

    @Mock
    private EmailNotifier emailNotifier;

    @Test
    @DisplayName("Verifica envío con anyString")
    void shouldVerifySendEmailWithAnyString() {
        // Arrange
        String email = "patient@chronus.com";
        String message = "Cita confirmada";

        // Act &
        emailNotifier.sendEmail(email, message);

        // Assert
        verify(emailNotifier).sendEmail(anyString(), anyString());
    }

    @Test
    @DisplayName("Verifica destino con eq y mensaje con contains")
    void shouldVerifyDestinationAndMessageWithEqAndContains() {
        // Arrange
        String email = "patient@chronus.com";
        String message = "Cita confirmada para mañana";

        // Act &
        emailNotifier.sendEmail(email, message);

        // Assert
        verify(emailNotifier).sendEmail(
                eq("patient@chronus.com"),
                contains("Cita confirmada"));
    }

    @Test
    @DisplayName("Verifica mensaje con startsWith")
    void shouldVerifyMessageWithStartsWith() {
        // Arrange
        String email = "patient@chronus.com";
        String message = "Payment of 100 accepted";

        // Act &
        emailNotifier.sendEmail(email, message);

        // Assert
        verify(emailNotifier).sendEmail(anyString(), startsWith("Payment of"));
    }

    @Test
    @DisplayName("No debe haber sido invocado (never + anyString)")
    void shouldNeverHaveBeenCalled() {
        // Assert — sin Act: nunca se invocó sendEmail
        verify(emailNotifier, never()).sendEmail(anyString(), anyString());
    }

    @Test
    @DisplayName("Falla con assertThrows si correo y mensaje llegan en formato estructurado")
    void shouldThrowWhenEmailAndMessageHaveWrongStructuredFormat() {
        // Arrange — JSON que "parece" correcto pero no es el formato plano esperado
        String structuredEmail = "{\"email\":\"patient@chronus.com\"}";
        String structuredMessage = "{\"text\":\"Cita confirmada\"}";

        // Act
        emailNotifier.sendEmail(structuredEmail, structuredMessage);

        // Assert — eq espera formato plano; verify lanza AssertionError por argumentos
        // distintos
        assertThrows(AssertionError.class, () -> verify(emailNotifier).sendEmail(
                eq("patient@chronus.com"),
                eq("Cita confirmada")));
    }
}
