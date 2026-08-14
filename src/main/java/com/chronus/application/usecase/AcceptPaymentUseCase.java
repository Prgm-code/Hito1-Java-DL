package com.chronus.application.usecase;

import com.chronus.domain.entity.Payment;

/**
 * Input port for accepting a payment.
 */
public interface AcceptPaymentUseCase {
    void acceptPayment(Payment payment);
}
