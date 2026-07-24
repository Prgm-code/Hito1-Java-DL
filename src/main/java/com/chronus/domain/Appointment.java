package com.chronus.domain;

import java.time.LocalDateTime;

public class Appointment {
    /** Fecha y hora programada del turno. */
    private final LocalDateTime dateTime;

    /**
     * Crea una cita con la fecha y hora indicadas.
     * No valida reglas de negocio aquí: eso lo hace el servicio.
     */
    public Appointment(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /** Devuelve la fecha y hora del turno. */
    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
