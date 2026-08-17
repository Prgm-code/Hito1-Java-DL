package com.chronus.application.service;

import com.chronus.application.usecase.CreatePatientUseCase;
import com.chronus.domain.entity.Patient;
import com.chronus.domain.repository.PatientRepository;

// servicio de aplicación que persiste pacientes mediante el contrato del dominio
public class CreatePatientService implements CreatePatientUseCase {
    private final PatientRepository patientRepository;

    public CreatePatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void createPatient(Patient patient) {
        patientRepository.save(patient);
    }
}
