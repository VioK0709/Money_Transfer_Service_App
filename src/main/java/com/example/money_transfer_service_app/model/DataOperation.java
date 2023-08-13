package com.example.money_transfer_service_app.model;

import java.math.BigDecimal;

public record DataOperation(
        Card cardFromNumber, String cardToNumber, BigDecimal transferValue, BigDecimal remains,
        BigDecimal transferFee) {
    public BigDecimal getTransferValue() {
        return transferValue;
    }

    public BigDecimal getRemains() {
        return remains;
    }

    public Card getCardFromNumber() {
        return cardFromNumber;
    }

    public String getCardToNumber() {
        return cardToNumber;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }
}