package com.chronus.domain.entity;

import com.chronus.domain.valueobject.PaymentAmount;

// entidad de dominio que representa un pago
public class Payment {
    private PaymentAmount amount;

    // constructor para crear un pago con el monto requerido
    public Payment(PaymentAmount amount) {
        this.amount = amount;
    }

    // metodo con semantica de negocio para actualizar el monto del pago
    public void updateAmount(PaymentAmount amount) {
        this.amount = amount;
    }

    // getters para obtener los detalles del pago
    public PaymentAmount getAmount() {
        return amount;
    }
}
