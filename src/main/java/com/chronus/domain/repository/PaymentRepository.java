package com.chronus.domain.repository;

import com.chronus.domain.entity.Payment;

import java.util.List;

/**
 * Persistence contract for payments. Implementations live in infrastructure.
 */
public interface PaymentRepository {
    void save(Payment payment);

    List<Payment> findAll();
}
