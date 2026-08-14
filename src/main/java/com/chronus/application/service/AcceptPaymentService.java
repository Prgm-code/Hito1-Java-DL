package com.chronus.application.service;

import com.chronus.application.port.EmailNotifier;
import com.chronus.application.port.WhatsAppNotifier;
import com.chronus.application.usecase.AcceptPaymentUseCase;
import com.chronus.domain.entity.Payment;
import com.chronus.domain.exception.InvalidPaymentException;
import com.chronus.domain.repository.PaymentRepository;

/**
 * Application service that validates payments, persists them through
 * {@link PaymentRepository}, and notifies through outbound ports.
 */
public class AcceptPaymentService implements AcceptPaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final EmailNotifier emailNotifier;
    private final WhatsAppNotifier whatsAppNotifier;

    public AcceptPaymentService(
            PaymentRepository paymentRepository,
            EmailNotifier emailNotifier,
            WhatsAppNotifier whatsAppNotifier) {
        this.paymentRepository = paymentRepository;
        this.emailNotifier = emailNotifier;
        this.whatsAppNotifier = whatsAppNotifier;
    }

    /**
     * Accepts a payment only when its amount is a strictly positive whole number.
     * Zero, negative, or fractional amounts raise {@link InvalidPaymentException}
     * and are not persisted. Valid payments are stored and notified.
     */
    @Override
    public void acceptPayment(Payment payment) {
        double amount = payment.getAmount();
        if (amount <= 0 || amount != Math.floor(amount)) {
            throw new InvalidPaymentException(
                    "The payment amount must be a positive whole number.");
        }
        paymentRepository.save(payment);
        String message = "Pago de " + (int) amount + " aceptado";
        emailNotifier.sendEmail("patient@chronus.com", message);
        whatsAppNotifier.sendWhatsApp("+56900000000", message);
    }
}
