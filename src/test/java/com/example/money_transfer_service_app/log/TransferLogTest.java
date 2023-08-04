package com.example.money_transfer_service_app.log;

import com.example.money_transfer_service_app.card.AmountCard;
import com.example.money_transfer_service_app.card.Card;
import com.example.money_transfer_service_app.model.DataOperation;

import static com.example.money_transfer_service_app.constant.ConstantApplication.LOG_INFORMATION;
import static com.example.money_transfer_service_app.log.TransferLog.createStringLog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransferLogTest {

    String testOperationId = "0001";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    void testCreateStringLog() {
        BigDecimal testCardValue = BigDecimal.valueOf(111_111.11);
        Card testCard = new Card("543673467673", "01/24", "123", new AmountCard(BigDecimal.valueOf(111_111.11), "RUB"));
        String testCardToNumber = "36476854459";
        BigDecimal transferValue = BigDecimal.valueOf(535_666).setScale(2, RoundingMode.CEILING);
        BigDecimal fee = transferValue.multiply(BigDecimal.valueOf(0.01))
                .setScale(2, RoundingMode.CEILING);
        BigDecimal newValueCardFrom = (testCardValue.subtract(transferValue.multiply(BigDecimal.valueOf(1.01))))
                .setScale(2, RoundingMode.CEILING);
        DataOperation testOperation =
                new DataOperation(testCard,
                        testCardToNumber, transferValue,
                        newValueCardFrom, fee);
        String expected = String.format(LOG_INFORMATION, testOperationId, LocalDateTime.now().format(formatter),
                testCard.getCardFromNumber(), testCardToNumber,
                transferValue, fee, newValueCardFrom);
        String result = createStringLog(testOperationId, testOperation);
        Assertions.assertEquals(expected, result);
    }
}