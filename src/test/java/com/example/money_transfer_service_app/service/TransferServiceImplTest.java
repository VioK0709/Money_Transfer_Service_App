package com.example.money_transfer_service_app.service;

import com.example.money_transfer_service_app.card.Amount;
import com.example.money_transfer_service_app.card.AmountCard;
import com.example.money_transfer_service_app.card.Card;
import com.example.money_transfer_service_app.model.*;
import com.example.money_transfer_service_app.repository.TransferRepositoryImpl;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Map;

public class TransferServiceImpTest {

    TransferRepositoryImpl cardRepositoryImplMock = Mockito.mock(TransferRepositoryImpl.class);
    Map<String, DataOperation> operationsRepositoryMock = Mockito.mock(Map.class);
    Map<String, String> verificationRepositoryMock = Mockito.mock(Map.class);

    TransferServiceImpl transferServiceImpl = new TransferServiceImpl(cardRepositoryImplMock, operationsRepositoryMock, verificationRepositoryMock);


    BigDecimal testCardValue = BigDecimal.valueOf(111_111.11);
    Card testCardImpl = new Card("543673467673", "01/24", "123", new AmountCard(testCardValue, "RUB"));

    String testCardToNumber = "36476854459";

    DataTransfer testTransferRequest = new DataTransfer("543673467673", testCardToNumber, "01/24", "123", new Amount(535_666, "RUB"));
    BigDecimal transferValue = BigDecimal.valueOf(535_666).setScale(2, RoundingMode.CEILING);
    BigDecimal fee = transferValue.multiply(BigDecimal.valueOf(0.01))
            .setScale(2, RoundingMode.CEILING);
    BigDecimal newValueCardFrom = (testCardValue.subtract(transferValue.multiply(BigDecimal.valueOf(1.01))))
            .setScale(2, RoundingMode.CEILING);


    DataOperation testOperation = new DataOperation(testCardImpl, testCardToNumber, transferValue, newValueCardFrom, fee);

    String testOperationId = "0001";
    String testCode = "2222";

    @BeforeEach
    public void mockBeforeEach() {
        Mockito.when(operationsRepositoryMock.get(testOperationId)).thenReturn(testOperation);
        Mockito.when(verificationRepositoryMock.containsKey(testOperationId)).thenReturn(true);
        Mockito.when(verificationRepositoryMock.get(testOperationId)).thenReturn(testCode);
    }


    @Before
    public void mockTransfer() {
        Mockito.when(cardRepositoryImplMock.transfer(testTransferRequest)).thenReturn(testOperation);
    }

    @Test
    void testTransferService() {
        mockTransfer();
        String result = transferServiceImpl.postTransfer(testTransferRequest);
        Assertions.assertEquals(testOperationId, result);
    }

    @Before
    public void mockConfirm() {
        Mockito.when(cardRepositoryImplMock.confirmOperation(testOperationId, testOperation)).thenReturn(true);
    }

    @Test
    void testConfirmService() {
        mockConfirm();
        Verification testVerification = new Verification(testOperationId,testCode);
        String result = transferServiceImpl.confirmOperation(testVerification);
        Assertions.assertEquals("0001", result);
    }

    @Test
    void testIsCodeCorrect() {
        String testCode = "2222";
        System.out.println("Верный код: " + testCode);
        Assertions.assertTrue(Verification.isCodeCorrect(testCode));
    }

    @Test
    void testIsCodeIncorrect() {
        String testCode = "0000";
        System.out.println("Некорректный код: " + testCode);
        Assertions.assertFalse(Verification.isCodeCorrect(testCode));
    }

    @Test
    void testIsValidateCardDateCorrect() {
        String testDate = "01/24";
        System.out.println("Верная дата: " + testDate);
        Assertions.assertTrue(TransferRepositoryImpl.validateCardDate(testDate));

    }

    @Test
    void testIsValidateCardDateIncorrect() {
        String testDate = "01/20";
        System.out.println("Неверная дата: " + testDate);
        Assertions.assertFalse(TransferRepositoryImpl.validateCardDate(testDate));
    }
}