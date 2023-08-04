package com.example.money_transfer_service_app.card;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AmountCard {

    private BigDecimal value;
    private String currency;
}