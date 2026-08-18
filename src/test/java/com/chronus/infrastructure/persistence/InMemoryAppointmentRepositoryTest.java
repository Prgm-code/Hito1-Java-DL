package com.chronus.infrastructure.persistence;

import com.chronus.domain.entity.Appointment;
import com.chronus.domain.repository.AppointmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("In-memory appointment repository")
class InMemoryAppointmentRepositoryTest {

    @Test
    void shouldStoreAppointmentAndReturnImmutableCopy() {
        // Arrange
        AppointmentRepository appointmentRepository = new InMemoryAppointmentRepository();
        Appointment appointment = new Appointment(
                "1",
                LocalDateTime.now().plusDays(1));

        // Act
        appointmentRepository.save(appointment);
        List<Appointment> appointments = appointmentRepository.findAll();

        // Assert
        assertEquals(List.of(appointment), appointments);
        assertThrows(UnsupportedOperationException.class, () -> appointments.add(appointment));
    }

    @Test
    void shouldFindAppointmentById() {
        // Arrange
        AppointmentRepository appointmentRepository = new InMemoryAppointmentRepository();
        Appointment appointment = new Appointment(
                "1",
                LocalDateTime.now().plusDays(1));
        appointmentRepository.save(appointment);

        // Act
        Optional<Appointment> foundAppointment = appointmentRepository.findByAppointmentId("1");

        // Assert
        assertEquals(Optional.of(appointment), foundAppointment);
    }

    @Test
    void shouldReturnEmptyWhenAppointmentIdDoesNotExist() {
        // Arrange
        AppointmentRepository appointmentRepository = new InMemoryAppointmentRepository();

        // Act
        Optional<Appointment> foundAppointment = appointmentRepository.findByAppointmentId("999");

        // Assert
        assertTrue(foundAppointment.isEmpty());
    }
}
