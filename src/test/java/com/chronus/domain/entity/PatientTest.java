package com.chronus.domain.entity;

import com.chronus.domain.exception.InvalidPatientDataException;
import com.chronus.domain.exception.InvalidEmailException;
import com.chronus.domain.exception.InvalidPhoneNumberException;
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
        String email = "juanito.perez@example.com";
        String phoneNumber = "+56912345678";

        // Act
        Patient patient = new Patient(patientId, fullName, email, phoneNumber);

        // Assert
        assertEquals(patientId, patient.getPatientId());
        assertEquals(fullName, patient.getFullName());
        assertEquals(new Email(email), patient.getEmail());
        assertEquals(new PhoneNumber(phoneNumber), patient.getPhoneNumber());
    }

    @Test
    void shouldUpdatePatientInformation() {
        // Arrange
        Patient patient = new Patient(
                "123",
                "Juanito Pérez",
                "juanito.perez@example.com",
                "+56912345678");
        String updatedEmail = "juan.perez@chronus.com";
        String updatedPhoneNumber = "+56987654321";

        // Act
        patient.updateInformation("Juan Pérez", updatedEmail, updatedPhoneNumber);

        // Assert
        assertEquals("123", patient.getPatientId());
        assertEquals("Juan Pérez", patient.getFullName());
        assertEquals(new Email(updatedEmail), patient.getEmail());
        assertEquals(new PhoneNumber(updatedPhoneNumber), patient.getPhoneNumber());
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
                        "juanito.perez@example.com",
                        "+56912345678"));

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
                        "juanito.perez@example.com",
                        "+56912345678"));

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
                        "juanito.perez@example.com",
                        "+56912345678"));

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
                        "juanito.perez@example.com",
                        "+56912345678"));

        // Assert
        assertEquals("The patient full name is required.", exception.getMessage());
    }

    @Test
    void shouldRejectPatientWithoutEmail() {
        // Act
        InvalidEmailException exception = assertThrows(
                InvalidEmailException.class,
                () -> new Patient("123", "Juanito Pérez", null, "+56912345678"));

        // Assert
        assertEquals("Email is required", exception.getMessage());
    }

    @Test
    void shouldRejectPatientWithoutPhoneNumber() {
        // Act
        InvalidPhoneNumberException exception = assertThrows(
                InvalidPhoneNumberException.class,
                () -> new Patient("123", "Juanito Pérez", "juanito.perez@example.com", null));

        // Assert
        assertEquals("Phone number is required", exception.getMessage());
    }
}
