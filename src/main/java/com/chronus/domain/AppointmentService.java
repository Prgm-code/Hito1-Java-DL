package com.chronus.domain;

import com.chronus.domain.exception.InvalidDateAppointmentException;
import com.chronus.domain.exception.OccupiedAppointmentException;

import java.time.LocalDateTime;

/**
 * Domain service that manages Chronus appointments.
 * It contains business validations and delegates persistence to
 * {@link AppointmentRepository}.
 */
public class AppointmentService {
    /** Repository used to store validated appointments. */
    private final AppointmentRepository appointmentRepository;

    /**
     * Injects the repository through the constructor.
     */
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void createAppointment(Appointment appointment) {
        if (!appointment.getDateTime().isAfter(LocalDateTime.now())) {
            throw new InvalidDateAppointmentException(
                    "The appointment date and time must be in the future.");
        }
        boolean collision = appointmentRepository.findAll().stream()
                .anyMatch(existing -> existing.getDateTime().equals(appointment.getDateTime()));
        if (collision) {
            throw new OccupiedAppointmentException(
                    "An appointment already exists at this date and time.");
        }
        appointmentRepository.save(appointment);
    }
}
