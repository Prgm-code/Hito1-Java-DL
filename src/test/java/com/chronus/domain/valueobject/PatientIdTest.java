package com.chronus.domain.valueobject;

import com.chronus.domain.exception.InvalidPatientDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Patient ID value object")
class PatientIdTest {

    @Test
    void shouldKeepValidPatientId() {
        PatientId patientId = new PatientId("123");

        assertEquals("123", patientId.value());
    }

    @Test
    void shouldRejectInvalidPatientIdFormat() {
        InvalidPatientDataException exception = assertThrows(
                InvalidPatientDataException.class,
                () -> new PatientId("ABC"));

        assertEquals(
                "The patient id must be a number.",
                exception.getMessage());
    }
}
