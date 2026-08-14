package com.chronus.application.port;

/**
 * Outbound port for email notifications.
 */
public interface EmailNotifier {
    void sendEmail(String email, String message);
}
