package com.chronus.application.service;

/**
 * Outbound service for email notifications.
 */
public interface EmailNotifier {
    void sendEmail(String email, String message);
}
