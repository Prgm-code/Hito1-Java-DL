package com.chronus.infrastructure.notification;

import com.chronus.application.service.EmailNotifier;

/**
 * Email adapter used until a real mail gateway is wired.
 */
public class NoOpEmailNotifier implements EmailNotifier {
    @Override
    public void sendEmail(String email, String message) {
    }
}
