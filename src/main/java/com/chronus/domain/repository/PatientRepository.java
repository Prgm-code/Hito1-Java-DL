package com.chronus.domain.repository;

import com.chronus.domain.entity.Patient;

import java.util.List;
import java.util.Optional;

// contract for the patient repository
public interface PatientRepository {
    // save a patient
    void save(Patient patient);

    List<Patient> findAll();

    Optional<Patient> findById(String id);

}
