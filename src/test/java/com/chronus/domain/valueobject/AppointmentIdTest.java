package com.chronus.domain.valueobject;

import com.chronus.domain.exception.InvalidDateAppointmentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Appointment ID value object")
class AppointmentIdTest {

    @Test
    void shouldKeepValidAppointmentId() {
        AppointmentId appointmentId = new AppointmentId("1");

        assertEquals("1", appointmentId.value());
    }

    @Test
    void shouldRejectBlankAppointmentId() {
        InvalidDateAppointmentException exception = assertThrows(
                InvalidDateAppointmentException.class,
                () -> new AppointmentId(" "));

        assertEquals("The appointment id is required.", exception.getMessage());
    }
}
