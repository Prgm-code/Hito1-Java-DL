package com.chronus.domain.entity;

import com.chronus.domain.exception.InvalidPatientDataException;
import com.chronus.domain.valueobject.Email;
import com.chronus.domain.valueobject.PhoneNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Patient")
class PatientTest {

    @Test
    void shouldKeepRequiredPatientContactData() {
        // Arrange
        String patientId = "123";
        String fullName = "Juanito Pérez";
        Email email = new Email("juanito.perez@example.com");
        PhoneNumber phoneNumber = new PhoneNumber("+56912345678");

        // Act
        Patient patient = new Patient(patientId, fullName, email, phoneNumber);

        // Assert
        assertEquals(patientId, patient.getPatientId());
        assertEquals(fullName, patient.getFullName());
        assertEquals(email, patient.getEmail());
        assertEquals(phoneNumber, patient.getPhoneNumber());
    }

    @Test
    void shouldUpdatePatientInformation() {
        // Arrange
        Patient patient = new Patient(
                "123",
                "Juanito Pérez",
                new Email("juanito.perez@example.com"),
                new PhoneNumber("+56912345678"));
        Email updatedEmail = new Email("juan.perez@chronus.com");
        PhoneNumber updatedPhoneNumber = new PhoneNumber("+56987654321");

        // Act
        patient.updateInformation("Juan Pérez", updatedEmail, updatedPhoneNumber);

        // Assert
        assertEquals("123", patient.getPatientId());
        assertEquals("Juan Pérez", patient.getFullName());
        assertEquals(updatedEmail, patient.getEmail());
        assertEquals(updatedPhoneNumber, patient.getPhoneNumber());
    }

    @Test
    void shouldRejectPatientWithoutId() {
        // Arrange
        String patientId = null;

        // Act
        InvalidPatientDataException exception = assertThrows(
                InvalidPatientDataException.class,
                () -> new Patient(
                        patientId,
                        "Juanito Pérez",
                        new Email("juanito.perez@example.com"),
                        new PhoneNumber("+56912345678")));

        // Assert
        assertEquals("The patient id is required.", exception.getMessage());
    }

    @Test
    void shouldRejectPatientWithBlankId() {
        // Arrange
        String patientId = " ";

        // Act
        InvalidPatientDataException exception = assertThrows(
                InvalidPatientDataException.class,
                () -> new Patient(
                        patientId,
                        "Juanito Pérez",
                        new Email("juanito.perez@example.com"),
                        new PhoneNumber("+56912345678")));

        // Assert
        assertEquals("The patient id is required.", exception.getMessage());
    }

    @Test
    void shouldRejectPatientWithoutFullName() {
        // Arrange
        String fullName = null;

        // Act
        InvalidPatientDataException exception = assertThrows(
                InvalidPatientDataException.class,
                () -> new Patient(
                        "123",
                        fullName,
                        new Email("juanito.perez@example.com"),
                        new PhoneNumber("+56912345678")));

        // Assert
        assertEquals("The patient full name is required.", exception.getMessage());
    }

    @Test
    void shouldRejectPatientWithBlankFullName() {
        // Arrange
        String fullName = " ";

        // Act
        InvalidPatientDataException exception = assertThrows(
                InvalidPatientDataException.class,
                () -> new Patient(
                        "123",
                        fullName,
                        new Email("juanito.perez@example.com"),
                        new PhoneNumber("+56912345678")));

        // Assert
        assertEquals("The patient full name is required.", exception.getMessage());
    }
}
