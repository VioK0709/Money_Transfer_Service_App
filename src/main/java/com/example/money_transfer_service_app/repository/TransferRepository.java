package com.example.money_transfer_service_app.repository;

import com.example.money_transfer_service_app.model.DataOperation;
import com.example.money_transfer_service_app.model.DataTransfer;

public interface TransferRepository {

    boolean confirmOperation(String operationId, DataOperation currentDataOperation);

    DataOperation transfer(DataTransfer transferRequest);
}