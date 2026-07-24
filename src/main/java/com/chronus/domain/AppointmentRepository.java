package com.chronus.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory repository for storing and retrieving appointments.
 */
public class AppointmentRepository {
    private final List<Appointment> appointments = new ArrayList<>();

    /**
     * Stores a validated appointment.
     */
    public void save(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * Returns all stored appointments.
     */
    public List<Appointment> findAll() {
        return List.copyOf(appointments);
    }
}
