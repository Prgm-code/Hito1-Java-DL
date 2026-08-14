package com.chronus.infrastructure.persistence;

import com.chronus.domain.entity.Payment;
import com.chronus.domain.repository.PaymentRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory adapter for {@link PaymentRepository}.
 */
public class InMemoryPaymentRepository implements PaymentRepository {
    private final List<Payment> payments = new ArrayList<>();

    @Override
    public void save(Payment payment) {
        payments.add(payment);
    }

    @Override
    public List<Payment> findAll() {
        return List.copyOf(payments);
    }
}
