package com.chronus.infrastructure.persistence;

import com.chronus.domain.entity.Appointment;
import com.chronus.domain.repository.AppointmentRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory adapter for {@link AppointmentRepository}.
 */
public class InMemoryAppointmentRepository implements AppointmentRepository {
    private final List<Appointment> appointments = new ArrayList<>();

    @Override
    public void save(Appointment appointment) {
        appointments.add(appointment);
    }

    @Override
    public List<Appointment> findAll() {
        return List.copyOf(appointments);
    }
}
