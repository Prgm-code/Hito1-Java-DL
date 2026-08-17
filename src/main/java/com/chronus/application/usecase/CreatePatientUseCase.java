package com.chronus.application.usecase;

import com.chronus.domain.entity.Patient;
import com.chronus.domain.repository.PatientRepository;

/**
 * Input port for creating a patient.
 */
public class CreatePatientUseCase {
    private final PatientRepository patientRepository;

    // inyeccion de dependencias por constructor
    public CreatePatientUseCase(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void execute(Patient patient) {
        patientRepository.save(patient);
    }
}
