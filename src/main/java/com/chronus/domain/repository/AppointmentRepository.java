package com.chronus.domain.repository;

import com.chronus.domain.entity.Appointment;

import java.util.List;

/**
 * Persistence contract for appointments. Implementations live in
 * infrastructure.
 */
public interface AppointmentRepository {
    void save(Appointment appointment);

    List<Appointment> findAll();

    Appointment findByAppointmentId(String appointmentId);
}
