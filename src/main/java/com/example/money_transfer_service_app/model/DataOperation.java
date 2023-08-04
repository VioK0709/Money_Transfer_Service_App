package com.example.money_transfer_service_app.model;

import com.example.money_transfer_service_app.card.Card;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class DataOperation {

    private Card cardFromNumber;
    private String cardToNumber;
    private BigDecimal transferValue;
    private BigDecimal remains;
    private BigDecimal transferFee;
}

record DataOperationRecord(Card cardFromNumber, String cardToNumber,
                           BigDecimal transferValue, BigDecimal remains,
                           BigDecimal transferFee) {
}