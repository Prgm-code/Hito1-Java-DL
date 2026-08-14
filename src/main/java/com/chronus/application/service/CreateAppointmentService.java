package com.chronus.application.service;

import com.chronus.application.usecase.CreateAppointmentUseCase;
import com.chronus.domain.entity.Appointment;
import com.chronus.domain.exception.InvalidDateAppointmentException;
import com.chronus.domain.exception.OccupiedAppointmentException;
import com.chronus.domain.repository.AppointmentRepository;
import com.chronus.domain.service.AppointmentConflictChecker;

import java.time.LocalDateTime;

/**
 * Application service that validates scheduling rules and persists appointments
 * through {@link AppointmentRepository}.
 */
public class CreateAppointmentService implements CreateAppointmentUseCase {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentConflictChecker appointmentConflictChecker;

    public CreateAppointmentService(
            AppointmentRepository appointmentRepository,
            AppointmentConflictChecker appointmentConflictChecker) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentConflictChecker = appointmentConflictChecker;
    }

    @Override
    public void createAppointment(Appointment appointment) {
        if (!appointment.getDateTime().isAfter(LocalDateTime.now())) {
            throw new InvalidDateAppointmentException(
                    "The appointment date and time must be in the future.");
        }
        if (appointmentConflictChecker.isOccupied(appointment, appointmentRepository.findAll())) {
            throw new OccupiedAppointmentException(
                    "An appointment already exists at this date and time.");
        }
        appointmentRepository.save(appointment);
    }
}
