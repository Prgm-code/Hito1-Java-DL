package com.chronus.application.usecase;

import com.chronus.domain.entity.Patient;
import com.chronus.domain.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@DisplayName("Create patient use case")
@ExtendWith(MockitoExtension.class)
class CreatePatientUseCaseTest {

    @Mock
    private PatientRepository patientRepository;

    private CreatePatientUseCase createPatientUseCase;

    @BeforeEach
    void setUp() {
        createPatientUseCase = new CreatePatientUseCase(patientRepository);
    }

    @Test
    void shouldStorePatientThroughRepository() {
        // Arrange
        Patient patient = new Patient(
                "123",
                "Juanito Pérez",
                "juanito.perez@example.com",
                "+56912345678");

        // Act
        createPatientUseCase.execute(patient);

        // Assert
        verify(patientRepository).save(patient);
    }
}
