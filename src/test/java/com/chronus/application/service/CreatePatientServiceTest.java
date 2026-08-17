package com.chronus.application.service;

import com.chronus.application.usecase.CreatePatientUseCase;
import com.chronus.domain.entity.Patient;
import com.chronus.domain.repository.PatientRepository;
import com.chronus.domain.valueobject.Email;
import com.chronus.domain.valueobject.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@DisplayName("Create patient service")
@ExtendWith(MockitoExtension.class)
class CreatePatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    private CreatePatientUseCase createPatientUseCase;

    @BeforeEach
    void setUp() {
        createPatientUseCase = new CreatePatientService(patientRepository);
    }

    @Test
    void shouldStorePatientThroughRepository() {
        // Arrange
        Patient patient = new Patient(
                "123",
                "Juanito Pérez",
                new Email("juanito.perez@example.com"),
                new PhoneNumber("+56912345678"));

        // Act
        createPatientUseCase.createPatient(patient);

        // Assert
        verify(patientRepository).save(patient);
    }
}
