package com.chronus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import com.chronus.domain.exception.InvalidDateAppointmentException;
import com.chronus.domain.exception.OccupiedAppointmentException;

@DisplayName("Appointment Service")
class AppointmentServiceTest {

    private AppointmentRepository appointmentRepository = new AppointmentRepository();
    private AppointmentService appointmentService = new AppointmentService(appointmentRepository);

    // Rechaza una cita con fecha en el pasado y no la persiste.
    @Test
    void shouldRejectPastAppointment() {
        // Arrange
        Appointment appointment = new Appointment(LocalDateTime.now().minusDays(1));

        // Act &
        assertThrows(InvalidDateAppointmentException.class, () -> {
            appointmentService.createAppointment(appointment);
        });

        // Assert
        assertEquals(0, appointmentRepository.findAll().size());
    }

    // Acepta y guarda una cita con fecha futura válida.
    @Test
    void shouldAcceptFutureAppointment() {
        // Arrange
        Appointment appointment = new Appointment(LocalDateTime.now().plusDays(1));

        // Act &
        appointmentService.createAppointment(appointment);

        // Assert
        assertEquals(1, appointmentRepository.findAll().size());
    }

    // Rechaza una segunda cita en la misma fecha/hora (colisión) y deja solo la
    // primera.
    @Test
    void shouldRejectAppointmentWithSameDateTime() {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        Appointment appointment = new Appointment(dateTime);
        Appointment appointment2 = new Appointment(dateTime);
        // Act &
        appointmentService.createAppointment(appointment);
        assertThrows(OccupiedAppointmentException.class, () -> {
            appointmentService.createAppointment(appointment2);
        });

        // Assert
        assertEquals(1, appointmentRepository.findAll().size());
    }

}
