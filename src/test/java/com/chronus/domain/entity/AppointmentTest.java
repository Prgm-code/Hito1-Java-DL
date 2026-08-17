package com.chronus.domain.entity;

import com.chronus.domain.valueobject.AppointmentDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Appointment")
class AppointmentTest {

    @Test
    void shouldKeepRequiredAppointmentDateTime() {
        // Arrange
        AppointmentDateTime dateTime =
                new AppointmentDateTime(LocalDateTime.now().plusDays(1).withNano(0));

        // Act
        Appointment appointment = new Appointment(dateTime);

        // Assert
        assertEquals(dateTime, appointment.getDateTime());
    }

    @Test
    void shouldUpdateAppointmentDateTime() {
        // Arrange
        Appointment appointment = new Appointment(
                new AppointmentDateTime(LocalDateTime.now().plusDays(1).withNano(0)));
        AppointmentDateTime updatedDateTime =
                new AppointmentDateTime(LocalDateTime.now().plusDays(2).withNano(0));

        // Act
        appointment.updateDateTime(updatedDateTime);

        // Assert
        assertEquals(updatedDateTime, appointment.getDateTime());
    }
}
