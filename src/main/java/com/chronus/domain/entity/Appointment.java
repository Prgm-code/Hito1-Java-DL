package com.chronus.domain.entity;

import com.chronus.domain.valueobject.AppointmentDateTime;

// entidad de dominio que representa una cita de atención médica
public class Appointment {
    private AppointmentDateTime dateTime;

    // constructor para crear una cita con la fecha y hora requeridas
    public Appointment(AppointmentDateTime dateTime) {
        this.dateTime = dateTime;
    }

    // metodo con semantica de negocio para actualizar la fecha y hora de la cita
    public void updateDateTime(AppointmentDateTime dateTime) {
        this.dateTime = dateTime;
    }

    // getters para obtener los detalles de la cita
    public AppointmentDateTime getDateTime() {
        return dateTime;
    }
}
