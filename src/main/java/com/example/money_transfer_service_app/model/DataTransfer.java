package com.example.money_transfer_service_app.model;

import com.example.money_transfer_service_app.card.Amount;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@RequiredArgsConstructor
@Validated
@AllArgsConstructor
public class DataTransfer {
    @NotNull(message = "Необходимо ввести номер карты отправителя")
    @Size(min = 16, message = "Номер карты состоит из 16-и символов")
    private String cardFromNumber;

    @NotNull(message = "Необходимо ввести номер карты получателя")
    @Size(min = 16, message = "Номер карты состоит из 16-и символов")
    private String cardToNumber;

    @NotNull(message = "Необходимо ввести срок действия карты")
    @Size(min = 4, message = "Срок действия состоит из 4-х символов")
    private String cardFromValidTill;

    @NotNull(message = "Необходимо ввести CVV/CVC")
    @Size(min = 3, message = "CVV/CVC состоит из 3-х символов")
    private String cardFromCVV;

    private Amount amount;
}

record DataTransferRecord(String cardFromNumber, String cardToNumber,
                          String cardFromValidTill, String cardFromCVV,
                          Amount amount) {
}