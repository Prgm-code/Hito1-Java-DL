package com.chronus.domain.exception;

/**
 * Raised when a phone number is missing or is not a valid E.164 WhatsApp number.
 */
public class InvalidPhoneNumberException extends RuntimeException {
    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}
