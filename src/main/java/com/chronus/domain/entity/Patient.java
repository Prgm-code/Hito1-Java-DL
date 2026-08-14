package com.chronus.domain.entity;

import com.chronus.domain.exception.InvalidPatientDataException;

/**
 * Domain entity that represents a patient receiving healthcare appointments.
 */
public class Patient {
    private final String fullName;
    private final String email;
    private final String phoneNumber;

    /**
     * Creates a patient with the contact details required for notifications.
     */
    public Patient(String fullName, String email, String phoneNumber) {
        validateRequired(fullName, "full name");
        validateRequired(email, "email");
        validateRequired(phoneNumber, "phone number");
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    private static void validateRequired(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidPatientDataException(
                    "The patient " + fieldName + " is required.");
        }
    }
}
