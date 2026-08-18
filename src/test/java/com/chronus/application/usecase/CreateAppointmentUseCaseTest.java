package com.chronus.application.usecase;

import com.chronus.domain.entity.Appointment;
import com.chronus.domain.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateAppointmentUseCaseTest {

    @Test
    void shouldStoreAppointmentWhenIdIsAvailable() {
        // Arrange: crear el mock de la interfaz de dominio
        AppointmentRepository repositoryMock = Mockito.mock(AppointmentRepository.class);
        CreateAppointmentUseCase useCase = new CreateAppointmentUseCase(repositoryMock);
        Appointment appointment = new Appointment(
                "1",
                LocalDateTime.now().plusMinutes(1));

        // Act
        when(repositoryMock.findByAppointmentId("1")).thenReturn(Optional.empty());
        useCase.execute(appointment);

        // Assert
        verify(repositoryMock).findByAppointmentId("1");
        verify(repositoryMock).save(appointment);
    }

    @Test
    void shouldThrowExceptionWhenAppointmentAlreadyExists() {
        // Arrange: crear el mock de la interfaz de dominio
        AppointmentRepository repositoryMock = Mockito.mock(AppointmentRepository.class);
        CreateAppointmentUseCase useCase = new CreateAppointmentUseCase(repositoryMock);

        Appointment appointment = new Appointment(
                "1",
                LocalDateTime.now().plusMinutes(1));
        when(repositoryMock.findByAppointmentId("1")).thenReturn(Optional.of(appointment));

        // Act: ejecutar el use case & Assert: verificar el resultado
        assertThrows(RuntimeException.class, () -> {
            useCase.execute(appointment);
        });

        verify(repositoryMock, never()).save(any());
    }
}
