package com.chronus.domain.entity;

/**
 * Domain entity that represents a Chronus payment.
 * The application service validates the amount before persistence
 * through {@link com.chronus.domain.repository.PaymentRepository}.
 */
public class Payment {
    /** Payment amount. */
    private final double amount;

    /**
     * Creates a payment with the provided amount.
     * Business rules are validated by the application service.
     */
    public Payment(double amount) {
        this.amount = amount;
    }

    /** Returns the payment amount. */
    public double getAmount() {
        return amount;
    }
}
