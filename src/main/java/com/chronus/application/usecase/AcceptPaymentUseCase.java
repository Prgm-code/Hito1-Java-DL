package com.chronus.application.usecase;

import com.chronus.application.port.EmailNotifier;
import com.chronus.application.port.WhatsAppNotifier;
import com.chronus.domain.entity.Payment;
import com.chronus.domain.repository.PaymentRepository;

/**
 * Input port for accepting a payment.
 */
public class AcceptPaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final EmailNotifier emailNotifier;
    private final WhatsAppNotifier whatsAppNotifier;

    // inyeccion de dependencias por constructor
    public AcceptPaymentUseCase(
            PaymentRepository paymentRepository,
            EmailNotifier emailNotifier,
            WhatsAppNotifier whatsAppNotifier) {
        this.paymentRepository = paymentRepository;
        this.emailNotifier = emailNotifier;
        this.whatsAppNotifier = whatsAppNotifier;
    }

    public void execute(Payment payment) {
        double amount = payment.getAmount().value();
        paymentRepository.save(payment);
        String message = "Pago de " + (int) amount + " aceptado";
        emailNotifier.sendEmail("patient@chronus.com", message);
        whatsAppNotifier.sendWhatsApp("+56900000000", message);
    }
}
