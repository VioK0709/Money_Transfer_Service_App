package com.example.money_transfer_service_app.controller;

import com.example.money_transfer_service_app.card.Amount;
import com.example.money_transfer_service_app.model.DataTransfer;
import com.example.money_transfer_service_app.model.Verification;
import com.example.money_transfer_service_app.service.TransferServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TransferControllerTest {

    TransferServiceImpl TransferServiceImplMock = Mockito.mock(TransferServiceImpl.class);
    BigDecimal testCardValue = BigDecimal.valueOf(203_345.15);

    DataTransfer testTransferData = new DataTransfer("567789234561", "222222222222", "23/25",
            "675",
            new Amount(556_433, "RUB"));

    BigDecimal transferValue = BigDecimal.valueOf(556_433)
            .setScale(2, RoundingMode.CEILING);
    BigDecimal fee = transferValue.multiply(BigDecimal.valueOf(0.01))
            .setScale(2, RoundingMode.CEILING);
    BigDecimal newValueCardFrom = (testCardValue.subtract(transferValue.multiply(BigDecimal.valueOf(1.01))))
            .setScale(2, RoundingMode.CEILING);

    String testOperationId = "0001";
    String testCode = "3543";
    Verification testVerification = new Verification(testOperationId, testCode);

    @BeforeEach
    public void mockBefore() {
        Mockito.when(TransferServiceImplMock.postTransfer(testTransferData))
                .thenReturn(testOperationId);
        Mockito.when(TransferServiceImplMock.confirmOperation(testVerification))
                .thenReturn(testOperationId);
    }

    @Test
    void testOperationIdTransferController() {
        String expected = testOperationId;
        String result = TransferServiceImplMock.postTransfer(testTransferData);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testOperationIdConfirmController() {
        String expected = testOperationId;
        String result = TransferServiceImplMock.confirmOperation(testVerification);
        Assertions.assertEquals(expected, result);
    }
}