package com.chronus.application.usecase;

import com.chronus.domain.entity.Appointment;

/**
 * Input port for scheduling an appointment.
 */
public interface CreateAppointmentUseCase {
    void createAppointment(Appointment appointment);
}
