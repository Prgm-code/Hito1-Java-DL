package com.chronus.domain.valueobject;

import com.chronus.domain.exception.InvalidEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Email")
class EmailTest {

    @Test
    void shouldKeepValidEmail() {
        // Arrange
        String value = "juanito.perez@example.com";

        // Act
        Email email = new Email(value);

        // Assert
        assertEquals(value, email.value());
    }

    @Test
    void shouldNormalizeEmailToLowerCase() {
        // Arrange
        String value = "Juanito.Perez@Example.COM";

        // Act
        Email email = new Email(value);

        // Assert
        assertEquals("juanito.perez@example.com", email.value());
    }

    @Test
    void shouldRejectNullEmail() {
        // Arrange
        String value = null;

        // Act
        InvalidEmailException exception = assertThrows(
                InvalidEmailException.class,
                () -> new Email(value));

        // Assert
        assertEquals("Email is required", exception.getMessage());
    }

    @Test
    void shouldRejectBlankEmail() {
        // Arrange
        String value = " ";

        // Act
        InvalidEmailException exception = assertThrows(
                InvalidEmailException.class,
                () -> new Email(value));

        // Assert
        assertEquals("Email is required", exception.getMessage());
    }

    @Test
    void shouldRejectInvalidEmail() {
        // Arrange
        String value = "juanito.perez";

        // Act
        InvalidEmailException exception = assertThrows(
                InvalidEmailException.class,
                () -> new Email(value));

        // Assert
        assertEquals("Invalid email address", exception.getMessage());
    }

    // saniitizacion del constructor de Email
    // se rechaza el email si no tiene un dominio
    @Test
    void shouldRejectEmailMissingDomainDot() {
        // Arrange
        String value = "juanito.perez@example";

        // Act
        InvalidEmailException exception = assertThrows(
                InvalidEmailException.class,
                () -> new Email(value));

        // Assert
        assertEquals("Invalid email address", exception.getMessage());
    }

    // se rechaza el email si termina en un punto
    @Test
    void shouldRejectEmailEndingInDot() {
        // Arrange
        String value = "juanito.perez@example.com.";

        // Act
        InvalidEmailException exception = assertThrows(
                InvalidEmailException.class,
                () -> new Email(value));

        // Assert
        assertEquals("Invalid email address", exception.getMessage());
    }

    // verificacion del trim del email
    @Test
    void shouldTrimEmail() {
        // Arrange
        String value = " juanito.perez@example.com ";

        // Act
        Email email = new Email(value);

        // Assert
        assertEquals("juanito.perez@example.com", email.value());
    }

    // verificacion del constructor de Email
    @Test
    void shouldRejectEmailWithInvalidDomain() {
        // Arrange
        String value = "juanito.perez@example_invalid.com";

        // Act
        InvalidEmailException exception = assertThrows(
                InvalidEmailException.class,
                () -> new Email(value));

        // Assert
        assertEquals("Invalid email address", exception.getMessage());
    }
}
