package com.example.money_transfer_service_app.card;

import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@AllArgsConstructor
@ToString
public class Amount {

    private long value;
    private String currency;
}