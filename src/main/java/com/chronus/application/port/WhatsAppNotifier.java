package com.chronus.application.port;

/**
 * Outbound port for WhatsApp notifications.
 */
public interface WhatsAppNotifier {
    void sendWhatsApp(String phone, String message);
}
