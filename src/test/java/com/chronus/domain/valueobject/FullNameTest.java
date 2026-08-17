package com.chronus.domain.valueobject;

import com.chronus.domain.exception.InvalidPatientDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Full name value object")
class FullNameTest {

    @Test
    void shouldKeepValidFullName() {
        FullName fullName = new FullName("Juanito Pérez");

        assertEquals("Juanito Pérez", fullName.value());
    }

    @Test
    void shouldRejectInvalidFullNameFormat() {
        InvalidPatientDataException exception = assertThrows(
                InvalidPatientDataException.class,
                () -> new FullName("Juan123"));

        assertEquals(
                "The patient full name has an invalid format.",
                exception.getMessage());
    }
}
