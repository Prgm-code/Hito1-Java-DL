package com.chronus.domain.entity;

import com.chronus.domain.valueobject.AppointmentDateTime;
import com.chronus.domain.valueobject.AppointmentId;

import java.time.LocalDateTime;

// entidad de dominio que representa una cita de atención médica
public class Appointment {
    private final AppointmentId appointmentId;
    private AppointmentDateTime dateTime;

    // constructor para crear una cita con la fecha y hora requeridas
    public Appointment(String appointmentId, LocalDateTime dateTime) {
        this.appointmentId = new AppointmentId(appointmentId);
        this.dateTime = new AppointmentDateTime(dateTime);
    }

    // metodo con semantica de negocio para actualizar la fecha y hora de la cita
    public void updateDateTime(LocalDateTime dateTime) {
        this.dateTime = new AppointmentDateTime(dateTime);
    }

    // getters para obtener los detalles de la cita
    public String getAppointmentId() {
        return appointmentId.value();
    }

    public AppointmentDateTime getDateTime() {
        return dateTime;
    }
}
