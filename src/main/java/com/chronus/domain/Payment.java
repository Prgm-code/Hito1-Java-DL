package com.chronus.domain;

/**
 * Domain entity that represents a Chronus payment.
 * It encapsulates the amount; {@link PaymentService} validates it before
 * persistence through {@link PaymentRepository}.
 */
public class Payment {
    /** Payment amount. */
    private final double amount;

    /**
     * Creates a payment with the provided amount.
     * Business rules are validated by the domain service.
     */
    public Payment(double amount) {
        this.amount = amount;
    }

    /** Returns the payment amount. */
    public double getAmount() {
        return amount;
    }
}
