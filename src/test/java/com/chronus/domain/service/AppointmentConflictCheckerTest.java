package com.chronus.domain.service;

import com.chronus.domain.entity.Appointment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Appointment conflict checker")
class AppointmentConflictCheckerTest {

    private final AppointmentConflictChecker appointmentConflictChecker =
            new AppointmentConflictChecker();

    @Test
    void shouldDetectOccupiedDateTime() {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.of(2026, 8, 1, 10, 30);
        Appointment candidate = new Appointment(dateTime);

        // Act
        boolean occupied = appointmentConflictChecker.isOccupied(
                candidate, List.of(new Appointment(dateTime)));

        // Assert
        assertTrue(occupied);
    }

    @Test
    void shouldAllowFreeDateTime() {
        // Arrange
        Appointment candidate = new Appointment(LocalDateTime.of(2026, 8, 1, 11, 0));

        // Act
        boolean occupied = appointmentConflictChecker.isOccupied(
                candidate, List.of(new Appointment(LocalDateTime.of(2026, 8, 1, 10, 30))));

        // Assert
        assertFalse(occupied);
    }
}
