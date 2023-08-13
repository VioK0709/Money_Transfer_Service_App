package com.example.money_transfer_service_app.model;

public record Card(String cardFromNumber, String cardFromValidTill,
                   String cardFromCVV, AmountCard amount) {

    public String printData() {
        return "номер карты: "
                + cardFromNumber + ", сроком действия до "
                + cardFromValidTill + ", CVV: "
                + cardFromCVV + ", сумма на карте: "
                + amount.getValue() + ", валюта: "
                + amount.getCurrency();
    }

    @Override
    public String toString() {
        return cardFromNumber;
    }

    public void setAmount(AmountCard amount) {
    }
    public String getCardFromNumber() {
        return cardFromNumber;
    }

    public AmountCard getAmount() {
        return amount;
    }

    public Object getCardFromCVV() {
        return cardFromCVV;
    }

    public Object getCardFromValidTill() {
        return cardFromValidTill;
    }
}