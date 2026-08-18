package com.chronus.infrastructure.persistence;

import com.chronus.domain.entity.Patient;
import com.chronus.domain.repository.PatientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * In-memory adapter for {@link PatientRepository}.
 */
public class InMemoryPatientRepository implements PatientRepository {
    private final List<Patient> patients = new ArrayList<>();

    @Override
    public void save(Patient patient) {
        patients.add(patient);
    }

    @Override
    public Optional<Patient> findById(String id) {
        return patients.stream()
                .filter(patient -> Objects.equals(patient.getPatientId(), id))
                .findFirst();
    }

    @Override
    public List<Patient> findAll() {
        return List.copyOf(patients);
    }
}
