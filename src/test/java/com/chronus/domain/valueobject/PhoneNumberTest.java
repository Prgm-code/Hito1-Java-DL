package com.chronus.domain.valueobject;

import com.chronus.domain.exception.InvalidPhoneNumberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Phone number")
class PhoneNumberTest {

    @Test
    void shouldKeepValidE164WhatsAppNumber() {
        // Arrange
        String value = "+56912345678";

        // Act
        PhoneNumber phoneNumber = new PhoneNumber(value);

        // Assert
        assertEquals(value, phoneNumber.value());
    }

    @Test
    void shouldTrimValidE164WhatsAppNumber() {
        // Arrange
        String value = "  +56912345678  ";

        // Act
        PhoneNumber phoneNumber = new PhoneNumber(value);

        // Assert
        assertEquals("+56912345678", phoneNumber.value());
    }

    @Test
    void shouldKeepShortestValidE164Number() {
        // Arrange
        String value = "+6831234";

        // Act
        PhoneNumber phoneNumber = new PhoneNumber(value);

        // Assert
        assertEquals(value, phoneNumber.value());
    }

    @Test
    void shouldKeepLongestValidE164Number() {
        // Arrange
        String value = "+123456789012345";

        // Act
        PhoneNumber phoneNumber = new PhoneNumber(value);

        // Assert
        assertEquals(value, phoneNumber.value());
    }

    @Test
    void shouldRejectNullPhoneNumber() {
        // Arrange
        String value = null;

        // Act
        InvalidPhoneNumberException exception = assertThrows(
                InvalidPhoneNumberException.class,
                () -> new PhoneNumber(value));

        // Assert
        assertEquals("Phone number is required", exception.getMessage());
    }

    @Test
    void shouldRejectBlankPhoneNumber() {
        // Arrange
        String value = " ";

        // Act
        InvalidPhoneNumberException exception = assertThrows(
                InvalidPhoneNumberException.class,
                () -> new PhoneNumber(value));

        // Assert
        assertEquals("Phone number is required", exception.getMessage());
    }

    @Test
    void shouldRejectNumberWithoutPlusPrefix() {
        // Arrange
        String value = "56912345678";

        // Act
        InvalidPhoneNumberException exception = assertThrows(
                InvalidPhoneNumberException.class,
                () -> new PhoneNumber(value));

        // Assert
        assertEquals("Invalid phone number", exception.getMessage());
    }

    @Test
    void shouldRejectNumberWithLeadingZero() {
        // Arrange
        String value = "+056912345678";

        // Act
        InvalidPhoneNumberException exception = assertThrows(
                InvalidPhoneNumberException.class,
                () -> new PhoneNumber(value));

        // Assert
        assertEquals("Invalid phone number", exception.getMessage());
    }

    @Test
    void shouldRejectNumberShorterThanE164Minimum() {
        // Arrange
        String value = "+123456";

        // Act
        InvalidPhoneNumberException exception = assertThrows(
                InvalidPhoneNumberException.class,
                () -> new PhoneNumber(value));

        // Assert
        assertEquals("Invalid phone number", exception.getMessage());
    }

    @Test
    void shouldRejectNumberLongerThanE164Maximum() {
        // Arrange
        String value = "+1234567890123456";

        // Act
        InvalidPhoneNumberException exception = assertThrows(
                InvalidPhoneNumberException.class,
                () -> new PhoneNumber(value));

        // Assert
        assertEquals("Invalid phone number", exception.getMessage());
    }

    @Test
    void shouldRejectFormattedPhoneNumber() {
        // Arrange
        String value = "+56 9 1234 5678";

        // Act
        InvalidPhoneNumberException exception = assertThrows(
                InvalidPhoneNumberException.class,
                () -> new PhoneNumber(value));

        // Assert
        assertEquals("Invalid phone number", exception.getMessage());
    }
}
