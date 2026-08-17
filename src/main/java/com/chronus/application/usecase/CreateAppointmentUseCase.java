package com.chronus.application.usecase;

import com.chronus.domain.entity.Appointment;
import com.chronus.domain.repository.AppointmentRepository;

/**
 * Input port for scheduling an appointment.
 */
public class CreateAppointmentUseCase {
    private final AppointmentRepository appointmentRepository;

    // inyeccion de dependencias por constructor
    public CreateAppointmentUseCase(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void execute(Appointment appointment) {
        if (appointmentRepository.findByAppointmentId(appointment.getAppointmentId()) != null) {
            throw new RuntimeException("Appointment already exists");
        }
        appointmentRepository.save(appointment);
    }
}
