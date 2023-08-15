package com.example.money_transfer_service_app.model;

public record Verification(String operationId, String code) {

    public static boolean isCodeCorrect(String code) {
        return (Integer.parseInt(code) > 1);
    }

    public String getOperationId() {
        return operationId;
    }

    public String getCode() {
        return code;
    }
}

