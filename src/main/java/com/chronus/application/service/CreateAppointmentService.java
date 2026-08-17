package com.chronus.application.service;

import com.chronus.application.usecase.CreateAppointmentUseCase;
import com.chronus.domain.entity.Appointment;
import com.chronus.domain.exception.OccupiedAppointmentException;
import com.chronus.domain.repository.AppointmentRepository;
import com.chronus.domain.service.AppointmentConflictChecker;

// servicio de aplicacion que valida las reglas de programacion de citas y persiste las citas en el repositorio
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
        if (appointmentConflictChecker.isOccupied(appointment, appointmentRepository.findAll())) {
            throw new OccupiedAppointmentException(
                    "An appointment already exists at this date and time.");
        }
        appointmentRepository.save(appointment);
    }
}
