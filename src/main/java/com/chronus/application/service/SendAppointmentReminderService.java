package com.chronus.application.service;

import com.chronus.application.port.EmailNotifier;
import com.chronus.application.port.WhatsAppNotifier;
import com.chronus.application.usecase.SendAppointmentReminderUseCase;
import com.chronus.domain.entity.Appointment;
import com.chronus.domain.entity.Patient;

// servicio de aplicacion que envia recordatorios de citas a través de los puertos de notificación

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
                + appointment.getDateTime().value() + ".";
        emailNotifier.sendEmail(patient.getEmail().value(), message);
        whatsAppNotifier.sendWhatsApp(patient.getPhoneNumber().value(), message);
    }
}
