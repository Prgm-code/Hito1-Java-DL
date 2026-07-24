package com.chronus.domain;

import com.chronus.domain.exception.InvalidDateAppointmentException;
import com.chronus.domain.exception.OccupiedAppointmentException;

import java.time.LocalDateTime;

/**
 * Servicio de dominio para gestionar citas en Chronus.
 * Contiene las reglas de negocio (validaciones) y delega la
 * persistencia en {@link AppointmentRepository}.
 */
public class AppointmentService {
    /** Repositorio donde se guardan las citas validadas. */
    private final AppointmentRepository appointmentRepository;

    /**
     * Inyecta el repositorio por constructor.
     * Facilita pruebas con dobles o con la implementación en memoria.
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
        // Si hay colisión, lanza una excepción Ocupado.
        if (collision) {
            throw new OccupiedAppointmentException(
                    "An appointment already exists at this date and time.");
        }
        appointmentRepository.save(appointment);
    }
}
