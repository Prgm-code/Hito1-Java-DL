package com.chronus.infrastructure.notification;

import com.chronus.application.port.WhatsAppNotifier;

/**
 * WhatsApp adapter used until a real messaging gateway is wired.
 */
public class NoOpWhatsAppNotifier implements WhatsAppNotifier {
    @Override
    public void sendWhatsApp(String phone, String message) {
    }
}
