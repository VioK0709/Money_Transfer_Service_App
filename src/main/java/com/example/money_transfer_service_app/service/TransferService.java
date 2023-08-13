package com.example.money_transfer_service_app.service;

import com.example.money_transfer_service_app.model.DataTransfer;

public interface TransferService {

    String postTransfer(DataTransfer transferRequest);

    String confirmOperation(Verification verification);

}