package com.chronus.application.service;

import com.chronus.application.port.EmailNotifier;
import com.chronus.application.port.WhatsAppNotifier;
import com.chronus.application.usecase.AcceptPaymentUseCase;
import com.chronus.domain.entity.Payment;
import com.chronus.domain.repository.PaymentRepository;

// servicio de aplicacion que valida los pagos, los persiste en el repositorio y
// notifica a través de los puertos de salida
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

    // metodo que acepta un pago y lo persiste en el repositorio
    @Override
    public void acceptPayment(Payment payment) {
        double amount = payment.getAmount().value();
        paymentRepository.save(payment);
        String message = "Pago de " + (int) amount + " aceptado";
        emailNotifier.sendEmail("patient@chronus.com", message);
        whatsAppNotifier.sendWhatsApp("+56900000000", message);
    }
}
