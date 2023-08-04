package com.example.money_transfer_service_app.exception;

public class ErrorConfirmation extends RuntimeException {

    public ErrorConfirmation(String message) {
        super(message);
    }
}