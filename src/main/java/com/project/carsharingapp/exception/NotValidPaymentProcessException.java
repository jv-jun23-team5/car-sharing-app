package com.project.carsharingapp.exception;

public class NotValidPaymentProcessException extends RuntimeException {
    public NotValidPaymentProcessException(String message) {
        super(message);
    }
}
