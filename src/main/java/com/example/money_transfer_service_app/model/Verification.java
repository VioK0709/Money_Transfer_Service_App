package com.example.money_transfer_service_app.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Verification {

    private String operationId;
    private String code;

    public static boolean isCodeCorrect(String code) {
        return (Integer.parseInt(code) > 1);
    }
}

record VerificationRecord(String operationId, String code) {
}