package com.chronus.domain.entity;

import com.chronus.domain.valueobject.Email;
import com.chronus.domain.valueobject.FullName;
import com.chronus.domain.valueobject.PatientId;
import com.chronus.domain.valueobject.PhoneNumber;

// entidad de dominio que representa a un paciente que recibe citas de atención médica
public class Patient {
    private final PatientId patientId;
    private FullName fullName;
    private Email email;
    private PhoneNumber phoneNumber;

    // constructor para crear un paciente con los detalles de contacto requeridos
    // para las notificaciones
    public Patient(String patientId, String fullName, Email email, PhoneNumber phoneNumber) {
        this.patientId = new PatientId(patientId);
        this.fullName = new FullName(fullName);
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // metodo con semantica de negocio par aactualizar la informacion del paciente
    public void updateInformation(String fullName, Email email, PhoneNumber phoneNumber) {
        this.fullName = new FullName(fullName);
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // getters para obtener los detalles del paciente
    public String getPatientId() {
        return patientId.value();
    }

    public String getFullName() {
        return fullName.value();
    }

    public Email getEmail() {
        return email;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }
}
