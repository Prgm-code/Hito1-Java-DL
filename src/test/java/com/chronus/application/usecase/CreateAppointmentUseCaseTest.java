package com.chronus.application.usecase;

import com.chronus.domain.entity.Appointment;
import com.chronus.domain.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Create appointment use case")
@ExtendWith(MockitoExtension.class)
class CreateAppointmentUseCaseTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    private CreateAppointmentUseCase createAppointmentUseCase;

    @BeforeEach
    void setUp() {
        createAppointmentUseCase = new CreateAppointmentUseCase(appointmentRepository);
    }

    @Test
    void shouldStoreAppointmentWhenIdIsAvailable() {
        // Arrange
        Appointment appointment = new Appointment(
                "1",
                LocalDateTime.now().plusMinutes(1));
        when(appointmentRepository.findByAppointmentId("1")).thenReturn(null);

        // Act
        createAppointmentUseCase.execute(appointment);

        // Assert
        verify(appointmentRepository).findByAppointmentId("1");
        verify(appointmentRepository).save(appointment);
    }

    @Test
    void shouldRejectAppointmentWhenIdAlreadyExists() {
        // Arrange
        Appointment appointment = new Appointment(
                "1",
                LocalDateTime.now().plusMinutes(1));
        when(appointmentRepository.findByAppointmentId("1")).thenReturn(appointment);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> createAppointmentUseCase.execute(appointment));

        // Assert
        assertEquals("Appointment already exists", exception.getMessage());
        verify(appointmentRepository).findByAppointmentId("1");
    }
}
