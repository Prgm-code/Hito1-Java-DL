package com.chronus.application.usecase;

import com.chronus.domain.entity.Patient;
import com.chronus.domain.exception.InvalidPatientDataException;
import com.chronus.domain.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreatePatientUseCaseTest {

    @Test
    void shouldStorePatientThroughRepository() {
        // Arrange: crear el mock de la interfaz de dominio
        PatientRepository repositoryMock = Mockito.mock(PatientRepository.class);
        CreatePatientUseCase useCase = new CreatePatientUseCase(repositoryMock);
        Patient patient = new Patient(
                "123",
                "Juanito Pérez",
                "juanito.perez@example.com",
                "+56912345678");

        // Act
        useCase.execute(patient);

        // Assert
        verify(repositoryMock).save(patient);
    }

    @Test
    void shouldThrowExceptionWhenPatientAlreadyExists() {
        // Arrange: crear el mock de la interfaz de dominio
        PatientRepository repositoryMock = Mockito.mock(PatientRepository.class);
        CreatePatientUseCase useCase = new CreatePatientUseCase(repositoryMock);

        Patient patient = new Patient(
                "123",
                "Juanito Pérez",
                "juanito.perez@example.com",
                "+56912345678");
        when(repositoryMock.findById("123")).thenReturn(Optional.of(patient));

        // Act: ejecutar el use case & Assert: verificar el resultado
        assertThrows(InvalidPatientDataException.class, () -> {
            useCase.execute(patient);
        });

        verify(repositoryMock, never()).save(any());
    }
}
