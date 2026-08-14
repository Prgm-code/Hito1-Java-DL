package com.chronus.application.service;

import com.chronus.application.port.EmailNotifier;
import com.chronus.application.port.WhatsAppNotifier;
import com.chronus.application.usecase.SendAppointmentReminderUseCase;
import com.chronus.domain.entity.Appointment;
import com.chronus.domain.entity.Patient;

/**
 * Application service that sends appointment reminders through notification ports.
 */
public class SendAppointmentReminderService implements SendAppointmentReminderUseCase {
    private final EmailNotifier emailNotifier;
    private final WhatsAppNotifier whatsAppNotifier;

    public SendAppointmentReminderService(
            EmailNotifier emailNotifier,
            WhatsAppNotifier whatsAppNotifier) {
        this.emailNotifier = emailNotifier;
        this.whatsAppNotifier = whatsAppNotifier;
    }

    @Override
    public void sendReminder(Patient patient, Appointment appointment) {
        String message = "Recordatorio: su cita está programada para "
                + appointment.getDateTime() + ".";
        emailNotifier.sendEmail(patient.getEmail(), message);
        whatsAppNotifier.sendWhatsApp(patient.getPhoneNumber(), message);
    }
}
