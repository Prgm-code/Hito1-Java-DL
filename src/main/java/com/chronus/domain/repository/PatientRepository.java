package com.chronus.domain.repository;

import com.chronus.domain.entity.Patient;

import java.util.List;

// contract for the patient repository
public interface PatientRepository {
    // save a patient
    void save(Patient patient);

    Patient findById(String id);

    List<Patient> findAll();
}
