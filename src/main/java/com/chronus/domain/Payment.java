package com.chronus.domain;

/**
 * Entidad de dominio que representa un pago en Chronus.
 * Encapsula el monto; el servicio de dominio ({@link PaymentService})
 * lo valida antes de persistirlo en {@link PaymentRepository}.
 */
public class Payment {
    /** Monto del pago. */
    private final double amount;

    /**
     * Crea un pago con el monto indicado.
     * No valida reglas de negocio aquí: eso lo hace el servicio.
     */
    public Payment(double amount) {
        this.amount = amount;
    }

    /** Devuelve el monto del pago. */
    public double getAmount() {
        return amount;
    }
}
