package com.example.money_transfer_service_app.repository;

import com.example.money_transfer_service_app.card.Amount;
import com.example.money_transfer_service_app.card.AmountCard;
import com.example.money_transfer_service_app.card.Card;
import com.example.money_transfer_service_app.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class TransferRepositoryImplTest {

    BigDecimal testCardValue = BigDecimal.valueOf(111_111.11);
    Card testCard = new Card("543673467673", "01/24", "123", new AmountCard(testCardValue, "RUB"));
    String testCardToNumber = "36476854459";
    DataTransfer testTransferData = new DataTransfer("543673467673", "36476854459", "01/24", "123", new Amount(535_666, "RUB"));
    BigDecimal transferValue = BigDecimal.valueOf(535_666 / 100).setScale(2, RoundingMode.CEILING);
    BigDecimal fee = transferValue.multiply(BigDecimal.valueOf(0.01))
            .setScale(2, RoundingMode.CEILING);
    BigDecimal newValueCardFrom = (testCardValue.subtract(transferValue.multiply(BigDecimal.valueOf(1.01))))
            .setScale(2, RoundingMode.CEILING);
    String testOperationId = "Operation#0001";
    Map<String, Card> testCardsRepository = new HashMap<>();
    DataOperation testDataOperation = new DataOperation(testCard, testCardToNumber, transferValue, newValueCardFrom, fee);

    @BeforeEach
    public void fillMap() {
        testCardsRepository.put("543673467673", testCard);
    }

    @Test
    void testTransfer() {
        DataOperation expected = new DataOperation(testCard, testCardToNumber, transferValue, newValueCardFrom, fee);
        DataOperation result = new TransferRepositoryImpl(testCardsRepository).transfer(testTransferData);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testConfirmOperation() {
        Boolean result = new TransferRepositoryImpl(testCardsRepository).confirmOperation(testOperationId, testDataOperation);
        Assertions.assertEquals(true, result);
    }

    @Test
    void testAcceptData() {
        DataOperation expected = new DataOperation(testCard, testCardToNumber, transferValue, newValueCardFrom, fee);
        DataOperation result = testDataOperation;
        Assertions.assertEquals(expected, result);
    }
}