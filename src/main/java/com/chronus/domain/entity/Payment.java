package com.chronus.domain.entity;

import com.chronus.domain.valueobject.PaymentAmount;
import com.chronus.domain.valueobject.PaymentId;

// entidad de dominio que representa un pago
public class Payment {
    private final PaymentId paymentId;
    private PaymentAmount amount;

    // constructor para crear un pago con el monto requerido
    public Payment(String paymentId, PaymentAmount amount) {
        this.paymentId = new PaymentId(paymentId);
        this.amount = amount;
    }

    // metodo con semantica de negocio para actualizar el monto del pago
    public void updateAmount(PaymentAmount amount) {
        this.amount = amount;
    }

    // getters para obtener los detalles del pago
    public String getPaymentId() {
        return paymentId.value();
    }

    public PaymentAmount getAmount() {
        return amount;
    }
}
