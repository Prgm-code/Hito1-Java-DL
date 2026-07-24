package com.chronus.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory repository for storing and retrieving payments.
 */
public class PaymentRepository {
    private final List<Payment> payments = new ArrayList<>();

    /**
     * Stores a validated payment.
     */
    public void save(Payment payment) {
        payments.add(payment);
    }

    /**
     * Returns all stored payments.
     */
    public List<Payment> findAll() {
        return List.copyOf(payments);
    }
}
