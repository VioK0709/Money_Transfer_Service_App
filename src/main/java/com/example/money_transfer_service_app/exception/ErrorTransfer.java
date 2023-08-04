package com.example.money_transfer_service_app.exception;

public class ErrorTransfer extends RuntimeException {

    public ErrorTransfer(String message) {
        super(message);
    }
}