package com.chronus.domain.entity;

import com.chronus.domain.valueobject.Email;
import com.chronus.domain.valueobject.PhoneNumber;

// entidad de dominio que representa a un paciente que recibe citas de atención médica
public class Patient {
    private final String patientId;
    private String fullName;
    private Email email;
    private PhoneNumber phoneNumber;

    // constructor para crear un paciente con los detalles de contacto requeridos
    // para las notificaciones
    public Patient(String patientId, String fullName, Email email, PhoneNumber phoneNumber) {

        this.patientId = patientId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // metodo con semantica de negocio par aactualizar la informacion del paciente
    public void updateInformation(String fullName, Email email, PhoneNumber phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;

    }

    // getters para obtener los detalles del paciente
    public String getPatientId() {
        return patientId;
    }

    public String getFullName() {
        return fullName;
    }

    public Email getEmail() {
        return email;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

}
