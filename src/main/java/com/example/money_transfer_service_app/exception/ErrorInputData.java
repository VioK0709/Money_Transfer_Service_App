package com.example.money_transfer_service_app.exception;

public class ErrorInputData extends RuntimeException {

    public ErrorInputData(String message) {
        super(message);
    }
}