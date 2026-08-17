package com.chronus.domain.entity;

import com.chronus.domain.exception.InvalidDateAppointmentException;
import com.chronus.domain.valueobject.AppointmentDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Appointment")
class AppointmentTest {

    @Test
    void shouldKeepRequiredAppointmentIdentityAndDateTime() {
        // Arrange
        String appointmentId = "1";
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1).withNano(0);

        // Act
        Appointment appointment = new Appointment(appointmentId, dateTime);

        // Assert
        assertEquals(appointmentId, appointment.getAppointmentId());
        assertEquals(new AppointmentDateTime(dateTime), appointment.getDateTime());
    }

    @Test
    void shouldUpdateAppointmentDateTime() {
        // Arrange
        Appointment appointment = new Appointment(
                "1",
                LocalDateTime.now().plusDays(1).withNano(0));
        LocalDateTime updatedDateTime =
                LocalDateTime.now().plusDays(2).withNano(0);

        // Act
        appointment.updateDateTime(updatedDateTime);

        // Assert
        assertEquals("1", appointment.getAppointmentId());
        assertEquals(new AppointmentDateTime(updatedDateTime), appointment.getDateTime());
    }

    @Test
    void shouldRejectAppointmentWithoutId() {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1).withNano(0);

        // Act
        InvalidDateAppointmentException exception = assertThrows(
                InvalidDateAppointmentException.class,
                () -> new Appointment(null, dateTime));

        // Assert
        assertEquals("The appointment id is required.", exception.getMessage());
    }

    @Test
    void shouldRejectAppointmentWithoutDateTime() {
        // Act
        InvalidDateAppointmentException exception = assertThrows(
                InvalidDateAppointmentException.class,
                () -> new Appointment("1", null));

        // Assert
        assertEquals("The appointment date and time is required.", exception.getMessage());
    }
}
