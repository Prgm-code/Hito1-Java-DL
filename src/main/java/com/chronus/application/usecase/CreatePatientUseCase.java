package com.chronus.application.usecase;

import com.chronus.domain.entity.Patient;

/**
 * Input port for creating a patient.
 */
public interface CreatePatientUseCase {
    void createPatient(Patient patient);
}
