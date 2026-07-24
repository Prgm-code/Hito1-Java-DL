package com.chronus.domain;

/**
 * Domain service that sends appointment reminders through notification collaborators.
 */
public class AppointmentReminder {
    private final EmailNotifier emailNotifier;
    private final WhatsAppNotifier whatsAppNotifier;

    /**
     * Injects the notification collaborators through the constructor.
     */
    public AppointmentReminder(
            EmailNotifier emailNotifier,
            WhatsAppNotifier whatsAppNotifier) {
        this.emailNotifier = emailNotifier;
        this.whatsAppNotifier = whatsAppNotifier;
    }

    /**
     * Sends the appointment reminder through email and WhatsApp.
     */
    public void sendReminder(Patient patient, Appointment appointment) {
        String message = "Recordatorio: su cita está programada para "
                + appointment.getDateTime() + ".";
        emailNotifier.sendEmail(patient.getEmail(), message);
        whatsAppNotifier.sendWhatsApp(patient.getPhoneNumber(), message);
    }
}
