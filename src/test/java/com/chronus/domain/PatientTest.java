package com.chronus.domain;

import com.chronus.domain.exception.InvalidPatientDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Patient")
class PatientTest {

    @Test
    void shouldKeepRequiredPatientContactData() {
        // Arrange
        String fullName = "Juanito Pérez";
        String email = "juanito.perez@example.com";
        String phoneNumber = "+56912345678";

        // Act
        Patient patient = new Patient(fullName, email, phoneNumber);

        // Assert
        assertEquals(fullName, patient.getFullName());
        assertEquals(email, patient.getEmail());
        assertEquals(phoneNumber, patient.getPhoneNumber());
    }

    @Test
    void shouldRejectPatientWithoutFullName() {
        // Arrange
        String fullName = null;

        // Act
        InvalidPatientDataException exception = assertThrows(
                InvalidPatientDataException.class,
                () -> new Patient(fullName, "juanito.perez@example.com", "+56912345678"));

        // Assert
        assertEquals("The patient full name is required.", exception.getMessage());
    }

    @Test
    void shouldRejectPatientWithBlankEmail() {
        // Arrange
        String email = " ";

        // Act
        InvalidPatientDataException exception = assertThrows(
                InvalidPatientDataException.class,
                () -> new Patient("Juanito Pérez", email, "+56912345678"));

        // Assert
        assertEquals("The patient email is required.", exception.getMessage());
    }

    @Test
    void shouldRejectPatientWithoutPhoneNumber() {
        // Arrange
        String phoneNumber = null;

        // Act
        InvalidPatientDataException exception = assertThrows(
                InvalidPatientDataException.class,
                () -> new Patient("Juanito Pérez", "juanito.perez@example.com", phoneNumber));

        // Assert
        assertEquals("The patient phone number is required.", exception.getMessage());
    }
}
