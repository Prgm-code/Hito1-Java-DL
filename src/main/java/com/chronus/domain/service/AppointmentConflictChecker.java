package com.chronus.domain.service;

import com.chronus.domain.entity.Appointment;

import java.util.List;

/**
 * Detects whether a candidate appointment collides with already scheduled ones.
 * The rule needs the collection, so it does not belong to a single entity.
 */
public class AppointmentConflictChecker {
    public boolean isOccupied(Appointment candidate, List<Appointment> existingAppointments) {
        return existingAppointments.stream()
                .anyMatch(existing -> existing.getDateTime().equals(candidate.getDateTime()));
    }
}
