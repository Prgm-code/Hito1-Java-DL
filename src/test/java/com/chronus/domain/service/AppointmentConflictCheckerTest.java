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
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        Appointment candidate = new Appointment("1", dateTime);

        // Act
        boolean occupied = appointmentConflictChecker.isOccupied(
                candidate, List.of(new Appointment("2", dateTime)));

        // Assert
        assertTrue(occupied);
    }

    @Test
    void shouldAllowFreeDateTime() {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1);
        Appointment candidate = new Appointment(
                "1",
                dateTime.plusMinutes(30));

        // Act
        boolean occupied = appointmentConflictChecker.isOccupied(
                candidate, List.of(new Appointment("2", dateTime)));

        // Assert
        assertFalse(occupied);
    }
}
