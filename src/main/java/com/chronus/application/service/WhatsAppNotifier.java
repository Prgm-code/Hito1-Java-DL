package com.chronus.application.service;

/**
 * Outbound service for WhatsApp notifications.
 */
public interface WhatsAppNotifier {
    void sendWhatsApp(String phone, String message);
}
