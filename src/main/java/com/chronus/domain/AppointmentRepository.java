package com.chronus.domain;

import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {
    /** Citas actualmente registradas en memoria. */
    private final List<Appointment> appointments = new ArrayList<>();

    /**
     * Persiste una cita en el repositorio.
     * Se espera recibir una cita ya validada por la capa de dominio
     * (por ejemplo, con fecha futura).
     */

    public void save(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * Devuelve una copia inmutable de todas las citas guardadas.
     */

    public List<Appointment> findAll() {
        return List.copyOf(appointments);
    }
}
