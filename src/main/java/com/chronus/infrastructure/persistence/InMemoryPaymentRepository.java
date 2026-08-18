package com.chronus.infrastructure.persistence;

import com.chronus.domain.entity.Payment;
import com.chronus.domain.repository.PaymentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @Override
    public Optional<Payment> findById(String id) {
        return payments.stream()
                .filter(payment -> Objects.equals(payment.getPaymentId(), id))
                .findFirst();
    }
}
