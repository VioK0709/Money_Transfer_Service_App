package com.example.money_transfer_service_app.model;

import java.math.BigDecimal;

public record AmountCard(BigDecimal value, String currency) {

    public BigDecimal getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }
}