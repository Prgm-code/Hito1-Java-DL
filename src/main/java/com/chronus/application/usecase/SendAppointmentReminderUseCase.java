package com.chronus.application.usecase;

import com.chronus.domain.entity.Appointment;
import com.chronus.domain.entity.Patient;

/**
 * Input port for sending an appointment reminder.
 */
public interface SendAppointmentReminderUseCase {
    void sendReminder(Patient patient, Appointment appointment);
}
