package com.chronus.infrastructure.persistence;

import com.chronus.domain.entity.Patient;
import com.chronus.domain.repository.PatientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
                "juanito.perez@example.com",
                "+56912345678");

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
                "juanito.perez@example.com",
                "+56912345678");
        patientRepository.save(patient);

        // Act
        Optional<Patient> foundPatient = patientRepository.findById("123");

        // Assert
        assertEquals(Optional.of(patient), foundPatient);
    }

    @Test
    void shouldReturnEmptyWhenPatientIdDoesNotExist() {
        // Arrange
        PatientRepository patientRepository = new InMemoryPatientRepository();

        // Act
        Optional<Patient> foundPatient = patientRepository.findById("999");

        // Assert
        assertTrue(foundPatient.isEmpty());
    }
}
