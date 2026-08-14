package com.chronus.domain.entity;

import java.time.LocalDateTime;

public class Appointment {
    /** Scheduled appointment date and time. */
    private final LocalDateTime dateTime;

    /**
     * Creates an appointment with the provided date and time.
     * Business rules are validated by the application service.
     */
    public Appointment(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /** Returns the scheduled appointment date and time. */
    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
