package com.chronus.domain;

import com.chronus.domain.exception.InvalidPaymentException;

/**
 * Servicio de dominio para gestionar pagos en Chronus.
 * Contiene las reglas de negocio (validaciones), delega la
 * persistencia en {@link PaymentRepository} y notifica mediante
 * {@link EmailNotifier} y {@link WhatsAppNotifier}.
 */
public class PaymentService {
    /** Repositorio donde se guardan los pagos validados. */
    private final PaymentRepository paymentRepository;
    private final EmailNotifier emailNotifier;
    private final WhatsAppNotifier whatsAppNotifier;

    /**
     * Inyecta repositorio y notificadores por constructor.
     * Facilita pruebas con dobles o con la implementación en memoria.
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
     * Acepta un pago si su monto es un entero estrictamente positivo.
     * Si el monto es cero, negativo o decimal lanza
     * {@link InvalidPaymentException} y no lo persiste.
     * Si es válido, lo guarda y envía notificaciones.
     */
    public void acceptPayment(Payment payment) {
        double amount = payment.getAmount();
        if (amount <= 0 || amount != Math.floor(amount)) {
            throw new InvalidPaymentException(
                    "The payment amount must be a positive whole number.");
        }
        paymentRepository.save(payment);
        String message = "Payment of " + (int) amount + " accepted";
        emailNotifier.sendEmail("patient@chronus.com", message);
        whatsAppNotifier.sendWhatsApp("+56900000000", message);
    }
}
