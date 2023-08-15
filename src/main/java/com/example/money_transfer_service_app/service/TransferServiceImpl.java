package com.example.money_transfer_service_app.service;

import com.example.money_transfer_service_app.exception.ErrorConfirmation;
import com.example.money_transfer_service_app.log.TransferLog;
import com.example.money_transfer_service_app.model.DataOperation;
import com.example.money_transfer_service_app.model.DataTransfer;
import com.example.money_transfer_service_app.model.Verification;
import com.example.money_transfer_service_app.repository.TransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
@Service
@Slf4j
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final AtomicInteger idNumber = new AtomicInteger(1);
    private Map<String, DataOperation> operationsRepository = new ConcurrentHashMap<>();
    private Map<String, String> verificationRepository = new ConcurrentHashMap<>();
    private final String code = "0000";

    @Autowired
    public TransferServiceImpl(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    public synchronized String postTransfer(DataTransfer transferRequest) {
        String operationId = "000" + idNumber.getAndIncrement();
        DataOperation newOperation = transferRepository.transfer(transferRequest);
        operationsRepository.put(operationId, newOperation);
        verificationRepository.put(operationId, code);
        TransferLog.createStringLog(operationId, newOperation);
        return operationId;
    }

    @Override
    public String confirmOperation(Verification verification) {
        String operationId = verification.getOperationId();
        if (verification.getOperationId() != null && this.code.equals(verification.getCode())) {
            return operationId;
        } else {
            log.info("Транзакция отклонена");
            throw new ErrorConfirmation("Транзакция отклонена");
        }
    }
}