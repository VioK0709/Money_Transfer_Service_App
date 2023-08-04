package com.example.money_transfer_service_app.card;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor

public class Card {

    private String cardFromNumber;
    private String cardFromValidTill;
    private String cardFromCVV;
    private AmountCard amount;

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
}