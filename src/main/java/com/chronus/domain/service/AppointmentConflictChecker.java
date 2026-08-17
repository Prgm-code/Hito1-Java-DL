package com.chronus.domain.service;

import com.chronus.domain.entity.Appointment;

import java.util.List;

// servicio de dominio que detecta si una cita candidata colisiona con las citas ya programadas
public class AppointmentConflictChecker {
    public boolean isOccupied(Appointment candidate, List<Appointment> existingAppointments) {
        return existingAppointments.stream()
                .anyMatch(existing -> existing.getDateTime().equals(candidate.getDateTime()));
    }
}
