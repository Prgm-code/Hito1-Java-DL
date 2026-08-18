package com.chronus.application.usecase;

import com.chronus.application.service.EmailNotifier;
import com.chronus.application.service.WhatsAppNotifier;
import com.chronus.domain.entity.Appointment;
import com.chronus.domain.entity.Patient;
import com.chronus.domain.exception.InvalidPatientDataException;
import com.chronus.domain.repository.PatientRepository;

/**
 * Input port for sending an appointment reminder.
 */
public class SendAppointmentReminderUseCase {
    private final PatientRepository patientRepository;
    private final EmailNotifier emailNotifier;
    private final WhatsAppNotifier whatsAppNotifier;

    // inyeccion de dependencias por constructor
    public SendAppointmentReminderUseCase(
            PatientRepository patientRepository,
            EmailNotifier emailNotifier,
            WhatsAppNotifier whatsAppNotifier) {
        this.patientRepository = patientRepository;
        this.emailNotifier = emailNotifier;
        this.whatsAppNotifier = whatsAppNotifier;
    }

    public void execute(String patientId, Appointment appointment) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new InvalidPatientDataException("The patient was not found."));

        String message = "Recordatorio: su cita está programada para "
                + appointment.getDateTime().value() + ".";
        emailNotifier.sendEmail(patient.getEmail().value(), message);
        whatsAppNotifier.sendWhatsApp(patient.getPhoneNumber().value(), message);
    }
}
