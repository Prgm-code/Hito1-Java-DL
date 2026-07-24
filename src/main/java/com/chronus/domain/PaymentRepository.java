package com.chronus.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Almacén en memoria de los pagos del dominio Chronus.
 * No usa base de datos: guarda y consulta {@link Payment} en una lista local.
 */
public class PaymentRepository {
    /** Pagos actualmente registrados en memoria. */
    private final List<Payment> payments = new ArrayList<>();

    /**
     * Persiste un pago en el repositorio.
     * Se espera recibir un pago ya validado por la capa de dominio.
     */
    public void save(Payment payment) {
        payments.add(payment);
    }

    /**
     * Devuelve una copia inmutable de todos los pagos guardados.
     */
    public List<Payment> findAll() {
        return List.copyOf(payments);
    }
}
