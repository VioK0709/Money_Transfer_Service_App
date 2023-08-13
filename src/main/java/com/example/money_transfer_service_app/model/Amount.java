package com.example.money_transfer_service_app.model;

import org.springframework.validation.annotation.Validated;

@Validated

public record Amount(long value, String currency) {
    public long getValue() {
        return value;
    }
}