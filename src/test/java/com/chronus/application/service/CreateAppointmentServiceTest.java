package com.chronus.application.service;

import com.chronus.application.usecase.CreateAppointmentUseCase;
import com.chronus.domain.entity.Appointment;
import com.chronus.domain.exception.InvalidDateAppointmentException;
import com.chronus.domain.exception.OccupiedAppointmentException;
import com.chronus.domain.repository.AppointmentRepository;
import com.chronus.domain.service.AppointmentConflictChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@DisplayName("Create appointment service")
@ExtendWith(MockitoExtension.class)
class CreateAppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    private CreateAppointmentUseCase createAppointmentUseCase;

    @BeforeEach
    void setUp() {
        createAppointmentUseCase = new CreateAppointmentService(
                appointmentRepository, new AppointmentConflictChecker());
    }

    @Test
    void shouldRejectPastAppointmentWithoutAccessingRepository() {
        // Arrange
        Appointment appointment = new Appointment(LocalDateTime.now().minusDays(1));

        // Act
        InvalidDateAppointmentException exception = assertThrows(
                InvalidDateAppointmentException.class,
                () -> createAppointmentUseCase.createAppointment(appointment));

        // Assert
        assertEquals("The appointment date and time must be in the future.", exception.getMessage());
        verifyNoInteractions(appointmentRepository);
    }

    @Test
    void shouldStoreFutureAppointmentWhenThereIsNoCollision() {
        // Arrange
        Appointment appointment = new Appointment(LocalDateTime.now().plusMinutes(1));
        when(appointmentRepository.findAll()).thenReturn(List.of());

        // Act
        createAppointmentUseCase.createAppointment(appointment);

        // Assert
        verify(appointmentRepository).findAll();
        verify(appointmentRepository).save(appointment);
    }

    @Test
    void shouldRejectAppointmentWhenDateTimeIsOccupied() {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(1);
        Appointment existingAppointment = new Appointment(dateTime);
        Appointment appointment = new Appointment(dateTime);
        when(appointmentRepository.findAll()).thenReturn(List.of(existingAppointment));

        // Act
        OccupiedAppointmentException exception = assertThrows(
                OccupiedAppointmentException.class,
                () -> createAppointmentUseCase.createAppointment(appointment));

        // Assert
        assertEquals("An appointment already exists at this date and time.", exception.getMessage());
        verify(appointmentRepository).findAll();
        verify(appointmentRepository, never()).save(appointment);
    }
}
