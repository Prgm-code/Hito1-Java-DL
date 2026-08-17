package com.chronus.domain.valueobject;

import com.chronus.domain.exception.InvalidDateAppointmentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Appointment date and time value object")
class AppointmentDateTimeTest {

    @Test
    void shouldCreateFutureAppointmentDateTime() {
        LocalDateTime futureDateTime = LocalDateTime.now().plusDays(1).withNano(0);

        AppointmentDateTime appointmentDateTime = new AppointmentDateTime(futureDateTime);

        assertEquals(futureDateTime, appointmentDateTime.value());
    }

    @Test
    void shouldRejectNullAppointmentDateTime() {
        InvalidDateAppointmentException exception = assertThrows(
                InvalidDateAppointmentException.class,
                () -> new AppointmentDateTime(null));

        assertEquals("The appointment date and time is required.", exception.getMessage());
    }

    @Test
    void shouldRejectPastAppointmentDateTime() {
        InvalidDateAppointmentException exception = assertThrows(
                InvalidDateAppointmentException.class,
                () -> new AppointmentDateTime(LocalDateTime.now().minusDays(1)));

        assertEquals(
                "The appointment date and time must be in the future.",
                exception.getMessage());
    }
}
