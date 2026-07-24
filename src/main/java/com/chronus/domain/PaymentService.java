package com.chronus.domain;

import com.chronus.domain.exception.InvalidPaymentException;

/**
 * Domain service that manages Chronus payments.
 * It contains business validations, delegates persistence to
 * {@link PaymentRepository}, and sends notifications through
 * {@link EmailNotifier} and {@link WhatsAppNotifier}.
 */
public class PaymentService {
    /** Collaborators used by the payment workflow. */
    private final PaymentRepository paymentRepository;
    private final EmailNotifier emailNotifier;
    private final WhatsAppNotifier whatsAppNotifier;

    /**
     * Injects repository and notification collaborators through the constructor.
     */
    public PaymentService(
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
