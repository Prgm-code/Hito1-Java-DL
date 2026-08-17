package com.chronus.infrastructure.persistence;

import com.chronus.domain.entity.Patient;
import com.chronus.domain.repository.PatientRepository;
import com.chronus.domain.valueobject.Email;
import com.chronus.domain.valueobject.PhoneNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("In-memory patient repository")
class InMemoryPatientRepositoryTest {

    @Test
    void shouldStorePatientAndReturnImmutableCopy() {
        // Arrange
        PatientRepository patientRepository = new InMemoryPatientRepository();
        Patient patient = new Patient(
                "123",
                "Juanito Pérez",
                new Email("juanito.perez@example.com"),
                new PhoneNumber("+56912345678"));

        // Act
        patientRepository.save(patient);
        List<Patient> patients = patientRepository.findAll();

        // Assert
        assertEquals(List.of(patient), patients);
        assertThrows(UnsupportedOperationException.class, () -> patients.add(patient));
    }

    @Test
    void shouldFindPatientById() {
        // Arrange
        PatientRepository patientRepository = new InMemoryPatientRepository();
        Patient patient = new Patient(
                "123",
                "Juanito Pérez",
                new Email("juanito.perez@example.com"),
                new PhoneNumber("+56912345678"));
        patientRepository.save(patient);

        // Act
        Patient foundPatient = patientRepository.findById("123");

        // Assert
        assertEquals(patient, foundPatient);
    }

    @Test
    void shouldReturnNullWhenPatientIdDoesNotExist() {
        // Arrange
        PatientRepository patientRepository = new InMemoryPatientRepository();

        // Act
        Patient foundPatient = patientRepository.findById("999");

        // Assert
        assertNull(foundPatient);
    }
}
