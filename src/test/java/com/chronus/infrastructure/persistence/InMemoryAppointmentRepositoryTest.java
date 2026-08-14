package com.chronus.infrastructure.persistence;

import com.chronus.domain.entity.Appointment;
import com.chronus.domain.repository.AppointmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("In-memory appointment repository")
class InMemoryAppointmentRepositoryTest {

    @Test
    void shouldStoreAppointmentAndReturnImmutableCopy() {
        // Arrange
        AppointmentRepository appointmentRepository = new InMemoryAppointmentRepository();
        Appointment appointment = new Appointment(LocalDateTime.of(2026, 8, 1, 10, 30));

        // Act
        appointmentRepository.save(appointment);
        List<Appointment> appointments = appointmentRepository.findAll();

        // Assert
        assertEquals(List.of(appointment), appointments);
        assertThrows(UnsupportedOperationException.class, () -> appointments.add(appointment));
    }
}
