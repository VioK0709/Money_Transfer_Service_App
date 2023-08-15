package com.example.money_transfer_service_app.service;

import com.example.money_transfer_service_app.model.DataTransfer;
import com.example.money_transfer_service_app.model.Verification;
public interface TransferService {

    String postTransfer(DataTransfer transferRequest);

    String confirmOperation(Verification verification);

}