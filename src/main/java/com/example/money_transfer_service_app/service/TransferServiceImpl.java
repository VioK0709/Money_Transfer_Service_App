package com.example.money_transfer_service_app.service;

import com.example.money_transfer_service_app.exception.ErrorConfirmation;
import com.example.money_transfer_service_app.exception.ErrorInputData;
import com.example.money_transfer_service_app.log.TransferLog;
import com.example.money_transfer_service_app.model.DataOperation;
import com.example.money_transfer_service_app.model.DataTransfer;
import com.example.money_transfer_service_app.model.Verification;
import com.example.money_transfer_service_app.repository.TransferRepository;
import com.example.money_transfer_service_app.repository.TransferRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@AllArgsConstructor
@Service
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final AtomicInteger idNumber = new AtomicInteger(1);
    private Map<String, DataOperation> operationsRepository = new ConcurrentHashMap<>();
    private Map<String, String> verificationRepository = new ConcurrentHashMap<>();
    private final String code = "0000";
    private final Logger log = Logger.getLogger("Service_Logger");

    @Autowired
    public TransferServiceImpl(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    public String postTransfer(DataTransfer transferRequest) {
        String operationId;
        String cardValidTill = transferRequest.getCardFromValidTill();
        if (TransferRepositoryImpl.validateCardDate(cardValidTill)) {
            DataOperation newOperation = transferRepository.transfer(transferRequest);
            if (newOperation != null) {
                operationId = "000" + idNumber.getAndIncrement();
                operationsRepository.put(operationId, newOperation);
                verificationRepository.put(operationId, code);
                TransferLog.createStringLog(operationId, newOperation);
            } else { //или
                log.info("Данные карты введены не верно");
                throw new ErrorInputData("Данные карты введены не верно");
            }
        } else {
            log.info("Срок действия карты истек");
            throw new ErrorInputData("Срок действия карты истек");
        }
        return operationId;
    }

    @Override
    public String confirmOperation(Verification verification) {
        String operationId = verification.getOperationId();
        if (this.code.equals(verification.getCode()))
            return "Операция подтверждена";
        if (verification.getOperationId() != null) {
            return operationId;
        } else {
            log.info("Транзакция отклонена");
            throw new ErrorConfirmation("Транзакция отклонена");
        }
    }
}